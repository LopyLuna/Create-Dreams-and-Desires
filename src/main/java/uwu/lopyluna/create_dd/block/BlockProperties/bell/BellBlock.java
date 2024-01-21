package uwu.lopyluna.create_dd.block.BlockProperties.bell;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;


@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BellBlock extends Block implements IBE<BellBlockEntity> {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final IntegerProperty NOTE = BlockStateProperties.NOTE;
    public static final VoxelShape SHAPEBOX = Block.box(0, 0, 0, 16, 16, 16);

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(3, 5, 3, 13, 16, 13),
            Block.box(2, 0, 2, 14, 5, 14),
            Block.box(6, 14, 6, 10, 18, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();;

    public static int bellCooldown = 0;

    public BellBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NOTE, Integer.valueOf(0)).setValue(POWERED, Boolean.valueOf(false)));
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            if (flag) {
                this.ring(pLevel, pState, pPos, 0.0F);
            }

            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)), 3);
        }
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        super.fallOn(pLevel, pState, pPos, pEntity, 6);
        pEntity.causeFallDamage(pFallDistance, 1.0F, DamageSource.FALL);
    }


    @Override
    public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity pEntity) {
        if (pEntity.fallDistance >= 4) {
            float fallingPitch = pEntity.fallDistance;
            fallingPitch %= -2;
            this.ringAfterFallOn(pEntity, fallingPitch);
        }
        pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
        pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
    }

    private void ringAfterFallOn(Entity pEntity, float pitch) {
        Level world = pEntity.getLevel();
        BlockState state = defaultBlockState();
        BlockPos pos = pEntity.getOnPos();

        this.ring(world, state, pos, pitch);
    }

    @NotNull
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult ray) {
        if (pPlayer.mayBuild() && !pPlayer.isCrouching()) {
            this.ring(pLevel, pState, pPos, 0.0F);
            return InteractionResult.SUCCESS;
        } else if (pPlayer.isCrouching()) {
            int _new = net.minecraftforge.common.ForgeHooks.onNoteChange(pLevel, pPos, pState, pState.getValue(NOTE), pState.cycle(NOTE).getValue(NOTE));
            if (_new == -1) return InteractionResult.FAIL;
            pState = pState.setValue(NOTE, _new);
            pLevel.setBlock(pPos, pState, 3);
            this.tweakingRing(pLevel, pState, pPos, 0.0F);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        this.ring(pLevel, pState, pPos, 0.0F);
    }

    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos blockpos = pHit.getBlockPos();
        this.ring(pLevel, pState, blockpos, 0.0F);
    }

    public void ring(Level pLevel, BlockState pState, BlockPos pPos, float pitch) {
        if (!pLevel.isClientSide && bellCooldown == 0) {
            int i = pState.getValue(NOTE);
            float f = (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
            pLevel.playSound((Player)null, pPos, DDSoundEvents.bell_ring.get(), SoundSource.BLOCKS, 10.0F, f + pitch);
            bellCooldown = 20 * 4;
        }
    }
    public void tweakingRing(Level pLevel, BlockState pState, BlockPos pPos, float pitch) {
        if (!pLevel.isClientSide) {
            int i = pState.getValue(NOTE);
            float f = (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
            pLevel.playSound((Player)null, pPos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.BLOCKS, 10.0F, f + pitch);
        }
    }


    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public Class<BellBlockEntity> getBlockEntityClass() {
        return BellBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BellBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.BELL.get();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED, NOTE);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {return true;}

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {return SHAPEBOX;}

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {return SHAPEBOX;}

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {return SHAPEBOX;}

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {return SHAPE;}

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pGetter, BlockPos pPos) {return SHAPE;}

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {return SHAPE;}
}
