package dev.lopyluna.dndesires.content.blocks.kinetics.creative_gear_motor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CreativeGearMotorBE extends GeneratingKineticBlockEntity {
    public static final int DEFAULT_SPEED = 16;
    public static final int MAX_SPEED = 256;

    protected ScrollValueBehaviour generatedSpeed;

    public CreativeGearMotorBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        int max = MAX_SPEED;
        generatedSpeed = new KineticScrollValueBehaviour(CreateLang.translateDirect("kinetics.creative_motor.rotation_speed"), this, new GearMotorValueBox());
        generatedSpeed.between(-max, max);
        generatedSpeed.value = DEFAULT_SPEED;
        generatedSpeed.withCallback(i -> this.updateGeneratedRotation());
        behaviours.add(generatedSpeed);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();
    }

    @Override
    public float getGeneratedSpeed() {
        if (!DesiresBlocks.CREATIVE_GEAR_MOTOR.has(getBlockState())) return 0;
        return convertToDirection(generatedSpeed.getValue(), getBlockState().getValue(CreativeGearMotorBlock.FACING));
    }

    static class GearMotorValueBox extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 15.5);
        }

        @Override
        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            var facing = state.getValue(CreativeGearMotorBlock.FACING);
            return super.getLocalOffset(level, pos, state).add(Vec3.atLowerCornerOf(facing.getNormal()).scale(0 / 16f));
        }

        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            super.rotate(level, pos, state, ms);
            var facing = state.getValue(CreativeGearMotorBlock.FACING);
            if (facing.getAxis() != Direction.Axis.Y) return;
            if (getSide() == Direction.UP) return;
            TransformStack.of(ms).rotateZDegrees(-AngleHelper.horizontalAngle(facing) + 180);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            var facing = state.getValue(CreativeGearMotorBlock.FACING);
            if (facing.getAxis() == Direction.Axis.Y && direction.getAxis() != Direction.Axis.Y) return false;
            return direction.getAxis() == facing.getAxis();
        }
    }
}