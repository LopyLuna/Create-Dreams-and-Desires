package uwu.lopyluna.create_dd.block.BlockProperties.copycat;

import com.simibubi.create.content.decoration.copycat.CopycatSpecialCases;
import com.simibubi.create.content.decoration.copycat.WaterloggedCopycatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlockShapes;


@SuppressWarnings("deprecation")
public class BlockcopycatBlock extends WaterloggedCopycatBlock {


    public BlockcopycatBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isAcceptedRegardless(BlockState material) {
        return CopycatSpecialCases.isBarsMaterial(material);
    }


    @Override
    public boolean canConnectTexturesToward(BlockAndTintGetter reader, BlockPos fromPos, BlockPos toPos, BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add());
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return DDBlockShapes.CASING_16PX;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }

    @Override
    public boolean supportsExternalFaceHiding(BlockState state) {
        return true;
    }

    @Override
    public boolean canFaceBeOccluded(BlockState state, Direction face) {
        return true;
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState,
                                     Direction dir) {
            if (state.is(this) == neighborState.is(this)) {
                if (getMaterial(level, pos).skipRendering(getMaterial(level, pos.relative(dir)), dir.getOpposite()))
                    return true;
            }

            return getMaterial(level, pos).skipRendering(neighborState, dir.getOpposite());
        }

    public static BlockcopycatBlock create(Properties properties) {
        return new BlockcopycatBlock(properties);
    }
}
