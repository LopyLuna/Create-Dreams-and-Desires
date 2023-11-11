package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import java.util.List;

import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class EngineBlockEntity extends SmartBlockEntity {

    public float appliedCapacity;
    public float appliedSpeed;
    protected FlywheelBlockEntity poweredWheel;

    public EngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(1.5f);
    }

    @Override
    public void tick() {
        super.tick();
        assert level != null;
        if (level.isClientSide)
            return;
        if (poweredWheel != null && poweredWheel.isRemoved())
            poweredWheel = null;
        if (poweredWheel == null)
            attachWheel();
    }

    public void attachWheel() {
        Direction engineFacing = getBlockState().getValue(EngineBlock.FACING);
        BlockPos wheelPos = worldPosition.relative(engineFacing, 2);
        assert level != null;
        BlockState wheelState = level.getBlockState(wheelPos);
        if (!DDBlocks.FLYWHEEL.has(wheelState))
            return;
        Direction wheelFacing = wheelState.getValue(FlywheelBlock.HORIZONTAL_FACING);
        if (wheelFacing.getAxis() != engineFacing.getClockWise().getAxis())
            return;
        if (FlywheelBlock.isConnected(wheelState)
                && FlywheelBlock.getConnection(wheelState) != engineFacing.getOpposite())
            return;
        BlockEntity be = level.getBlockEntity(wheelPos);
        assert be != null;
        if (be.isRemoved())
            return;
        if (be instanceof FlywheelBlockEntity) {
            if (!FlywheelBlock.isConnected(wheelState))
                FlywheelBlock.setConnection(level, be.getBlockPos(), be.getBlockState(), engineFacing.getOpposite());
            poweredWheel = (FlywheelBlockEntity) be;
            refreshWheelSpeed();
        }
    }

    public void detachWheel() {
        if (poweredWheel == null || poweredWheel.isRemoved())
            return;
        poweredWheel.setRotation(0, 0);
        FlywheelBlock.setConnection(level, poweredWheel.getBlockPos(), poweredWheel.getBlockState(), null);
        poweredWheel = null;
    }

    @Override
    public void remove() {
        detachWheel();
        super.remove();
    }

    protected void refreshWheelSpeed() {
        if (poweredWheel == null)
            return;
        poweredWheel.setRotation(appliedSpeed, appliedCapacity);
    }

}