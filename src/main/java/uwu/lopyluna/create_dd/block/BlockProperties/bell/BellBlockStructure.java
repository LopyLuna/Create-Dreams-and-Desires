package uwu.lopyluna.create_dd.block.BlockProperties.bell;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.lopyluna.create_dd.block.DDBlocks;

import javax.annotation.ParametersAreNonnullByDefault;


@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BellBlockStructure extends BellBlock {

    public static final VoxelShape SHAPE = Block.box(2, 2, 2, 14, 14, 14);
    public static final VoxelShape SHAPEBOX = Block.box(0, 0, 0, 16, 16, 16);

    public BellBlockStructure(Properties pProperties) {
        super(pProperties);
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

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_54582_) {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return DDBlocks.BRONZE_BELL.asStack();
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }


    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        return true;
    }

}
