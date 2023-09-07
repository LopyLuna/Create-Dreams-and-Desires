package uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive;

import com.simibubi.create.content.contraptions.ITransformableBlock;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.chainDrive.ChainDriveBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

public class ChainDriveBlock2 extends ChainDriveBlock
        implements IBE<KineticBlockEntity>, ITransformableBlock {

    public static final Property<ChainDriveBlock2.Part> PART = EnumProperty.create("part", ChainDriveBlock2.Part.class);
    public static final BooleanProperty CONNECTED_ALONG_FIRST_COORDINATE =
            DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;

    public ChainDriveBlock2(Properties properties, boolean visible) {
        super(properties);
        this.visible = visible;
        registerDefaultState(defaultBlockState().setValue(PART, ChainDriveBlock2.Part.NONE));
    }

    private boolean visible;

    public ChainDriveBlock2(Properties p_i48440_1_) {
        this(p_i48440_1_, false);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (visible)
            super.fillItemCategory(pCategory, pItems);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction.Axis placedAxis = context.getNearestLookingDirection()
                .getAxis();
        Direction.Axis axis = context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown() ? placedAxis : getPreferredAxis(context);
        if (axis == null)
            axis = placedAxis;

        BlockState state = defaultBlockState().setValue(AXIS, axis);
        for (Direction facing : Iterate.directions) {
            if (facing.getAxis() == axis)
                continue;
            BlockPos pos = context.getClickedPos();
            BlockPos offset = pos.relative(facing);
            state = updateShape(state, facing, context.getLevel()
                    .getBlockState(offset), context.getLevel(), pos, offset);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction face, BlockState neighbour, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        ChainDriveBlock2.Part part = stateIn.getValue(PART);
        Direction.Axis axis = stateIn.getValue(AXIS);
        boolean connectionAlongFirst = stateIn.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis connectionAxis =
                connectionAlongFirst ? (axis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X) : (axis == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z);

        Direction.Axis faceAxis = face.getAxis();
        boolean facingAlongFirst = axis == Direction.Axis.X ? faceAxis.isVertical() : faceAxis == Direction.Axis.X;
        boolean positive = face.getAxisDirection() == Direction.AxisDirection.POSITIVE;

        if (axis == faceAxis)
            return stateIn;

        if (!(neighbour.getBlock() instanceof ChainDriveBlock2)) {
            if (facingAlongFirst != connectionAlongFirst || part == ChainDriveBlock2.Part.NONE)
                return stateIn;
            if (part == ChainDriveBlock2.Part.MIDDLE)
                return stateIn.setValue(PART, positive ? ChainDriveBlock2.Part.END : ChainDriveBlock2.Part.START);
            if ((part == ChainDriveBlock2.Part.START) == positive)
                return stateIn.setValue(PART, ChainDriveBlock2.Part.NONE);
            return stateIn;
        }

        ChainDriveBlock2.Part otherPart = neighbour.getValue(PART);
        Direction.Axis otherAxis = neighbour.getValue(AXIS);
        boolean otherConnection = neighbour.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis otherConnectionAxis =
                otherConnection ? (otherAxis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X) : (otherAxis == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z);

        if (neighbour.getValue(AXIS) == faceAxis)
            return stateIn;
        if (otherPart != ChainDriveBlock2.Part.NONE && otherConnectionAxis != faceAxis)
            return stateIn;

        if (part == ChainDriveBlock2.Part.NONE) {
            part = positive ? ChainDriveBlock2.Part.START : ChainDriveBlock2.Part.END;
            connectionAlongFirst = axis == Direction.Axis.X ? faceAxis.isVertical() : faceAxis == Direction.Axis.X;
        } else if (connectionAxis != faceAxis) {
            return stateIn;
        }

        if ((part == ChainDriveBlock2.Part.START) != positive)
            part = ChainDriveBlock2.Part.MIDDLE;

        return stateIn.setValue(PART, part)
                .setValue(CONNECTED_ALONG_FIRST_COORDINATE, connectionAlongFirst);
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        if (originalState.getValue(PART) == ChainDriveBlock2.Part.NONE)
            return super.getRotatedBlockState(originalState, targetedFace);
        return super.getRotatedBlockState(originalState,
                Direction.get(Direction.AxisDirection.POSITIVE, getConnectionAxis(originalState)));
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        Direction.Axis axis = newState.getValue(AXIS);
        newState = defaultBlockState().setValue(AXIS, axis);
        if (newState.hasProperty(BlockStateProperties.POWERED))
            newState = newState.setValue(BlockStateProperties.POWERED, context.getLevel()
                    .hasNeighborSignal(context.getClickedPos()));
        for (Direction facing : Iterate.directions) {
            if (facing.getAxis() == axis)
                continue;
            BlockPos pos = context.getClickedPos();
            BlockPos offset = pos.relative(facing);
            newState = updateShape(newState, facing, context.getLevel()
                    .getBlockState(offset), context.getLevel(), pos, offset);
        }
        return newState;
    }

    public static boolean areBlocksConnected(BlockState state, BlockState other, Direction facing) {
        ChainDriveBlock2.Part part = state.getValue(PART);
        Direction.Axis connectionAxis = getConnectionAxis(state);
        Direction.Axis otherConnectionAxis = getConnectionAxis(other);

        if (otherConnectionAxis != connectionAxis)
            return false;
        if (facing.getAxis() != connectionAxis)
            return false;
        if (facing.getAxisDirection() == Direction.AxisDirection.POSITIVE && (part == ChainDriveBlock2.Part.MIDDLE || part == ChainDriveBlock2.Part.START))
            return true;
        if (facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE && (part == ChainDriveBlock2.Part.MIDDLE || part == ChainDriveBlock2.Part.END))
            return true;

        return false;
    }

    protected static Direction.Axis getConnectionAxis(BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);
        boolean connectionAlongFirst = state.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis connectionAxis =
                connectionAlongFirst ? (axis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X) : (axis == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z);
        return connectionAxis;
    }

    public static float getRotationSpeedModifier(KineticBlockEntity from, KineticBlockEntity to) {
        float fromMod = 1;
        float toMod = 1;
        if (from instanceof ChainGearshiftBlock2Entity)
            fromMod = ((ChainGearshiftBlock2Entity) from).getModifier();
        if (to instanceof ChainGearshiftBlock2Entity)
            toMod = ((ChainGearshiftBlock2Entity) to).getModifier();
        return fromMod / toMod;
    }


    @Override
    public Class<KineticBlockEntity> getBlockEntityClass() {
        return KineticBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.ENCASED_SHAFT.get();
    }


    protected BlockState rotate(BlockState pState, Rotation rot, Direction.Axis rotAxis) {
        Direction.Axis connectionAxis = getConnectionAxis(pState);
        Direction direction = Direction.fromAxisAndDirection(connectionAxis, Direction.AxisDirection.POSITIVE);
        Direction normal = Direction.fromAxisAndDirection(pState.getValue(AXIS), Direction.AxisDirection.POSITIVE);
        for (int i = 0; i < rot.ordinal(); i++) {
            direction = direction.getClockWise(rotAxis);
            normal = normal.getClockWise(rotAxis);
        }

        if (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE)
            pState = reversePart(pState);

        Direction.Axis newAxis = normal.getAxis();
        Direction.Axis newConnectingDirection = direction.getAxis();
        boolean alongFirst = newAxis == Direction.Axis.X && newConnectingDirection == Direction.Axis.Y
                || newAxis != Direction.Axis.X && newConnectingDirection == Direction.Axis.X;

        return pState.setValue(AXIS, newAxis)
                .setValue(CONNECTED_ALONG_FIRST_COORDINATE, alongFirst);
    }


    protected BlockState reversePart(BlockState pState) {
        ChainDriveBlock2.Part part = pState.getValue(PART);
        if (part == ChainDriveBlock2.Part.START)
            return pState.setValue(PART, ChainDriveBlock2.Part.END);
        if (part == ChainDriveBlock2.Part.END)
            return pState.setValue(PART, ChainDriveBlock2.Part.START);
        return pState;
    }

}
