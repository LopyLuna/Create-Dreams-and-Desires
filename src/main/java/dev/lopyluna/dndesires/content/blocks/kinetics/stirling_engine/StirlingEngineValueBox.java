package dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.Pointing;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StirlingEngineValueBox extends ValueBoxTransform.Sided {
    @Override
    protected boolean isSideActive(BlockState state, Direction side) {
        var engineFacing = StirlingEngineBlock.getFacing(state);
        if (engineFacing.getAxis() == side.getAxis()) return false;
        var roll = 0;
        for (Pointing p : Pointing.values()) if (p.getCombinedDirection(engineFacing) == side) roll = p.getXRotation();
        if (engineFacing == Direction.UP) roll += 180;
        var recessed = roll % 180 == 0;
        if (engineFacing.getAxis() == Direction.Axis.Y) recessed ^= state.getValue(StirlingEngineBlock.FACING).getAxis() == Direction.Axis.X;
        return !recessed;
    }

    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        var side = getSide();
        var engineFacing = StirlingEngineBlock.getFacing(state);
        float roll = 0;
        for (Pointing p : Pointing.values()) if (p.getCombinedDirection(engineFacing) == side) roll = p.getXRotation();
        if (engineFacing == Direction.UP) roll += 180;
        float horizontalAngle = AngleHelper.horizontalAngle(engineFacing);
        float verticalAngle = AngleHelper.verticalAngle(engineFacing);
        var local = VecHelper.voxelSpace(8, 14.75, 4);
        local = VecHelper.rotateCentered(local, roll, Direction.Axis.Z);
        local = VecHelper.rotateCentered(local, horizontalAngle, Direction.Axis.Y);
        local = VecHelper.rotateCentered(local, verticalAngle, Direction.Axis.X);
        return local;
    }

    @Override
    public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
        var facing = StirlingEngineBlock.getFacing(state);
        if (facing.getAxis() == Direction.Axis.Y) {
            super.rotate(level, pos, state, ms);
            return;
        }
        var roll = 0;
        for (Pointing p : Pointing.values()) if (p.getCombinedDirection(facing) == getSide()) roll = p.getXRotation();
        var yRot = AngleHelper.horizontalAngle(facing) + (facing == Direction.DOWN ? 180 : 0);
        TransformStack.of(ms).rotateYDegrees(yRot).rotateXDegrees(facing == Direction.DOWN ? -90 : 90).rotateYDegrees(roll);
    }

    @Override
    protected Vec3 getSouthLocation() {
        return Vec3.ZERO;
    }

}
