package dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SmartHopperBlock extends Block implements IWrenchable, IBE<SmartHopperBE> {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;

    public SmartHopperBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.DOWN).setValue(POWERED, false));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var face = context.getClickedFace();
        if (face.getAxis() != Direction.Axis.Y) return InteractionResult.PASS;
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var rotated = getRotatedBlockState(state, face);
        if (!rotated.canSurvive(level, pos)) return InteractionResult.PASS;
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(rotated, context));
        if (level.getBlockState(pos) != state) AllSoundEvents.WRENCH_ROTATE.playOnServer(level, pos, 1, level.random.nextFloat() + .5f);
        return InteractionResult.SUCCESS;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var direction = context.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }

    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) checkPoweredState(level, pos, state);
    }

    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof SmartHopperBE be) player.openMenu(be);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        this.checkPoweredState(level, pos, state);
    }

    private void checkPoweredState(Level level, BlockPos pos, BlockState state) {
        var flag = level.hasNeighborSignal(pos);
        if (flag != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, flag), 2);
            if (level.getBlockEntity(pos) instanceof SmartHopperBE be) {
                be.invVersionTracker.reset();
                be.notifyUpdate();
            }
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && level.getBlockEntity(pos) instanceof SmartHopperBE be) {
            if (be.inv == null) return;
            var stack = itemEntity.getItem();
            if (!stack.isEmpty() && entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ()).intersects(SUCK_AABB)) {
                if (be.cantAcceptItem(stack, state)) return;
                itemEntity.setItem(be.handleExtracting(stack, state));
            }
        }
    }

    public static AABB SUCK_AABB = Block.box(0F, 11F, 0F, 16F, 32F, 16F).toAabbs().getFirst();

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        IBE.onRemove(state, level, pos, newState);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public Class<SmartHopperBE> getBlockEntityClass() {
        return SmartHopperBE.class;
    }

    @Override
    public BlockEntityType<? extends SmartHopperBE> getBlockEntityType() {
        return DesiresBETypes.SMART_HOPPER.get();
    }

    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof SmartHopperBE be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be.inv) : 0;
    }

    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> BASE;
        };
    }

    protected @NotNull VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_INTERACTION_SHAPE;
            case NORTH -> NORTH_INTERACTION_SHAPE;
            case SOUTH -> SOUTH_INTERACTION_SHAPE;
            case WEST -> WEST_INTERACTION_SHAPE;
            case EAST -> EAST_INTERACTION_SHAPE;
            default -> INSIDE;
        };
    }

    private static final VoxelShape TOP = Block.box(0F, 10F, 0F, 16F, 16F, 16F);
    private static final VoxelShape FUNNEL = Block.box(2F, 4F, 2F, 14F, 10F, 14F);
    private static final VoxelShape CONVEX_BASE = Shapes.or(FUNNEL, TOP);
    private static final VoxelShape INSIDE = box(3F, 12F, 3F, 13F, 16F, 13F);
    private static final VoxelShape BASE = Shapes.join(CONVEX_BASE, INSIDE, BooleanOp.ONLY_FIRST);

    private static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(5F, 0F, 5F, 11F, 4F, 11F));
    private static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(13F, 4F, 5F, 18F, 10F, 11F));
    private static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(5F, 4F, -2F, 11F, 10F, 2));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(5F, 4F, 14F, 11F, 10F, 18F));
    private static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(-2F, 4F, 5F, 2F, 10F, 11F));
    private static final VoxelShape DOWN_INTERACTION_SHAPE = INSIDE;
    private static final VoxelShape EAST_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(12F, 8F, 6F, 16F, 10F, 10F));
    private static final VoxelShape NORTH_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(6F, 8F, 0F, 10F, 10F, 4F));
    private static final VoxelShape SOUTH_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(6F, 8F, 12F, 10F, 10F, 16F));
    private static final VoxelShape WEST_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(0F, 8F, 6F, 4F, 10F, 10F));
}
