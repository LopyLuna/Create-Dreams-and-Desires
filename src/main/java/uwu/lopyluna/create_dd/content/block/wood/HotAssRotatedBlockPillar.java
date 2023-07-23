package uwu.lopyluna.create_dd.content.block.wood;

import io.github.fabricators_of_create.porting_lib.util.ToolAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.init.DDBlocks;

import javax.annotation.Nullable;

public class HotAssRotatedBlockPillar extends RotatedPillarBlock {
	public HotAssRotatedBlockPillar(Properties pProperties) {
		super(pProperties);
	}

	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return true;
	}

	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 2;
	}

	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 3;
	}

	@Nullable
	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
		if(context.getItemInHand().getItem() instanceof AxeItem) {
			if(state.is(DDBlocks.rose_log.get())) {
				return DDBlocks.stripped_rose_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			if(state.is(DDBlocks.rose_wood.get())) {
				return DDBlocks.stripped_rose_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			if(state.is(DDBlocks.smoked_log.get())) {
				return DDBlocks.stripped_smoked_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			if(state.is(DDBlocks.smoked_wood.get())) {
				return DDBlocks.stripped_smoked_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);
	}
}
