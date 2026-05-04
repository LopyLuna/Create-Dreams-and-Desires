package dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.*;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.mixin.accessor.ItemStackHandlerAccessor;
import com.simibubi.create.foundation.particle.AirParticleData;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.lopyluna.dndesires.mixins.PotatoProjectileEntityAccessor;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import dev.lopyluna.dndesires.register.DesiresLangPartial;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

public class SpudSentryBE extends KineticBlockEntity implements Clearable {
    public static final BiFunction<Player, Integer, TargetingConditions> SELECTOR = (owner, radius) -> TargetingConditions.forCombat().range(radius).selector(
            living -> !living.is(owner)
                    && living.isAttackable()
                    && living.hurtTime <= 0
                    && living.isAlive()
                    && !living.isInvulnerable()
                    && !(living instanceof Breeze)
                    && !(living instanceof ArmorStand)
    );

    public SentryItemHandler inputInv;
    public UUID ownerUUID;
    public Player owner;
    public LivingEntity target;
    public int timer;
    public float yRot;
    public float xRot;
    public int radius;
    private long lastAimSyncTick = Long.MIN_VALUE;
    private static final float MIN_AIM_STEP = 3.0f;
    private static final float MAX_AIM_STEP = 18.0f;
    private static final float AIM_SPEED_MULTIPLIER = 0.25f;
    private static final float FIRE_ALIGNMENT_THRESHOLD = 4.0f;

    public final LerpedFloat lerpX = LerpedFloat.angular();
    public final LerpedFloat lerpY = LerpedFloat.angular();

    public Vec3 centerPos;

    public TargetingConditions selector;
    public Player selOwner;
    public int selRadius;

    public AABB aabbRadius;
    public int abRadius;

    protected ScrollOptionBehaviour<TargetMode> targetMode;
    protected ScrollOptionBehaviour<FilterMode> filterMode;

