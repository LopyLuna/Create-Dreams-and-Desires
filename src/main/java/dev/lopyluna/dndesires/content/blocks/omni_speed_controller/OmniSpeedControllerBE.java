package dev.lopyluna.dndesires.content.blocks.omni_speed_controller;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;
import com.simibubi.create.compat.computercraft.ComputerCraftProxy;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class OmniSpeedControllerBE extends SplitShaftBlockEntity {
    public static final int DEFAULT_SPEED = 16;

    public ScrollValueBehaviour targetSpeed;
    public AbstractComputerBehaviour computerBehaviour;

    public OmniSpeedControllerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        var max = AllConfigs.server().kinetics.maxRotationSpeed.get();

        targetSpeed = new KineticScrollValueBehaviour(CreateLang.translateDirect("kinetics.speed_controller.rotation_speed"), this, new ControllerValueBoxTransform());
        targetSpeed.between(-max, max);
        targetSpeed.value = DEFAULT_SPEED;
        targetSpeed.withCallback(i -> this.updateTargetRotation());
        behaviours.add(targetSpeed);
        behaviours.add(computerBehaviour = ComputerCraftProxy.behaviour(this));

        registerAwardables(behaviours, AllAdvancements.SPEED_CONTROLLER);
    }

    private void updateTargetRotation() {
        assert level != null;
        if (hasNetwork()) getOrCreateNetwork().remove(this);
        RotationPropagator.handleRemoved(level, worldPosition, this);
        removeSource();
        attachKinetics();
        if (hasSource() && getSpeed() != 0) award(AllAdvancements.SPEED_CONTROLLER);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        computerBehaviour.removePeripheral();
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        return hasSource() ? face != getSourceFacing() ? targetSpeed.value / getSpeed() : 1 : 1;
    }

    private static class ControllerValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8f, 15.5f);
        }
        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return state.getValue(OmniSpeedControllerBlock.AXIS) != direction.getAxis();
        }
        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            var player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
            var side = getSide();
            var vertical = side.getAxis().isVertical();
            var angle = vertical ? player != null ? player.getDirection() : side : side;
            float yRot = AngleHelper.horizontalAngle(angle) + (vertical && player != null ? 0 : 180);
            float xRot = getSide() == Direction.UP ? 90 : getSide() == Direction.DOWN ? 270 : 0;
            TransformStack.of(ms).rotateYDegrees(yRot).rotateXDegrees(xRot);
        }
    }
}
