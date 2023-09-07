package uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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
    public boolean canBreak(BlockState stateToBreak, float blockHardness) {return isBreakable(stateToBreak, blockHardness);}

    public static boolean isBreakable(BlockState stateToBreak, float blockHardness) {
        return !(stateToBreak.getMaterial().isLiquid() ||
                stateToBreak.getBlock() instanceof AirBlock ||
                blockHardness == -1
        );
    }
}
