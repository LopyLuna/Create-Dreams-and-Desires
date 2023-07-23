package uwu.lopyluna.create_dd.content.block.wood;

import io.github.fabricators_of_create.porting_lib.util.ToolAction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.init.DDBlocks;

import javax.annotation.Nullable;

public class SpiritLogRotatedBlockPillar extends RotatedPillarBlock {
	public SpiritLogRotatedBlockPillar(Properties pProperties) {
		super(pProperties);
	}

	@Nullable
	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
		if(context.getItemInHand().getItem() instanceof AxeItem) {
			if(state.is(DDBlocks.spirit_log.get())) {
				return DDBlocks.stripped_spirit_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			if(state.is(DDBlocks.spirit_wood.get())) {
				return DDBlocks.stripped_spirit_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);
	}
}
