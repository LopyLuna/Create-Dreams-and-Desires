package uwu.lopyluna.create_dd.block.BlockProperties.bell;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.lopyluna.create_dd.block.DDBlocks;

import javax.annotation.ParametersAreNonnullByDefault;


@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigBellBlock extends BellBlock {
    boolean placed = false;
    public BigBellBlock(Properties pProperties) {
        super(pProperties);
    }
    
    public static final VoxelShape SHAPE = Block.box(-16, -16, -16, 32, 32, 32);

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (getPlaced(pLevel, pPos) == Blocks.AIR.defaultBlockState() && placed) {
            pLevel.destroyBlock(pPos, true);
            this.setPlaced(pLevel, pPos, Blocks.AIR.defaultBlockState());
        }

        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            if (flag) {
                this.ring(pLevel, pState, pPos, 0.0F);
            }

            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)), 3);
        }
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {return true;}

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {return SHAPE;}

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {return SHAPE;}

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {return SHAPE;}

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {return SHAPE;}

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pGetter, BlockPos pPos) {return SHAPE;}

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {return SHAPE;}

    @Override
    public RenderShape getRenderShape(BlockState pState) {return RenderShape.MODEL;}

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_54582_) {
        return true;
    }

    public BlockState getPlaced(Level pLevel, BlockPos pPos) {
        pLevel.getBlockState(pPos.offset(0, -1, 1));
        pLevel.getBlockState(pPos.offset(-1, 0, 1));
        pLevel.getBlockState(pPos.offset(-1, 1, 0));

        pLevel.getBlockState(pPos.offset(0, 1, -1));
        pLevel.getBlockState(pPos.offset(1, 0, -1));
        pLevel.getBlockState(pPos.offset(1, -1, 0));

        pLevel.getBlockState(pPos.offset(0, -1, -1));
        pLevel.getBlockState(pPos.offset(-1, 0, -1));
        pLevel.getBlockState(pPos.offset(-1, -1, 0));

        pLevel.getBlockState(pPos.offset(0, 1, 1));
        pLevel.getBlockState(pPos.offset(1, 0, 1));
        pLevel.getBlockState(pPos.offset(1, 1, 0));

        //FACES
        pLevel.getBlockState(pPos.offset(1, 0, 0));
        pLevel.getBlockState(pPos.offset(0, 1, 0));
        pLevel.getBlockState(pPos.offset(0, 0, 1));

        pLevel.getBlockState(pPos.offset(-1, 0, 0));
        pLevel.getBlockState(pPos.offset(0, -1, 0));
        pLevel.getBlockState(pPos.offset(0, 0, -1));

        //CORNERS
        pLevel.getBlockState(pPos.offset(1, 1, 1));
        pLevel.getBlockState(pPos.offset(1, 1, -1));
        pLevel.getBlockState(pPos.offset(-1, 1, 1));
        pLevel.getBlockState(pPos.offset(-1, 1, -1));

        pLevel.getBlockState(pPos.offset(-1, -1, -1));
        pLevel.getBlockState(pPos.offset(1, -1, 1));
        pLevel.getBlockState(pPos.offset(1, -1, -1));
        return pLevel.getBlockState(pPos.offset(-1, -1, 1));
    }

    public void setPlaced(Level pLevel, BlockPos pPos, BlockState pState) {
        //I KNOW I KNOW THIS LOOKS HORRIBLE LMAO

        //EDGES
        pLevel.setBlock(pPos.offset(0, -1, 1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, 0, 1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, 1, 0), pState, 3);

        pLevel.setBlock(pPos.offset(0, 1, -1), pState, 3);
        pLevel.setBlock(pPos.offset(1, 0, -1), pState, 3);
        pLevel.setBlock(pPos.offset(1, -1, 0), pState, 3);

        pLevel.setBlock(pPos.offset(0, -1, -1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, 0, -1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, -1, 0), pState, 3);

        pLevel.setBlock(pPos.offset(0, 1, 1), pState, 3);
        pLevel.setBlock(pPos.offset(1, 0, 1), pState, 3);
        pLevel.setBlock(pPos.offset(1, 1, 0), pState, 3);

        //FACES
        pLevel.setBlock(pPos.offset(1, 0, 0), pState, 3);
        pLevel.setBlock(pPos.offset(0, 1, 0), pState, 3);
        pLevel.setBlock(pPos.offset(0, 0, 1), pState, 3);

        pLevel.setBlock(pPos.offset(-1, 0, 0), pState, 3);
        pLevel.setBlock(pPos.offset(0, -1, 0), pState, 3);
        pLevel.setBlock(pPos.offset(0, 0, -1), pState, 3);

        //CORNERS
        pLevel.setBlock(pPos.offset(1, 1, 1), pState, 3);
        pLevel.setBlock(pPos.offset(1, 1, -1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, 1, 1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, 1, -1), pState, 3);

        pLevel.setBlock(pPos.offset(-1, -1, -1), pState, 3);
        pLevel.setBlock(pPos.offset(1, -1, 1), pState, 3);
        pLevel.setBlock(pPos.offset(1, -1, -1), pState, 3);
        pLevel.setBlock(pPos.offset(-1, -1, 1), pState, 3);

    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.setPlaced(level, pos, DDBlocks.BRONZE_BELL_STRUCTURE.getDefaultState());
        super.onPlace(state, level, pos, oldState, isMoving);
        placed = true;
        if (!level.getBlockTicks()
                .hasScheduledTick(pos, this))
            level.scheduleTick(pos, this, 1);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        this.setPlaced(level, pos, Blocks.AIR.defaultBlockState());
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

}
