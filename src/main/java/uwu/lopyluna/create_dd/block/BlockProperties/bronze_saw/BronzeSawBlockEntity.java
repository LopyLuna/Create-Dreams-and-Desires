package uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.foundation.utility.TreeCutter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BronzeSawBlockEntity extends SawBlockEntity {
    public BronzeSawBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new ProcessingInventory(this::start).withSlotLimit(false);
    }

    @Override
    protected BlockPos getBreakingPos() {return getBlockPos().relative(getBlockState().getValue(BronzeDrillBlock.FACING));}

    @Override
    protected float getBreakSpeed() {return Math.abs(getSpeed() / 35f);}

    @Override
    public boolean canBreak(BlockState stateToBreak, float blockHardness) {return isSawable(stateToBreak, blockHardness);}

    public static boolean isSawable(BlockState stateToBreak, float blockHardness) {
        Block block = stateToBreak.getBlock();
        if (!(stateToBreak.getMaterial().isLiquid() ||
                block instanceof AirBlock ||
                blockHardness == -1 ||
                DDTags.AllBlockTags.bronze_drill_immune.matches(stateToBreak)))
            return false;
        if (stateToBreak.is(BlockTags.SAPLINGS))
            return false;
        if (TreeCutter.isLog(stateToBreak) || (stateToBreak.is(BlockTags.LEAVES)))
            return true;
        if (TreeCutter.isRoot(stateToBreak))
            return true;
        if (block instanceof BambooBlock)
            return true;
        if (block instanceof StemGrownBlock)
            return true;
        if (block instanceof CactusBlock)
            return true;
        if (block instanceof SugarCaneBlock)
            return true;
        if (block instanceof KelpPlantBlock)
            return true;
        if (block instanceof KelpBlock)
            return true;
        if (block instanceof ChorusPlantBlock)
            return true;
        if (TreeCutter.canDynamicTreeCutFrom(block))
            return true;
        if (DDTags.AllBlockTags.bronze_saw_valid.matches(stateToBreak))
            return true;
        return false;
    }
}
