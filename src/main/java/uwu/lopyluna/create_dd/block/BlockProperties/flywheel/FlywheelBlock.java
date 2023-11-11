package uwu.lopyluna.create_dd.block.BlockProperties.flywheel;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

import java.util.Objects;

public class FlywheelBlock extends HorizontalKineticBlock implements IBE<FlywheelBlockEntity> {

    public static final EnumProperty<ConnectionState> CONNECTION = EnumProperty.create("connection", ConnectionState.class);

    public FlywheelBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(CONNECTION, ConnectionState.NONE));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(CONNECTION));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = Objects.requireNonNull(context.getPlayer()).getDirection();

        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            return defaultBlockState().setValue(HORIZONTAL_FACING, facing);
        }
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, facing.getOpposite());
    }

    public static boolean isConnected(BlockState state) {
        return getConnection(state) != null;
    }

    public static Direction getConnection(BlockState state) {
        Direction facing = state.getValue(HORIZONTAL_FACING);
        ConnectionState connection = state.getValue(CONNECTION);

        if (connection == ConnectionState.LEFT)
            return facing.getCounterClockWise();
        if (connection == ConnectionState.RIGHT)
            return facing.getClockWise();
        return null;
    }

    public static void setConnection(Level world, BlockPos pos, BlockState state, Direction direction) {
        Direction facing = state.getValue(HORIZONTAL_FACING);
        ConnectionState connection = ConnectionState.NONE;

        if (direction == facing.getClockWise())
            connection = ConnectionState.RIGHT;
        if (direction == facing.getCounterClockWise())
            connection = ConnectionState.LEFT;

        world.setBlock(pos, state.setValue(CONNECTION, connection), 18);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Direction connection = getConnection(state);
        if (connection == null)
            return super.onWrenched(state ,context);

        if (context.getClickedFace().getAxis() == state.getValue(HORIZONTAL_FACING).getAxis())
            return InteractionResult.PASS;

        Level world = context.getLevel();
        BlockPos enginePos = context.getClickedPos().relative(connection, 2);
        BlockState engine = world.getBlockState(enginePos);
        if (engine.getBlock() instanceof FurnaceEngineBlock)
            ((FurnaceEngineBlock) engine.getBlock()).withBlockEntityDo(world, enginePos, EngineBlockEntity::detachWheel);

        return super.onWrenched(state.setValue(CONNECTION, ConnectionState.NONE), context);
    }

    public enum ConnectionState implements StringRepresentable {
        NONE, LEFT, RIGHT;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }
    }

    @Override
    public Class<FlywheelBlockEntity> getBlockEntityClass() {
        return FlywheelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FlywheelBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.FLYWHEEL.get();
    }

}