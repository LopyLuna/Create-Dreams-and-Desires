package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;


public class SpectralRubyLampBlock extends Block implements IWrenchable {

    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

    public SpectralRubyLampBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWER, Integer.valueOf(0))
                .setValue(POWERED, Boolean.valueOf(false))
                .setValue(INVERTED, Boolean.valueOf(false)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState stateForPlacement = super.getStateForPlacement(pContext);
        return stateForPlacement.setValue(POWERED, pContext.getLevel()
                .hasNeighborSignal(pContext.getClickedPos()));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (pDirection == null)
            return 0;
        BlockState toState = pLevel.getBlockState(pPos.relative(pDirection.getOpposite()));
        if (toState.is(this))
            return 0;
        if (toState.is(Blocks.COMPARATOR))
            return getDistanceToPowered(pLevel, pPos, pDirection);
		else if (toState.is(this))
            return pState.getValue(POWERED) ? 15 : 0;

        return pState.getValue(POWER);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos,
                                boolean pIsMoving) {
        if (pLevel.isClientSide)
            return;

        boolean isPowered = pState.getValue(POWERED);

        if (isPowered == pLevel.hasNeighborSignal(pPos))
            return;
        if (isPowered) {
            pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
            return;
        }

        updateSignalStrength(pState, pLevel, pPos);
        pLevel.setBlock(pPos, pState.setValue(POWERED, false), 2);
        pLevel.updateNeighborsAt(pPos, this);
        scheduleActivation(pLevel, pPos);
    }

    public void scheduleActivation(Level pLevel, BlockPos pPos) {
        if (!pLevel.getBlockTicks()
                .hasScheduledTick(pPos, this))
            pLevel.scheduleTick(pPos, this, 1);
    }

    public int getDistanceToPowered(BlockGetter level, BlockPos pos, Direction column) {
        BlockPos.MutableBlockPos currentPos = pos.mutable();
        for (int power = 15; power > 0; power--) {
            BlockState blockState = level.getBlockState(currentPos);
            if (!blockState.is(this))
                return 0;
            if (blockState.getValue(POWERED))
                return power;
            currentPos.move(column);
        }
        return 0;
    }


    public static void updateSignalStrength(BlockState pState, Level pLevel, BlockPos pPos) {
        int i = pLevel.getBrightness(LightLayer.SKY, pPos) - pLevel.getSkyDarken();
        float f = pLevel.getSunAngle(1.0F);
        boolean flag = pState.getValue(INVERTED);
        if (flag) {
            i = 15 - i;
        } else if (i > 0) {
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f += (f1 - f) * 0.2F;
            i = Math.round((float)i * Mth.cos(f));
        }

        i = Mth.clamp(i, 0, 15);
        if (pState.getValue(POWER) != i) {
            pLevel.setBlock(pPos, pState.setValue(POWER, Integer.valueOf(i)), 3);
        }

    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRand) {
        boolean isInverted = pState.getValue(INVERTED);
        boolean isPowered = pState.getValue(POWERED);
        updateSignalStrength(pState, pLevel, pPos);
        if (pLevel.isClientSide && pLevel.dimensionType().hasSkyLight()) {
            return;
        }
        pLevel.setBlock(pPos, pState, 4);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pState));
        tickEntity(pLevel, pPos, pState);
        updateSignalStrength(pState, pLevel, pPos);
    }
    public static void tickEntity(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.getGameTime() % 20L == 0L) {
            updateSignalStrength(pState, pLevel, pPos);
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.mayBuild()) {
            if (pLevel.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockState blockstate = pState.cycle(INVERTED);
                pLevel.setBlock(pPos, blockstate, 4);
                pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
                updateSignalStrength(blockstate, pLevel, pPos);
                return InteractionResult.CONSUME;
            }
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED, POWER, INVERTED);
    }

}
