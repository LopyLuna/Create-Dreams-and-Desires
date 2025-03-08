package dev.lopyluna.create_d2d.content.blocks.stirling_engine;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.create_d2d.mixins.FurnaceBEAccessor;
import dev.lopyluna.create_d2d.register.DesiresBlocks;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;

import static dev.lopyluna.create_d2d.register.DesiresTags.BlockTags.*;

public class StirlingEngineBE extends SmartBlockEntity implements IHaveGoggleInformation {
    protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;
    public WeakReference<PoweredFlywheelBE> target;
    public WeakReference<AbstractFurnaceBlockEntity> source;
    public float prevAngle = 0;
    public int delayedTimer = 0;

    public StirlingEngineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        source = new WeakReference<>(null);
        target = new WeakReference<>(null);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        movementDirection = new ScrollOptionBehaviour<>(WindmillBearingBlockEntity.RotationDirection.class,
                CreateLang.translateDirect("contraptions.windmill.rotation_direction"), this, new StirlingEngineValueBox());
        movementDirection.onlyActiveWhen(() -> {
            PoweredFlywheelBE shaft = getFlywheel();
            return shaft == null || !shaft.hasSource();
        });
        movementDirection.withCallback($ -> onDirectionChanged());
        behaviours.add(movementDirection);

