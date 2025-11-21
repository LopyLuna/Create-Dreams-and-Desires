package dev.lopyluna.dndesires.content.blocks.logistics.fluid_hatch;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.AllShapes;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.CreativeFluidTankBlockEntity;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import dev.lopyluna.dndesires.register.DesiresSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FluidHatchBlock extends HorizontalDirectionalBlock implements IBE<FluidHatchBE>, IWrenchable, ProperWaterloggedBlock {
    public static final MapCodec<FluidHatchBlock> CODEC = simpleCodec(FluidHatchBlock::new);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public FluidHatchBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(OPEN, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(OPEN, FACING, WATERLOGGED));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) return null;
        if (pContext.getClickedFace().getAxis().isVertical()) return null;

        return withWater(state.setValue(FACING, pContext.getClickedFace().getOpposite()).setValue(OPEN, false), pContext);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pPos);
        return pState;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return ItemInteractionResult.SUCCESS;
        if (player instanceof FakePlayer) return ItemInteractionResult.SUCCESS;
        var facing = state.getValue(FACING);
        var blockPos = pos.relative(facing);
        var blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity == null) return ItemInteractionResult.FAIL;
        var targetInv = level.getCapability(Capabilities.FluidHandler.BLOCK, blockPos, facing);
        if (targetInv == null) return ItemInteractionResult.FAIL;

        var filter = BlockEntityBehaviour.get(level, pos, FilteringBehaviour.TYPE);
        if (filter == null) return ItemInteractionResult.FAIL;

        var inventory = player.getInventory();
        var anyInserted = false;
        var depositItemInHand = !player.isShiftKeyDown();

        if (!depositItemInHand && AllItems.WRENCH.isIn(stack)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        for (int i = 0; i < inventory.items.size(); i++) {
            if (Inventory.isHotbarSlot(i) != depositItemInHand) continue;
            if (depositItemInHand && i != inventory.selected) continue;
            var item = inventory.getItem(i);

            if (item.isEmpty()) continue;
            if (!GenericItemEmptying.canItemBeEmptied(level, item)) continue;
            var emptyingResult = GenericItemEmptying.emptyItem(level, item, true);
            var fluidStack = emptyingResult.getFirst();

            if (fluidStack.isEmpty() || !filter.test(fluidStack)) continue;
            if (fluidStack.getAmount() != targetInv.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE)) continue;
            if (level.isClientSide) continue;

            var copyOfItem = item.copy();
            emptyingResult = GenericItemEmptying.emptyItem(level, copyOfItem, false);
            targetInv.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

            if (!player.isCreative() && !(blockEntity instanceof CreativeFluidTankBlockEntity)) {
                if (copyOfItem.isEmpty()) inventory.setItem(i, emptyingResult.getSecond());
                else {
                    inventory.setItem(i, copyOfItem);
                    inventory.placeItemBackInInventory(emptyingResult.getSecond());
                }
            }
            anyInserted = true;

        }
        if (!anyInserted) return ItemInteractionResult.SUCCESS;

        DesiresSoundEvents.FLUID_HATCH.playOnServer(level, pos);
        level.setBlockAndUpdate(pos, state.setValue(OPEN, true));
        level.scheduleTick(pos, this, 10);

        CreateLang.translate(depositItemInHand ? "item_hatch.deposit_item" : "item_hatch.deposit_inventory").sendStatus(player);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.ITEM_HATCH.get(pState.getValue(FACING).getOpposite());
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(OPEN)) pLevel.setBlockAndUpdate(pPos, pState.setValue(OPEN, false));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        IBE.onRemove(state, level, pos, newState);
    }

    @Override
    public Class<FluidHatchBE> getBlockEntityClass() {
        return FluidHatchBE.class;
    }

    @Override
    public BlockEntityType<? extends FluidHatchBE> getBlockEntityType() {
        return DesiresBETypes.FLUID_HATCH.get();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
