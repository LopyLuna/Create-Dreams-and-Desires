package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FurnaceFlywheelBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FurnaceFlywheelBlockEntity;
import uwu.lopyluna.create_dd.block.DDBlocks;

import java.util.List;

public class EngineTileEntity extends KineticBlockEntity {

	public float appliedCapacity;
	public float appliedSpeed;
	protected FurnaceFlywheelBlockEntity poweredWheel;

	public EngineTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}


	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
	}

	protected AABB cachedBoundingBox;
	@Override
	@OnlyIn(Dist.CLIENT)
	public AABB getRenderBoundingBox() {
		if (cachedBoundingBox == null) {
			cachedBoundingBox = super.getRenderBoundingBox().inflate(1.5f);
		}
		return cachedBoundingBox;
	}

	@Override
	public void lazyTick() {
		super.lazyTick();
		if (level.isClientSide)
			return;
		if (poweredWheel != null && poweredWheel.isRemoved())
			poweredWheel = null;
		if (poweredWheel == null)
			attachWheel();
	}

	public void attachWheel() {
		Direction engineFacing = getBlockState().getValue(EngineBlock.FACING);
		BlockPos wheelPos = worldPosition.relative(engineFacing, 2);
		BlockState wheelState = level.getBlockState(wheelPos);
		if (!DDBlocks.FURNACE_FLYWHEEL.has(wheelState))
			return;
		Direction.Axis wheelFacing = wheelState.getValue(FurnaceFlywheelBlock.HORIZONTAL_AXIS);
		if (wheelFacing != engineFacing.getClockWise().getAxis())
			return;
		if (FurnaceFlywheelBlock.isConnected(wheelState)
				&& FurnaceFlywheelBlock.getConnection(wheelState) != engineFacing.getOpposite())
			return;
		BlockEntity te = level.getBlockEntity(wheelPos);
		if (te.isRemoved())
			return;
		if (te instanceof FurnaceFlywheelBlockEntity) {
			if (!FurnaceFlywheelBlock.isConnected(wheelState))
				FurnaceFlywheelBlock.setConnection(level, te.getBlockPos(), te.getBlockState(), engineFacing.getOpposite());
			poweredWheel = (FurnaceFlywheelBlockEntity) te;
			refreshWheelSpeed();
		}
	}

	public void detachWheel() {
		if (poweredWheel == null || poweredWheel.isRemoved())
			return;
		poweredWheel.setRotation(0, 0);
		FurnaceFlywheelBlock.setConnection(level, poweredWheel.getBlockPos(), poweredWheel.getBlockState(), null);
		poweredWheel = null;
	}

	@Override
	public void setRemoved() {
		detachWheel();
		super.setRemoved();
	}

	protected void refreshWheelSpeed() {
		if (poweredWheel == null)
			return;
		poweredWheel.setRotation(appliedSpeed, appliedCapacity);
	}


}
