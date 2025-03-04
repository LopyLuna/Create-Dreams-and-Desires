package dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractShaftBlock;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.create_d2d.register.DesiresBETypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PoweredFlywheelBlock extends AbstractShaftBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public PoweredFlywheelBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return super.rotate(state, rot).setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING).getOpposite();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return super.mirror(state, mirror).rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return AllBlocks.FLYWHEEL.asStack();
    }

    public void simpleTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Direction pDirection, Direction.Axis pAxis) {
        if (pAxis != pDirection.getAxis()) pState.setValue(AXIS, pDirection.getAxis());

        if (!stillValid(pState, pLevel, pPos))
            pLevel.setBlock(pPos, AllBlocks.FLYWHEEL.getDefaultState().setValue(FlywheelBlock.AXIS, pAxis), 3);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var direction = getPreferredHorizontalFacing(context);
        var state = this.defaultBlockState();
        if (direction != null) state.setValue(FACING, direction).setValue(AXIS, direction.getAxis());
        return withWater(state, context);
    }

    public Direction getPreferredHorizontalFacing(BlockPlaceContext context) {
        Direction preferSide = null;
        for (Direction side : Iterate.horizontalDirections) {
            BlockState blockState = context.getLevel()
                    .getBlockState(context.getClickedPos()
                            .relative(side));
            if (blockState.getBlock() instanceof IRotate) {
                if (((IRotate) blockState.getBlock()).hasShaftTowards(context.getLevel(), context.getClickedPos()
                        .relative(side), blockState, side.getOpposite()))
                    if (preferSide != null && preferSide.getAxis() != side.getAxis()) {
                        preferSide = null;
                        break;
                    } else {
                        preferSide = side;
                    }
            }
        }
        return preferSide;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return stillValid(pState, pLevel, pPos);
    }

    public static boolean stillValid(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        for (Direction d : Iterate.directions) {
            if (d.getAxis() == pState.getValue(AXIS)) continue;
            BlockPos enginePos = pPos.relative(d, 2);
            BlockState engineState = pLevel.getBlockState(enginePos);
            if (!(engineState.getBlock() instanceof StirlingEngineBlock)) continue;
            if (!StirlingEngineBlock.getFlywheelPos(engineState, enginePos).equals(pPos)) continue;
            if (StirlingEngineBlock.isFlywheelValid(engineState, pState)) return true;
        }
        return false;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return DesiresBETypes.POWERED_FLYWHEEL.get();
    }
}
