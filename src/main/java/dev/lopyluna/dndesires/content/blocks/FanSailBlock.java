package dev.lopyluna.dndesires.content.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
public class FanSailBlock extends WrenchableDirectionalBlock {
    public static @NotNull FanSailBlock sail(Properties properties) {
        return new FanSailBlock(properties);
    }
    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    protected FanSailBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (!player.isShiftKeyDown() && player.mayBuild()) if (placementHelper.matchesItem(stack)) {
            placementHelper.getOffset(player, level, state, pos, hitResult).placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        assert state != null;
        return state.setValue(FACING, state.getValue(FACING).getOpposite());
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AllShapes.SAIL.get(state.getValue(FACING));
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, 0);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        bounce(entity);
    }

    private void bounce(Entity entity) {
        Vec3 Vector3d = entity.getDeltaMovement();
        if (Vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.5D : 1.2D;
            entity.setDeltaMovement(Vector3d.x, -Vector3d.y * (double) 0.26F * d0, Vector3d.z);
        }
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> DesiresBlocks.SPLASHING_SAIL.isIn(i)
                    || DesiresBlocks.HAUNTING_SAIL.isIn(i)
                    || DesiresBlocks.SMOKING_SAIL.isIn(i)
                    || DesiresBlocks.BLASTING_SAIL.isIn(i)
                    || DesiresBlocks.SEETHING_SAIL.isIn(i)
                    || DesiresBlocks.FREEZING_SAIL.isIn(i)
                    || DesiresBlocks.SANDING_SAIL.isIn(i)
                    || DesiresBlocks.DRAGON_BREATHING_SAIL.isIn(i)
                    || AllBlocks.SAIL.isIn(i)
                    || AllBlocks.SAIL_FRAME.isIn(i);
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof FanSailBlock;
        }
        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    state.getValue(FanSailBlock.FACING).getAxis(),
                    dir -> world.getBlockState(pos.relative(dir)).canBeReplaced());
            if (directions.isEmpty()) return PlacementOffset.fail();
            else return PlacementOffset.success(pos.relative(directions.getFirst()), s -> s.setValue(FACING, state.getValue(FACING)));
        }
    }
}