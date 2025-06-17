package dev.lopyluna.dndesires.content.blocks.inverse_gearshift;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class InverseGearshiftBE extends SplitShaftBlockEntity {

    public InverseGearshiftBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        return hasSource() ? face != getSourceFacing() ? -1 : 1 : 1;
    }
}