        registerAwardables(behaviours, AllAdvancements.STEAM_ENGINE);
    }

    private void onDirectionChanged() {
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null) return;

        AbstractFurnaceBlockEntity furnace = getFurnace();
        PoweredFlywheelBE flywheel = getFlywheel();

        if (furnace == null || flywheel == null) {
            if (level.isClientSide()) return;
            if (flywheel == null) return;
            if (!flywheel.getBlockPos().subtract(worldPosition).equals(flywheel.enginePos)) return;
            if (flywheel.engineEfficiency == 0) return;
            Direction facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level.isLoaded(worldPosition.relative(facing.getOpposite()))) flywheel.update(facing.getOpposite(), worldPosition, 0, 0);
            return;
        }

        BlockState shaftState = flywheel.getBlockState();
        Direction.Axis targetAxis = Direction.Axis.X;
        if (shaftState.getBlock() instanceof IRotate ir) targetAxis = ir.getRotationAxis(shaftState);
        boolean verticalTarget = targetAxis == Direction.Axis.Y;

        BlockState blockState = getBlockState();
        if (!DesiresBlocks.STIRLING_ENGINE.has(blockState)) return;
        Direction facing = StirlingEngineBlock.getFacing(blockState);
        if (facing.getAxis() == Direction.Axis.Y) facing = blockState.getValue(StirlingEngineBlock.FACING);

        BlockState furnaceState = furnace.getBlockState();
        float multiplier = furnaceState.is(SUPER_STRONG_FURNACE.tag) ? 1.0f : furnaceState.is(STRONG_FURNACE.tag) ? 0.75f :
                furnaceState.is(WEAK_FURNACE.tag) ? 0.25f : 0.5f;

        var access = ((FurnaceBEAccessor) furnace);
        float smelting = access.getCookingProgress$D2D() > 0 && access.getCookingTotalTime$D2D() > 0 ? 1.0f : 0.0f;
        if (smelting > 0) award(AllAdvancements.STEAM_ENGINE);
        if (smelting > 0 && delayedTimer < 6) delayedTimer++;
        else if (delayedTimer > 0) delayedTimer--;
        float efficiency = delayedTimer > 1 ? multiplier : 0.0f;

        int conveyedSpeedLevel = efficiency == 0 ? 1 : verticalTarget ? 1 : (int) GeneratingKineticBlockEntity.convertToDirection(1, facing);
        if (targetAxis == Direction.Axis.Z) conveyedSpeedLevel *= -1;
        if (movementDirection.get() == WindmillBearingBlockEntity.RotationDirection.COUNTER_CLOCKWISE) conveyedSpeedLevel *= -1;

        float shaftSpeed = flywheel.getTheoreticalSpeed();
        if (flywheel.hasSource() && shaftSpeed != 0 && conveyedSpeedLevel != 0 && (shaftSpeed > 0) != (conveyedSpeedLevel > 0)) {
            movementDirection.setValue(1 - movementDirection.get().ordinal());
            conveyedSpeedLevel *= -1;
        }

        flywheel.update(facing.getOpposite(), worldPosition, conveyedSpeedLevel, efficiency);

        if (!level.isClientSide) return;
        CatnipServices.PLATFORM.executeOnClientOnly(() -> this::spawnParticles);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.putInt("DelayedTimer", delayedTimer);
        super.write(tag, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        delayedTimer = tag.getInt("DelayedTimer");
    }

    @Override
    public void remove() {
        PoweredFlywheelBE flywheel = getFlywheel();
        if (flywheel != null) flywheel.remove(worldPosition);
        super.remove();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    public PoweredFlywheelBE getFlywheel() {
        PoweredFlywheelBE flywheel = target.get();
        if (flywheel == null || flywheel.isRemoved() || !flywheel.canBePoweredBy(worldPosition)) {
            if (flywheel != null) target = new WeakReference<>(null);
            Direction facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level != null && (level.getBlockEntity(worldPosition.relative(facing, 2)) instanceof PoweredFlywheelBE ps && ps.canBePoweredBy(worldPosition)))
                target = new WeakReference<>(flywheel = ps);
        }
        return flywheel;
    }

    public AbstractFurnaceBlockEntity getFurnace() {
        AbstractFurnaceBlockEntity furnace = source.get();
        if (furnace == null || furnace.isRemoved()) {
            if (furnace != null) source = new WeakReference<>(null);
            Direction facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level != null && level.getBlockEntity(worldPosition.relative(facing.getOpposite())) instanceof AbstractFurnaceBlockEntity furnaceBe)
                source = new WeakReference<>(furnace = furnaceBe);
        }
        return furnace;
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnParticles() {
        Float targetAngle = this.getTargetAngle();
        PoweredFlywheelBE ste = this.target.get();
        if (ste != null) if (ste.isPoweredBy(this.worldPosition)) if (targetAngle != null) {
            float angle = AngleHelper.deg((double) targetAngle);
            angle += angle < 0.0F ? -105.0F : 285.0F;
            angle %= 360.0F;
            PoweredFlywheelBE flywheel = this.getFlywheel();
            if (flywheel != null && flywheel.getSpeed() != 0.0F) {
                if (!(angle >= 0.0F) || this.prevAngle > 180.0F && angle < 180.0F) {
                    if (!(angle < 0.0F) || (this.prevAngle < -180.0F && angle > -180.0F)) {
                        AbstractFurnaceBlockEntity sourceBE = this.source.get();
                        if (sourceBE != null) {
                            float volume = 3.0F / 2;
                            assert this.level != null;
                            float pitch = 0.28F + this.level.random.nextFloat() * 0.1F;
                            this.level.playLocalSound(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, volume, pitch, false);
                            AllSoundEvents.STEAM.playAt(this.level, this.worldPosition, volume / 16.0F, 0.25F, false);
                        }
                    }
                }
                this.prevAngle = angle;
            }
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Float getTargetAngle() {
        float angle;
        BlockState blockState = this.getBlockState();
        if (DesiresBlocks.STIRLING_ENGINE.has(blockState)) {
            Direction facing = StirlingEngineBlock.getFacing(blockState);
            PoweredFlywheelBE flywheel = this.getFlywheel();
            Direction.Axis facingAxis = facing.getAxis();
            Direction.Axis axis;
            if (flywheel != null) {
                axis = KineticBlockEntityRenderer.getRotationAxisOf(flywheel);
                angle = KineticBlockEntityRenderer.getAngleForBe(flywheel, flywheel.getBlockPos(), axis);
                if (axis != facingAxis) {
                    if (axis.isHorizontal() && facingAxis == Direction.Axis.X ^ facing.getAxisDirection() == Direction.AxisDirection.POSITIVE) angle *= -1.0F;
                    if (axis == Direction.Axis.X && facing == Direction.DOWN) angle *= -1.0F;
                    return angle;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        PoweredFlywheelBE flywheel = getFlywheel();
        return flywheel != null && flywheel.addToEngineTooltip(tooltip, isPlayerSneaking);
    }
}
