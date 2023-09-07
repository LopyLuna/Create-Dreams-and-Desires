package uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RadiantDrillBlockEntity extends RadiantDrillBlockBreakingKineticBlockEntity {

    public RadiantDrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected BlockPos getBreakingPos() {
        return getBlockPos().relative(getBlockState().getValue(RadiantDrillBlock.FACING));
    }

}
