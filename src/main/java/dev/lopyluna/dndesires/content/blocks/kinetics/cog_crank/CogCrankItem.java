package dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import static com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock.isValidCogwheelPosition;

public class CogCrankItem extends BlockItem {
    boolean large;

    private final int placementHelperId;
    private final int integratedCogHelperId;

    public CogCrankItem(CogCrankBlock block, Properties properties) {
        super(block, properties);
        large = block.isLarge;

        placementHelperId = PlacementHelpers.register(large ? new LargeCogHelper() : new SmallCogHelper());
        integratedCogHelperId = PlacementHelpers.register(large ? new IntegratedLargeCogHelper() : new IntegratedSmallCogHelper());
    }

    @Override
    public @NotNull InteractionResult onItemUseFirst(@NotNull ItemStack stack, UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        var helper = PlacementHelpers.get(placementHelperId);
        var player = context.getPlayer();
        var ray = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, true);
        var rayAlt = player != null ? CogCrankItem.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE) : ray;
        if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) return helper.getOffset(player, level, state, pos, ray).placeInWorld(level, this, player, context.getHand(), ray).result();

        if (integratedCogHelperId != -1) {
            helper = PlacementHelpers.get(integratedCogHelperId);
            if (helper.matchesState(state) && player != null && !player.isShiftKeyDown()) return helper.getOffset(player, level, state, pos, ray).placeInWorld(level, this, player, context.getHand(), ray).result();
        }
        return super.onItemUseFirst(stack, context);
    }

    @MethodsReturnNonnullByDefault
    private static class SmallCogHelper extends DiagonalCogHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isSmallCogItem).and(ICogWheel::isDedicatedCogItem);
        }
        @Override
        public PlacementOffset getOffset(@NotNull Player player, @NotNull Level level, BlockState state, @NotNull BlockPos pos, @NotNull BlockHitResult ray) {
            if (hitOnShaft(state, ray)) return PlacementOffset.fail();
            if (!ICogWheel.isLargeCog(state) && state.getBlock() instanceof IRotate rot) {
                var axis = rot.getRotationAxis(state);
                for (var dir : IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis)) {
                    var newPos = pos.relative(dir);

                    if (!isValidCogwheelPosition(false, level, newPos, axis)) continue;

                    if (!level.getBlockState(newPos).canBeReplaced()) continue;
                    var axisProp = BlockStateProperties.AXIS;
                    var facingProp = BlockStateProperties.FACING;
                    return PlacementOffset.success(newPos, s -> s.hasProperty(axisProp) ? s.setValue(axisProp, axis) : s.hasProperty(facingProp) ? s.setValue(facingProp, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE)) : s);
                }
                return PlacementOffset.fail();
            }
            return super.getOffset(player, level, state, pos, ray);
        }
    }

    @MethodsReturnNonnullByDefault
    private static class LargeCogHelper extends DiagonalCogHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isLargeCogItem).and(ICogWheel::isDedicatedCogItem);
        }
        @Override
        public PlacementOffset getOffset(@NotNull Player player, @NotNull Level level, BlockState state, @NotNull BlockPos pos, @NotNull BlockHitResult ray) {
            if (hitOnShaft(state, ray)) return PlacementOffset.fail();
            if (ICogWheel.isLargeCog(state) && state.getBlock() instanceof IRotate rot) {
                var axis = rot.getRotationAxis(state);
                var side = IPlacementHelper.orderedByDistanceOnlyAxis(pos, ray.getLocation(), axis).getFirst();
                for (var dir : IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis)) {
                    var newPos = pos.relative(dir).relative(side);
                    if (!isValidCogwheelPosition(true, level, newPos, dir.getAxis())) continue;
                    if (!level.getBlockState(newPos).canBeReplaced()) continue;
                    var axisProp = BlockStateProperties.AXIS;
                    var facingProp = BlockStateProperties.FACING;
                    return PlacementOffset.success(newPos, s -> s.hasProperty(axisProp) ? s.setValue(axisProp, dir.getAxis()) : s.hasProperty(facingProp) ? s.setValue(facingProp, Direction.fromAxisAndDirection(dir.getAxis(), Direction.AxisDirection.POSITIVE)) : s);
                }
                return PlacementOffset.fail();
            }
            return super.getOffset(player, level, state, pos, ray);
        }
    }

    @MethodsReturnNonnullByDefault
    public abstract static class DiagonalCogHelper implements IPlacementHelper {
        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> ICogWheel.isSmallCog(s) || ICogWheel.isLargeCog(s);
        }
        @Override
        public PlacementOffset getOffset(@NotNull Player player, @NotNull Level level, BlockState state, @NotNull BlockPos pos, @NotNull BlockHitResult ray) {
            if (state.getBlock() instanceof IRotate rot) {
                var axis = rot.getRotationAxis(state);
                var closest = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis).getFirst();
                for (var dir : IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), axis, d -> d.getAxis() != closest.getAxis())) {
                    var newPos = pos.relative(dir).relative(closest);
                    if (!level.getBlockState(newPos).canBeReplaced()) continue;

                    if (!isValidCogwheelPosition(ICogWheel.isLargeCog(state), level, newPos, axis)) continue;
                    var axisProp = BlockStateProperties.AXIS;
                    var facingProp = BlockStateProperties.FACING;
                    return PlacementOffset.success(newPos, s -> s.hasProperty(axisProp) ? s.setValue(axisProp, axis) : s.hasProperty(facingProp) ? s.setValue(facingProp, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE)) : s);
                }
            }
            return PlacementOffset.fail();
        }
        protected boolean hitOnShaft(BlockState state, BlockHitResult ray) {
            return state.getBlock() instanceof IRotate rot && AllShapes.SIX_VOXEL_POLE.get(rot.getRotationAxis(state)).bounds()
                    .inflate(0.001).contains(ray.getLocation().subtract(ray.getLocation().align(Iterate.axisSet)));
        }
    }

    @MethodsReturnNonnullByDefault
    public static class IntegratedLargeCogHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isLargeCogItem).and(ICogWheel::isDedicatedCogItem);
        }
        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> !ICogWheel.isDedicatedCogWheel(s.getBlock()) && ICogWheel.isSmallCog(s);
        }
        @Override
        public PlacementOffset getOffset(@NotNull Player player, @NotNull Level level, BlockState state, @NotNull BlockPos pos, BlockHitResult ray) {
            var face = ray.getDirection();
            Direction.Axis newAxis;
            if (state.hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING)) newAxis = state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING).getAxis();
            else if (state.hasProperty(DirectionalKineticBlock.FACING)) newAxis = state.getValue(DirectionalKineticBlock.FACING).getAxis();
            else if (state.hasProperty(RotatedPillarKineticBlock.AXIS)) newAxis = state.getValue(RotatedPillarKineticBlock.AXIS);
            else newAxis = Direction.Axis.Y;

            if (face.getAxis() == newAxis) return PlacementOffset.fail();

            for (var dir : IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), face.getAxis(), newAxis)) {
                var newPos = pos.relative(face).relative(dir);
                if (!level.getBlockState(newPos).canBeReplaced()) continue;

                if (!isValidCogwheelPosition(false, level, newPos, newAxis)) return PlacementOffset.fail();
                var axisProp = BlockStateProperties.AXIS;
                var facingProp = BlockStateProperties.FACING;
                return PlacementOffset.success(newPos, s -> s.hasProperty(axisProp) ? s.setValue(axisProp, newAxis) : s.hasProperty(facingProp) ? s.setValue(facingProp, Direction.fromAxisAndDirection(newAxis, Direction.AxisDirection.POSITIVE)) : s);
            }
            return PlacementOffset.fail();
        }

    }

    @MethodsReturnNonnullByDefault
    public static class IntegratedSmallCogHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return ((Predicate<ItemStack>) ICogWheel::isSmallCogItem).and(ICogWheel::isDedicatedCogItem);
        }
        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> !ICogWheel.isDedicatedCogWheel(s.getBlock()) && ICogWheel.isSmallCog(s);
        }
        @Override
        public PlacementOffset getOffset(@NotNull Player player, @NotNull Level level, BlockState state, @NotNull BlockPos pos, BlockHitResult ray) {
            var face = ray.getDirection();
            Direction.Axis newAxis;

            if (state.hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING)) newAxis = state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING).getAxis();
            else if (state.hasProperty(DirectionalKineticBlock.FACING)) newAxis = state.getValue(DirectionalKineticBlock.FACING).getAxis();
            else if (state.hasProperty(RotatedPillarKineticBlock.AXIS)) newAxis = state.getValue(RotatedPillarKineticBlock.AXIS);
            else newAxis = Direction.Axis.Y;

            if (face.getAxis() == newAxis) return PlacementOffset.fail();

            for (var d : IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), newAxis)) {
                var newPos = pos.relative(d);
                if (!level.getBlockState(newPos).canBeReplaced()) continue;

                if (!isValidCogwheelPosition(false, level, newPos, newAxis)) return PlacementOffset.fail();

                var axisProp = BlockStateProperties.AXIS;
                var facingProp = BlockStateProperties.FACING;
                return PlacementOffset.success().at(newPos).withTransform(s -> s.hasProperty(axisProp) ? s.setValue(axisProp, newAxis) : s.hasProperty(facingProp) ? s.setValue(facingProp, Direction.fromAxisAndDirection(newAxis, Direction.AxisDirection.POSITIVE)) : s);
            }
            return PlacementOffset.fail();
        }
    }
}
