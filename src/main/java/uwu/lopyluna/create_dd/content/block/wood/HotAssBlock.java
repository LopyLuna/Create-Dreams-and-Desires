package uwu.lopyluna.create_dd.content.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class HotAssBlock extends Block {
	public HotAssBlock(Properties pProperties) {
		super(pProperties);
	}

	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return true;
	}

	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 10;
	}

	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 3;
	}
}
