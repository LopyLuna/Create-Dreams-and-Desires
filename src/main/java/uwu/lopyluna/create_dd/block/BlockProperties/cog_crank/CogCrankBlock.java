package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.DDBlockShapes;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock.isValidCogwheelPosition;

@SuppressWarnings({"deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CogCrankBlock extends RotatedPillarKineticBlock
implements IBE<CogCrankBlockEntity>, ProperWaterloggedBlock, ICogWheel {

    public CogCrankBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return DDBlockShapes.cogCrank.get(state.getValue(AXIS));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }

    public int getRotationSpeed() {
        return 32;
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
    
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        if (player.isSpectator())
            return InteractionResult.PASS;
        if (player.getItemInHand(handIn).getItem() instanceof CogwheelBlockItem)
            return InteractionResult.PASS;
        withBlockEntityDo(worldIn, pos, be -> be.turn(player.isShiftKeyDown()));
        if (!player.getItemInHand(handIn)
                .is(AllItems.EXTENDO_GRIP.get()))
            player.causeFoodExhaustion(getRotationSpeed() * AllConfigs.server().kinetics.crankHungerMultiplier.getF());
        
        if (player.getFoodData()
                .getFoodLevel() == 0)
            AllAdvancements.HAND_CRANK.awardTo(player);
        
        return InteractionResult.SUCCESS;
    }

    protected Direction.Axis getAxisForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown())
            return context.getClickedFace()
                    .getAxis();
        
        Level world = context.getLevel();
        
        BlockPos placedOnPos = context.getClickedPos()
                .relative(context.getClickedFace()
                        .getOpposite());
        BlockState placedAgainst = world.getBlockState(placedOnPos);
        
        Block block = placedAgainst.getBlock();
        if (ICogWheel.isSmallCog(placedAgainst))
            return ((IRotate) block).getRotationAxis(placedAgainst);
        
        Direction.Axis getPreferredFacing = getPreferredAxis(context);
        return getPreferredFacing != null ? getPreferredFacing
                : context.getClickedFace()
                .getAxis();
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean shouldWaterlog = context.getLevel()
                .getFluidState(context.getClickedPos())
                .getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(AXIS, getAxisForPlacement(context))
                .setValue(BlockStateProperties.WATERLOGGED, shouldWaterlog);
    }
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return isValidCogwheelPosition(ICogWheel.isLargeCog(state), worldIn, pos, state.getValue(AXIS));
    }
    
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }
    
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }
    
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public Class<CogCrankBlockEntity> getBlockEntityClass() {
        return CogCrankBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CogCrankBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.cogCrank.get();
    }
    
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    public static Couple<Integer> getSpeedRange() {
        return Couple.create(32, 32);
    }
    
}
