package dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine;

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
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.dndesires.mixins.FurnaceBEAccessor;
import dev.lopyluna.dndesires.register.DesiresBlocks;
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

import static dev.lopyluna.dndesires.register.DesiresTags.BlockTags.*;

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
        movementDirection = new ScrollOptionBehaviour<>(WindmillBearingBlockEntity.RotationDirection.class, CreateLang.translateDirect("contraptions.windmill.rotation_direction"), this, new StirlingEngineValueBox());
        movementDirection.onlyActiveWhen(() -> {
            var flywheel = getFlywheel();
            return flywheel == null || !flywheel.hasSource();
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

        var furnace = getFurnace();
        var flywheel = getFlywheel();

        if (furnace == null || flywheel == null || !isValid()) {
            if (level.isClientSide()) return;
            if (flywheel == null) return;
            if (!flywheel.getBlockPos().subtract(worldPosition).equals(flywheel.enginePos)) return;
            if (flywheel.engineEfficiency == 0) return;
            var facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level.isLoaded(worldPosition.relative(facing.getOpposite()))) flywheel.update(facing.getOpposite(), worldPosition, 0, 0);
            return;
        }

        var shaftState = flywheel.getBlockState();
        var targetAxis = Direction.Axis.X;
        if (shaftState.getBlock() instanceof IRotate ir) targetAxis = ir.getRotationAxis(shaftState);
        var verticalTarget = targetAxis == Direction.Axis.Y;

        var blockState = getBlockState();
        if (!DesiresBlocks.STIRLING_ENGINE.has(blockState)) return;
        var facing = StirlingEngineBlock.getFacing(blockState);
        if (facing.getAxis() == Direction.Axis.Y) facing = blockState.getValue(StirlingEngineBlock.FACING);

        var furnaceState = furnace.getBlockState();
        var multiplier = furnaceState.is(SUPER_STRONG_FURNACE.tag) ? 1.0f : furnaceState.is(STRONG_FURNACE.tag) ? 0.75f :
                furnaceState.is(WEAK_FURNACE.tag) ? 0.25f : 0.5f;

        var access = ((FurnaceBEAccessor) furnace);
        var smelting = access.getCookingProgress$D2D() > 0 && access.getCookingTotalTime$D2D() > 0 ? 1.0f : 0.0f;
        if (smelting > 0) award(AllAdvancements.STEAM_ENGINE);
        if (smelting > 0 && delayedTimer < 6) delayedTimer++;
        else if (delayedTimer > 0) delayedTimer--;
        var efficiency = delayedTimer > 1 ? multiplier : 0.0f;

        int conveyedSpeedLevel = efficiency == 0 ? 1 : verticalTarget ? 1 : (int) GeneratingKineticBlockEntity.convertToDirection(1, facing);
        if (targetAxis == Direction.Axis.Z) conveyedSpeedLevel *= -1;
        if (movementDirection.get() == WindmillBearingBlockEntity.RotationDirection.COUNTER_CLOCKWISE) conveyedSpeedLevel *= -1;

        var shaftSpeed = flywheel.getTheoreticalSpeed();
        if (flywheel.hasSource() && shaftSpeed != 0 && conveyedSpeedLevel != 0 && (shaftSpeed > 0) != (conveyedSpeedLevel > 0)) {
            movementDirection.setValue(1 - movementDirection.get().ordinal());
            conveyedSpeedLevel *= -1;
        }

        flywheel.update(facing.getOpposite(), worldPosition, conveyedSpeedLevel, efficiency);

        if (!level.isClientSide) return;
        CatnipServices.PLATFORM.executeOnClientOnly(() -> this::spawnEffects);
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
        var flywheel = getFlywheel();
        if (flywheel != null) flywheel.remove(worldPosition);
        super.remove();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    public PoweredFlywheelBE getFlywheel() {
        var flywheel = target.get();
        if (flywheel == null || flywheel.isRemoved() || !flywheel.canBePoweredBy(worldPosition)) {
            if (flywheel != null) target = new WeakReference<>(null);
            var facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level != null && (level.getBlockEntity(worldPosition.relative(facing, 2)) instanceof PoweredFlywheelBE ps && ps.canBePoweredBy(worldPosition)))
                target = new WeakReference<>(flywheel = ps);
        }
        return flywheel;
    }

    public AbstractFurnaceBlockEntity getFurnace() {
        var furnace = source.get();
        if (furnace == null || furnace.isRemoved()) {
            if (furnace != null) source = new WeakReference<>(null);
            var facing = StirlingEngineBlock.getFacing(getBlockState());
            if (level != null && level.getBlockEntity(worldPosition.relative(facing.getOpposite())) instanceof AbstractFurnaceBlockEntity be)
                source = new WeakReference<>(furnace = be);
        }
        return furnace;
    }

    public boolean isValid() {
        var level = getLevel();
        if (level == null) return false;
        return level.getBlockEntity(getBlockPos().relative(StirlingEngineBlock.getConnectedDirection(getBlockState()).getOpposite())) instanceof AbstractFurnaceBlockEntity;
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnEffects() {
        assert this.level != null;
        var targetAngle = this.getTargetAngle();
        var ste = this.target.get();
        if (ste == null) return;
        if (!ste.isPoweredBy(worldPosition)) return;
        if (targetAngle == null) return;

        float angle = AngleHelper.deg(targetAngle);
        angle += (angle < 0) ? -105.0F : 285.0F;
        angle %= 360;

        var flywheel = this.getFlywheel();
        if (flywheel == null || flywheel.getSpeed() == 0) return;
        if (angle >= 0 && !(prevAngle > 180 && angle < 180)) {
            prevAngle = angle;
            return;
        }
        if (angle < 0 && !(prevAngle < -180 && angle > -180)) {
            prevAngle = angle;
            return;
        }
        var sourceBE = source.get();
        if (sourceBE != null) {
            var volume = 3.0F / 2;
            var pitch = 0.28F + this.level.random.nextFloat() * 0.1F;
            this.level.playLocalSound(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, volume, pitch, false);
            AllSoundEvents.STEAM.playAt(this.level, this.worldPosition, volume / 16.0F, 0.25F, false);
        }
        prevAngle = angle;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Float getTargetAngle() {
        var angle = 0f;
        var blockState = this.getBlockState();
        if (!DesiresBlocks.STIRLING_ENGINE.has(blockState)) return null;
        var facing = StirlingEngineBlock.getFacing(blockState);
        var flywheel = this.getFlywheel();
        var facingAxis = facing.getAxis();
        var axis = Direction.Axis.Y;
        if (flywheel == null) return null;

        axis = KineticBlockEntityRenderer.getRotationAxisOf(flywheel);
        angle = KineticBlockEntityRenderer.getAngleForBe(flywheel, flywheel.getBlockPos(), axis);

        if (axis == facingAxis) return null;

        if (axis.isHorizontal() && (facingAxis == Direction.Axis.X ^ facing.getAxisDirection() == Direction.AxisDirection.POSITIVE)) angle *= -1f;
        if (axis == Direction.Axis.X && facing == Direction.DOWN) angle *= -1f;
        return angle;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        var flywheel = getFlywheel();
        return flywheel != null && flywheel.addToEngineTooltip(tooltip, isPlayerSneaking);
    }
}
