package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.BlockStressValues;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class FurnaceEngineTileEntity extends EngineTileEntity {

	public FurnaceEngineTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void lazyTick() {
		updateFurnace();
		super.lazyTick();
	}

	public void updateFurnace() {
		BlockState state = level.getBlockState(EngineBlock.getBaseBlockPos(getBlockState(), worldPosition));
		if (!(state.getBlock() instanceof AbstractFurnaceBlock))
			return;

		float modifier = FurnaceEngineModifiers.INSTANCE.getModifier(state);
		boolean active = state.hasProperty(AbstractFurnaceBlock.LIT) && state.getValue(AbstractFurnaceBlock.LIT);
		float speed = active ? 16 * modifier : 0;
		float capacity =
			(float) (active ? BlockStressValues.getCapacity(DDBlocks.FURNACE_ENGINE.get())
				: 0);

		appliedCapacity = capacity;
		appliedSpeed = speed;
		refreshWheelSpeed();
	}


	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
		return super.getCapability(cap);
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
	}

	@Override
	public CompoundTag serializeNBT() {
		return super.serializeNBT();
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void requestModelDataUpdate() {
		super.requestModelDataUpdate();
	}

	@Override
	public @NotNull ModelData getModelData() {
		return super.getModelData();
	}

	@Override
	public boolean hasCustomOutlineRendering(Player player) {
		return super.hasCustomOutlineRendering(player);
	}
}
