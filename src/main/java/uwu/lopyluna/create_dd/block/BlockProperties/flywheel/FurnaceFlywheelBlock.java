package uwu.lopyluna.create_dd.block.BlockProperties.flywheel;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineTileEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlock;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

public class FurnaceFlywheelBlock extends HorizontalAxisKineticBlock implements IBE<FurnaceFlywheelBlockEntity> {

	public static EnumProperty<ConnectionState> CONNECTION = EnumProperty.create("connection", ConnectionState.class);

	public FurnaceFlywheelBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(CONNECTION, ConnectionState.NONE));
	}

	@Override
	public Class<FurnaceFlywheelBlockEntity> getBlockEntityClass() {
		return FurnaceFlywheelBlockEntity.class;
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return AllShapes.LARGE_GEAR.get(pState.getValue(HORIZONTAL_AXIS));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntityType<? extends FurnaceFlywheelBlockEntity> getBlockEntityType() {
		return DDBlockEntityTypes.FURNACE_FLYWHEEL.get();
	}
	
	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == getRotationAxis(state);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(HORIZONTAL_AXIS);
	}

	@Override
	public float getParticleTargetRadius() {
		return 2f;
	}

	@Override
	public float getParticleInitialRadius() {
		return 1.75f;
	}



	public static boolean isConnected(BlockState state) {
		return getConnection(state) != null;
	}

	public static Direction getConnection(BlockState state) {
		Direction facing = state.getValue(HORIZONTAL_AXIS);
		ConnectionState connection = state.getValue(CONNECTION);

		if (connection == ConnectionState.LEFT)
			return facing.getCounterClockWise();
		if (connection == ConnectionState.RIGHT)
			return facing.getClockWise();
		return null;
	}

	public static void setConnection(Level world, BlockPos pos, BlockState state, Direction direction) {
		Direction facing = state.getValue(HORIZONTAL_AXIS);
		ConnectionState connection = ConnectionState.NONE;

		if (direction == facing.getClockWise())
			connection = ConnectionState.RIGHT;
		if (direction == facing.getCounterClockWise())
			connection = ConnectionState.LEFT;

		world.setBlock(pos, state.setValue(CONNECTION, connection), 18);
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		Direction connection = getConnection(state);
		if (connection == null)
			return super.onWrenched(state ,context);

		if (context.getClickedFace().getAxis() == state.getValue(HORIZONTAL_AXIS))
			return InteractionResult.PASS;

		Level world = context.getLevel();
		BlockPos enginePos = context.getClickedPos().relative(connection, 2);
		BlockState engine = world.getBlockState(enginePos);
		if (engine.getBlock() instanceof FurnaceEngineBlock)
			((FurnaceEngineBlock) engine.getBlock()).withTileEntityDo(world, enginePos, EngineTileEntity::detachWheel);

		return super.onWrenched(state.setValue(CONNECTION, ConnectionState.NONE), context);
	}

	public enum ConnectionState implements ISerializable, StringRepresentable {
		NONE, LEFT, RIGHT;

		@Override
		public String getSerializedName() {
			return Lang.asId(name());
		}
	}
}
