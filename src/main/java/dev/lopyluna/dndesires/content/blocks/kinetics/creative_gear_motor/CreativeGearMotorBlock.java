package dev.lopyluna.dndesires.content.blocks.kinetics.creative_gear_motor;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CreativeGearMotorBlock extends DirectionalKineticBlock implements SimpleWaterloggedBlock, ICogWheel, IBE<CreativeGearMotorBE> {
    public CreativeGearMotorBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.setValue(FACING, originalState.getValue(FACING).getOpposite());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AllShapes.PUMP.get(state.getValue(FACING));
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var toPlace = super.getStateForPlacement(context);
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        toPlace = ProperWaterloggedBlock.withWater(level, toPlace, pos);

        var nearestLookingDirection = context.getNearestLookingDirection();
        var targetDirection = context.getPlayer() != null && context.getPlayer().isShiftKeyDown() ? nearestLookingDirection : nearestLookingDirection.getOpposite();
        Direction bestConnectedDirection = null;
        var bestDistance = Double.MAX_VALUE;

        for (var d : Iterate.directions) {
            var adjPos = pos.relative(d);
            var adjState = level.getBlockState(adjPos);
            if (!FluidPipeBlock.canConnectTo(level, adjPos, adjState, d)) continue;
            var distance = Vec3.atLowerCornerOf(d.getNormal()).distanceTo(Vec3.atLowerCornerOf(targetDirection.getNormal()));
            if (distance > bestDistance) continue;
            bestDistance = distance;
            bestConnectedDirection = d;
        }

        if (bestConnectedDirection == null) return toPlace;
        if (bestConnectedDirection.getAxis() == targetDirection.getAxis()) return toPlace;
        if (player != null && player.isShiftKeyDown()) return toPlace;

        return toPlace != null ? toPlace.setValue(FACING, bestConnectedDirection) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public Class<CreativeGearMotorBE> getBlockEntityClass() {
        return CreativeGearMotorBE.class;
    }

    @Override
    public BlockEntityType<? extends CreativeGearMotorBE> getBlockEntityType() {
        return DesiresBETypes.MOTOR.get();
    }
}
