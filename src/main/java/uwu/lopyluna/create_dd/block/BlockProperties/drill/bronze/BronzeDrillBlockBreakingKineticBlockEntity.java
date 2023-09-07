package uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.DDTags;

public class BronzeDrillBlockBreakingKineticBlockEntity extends BlockBreakingKineticBlockEntity {
    public BronzeDrillBlockBreakingKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected BlockPos getBreakingPos() {return getBlockPos().relative(getBlockState().getValue(BronzeDrillBlock.FACING));}

    @Override
    protected float getBreakSpeed() {return Math.abs(getSpeed() / 40f);}

    @Override
    public boolean canBreak(BlockState stateToBreak, float blockHardness) {return isBreakable(stateToBreak, blockHardness);}

    public static boolean isBreakable(BlockState stateToBreak, float blockHardness) {
        return !(stateToBreak.getMaterial().isLiquid() ||
                stateToBreak.getBlock() instanceof AirBlock ||
                blockHardness == -1 ||
                DDTags.AllBlockTags.bronze_drill_immune.matches(stateToBreak)
        );
    }
}
