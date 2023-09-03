package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.bearing.SailBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.YIPPEE;

import java.util.List;
import java.util.function.Predicate;

import static com.simibubi.create.content.kinetics.base.IRotate.StressImpact.isEnabled;

public class FanSailBlock extends SailBlock {
    public static @NotNull FanSailBlock sail(Properties properties) {
        return new FanSailBlock(properties, false, null);
    }
    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    protected FanSailBlock(Properties properties, boolean frame, DyeColor color) {
        super(properties, frame, color);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if(isEnabled() || tab == CreativeModeTab.TAB_SEARCH)
            super.fillItemCategory(tab, items);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {
        ItemStack heldItem = player.getItemInHand(hand);

        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (!player.isShiftKeyDown() && player.mayBuild()) {
            if (placementHelper.matchesItem(heldItem)) {
                placementHelper.getOffset(player, world, state, pos, ray)
                        .placeInWorld(world, (BlockItem) heldItem.getItem(), player, hand, ray);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> YIPPEE.splashing_sail.isIn(i)
                    || YIPPEE.haunting_sail.isIn(i)
                    || YIPPEE.smoking_sail.isIn(i)
                    || YIPPEE.blasting_sail.isIn(i)
                    || YIPPEE.superheating_sail.isIn(i)
                    || YIPPEE.freezing_sail.isIn(i)
                    || AllBlocks.SAIL.isIn(i)
                    || AllBlocks.SAIL_FRAME.isIn(i);
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof FanSailBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    state.getValue(FanSailBlock.FACING)
                            .getAxis(),
                    dir -> world.getBlockState(pos.relative(dir))
                            .getMaterial()
                            .isReplaceable());

            if (directions.isEmpty())
                return PlacementOffset.fail();
            else {
                return PlacementOffset.success(pos.relative(directions.get(0)),
                        s -> s.setValue(FACING, state.getValue(FACING)));
            }
        }
    }

}
