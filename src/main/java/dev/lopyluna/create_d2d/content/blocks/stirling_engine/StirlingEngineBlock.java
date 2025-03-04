package dev.lopyluna.create_d2d.content.blocks.stirling_engine;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBlock;
import dev.lopyluna.create_d2d.mixins.FurnaceBlockAccessor;
import dev.lopyluna.create_d2d.register.DesiresBETypes;
import dev.lopyluna.create_d2d.register.DesiresBlocks;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@EventBusSubscriber
public class StirlingEngineBlock extends FaceAttachedHorizontalDirectionalBlock implements IWrenchable, ProperWaterloggedBlock, IBE<StirlingEngineBE> {

    @SubscribeEvent
    public static void usingEngineOnFurnacePreventsGUI(PlayerInteractEvent.RightClickBlock event) {
        BlockItem blockItem;
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof BlockItem) blockItem = (BlockItem) item;
        else return;
        if (blockItem.getBlock() != DesiresBlocks.STIRLING_ENGINE.get()) return;
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (state.getBlock() instanceof AbstractFurnaceBlock) event.setUseBlock(TriState.FALSE);
    }

    public static Couple<Integer> getSpeedRange() {
        return Couple.create(16, 64);
    }

    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    public static final MapCodec<StirlingEngineBlock> CODEC = simpleCodec(StirlingEngineBlock::new);

    public StirlingEngineBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACE, FACING, WATERLOGGED));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            var furnacePos = getFurnacePos(state, pos);
            if (level.getBlockState(furnacePos).getBlock() instanceof AbstractFurnaceBlock furnaceBlock)
                ((FurnaceBlockAccessor) furnaceBlock).openContainer$D2D(level, furnacePos, player);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return getConnectedDirection(pState).getOpposite() == pDirection && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public static Direction getFacing(BlockState sideState) {
        return getConnectedDirection(sideState);
    }

    public static BlockPos getFlywheelPos(BlockState sideState, BlockPos pos) {
        return pos.relative(getConnectedDirection(sideState), 2);
    }
    public static BlockPos getFurnacePos(BlockState sideState, BlockPos pos) {
        return pos.relative(getConnectedDirection(sideState), -1);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pReader, BlockPos pPos) {
        return canAttach(pReader, pPos, getConnectedDirection(pState).getOpposite()) && isValidPosition(pReader, pPos.relative(getConnectedDirection(pState).getOpposite()),getConnectedDirection(pState));
    }

    public static boolean canAttach(LevelReader pReader, BlockPos pPos, Direction pDirection) {
        BlockPos blockpos = pPos.relative(pDirection);
        return pReader.getBlockState(blockpos).getBlock() instanceof AbstractFurnaceBlock;
    }
    public static boolean isValidPosition(LevelReader world, BlockPos pos, Direction facing) {
        for (Direction otherFacing : Iterate.directions) {
            if (otherFacing != facing) {
                BlockPos otherPos = pos.relative(otherFacing);
                BlockState otherState = world.getBlockState(otherPos);
                if (otherState.getBlock() instanceof StirlingEngineBlock) return false;
            }
        }
        return true;
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
            else blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite());

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) return blockstate;
        }
        return null;
    }
    protected static @NotNull Direction getConnectedDirection(BlockState state) {
        return state.getValue(FACING);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (placementHelper.matchesItem(stack))
            return placementHelper.getOffset(player, level, state, pos, hitResult).placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static boolean isFlywheelValid(BlockState state, BlockState shaft) {
        return (AllBlocks.FLYWHEEL.has(shaft) || (DesiresBlocks.POWERED_FLYWHEEL.has(shaft) && shaft.getValue(PoweredFlywheelBlock.FACING).getAxis() != getFacing(state).getAxis())) &&
                shaft.getValue(FlywheelBlock.AXIS) != getFacing(state).getAxis();
    }

    @Override
    protected @NotNull MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public Class<StirlingEngineBE> getBlockEntityClass() {
        return StirlingEngineBE.class;
    }

    @Override
    public BlockEntityType<? extends StirlingEngineBE> getBlockEntityType() {
        return DesiresBETypes.STIRLING_ENGINE.get();
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return AllBlocks.FLYWHEEL::isIn;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof StirlingEngineBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            BlockPos flywheelPos = StirlingEngineBlock.getFlywheelPos(state, pos);
            BlockState flywheel = DesiresBlocks.POWERED_FLYWHEEL.getDefaultState();
            for (Direction direct : Direction.orderedByNearest(player)) {
                if (direct.getAxis().isVertical()) continue;
                flywheel = flywheel.setValue(PoweredFlywheelBlock.FACING, direct);
                if (isFlywheelValid(state, flywheel)) break;
            }

            BlockState newState = world.getBlockState(flywheelPos);
            if (!newState.canBeReplaced()) return PlacementOffset.fail();

            Direction direction = flywheel.getValue(PoweredFlywheelBlock.FACING);
            direction = player.isShiftKeyDown() ? direction.getOpposite() : direction;
            Direction finalDirection = direction;
            return PlacementOffset.success(flywheelPos, s -> BlockHelper
                    .copyProperties(s, DesiresBlocks.POWERED_FLYWHEEL.getDefaultState())
                    .setValue(PoweredFlywheelBlock.FACING, finalDirection).setValue(PoweredFlywheelBlock.AXIS, finalDirection.getAxis()));
        }
    }
}