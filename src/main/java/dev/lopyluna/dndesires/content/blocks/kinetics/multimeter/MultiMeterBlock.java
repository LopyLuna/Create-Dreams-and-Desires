package dev.lopyluna.dndesires.content.blocks.kinetics.multimeter;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.gauge.GaugeBlockEntity;
import com.simibubi.create.content.kinetics.gauge.GaugeShaper;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.mixins.GaugeShaperAccessor;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.levelWrappers.WrappedLevel;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MultiMeterBlock extends DirectionalAxisKineticBlock implements IBE<MultiMeterBE> {
    public static final GaugeShaper GAUGE = GaugeShaperAccessor.make();

    public MultiMeterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var face = context.getClickedFace();
        var placedOnPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        var placedOnState = level.getBlockState(placedOnPos);
        var block = placedOnState.getBlock();

        if (block instanceof IRotate rotate && rotate.hasShaftTowards(level, placedOnPos, placedOnState, face)) {
            var toPlace = defaultBlockState();
            var horizontalFacing = context.getHorizontalDirection();
            var nearestLookingDirection = context.getNearestLookingDirection();
            var lookPositive = nearestLookingDirection.getAxisDirection() == Direction.AxisDirection.POSITIVE;
            return switch (face.getAxis()) {
                case X -> toPlace.setValue(FACING, lookPositive ? Direction.NORTH : Direction.SOUTH)
                        .setValue(AXIS_ALONG_FIRST_COORDINATE, true);
                case Y -> toPlace.setValue(FACING, horizontalFacing.getOpposite())
                        .setValue(AXIS_ALONG_FIRST_COORDINATE, horizontalFacing.getAxis() == Direction.Axis.X);
                default -> toPlace.setValue(FACING, lookPositive ? Direction.WEST : Direction.EAST)
                        .setValue(AXIS_ALONG_FIRST_COORDINATE, false);
            };
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected Direction getFacingForPlacement(BlockPlaceContext context) {
        return context.getClickedFace();
    }

    @Override
    protected boolean getAxisAlignmentForPlacement(BlockPlaceContext context) {
        return context.getHorizontalDirection().getAxis() != Direction.Axis.X;
    }

    public boolean shouldntRenderHeadOnFace(Level level, BlockPos pos, BlockState state, Direction face) {
        if (face.getAxis().isVertical() || face == state.getValue(FACING).getOpposite() || face.getAxis() == getRotationAxis(state)) return true;
        if (getRotationAxis(state) == Direction.Axis.Y && face != state.getValue(FACING)) return true;
        return !Block.shouldRenderFace(state, level, pos, face, pos.relative(face)) && !(level instanceof WrappedLevel);
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!(level.getBlockEntity(pos) instanceof GaugeBlockEntity be)) return;
        if (be.dialTarget == 0) return;
        int color = be.color;

        for (var face : Iterate.directions) {
            if (shouldntRenderHeadOnFace(level, pos, state, face)) continue;

            var rgb = new Color(color).asVectorF();
            var faceVec = Vec3.atLowerCornerOf(face.getNormal());
            var pFacing = Direction.get(Direction.AxisDirection.POSITIVE, face.getAxis());
            var pFaceVec = Vec3.atLowerCornerOf(pFacing.getNormal());
            int count = be.dialTarget > 1 ? 4 : 1;

            if (count == 1 && random.nextFloat() > 1 / 4f) continue;

            for (int i = 0; i < count; i++) {
                var mul = VecHelper.offsetRandomly(Vec3.ZERO, random, .25f)
                        .multiply(new Vec3(1, 1, 1).subtract(pFaceVec))
                        .normalize()
                        .scale(.3f);
                var offset = VecHelper.getCenterOf(pos)
                        .add(faceVec.scale(.55))
                        .add(mul);
                level.addParticle(new DustParticleOptions(rgb, 1), offset.x, offset.y, offset.z, mul.x, mul.y, mul.z);
            }
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return GAUGE.get(state.getValue(FACING), state.getValue(AXIS_ALONG_FIRST_COORDINATE));
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof GaugeBlockEntity be) return Mth.ceil(Mth.clamp(be.dialTarget * 14, 0, 15));
        return 0;
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }

    @Override
    public Class<MultiMeterBE> getBlockEntityClass() {
        return MultiMeterBE.class;
    }

    @Override
    public BlockEntityType<? extends MultiMeterBE> getBlockEntityType() {
        return DesiresBETypes.MULTIMETER.get();
    }
}
