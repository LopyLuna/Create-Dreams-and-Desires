package uwu.lopyluna.create_dd.content.block.spectral_ruby_lamp;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SpectralRubyLampBlockEntity extends BlockEntity {
	public SpectralRubyLampBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(BlockEntityType.DAYLIGHT_DETECTOR, pPos, pBlockState);
	}
}
