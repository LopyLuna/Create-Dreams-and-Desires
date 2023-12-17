package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.google.common.base.Predicates;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

import java.util.List;
import java.util.function.Predicate;

import static uwu.lopyluna.create_dd.block.BlockProperties.panels.ShadowPanelBlockEntity.active;
import static uwu.lopyluna.create_dd.block.BlockProperties.panels.ShadowPanelBlockEntity.weak_active;

@SuppressWarnings({"all"})
public class ShadowPanelBlock extends KineticBlock implements IBE<ShadowPanelBlockEntity>, SimpleWaterloggedBlock, IWrenchable, ICogWheel {
    public static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    public ShadowPanelBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override public Class<ShadowPanelBlockEntity> getBlockEntityClass() {return ShadowPanelBlockEntity.class;}

    @Override public BlockEntityType<? extends ShadowPanelBlockEntity> getBlockEntityType() {return DDBlockEntityTypes.SHADOW_PANEL.get();}

    @Override public Direction.Axis getRotationAxis(BlockState state) {return Direction.Axis.Y;}
    @Override public SpeedLevel getMinimumRequiredSpeedLevel() {return super.getMinimumRequiredSpeedLevel();}
    @Override public boolean hideStressImpact() {return super.hideStressImpact();}
    @Override public boolean showCapacityWithAnnotation() {return super.showCapacityWithAnnotation();}


    //WATERLOGGING STUFF
    @Override public FluidState getFluidState(BlockState state) {return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();}
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {builder.add(BlockStateProperties.WATERLOGGED);super.createBlockStateDefinition(builder);}
    @Override public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {if (state.getValue(BlockStateProperties.WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));return state;}
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(FluidState.getType() == Fluids.WATER));}



    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override public Predicate<ItemStack> getItemPredicate() {
            return Predicates.or(DDBlocks.SHADOW_PANEL::isIn, DDBlocks.RADIANT_PANEL::isIn);}

        @Override public Predicate<BlockState> getStatePredicate() {
            return Predicates.or(DDBlocks.SHADOW_PANEL::has, DDBlocks.RADIANT_PANEL::has);}

        @Override public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
                    Direction.Axis.Y,
                    dir -> world.getBlockState(pos.relative(dir))
                            .getMaterial()
                            .isReplaceable());

            if (directions.isEmpty())
                return PlacementOffset.fail();
            else {
                return PlacementOffset.success(pos.relative(directions.get(0)));
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
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

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRand) {
        if (active || weak_active) {
            if (pRand.nextInt(5) != 0)
                return;
            Vec3 vec3 = VecHelper.clampComponentWise(VecHelper.offsetRandomly(Vec3.ZERO, pRand, 1.25f), 1f)
                    .add(VecHelper.getCenterOf(pPos));
            pLevel.addParticle(ParticleTypes.REVERSE_PORTAL, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                    pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
            pLevel.addParticle(ParticleTypes.REVERSE_PORTAL, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                    pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
            pLevel.addParticle(ParticleTypes.WITCH, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                    pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        }
    }

    //I know this looks ass lmao
}