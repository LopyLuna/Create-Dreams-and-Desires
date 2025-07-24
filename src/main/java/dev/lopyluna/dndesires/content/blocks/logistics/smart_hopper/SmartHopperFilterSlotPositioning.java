package dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SmartHopperFilterSlotPositioning extends ValueBoxTransform.Sided {
    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        return VecHelper.rotateCentered(VecHelper.voxelSpace(8, 13, 15.5f), AngleHelper.horizontalAngle(getSide()), Direction.Axis.Y);
    }
    @Override
    protected boolean isSideActive(BlockState state, Direction direction) {
        return direction.getAxis().isHorizontal();
    }
    @Override
    protected Vec3 getSouthLocation() {
        return Vec3.ZERO;
    }
}
