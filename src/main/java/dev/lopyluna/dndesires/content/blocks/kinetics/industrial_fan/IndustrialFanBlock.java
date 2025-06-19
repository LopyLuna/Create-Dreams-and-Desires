package dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.logistics.chute.AbstractChuteBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.createmod.catnip.levelWrappers.WrappedLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IndustrialFanBlock extends DirectionalKineticBlock implements IBE<IndustrialFanBE>, ICogWheel {
    public IndustrialFanBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        blockUpdate(level, pos);
    }
    @Override
    public void updateIndirectNeighbourShapes(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, int flags, int count) {
        super.updateIndirectNeighbourShapes(stateIn, worldIn, pos, flags, count);
        blockUpdate(worldIn, pos);
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        blockUpdate(level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var face = context.getClickedFace();

        var placedOn = level.getBlockState(pos.relative(face.getOpposite()));
        var placedOnOpposite = level.getBlockState(pos.relative(face));
        if (AbstractChuteBlock.isChute(placedOn)) return defaultBlockState().setValue(FACING, face.getOpposite());
        if (AbstractChuteBlock.isChute(placedOnOpposite)) return defaultBlockState().setValue(FACING, face);

        var preferredFacing = getPreferredFacing(context);
        if (preferredFacing == null) preferredFacing = context.getNearestLookingDirection();
        return defaultBlockState().setValue(FACING, context.getPlayer() != null && context.getPlayer().isShiftKeyDown() ? preferredFacing : preferredFacing.getOpposite());
    }
    protected void blockUpdate(LevelAccessor level, BlockPos pos) {
        if (level instanceof WrappedLevel) return;
        notifyFanBlockEntity(level, pos);
        if (level.isClientSide()) return;
        withBlockEntityDo(level, pos, IndustrialFanBE::queueGeneratorUpdate);
    }
    protected void notifyFanBlockEntity(LevelAccessor world, BlockPos pos) {
        withBlockEntityDo(world, pos, IndustrialFanBE::blockInFrontChanged);
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        blockUpdate(context.getLevel(), context.getClickedPos());
        return newState;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public boolean showCapacityWithAnnotation() {
        return true;
    }

    @Override
    public Class<IndustrialFanBE> getBlockEntityClass() {
        return IndustrialFanBE.class;
    }

    @Override
    public BlockEntityType<? extends IndustrialFanBE> getBlockEntityType() {
        return DesiresBETypes.INDUSTRIAL_FAN.get();
    }
}
