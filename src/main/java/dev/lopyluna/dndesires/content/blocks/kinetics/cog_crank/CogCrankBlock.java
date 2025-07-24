package dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import dev.lopyluna.dndesires.register.DesiresShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock.isValidCogwheelPosition;

@ParametersAreNonnullByDefault
public class CogCrankBlock extends RotatedPillarKineticBlock implements IBE<CogCrankBE>, ICogWheel, ProperWaterloggedBlock {

    boolean isLarge;
    protected CogCrankBlock(boolean large, Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
        isLarge = large;
    }

    public static CogCrankBlock small(Properties properties) {
        return new CogCrankBlock(false, properties);
    }
    public static CogCrankBlock large(Properties properties) {
        return new CogCrankBlock(true, properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var small = DesiresShapes.shape(DesiresShapes.cuboid(2, 6, 2, 14, 10, 14)).forAxis();
        var large = DesiresShapes.shape(DesiresShapes.cuboid(0, 6, 0, 16, 10, 16)).forAxis();
        return (isLarge ? large : small).get(state.getValue(AXIS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }

    public int getRotationSpeed() {
        return isLarge ? 16 : 32;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof ICogWheel cog && cog.isDedicatedCogWheel() || player.isSpectator()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        withBlockEntityDo(level, pos, be -> be.turn(player.isShiftKeyDown()));
        if (!stack.is(AllItems.EXTENDO_GRIP.get())) player.causeFoodExhaustion(getRotationSpeed() * AllConfigs.server().kinetics.crankHungerMultiplier.getF());
        if (player.getFoodData().getFoodLevel() == 0) AllAdvancements.HAND_CRANK.awardTo(player);

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidCogwheelPosition(ICogWheel.isLargeCog(state), level, pos, state.getValue(AXIS));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }

    protected Direction.Axis getAxisForPlacement(BlockPlaceContext context) {
        var face = context.getClickedFace();
        var faceAxis = face.getAxis();
        var player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) return faceAxis;

        var level = context.getLevel();
        var stateBelow = level.getBlockState(context.getClickedPos().below());

        if (AllBlocks.ROTATION_SPEED_CONTROLLER.has(stateBelow) && isLargeCog())
            return stateBelow.getValue(SpeedControllerBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;

        var placedOnPos = context.getClickedPos().relative(face.getOpposite());
        var placedAgainst = level.getBlockState(placedOnPos);

        if (placedAgainst.getBlock() instanceof IRotate rot && ICogWheel.isSmallCog(placedAgainst)) return rot.getRotationAxis(placedAgainst);
        var preferredAxis = getPreferredAxis(context);
        return preferredAxis != null ? preferredAxis : faceAxis;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var defaultBlockState = withWater(defaultBlockState(), context);
        return defaultBlockState.setValue(AXIS, getAxisForPlacement(context));
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }
    @Override
    public Class<CogCrankBE> getBlockEntityClass() {
        return CogCrankBE.class;
    }
    @Override
    public BlockEntityType<? extends CogCrankBE> getBlockEntityType() {
        return DesiresBETypes.COG_CRANK.get();
    }

    @Override
    public boolean isLargeCog() {
        return isLarge;
    }

    @Override
    public boolean isSmallCog() {
        return !isLarge;
    }

    @Override
    public float getParticleTargetRadius() {
        return isLargeCog() ? 1.125f : .65f;
    }
    @Override
    public float getParticleInitialRadius() {
        return isLargeCog() ? 1f : .75f;
    }
    @Override
    public boolean isDedicatedCogWheel() {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
