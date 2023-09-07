package uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw;

import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BronzeSawBlock extends SawBlock {
    public BronzeSawBlock(Properties properties) {
        super(properties);
    }

    public static final int placementHelperId = PlacementHelpers.register(new BronzeSawBlock.PlacementHelper());

    @Override
    public BlockEntityType<? extends BronzeSawBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.BRONZE_SAW.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        IPlacementHelper placementHelper = PlacementHelpers.get(placementHelperId);
        if (!player.isShiftKeyDown() && player.mayBuild()) {
            if (placementHelper.matchesItem(heldItem) && placementHelper.getOffset(player, worldIn, state, pos, hit)
                    .placeInWorld(worldIn, (BlockItem) heldItem.getItem(), player, handIn, hit)
                    .consumesAction())
                return InteractionResult.SUCCESS;
        }

        if (player.isSpectator() || !player.getItemInHand(handIn)
                .isEmpty())
            return InteractionResult.PASS;
        if (state.getOptionalValue(FACING)
                .orElse(Direction.WEST) != Direction.UP)
            return InteractionResult.PASS;

        return onBlockEntityUse(worldIn, pos, be -> {
            for (int i = 0; i < be.inventory.getSlots(); i++) {
                ItemStack heldItemStack = be.inventory.getStackInSlot(i);
                if (!worldIn.isClientSide && !heldItemStack.isEmpty())
                    player.getInventory()
                            .placeItemBackInInventory(heldItemStack);
            }
            be.inventory.clear();
            be.notifyUpdate();
            return InteractionResult.SUCCESS;
        });
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return DDBlocks.BRONZE_SAW::isIn;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return state -> DDBlocks.BRONZE_SAW.has(state);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    state.getValue(FACING)
                            .getAxis(),
                    dir -> world.getBlockState(pos.relative(dir))
                            .getMaterial()
                            .isReplaceable());

            if (directions.isEmpty())
                return PlacementOffset.fail();
            else {
                return PlacementOffset.success(pos.relative(directions.get(0)),
                        s -> s.setValue(FACING, state.getValue(FACING))
                                .setValue(AXIS_ALONG_FIRST_COORDINATE, state.getValue(AXIS_ALONG_FIRST_COORDINATE))
                                .setValue(FLIPPED, state.getValue(FLIPPED)));
            }
        }

    }
}
