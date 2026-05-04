package dev.lopyluna.dndesires.mixins.compat.sable;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.particle.AirParticleData;
import dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry.SentryItemHandler;
import dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry.SpudSentryBE;
import dev.lopyluna.dndesires.mixins.PotatoProjectileEntityAccessor;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpudSentryBE.class)
public abstract class SpudSentryBEMixin extends KineticBlockEntity {
    @Shadow public Vec3 centerPos;
    @Shadow public AABB aabbRadius;
    @Shadow public int abRadius;
    @Shadow public int radius;
    @Shadow public int timer;
    @Shadow public float xRot;
    @Shadow public Player owner;
    @Final @Shadow public LerpedFloat lerpX;
    @Shadow public SentryItemHandler inputInv;

    @Shadow public abstract ItemStack getProjectile(ItemStack input);
    @Shadow public abstract @Nullable PotatoCannonItem.Ammo getAmmo(Level level, ItemStack ammoStack);
    @Shadow public abstract Vec3 getLookAngle();

    protected SpudSentryBEMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique
    private @Nullable SubLevel DnDesires$getSubLevel() {
        if (level == null) return null;
        return Sable.HELPER.getContaining(this);
    }

    @Inject(method = "getTargetPos", at = @At("HEAD"), cancellable = true)
    private void DnDesires$getTargetPos(LivingEntity entity, CallbackInfoReturnable<Vec3> cir) {
        final SubLevel subLevel = DnDesires$getSubLevel();
        if (subLevel == null) return;

        final Vec3 targetPos = entity.getBoundingBox().getCenter().add(entity.getEyePosition()).scale(0.5);
        cir.setReturnValue(subLevel.logicalPose().transformPositionInverse(targetPos));
    }

    @Inject(method = "updateRadius", at = @At("HEAD"), cancellable = true)
    private void DnDesires$updateRadius(CallbackInfo ci) {
        if (level == null) return;

        final SubLevel subLevel = DnDesires$getSubLevel();
        if (subLevel == null) return;

        aabbRadius = AABB.ofSize(Sable.HELPER.projectOutOfSubLevel(level, centerPos), radius * 2, radius * 2, radius * 2);
        abRadius = radius;

        ci.cancel();
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void DnDesires$attack(Level level, ItemStack inputStack, CallbackInfo ci) {
        final SubLevel subLevel = DnDesires$getSubLevel();
        if (subLevel == null) return;

        final ItemStack projectileInput = getProjectile(inputStack);
        if (projectileInput.isEmpty()) {
            ci.cancel();
            return;
        }

        final PotatoCannonItem.Ammo ammo = getAmmo(level, projectileInput);
        if (ammo == null || ammo.stack().isEmpty()) {
            ci.cancel();
            return;
        }

        final ItemStack ammoStack = ammo.stack();
        final PotatoCannonProjectileType projectileType = ammo.type();
        timer = projectileType.reloadTicks() * 8;

        final Vec3 lookVec = getLookAngle();
        final Vec3 origin = centerPos;
        final Vec3 barrelPosLocal = origin.add(lookVec.normalize().scale(1 + 8 / 16f)).subtract(0, 2 / 16f, 0);
        final Vec3 correction = barrelPosLocal.subtract(origin);
        final Vec3 motionLocal = lookVec.add(correction).normalize().scale(2).scale(projectileType.velocityMultiplier());
        final Vec3 barrelPos = subLevel.logicalPose().transformPosition(barrelPosLocal);
        final Vec3 motionWorld = subLevel.logicalPose().transformNormal(motionLocal);
        final Vec3 motionC = motionWorld.normalize();
        final float soundPitch = projectileType.soundPitch() + (level.getRandom().nextFloat() - .5f) / 4f;

        final Vec3 m = VecHelper.offsetRandomly(motionC.scale(0.1f), level.random, .025f);
        level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, ammoStack), barrelPos.x, barrelPos.y, barrelPos.z, m.x, m.y, m.z);
        final Vec3 m2 = VecHelper.offsetRandomly(motionC.scale(2f), level.random, .5f);
        level.addParticle(new AirParticleData(1, 1 / 4f), barrelPos.x, barrelPos.y, barrelPos.z, m2.x, m2.y, m2.z);

        AllSoundEvents.FWOOMP.playAt(level, barrelPos, 1, soundPitch, true);

        if (level.isClientSide) {
            lerpX.chase(xRot - 11.25, 1, LerpedFloat.Chaser.EXP);
            lerpX.tickChaser();
            ci.cancel();
            return;
        }

        final boolean spray = projectileType.split() > 1;
        final Vec3 sprayBase = VecHelper.rotate(new Vec3(0, 0.1, 0), 360 * level.getRandom().nextFloat(), Direction.Axis.Z);
        final float sprayChange = 360f / projectileType.split();
        final ItemStack ammoStackCopy = ammoStack.copy();
        final Vec3 launchVelocity = Sable.HELPER.getVelocity(level, subLevel, barrelPosLocal);

        for (int i = 0; i < projectileType.split(); i++) {
            final PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(level);
            if (projectile == null) continue;
            projectile.setItem(ammoStackCopy);

            Vec3 splitMotionLocal = motionLocal;
            if (spray) {
                final float imperfection = 40 * (level.getRandom().nextFloat() - 0.5f);
                final Vec3 sprayOffset = VecHelper.rotate(sprayBase, i * sprayChange + imperfection, Direction.Axis.Z);
                splitMotionLocal = splitMotionLocal.add(VecHelper.lookAt(sprayOffset, motionLocal));
            }

            if (i != 0) ((PotatoProjectileEntityAccessor) projectile).recoveryChance(0);

            projectile.setPos(barrelPos.x, barrelPos.y, barrelPos.z);
            projectile.setDeltaMovement(subLevel.logicalPose().transformNormal(splitMotionLocal).add(launchVelocity));
            projectile.setOwner(owner);
            level.addFreshEntity(projectile);
        }

        inputInv.extractItem(0, 1, false);
        ci.cancel();
    }
}