    public SpudSentryBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inputInv = new SentryItemHandler(this);
        radius = 25;
        centerPos = pos.above().getCenter();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DesiresBETypes.SPUD_SENTRY.get(), (be, dir) -> be.inputInv);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        var targetMode = new ScrollOptionBehaviourDouble<>(TargetMode.class, false,
                DesiresLangPartial.translateDirect("spud_sentry.target_mode"), this, new TargetModeValueBox());
        targetMode.withCallback($ -> target = null);
        behaviours.add(this.targetMode = targetMode);

        var filterMode = new ScrollOptionBehaviourDouble<>(FilterMode.class, true,
                DesiresLangPartial.translateDirect("spud_sentry.filter_mode"), this, new FilterModeValueBox());
        filterMode.withCallback($ -> target = null);
        behaviours.add(this.filterMode = filterMode);
    }

    @Override
    public void tick() {
        super.tick();
        if (level instanceof ServerLevel server && inputInv.level == null) inputInv.level = server;
        if (level == null) return;

        if (level.isClientSide) {
            var speed = (Math.abs(getSpeed()) / AllConfigs.server().kinetics.maxRotationSpeed.get()) * 0.85f + 0.35f;
            lerpX.chase(xRot, speed, LerpedFloat.Chaser.EXP);
            lerpY.chase(yRot, speed, LerpedFloat.Chaser.EXP);
            var xRotV = lerpX.getValue();
            var yRotV = lerpY.getValue();
            var scalar = 360 * 32;
            if (xRotV >= 360 + scalar) xRotV -= 360 + scalar;
            if (xRotV < -scalar) xRotV += 360 + scalar;
            if (yRotV >= 360 + scalar) yRotV -= 360 + scalar;
            if (yRotV < -scalar) yRotV += 360 + scalar;

            if (lerpX.getValue() != xRotV) lerpX.setValue(xRotV);
            if (lerpY.getValue() != yRotV) lerpY.setValue(yRotV);
            
            lerpX.tickChaser();
            lerpY.tickChaser();
            return;
        }

        if (getSpeed() == 0) return;

        if (target != null && owner != null) {
            var origin = centerPos;
            var input = getInput();
            boolean hasAmmo = !input.isEmpty();
            if (target != null && !isValidTarget(target, origin)) target = null;
            if (target != null && hasAmmo) {
                float prevXRot = xRot;
                float prevYRot = yRot;
                aim();
                syncAimIfChanged(prevXRot, prevYRot);

                if (timer > 0) timer -= getProcessingSpeed();
                if (timer <= 0 && isAimedAtTarget()) attack(level, input);
            }
        }
        if (level.getGameTime() % 2 == 0) {
            if (owner == null && ownerUUID != null) owner = level.getPlayerByUUID(ownerUUID);
            updateSelector();
            updateRadius();

            if (owner == null || selector == null || aabbRadius == null) return;
            if (getInput().isEmpty()) return;
            var origin = centerPos;


            double minDistSqr = 2.5;
            minDistSqr *= minDistSqr;
            double slope = 1;
            var mode = getTargetMode();

            if (mode == TargetMode.RANDOM && target != null && isValidTarget(target, origin)) return;

            LivingEntity best = null;
            double bestDist = 0;
            float bestHealth = 0;
            int candidates = 0;

            for (var entity :  level.getNearbyEntities(LivingEntity.class, selector, owner, aabbRadius)) {
                var targetPos = getTargetPos(entity);

                double dx = targetPos.x - origin.x;
                double dy = targetPos.y - origin.y;
                double dz = targetPos.z - origin.z;

                double horizontalDist = Math.sqrt(dx * dx + dz * dz);
                double distSqr = dx * dx + dy * dy + dz * dz;

                if (minDistSqr > distSqr) continue;
                if (Math.abs(dy) > horizontalDist * slope) continue;
                if (!hasLineOfSight(origin, targetPos, entity)) continue;
                if (!matchesFilterMode(entity)) continue;

                switch (mode) {
                    case RANDOM -> {
                        candidates++;
                        if (level.random.nextInt(candidates) == 0) best = entity;
                    }
                    case FARTHEST -> {
                        if (best == null || distSqr > bestDist) {
                            best = entity;
                            bestDist = distSqr;
                        }
                    }
                    case LEAST_HEALTH -> {
                        float health = entity.getHealth();
                        if (best == null || health < bestHealth || (health == bestHealth && distSqr < bestDist)) {
                            best = entity;
                            bestDist = distSqr;
                            bestHealth = health;
                        }
                    }
                    case MOST_HEALTH -> {
                        float health = entity.getHealth();
                        if (best == null || health > bestHealth || (health == bestHealth && distSqr < bestDist)) {
                            best = entity;
                            bestDist = distSqr;
                            bestHealth = health;
                        }
                    }
                    case CLOSEST -> {
                        if (best == null || distSqr < bestDist) {
                            best = entity;
                            bestDist = distSqr;
                        }
                    }
                }
            }
            target = best;
            if (target != null) {
                float prevXRot = xRot;
                float prevYRot = yRot;
                aim();
                syncAimIfChanged(prevXRot, prevYRot);
            }
        }
    }

    public void syncAimIfChanged(float prevXRot, float prevYRot) {
        if (level == null || level.isClientSide) return;
        if (Mth.degreesDifferenceAbs(prevXRot, xRot) < 0.5f && Mth.degreesDifferenceAbs(prevYRot, yRot) < 0.5f) return;
        if (lastAimSyncTick == level.getGameTime()) return;
        lastAimSyncTick = level.getGameTime();
        sendData();
    }

    public boolean isValidTarget(LivingEntity entity, Vec3 origin) {
        if (owner == null) return false;
        if (!matchesFilterMode(entity)) return false;

        var targetPos = getTargetPos(entity);
        double dx = targetPos.x - origin.x;
        double dy = targetPos.y - origin.y;
        double dz = targetPos.z - origin.z;
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        double distSqr = dx * dx + dy * dy + dz * dz;
        double minDistSqr = 2.5 * 2.5;

        return distSqr >= minDistSqr
                && Math.abs(dy) <= horizontalDist
                && hasLineOfSight(origin, targetPos, entity);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean matchesFilterMode(LivingEntity entity) {
        if (owner == null) return false;
        if (entity.is(owner)
                || !entity.isAttackable()
                || entity.hurtTime > 0
                || !entity.isAlive()
                || entity.isInvulnerable()
                || entity instanceof Breeze
                || entity instanceof ArmorStand
                || isOwnersTeammate(entity))
            return false;

        return switch (getFilterMode()) {
            case FOES -> entity instanceof Enemy;
            case PASSIVE -> entity instanceof Mob && !(entity instanceof Enemy);
            case NEUTRAL -> entity instanceof Mob;
            case PLAYERS -> entity instanceof Player;
            case ANYTHING -> true;
        };
    }

    public boolean isOwnersTeammate(LivingEntity entity) {
        return entity.isAlliedTo(owner) || owner.isAlliedTo(entity);
    }

    public TargetMode getTargetMode() {
        return targetMode == null ? TargetMode.CLOSEST : targetMode.get();
    }

    public FilterMode getFilterMode() {
        return filterMode == null ? FilterMode.FOES : filterMode.get();
    }

    public void aim() {
        if (target == null) return;
        float desiredXRot = getDesiredXRot(target);
        float desiredYRot = getDesiredYRot(target);
        float aimStep = getAimStep();

        xRot = Mth.clamp(approachAngle(xRot, desiredXRot, aimStep), -45, 45);
        yRot = Mth.wrapDegrees(approachAngle(yRot, desiredYRot, aimStep * 1.25f));
    }

    public boolean isAimedAtTarget() {
        if (target == null) return false;
        return Mth.degreesDifferenceAbs(xRot, getDesiredXRot(target)) <= FIRE_ALIGNMENT_THRESHOLD
                && Mth.degreesDifferenceAbs(yRot, getDesiredYRot(target)) <= FIRE_ALIGNMENT_THRESHOLD;
    }

    public float getDesiredXRot(LivingEntity entity) {
        var pos = centerPos;
        var target = getAimingTargetPos(entity, pos);
        double d0 = target.x - pos.x, d1 = target.y - pos.y, d2 = target.z - pos.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        return Mth.clamp(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * 180.0F / (float)Math.PI))), -45, 45);
    }

    public float getDesiredYRot(LivingEntity entity) {
        var pos = centerPos;
        var target = getAimingTargetPos(entity, pos);
        double d0 = target.x - pos.x, d2 = target.z - pos.z;

        return Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F);
    }

    public Vec3 getAimingTargetPos(LivingEntity entity, Vec3 origin) {
        var target = getTargetPos(entity);
        return target.add(0, Math.sqrt(target.distanceToSqr(origin)) * 0.1, 0);
    }

    public float getAimStep() {
        return Mth.clamp(getProcessingSpeed() * 2.0f, MIN_AIM_STEP, MAX_AIM_STEP) * AIM_SPEED_MULTIPLIER;
    }

    public float approachAngle(float current, float target, float maxStep) {
        return current + Mth.clamp(Mth.wrapDegrees(target - current), -maxStep, maxStep);
    }

    public void attack(Level pLevel, ItemStack pInput) {
        var input = getProjectile(pInput);
        if (input.isEmpty()) return;
        var ammo = getAmmo(pLevel, input);
        if (ammo == null) return;

        ItemStack ammoStack = ammo.stack();
        if (ammoStack.isEmpty()) return;
        PotatoCannonProjectileType projectileType = ammo.type();
        //timer = projectileType.reloadTicks();
        timer = projectileType.reloadTicks() * 8;

        Vec3 lookVec = getLookAngle();
        Vec3 origin = centerPos;


        Vec3 barrelPos = origin.add(lookVec.normalize().scale(1+8/16f)).subtract(0, 2/16f, 0);
        Vec3 correction = barrelPos.subtract(origin);

        Vec3 motion = lookVec.add(correction).normalize().scale(2).scale(projectileType.velocityMultiplier());

        float soundPitch = projectileType.soundPitch() + (pLevel.getRandom().nextFloat() - .5f) / 4f;
        var motionC = lookVec.normalize();

        Vec3 m = VecHelper.offsetRandomly(motionC.scale(0.1f), pLevel.random, .025f);
        pLevel.addParticle(new ItemParticleOption(ParticleTypes.ITEM, ammoStack), barrelPos.x, barrelPos.y, barrelPos.z, m.x, m.y, m.z);
        Vec3 m2 = VecHelper.offsetRandomly(motionC.scale(2f), pLevel.random, .5f);
        pLevel.addParticle(new AirParticleData(1, 1 / 4f), barrelPos.x, barrelPos.y, barrelPos.z, m2.x, m2.y, m2.z);

        AllSoundEvents.FWOOMP.playAt(pLevel, barrelPos, 1, soundPitch, true);

        if (pLevel.isClientSide) {
            lerpX.chase(xRot - 11.25, 1, LerpedFloat.Chaser.EXP);
            lerpX.tickChaser();
            return;
        }

        boolean spray = projectileType.split() > 1;
        Vec3 sprayBase = VecHelper.rotate(new Vec3(0, 0.1, 0), 360 * pLevel.getRandom().nextFloat(), Direction.Axis.Z);
        float sprayChange = 360f / projectileType.split();

        ItemStack ammoStackCopy = ammoStack.copy();

        for (int i = 0; i < projectileType.split(); i++) {
            PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(pLevel);
            if (projectile == null) continue;
            projectile.setItem(ammoStackCopy);
            //projectile.setEnchantmentEffectsFromCannon(heldStack);

            Vec3 splitMotion = motion;
            if (spray) {
                float imperfection = 40 * (pLevel.getRandom().nextFloat() - 0.5f);
                Vec3 sprayOffset = VecHelper.rotate(sprayBase, i * sprayChange + imperfection, Direction.Axis.Z);
                splitMotion = splitMotion.add(VecHelper.lookAt(sprayOffset, motion));
            }

            if (i != 0) ((PotatoProjectileEntityAccessor) projectile).recoveryChance(0);

            projectile.setPos(barrelPos.x, barrelPos.y, barrelPos.z);
            projectile.setDeltaMovement(splitMotion);
            projectile.setOwner(owner);
            pLevel.addFreshEntity(projectile);
        }
        inputInv.extractItem(0, 1, false);
    }

    public void updateSelector() {
        if (owner == null) return;
        if (selRadius == radius && selOwner != null && selOwner.getUUID().equals(owner.getUUID())) return;
        selector = SELECTOR.apply(owner, radius);
        selOwner = owner;
        selRadius = radius;
    }

    public void updateRadius() {
        if (abRadius == radius) return;
        aabbRadius = AABB.ofSize(centerPos, radius * 2, radius * 2, radius * 2);
        abRadius = radius;
    }

    public Vec3 getLookAngle() {
        return calculateViewVector(xRot, yRot);
    }

    public Vec3 getTargetPos(LivingEntity entity) {
        return entity.getBoundingBox().getCenter().add(entity.getEyePosition()).scale(0.5);
    }

    public boolean hasLineOfSight(Vec3 origin, Vec3 targetPos, LivingEntity entity) {
        if (level == null) return false;
        var hitResult = level.clip(new ClipContext(origin, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        return hitResult.getType() == HitResult.Type.MISS;
    }

    public boolean canInsert(Level level, ItemStack toInsert) {
        return getAmmo(level, toInsert) != null;
    }

    @Nullable
    public PotatoCannonItem.Ammo getAmmo(Level level, ItemStack ammoStack) {
        if (ammoStack.isEmpty()) return null;
        return PotatoCannonProjectileType.getTypeForItem(level.registryAccess(), ammoStack.getItem())
                .map(r -> new PotatoCannonItem.Ammo(ammoStack, r.value())).orElse(null);
    }

    public ItemStack getProjectile(ItemStack input) {
        var shootable = AllItems.POTATO_CANNON.asStack();
        if (((ProjectileWeaponItem)shootable.getItem()).getSupportedHeldProjectiles(shootable).test(input)) return input;
        return ItemStack.EMPTY;
    }

    public ItemStack getInput() {
        return inputInv.getStackInSlot(0);
    }

    @Override
    public void clearContent() {
        ((ItemStackHandlerAccessor) inputInv).create$getStacks().clear();
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inputInv);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        if (ownerUUID != null) compound.putUUID("OwnerUUID", ownerUUID);
        compound.putInt("Timer", timer);
        compound.putInt("Radius", radius);
        compound.putFloat("xRot", xRot);
        compound.putFloat("yRot", yRot);
        compound.put("InputInventory", inputInv.serializeNBT(registries));
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        if (compound.contains("OwnerUUID")) ownerUUID = compound.getUUID("OwnerUUID");
        timer = compound.getInt("Timer");
        radius = compound.getInt("Radius");
        xRot = compound.getFloat("xRot");
        yRot = compound.getFloat("yRot");
        inputInv.deserializeNBT(registries, compound.getCompound("InputInventory"));
        super.read(compound, registries, clientPacket);
    }

    public int getProcessingSpeed() {
        return Mth.clamp((int) Math.abs(getSpeed() / 16f), 1, 512);
    }

    public final Vec3 calculateViewVector(float xRot, float yRot) {
        float f = xRot * (float) (Math.PI / 180.0), f1 = -yRot * (float) (Math.PI / 180.0);
        float f2 = Mth.cos(f1), f3 = Mth.sin(f1), f4 = Mth.cos(f), f5 = Mth.sin(f);
        return new Vec3(f3 * f4, -f5, f2 * f4);
    }

    private static class TargetModeValueBox extends CenteredSideValueBoxTransform {
        public TargetModeValueBox() { super((blockState, direction) -> !direction.getAxis().isVertical()); }
        @Override public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            return VecHelper.rotateCentered(VecHelper.voxelSpace(11, 3, 15.5), AngleHelper.horizontalAngle(getSide()), Direction.Axis.Y);
        }
    }
    private static class FilterModeValueBox extends CenteredSideValueBoxTransform {
        public FilterModeValueBox() { super((blockState, direction) -> !direction.getAxis().isVertical()); }
        @Override public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            return VecHelper.rotateCentered(VecHelper.voxelSpace(5, 3, 15.5), AngleHelper.horizontalAngle(getSide()), Direction.Axis.Y);
        }
    }


    public static class ScrollOptionBehaviourDouble<E extends Enum<E> & INamedIconOptions> extends ScrollOptionBehaviour<E> {
        public static final BehaviourType<ScrollOptionBehaviourDouble<? extends Enum<?>>> TYPE = new BehaviourType<>();
        public static final BehaviourType<ScrollOptionBehaviourDouble<? extends Enum<?>>> ALT_TYPE = new BehaviourType<>();

        private final boolean alt;
        private final E[] options;

        public ScrollOptionBehaviourDouble(Class<E> enum_, boolean alt, Component label, SmartBlockEntity be, ValueBoxTransform slot) {
            super(enum_, label, be, slot);
            this.alt = alt;
            options = enum_.getEnumConstants();
            between(0, options.length - 1);
        }

        public E get() {
            return options[value];
        }

        @Override
        public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
            return new ValueSettingsBoard(label, max, 1, ImmutableList.of(Component.literal(alt ? "SelectAlt" : "Select")),
                    new ValueSettingsFormatter.ScrollOptionSettingsFormatter(options));
        }

        @Override
        public String getClipboardKey() {
            return options[0].getClass().getSimpleName();
        }

        @Override
        public int netId() {
            return alt ? 1 : 2;
        }

        @Override
        public void write(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
            if (!alt) super.write(nbt, registries, clientPacket);
            else nbt.putInt("ScrollValueAlt", value);
        }

        @Override
        public void read(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
            if (!alt) super.read(nbt, registries, clientPacket);
            else value = nbt.getInt("ScrollValueAlt");
        }

        @Override
        public BehaviourType<?> getType() {
            return alt ? ALT_TYPE : TYPE;
        }
    }

    public enum TargetMode implements INamedIconOptions {
        CLOSEST(AllIcons.I_PRIORITY_LOW),
        FARTHEST(AllIcons.I_PRIORITY_HIGH),
        LEAST_HEALTH(AllIcons.I_PRIORITY_VERY_LOW),
        MOST_HEALTH(AllIcons.I_PRIORITY_VERY_HIGH),
        RANDOM(AllIcons.I_PATTERN_CHANCE_25),
        ;
        private final String translationKey;
        private final AllIcons icon;

        TargetMode(AllIcons icon) {
            this.icon = icon;
            this.translationKey = MOD_ID + ".spud_sentry.target_mode." + Lang.asId(name());
        }
        @Override public AllIcons getIcon() { return icon; }
        @Override public String getTranslationKey() { return translationKey; }
    }

    public enum FilterMode implements INamedIconOptions {
        FOES(AllIcons.I_IGNORE_NBT),
        PASSIVE(AllIcons.I_PASSIVE),
        NEUTRAL(AllIcons.I_FX_FIELD_ON),
        PLAYERS(AllIcons.I_FX_SURFACE_ON),
        ANYTHING(AllIcons.I_ACTIVE),
        ;
        private final String translationKey;
        private final AllIcons icon;

        FilterMode(AllIcons icon) {
            this.icon = icon;
            this.translationKey = MOD_ID + ".spud_sentry.filter_mode." + Lang.asId(name());
        }
        @Override public AllIcons getIcon() { return icon; }
        @Override public String getTranslationKey() { return translationKey; }
    }
}
