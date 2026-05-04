package dev.lopyluna.dndesires.content.blocks.logistics.fluid_gauge;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class FluidGaugeBE extends SmartBlockEntity implements IHaveGoggleInformation {
    public LerpedFloat target = LerpedFloat.linear();
    public IFluidHandler targetInv = null;

    public FluidGaugeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(1);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null) return;
        target.tickChaser();
    }

    @Override
    public void lazyTick() {
        if (level == null) {
            targetInv = null;
            target.chase(0, 0.125f, LerpedFloat.Chaser.EXP);
            return;
        }

        if (getBoiler(level.getBlockEntity(worldPosition.relative(getBlockState().getValue(FluidGaugeBlock.FACING)))) != null) return;

        if (targetInv != null) {
            var capacity = targetInv.getTankCapacity(0);
            var fluid = targetInv.getFluidInTank(0);
            var amount = fluid.getAmount();
            var targetAmount = (float) amount / (float) capacity;
            target.chase(targetAmount, 0.125f, LerpedFloat.Chaser.EXP);
        } else target.chase(0, 0.125f, LerpedFloat.Chaser.EXP);

        var pos = getBlockPos();
        var state = getBlockState();
        var facing = state.getValue(FluidGaugeBlock.FACING);
        var blockPos = pos.relative(facing);
        var be = level.getBlockEntity(blockPos);
        if (be == null) {
            this.targetInv = null;
            return;
        }
        this.targetInv = level.getCapability(Capabilities.FluidHandler.BLOCK, blockPos, facing);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (level != null && getBoiler(level.getBlockEntity(worldPosition.relative(getBlockState().getValue(FluidGaugeBlock.FACING)))) instanceof FluidTankBlockEntity be) return be.addToGoggleTooltip(tooltip, isPlayerSneaking);

        var result = IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if (targetInv == null) return result;
        containedFluidTooltip(tooltip, isPlayerSneaking, targetInv);
        return result;
    }

    @Nullable
    public FluidTankBlockEntity getBoiler(BlockEntity be) {
        if (be instanceof FluidTankBlockEntity b &&
                b.getControllerBE() instanceof FluidTankBlockEntity tank &&
                tank.boiler != null &&
                (tank.boiler.attachedEngines > 0  || tank.boiler.attachedWhistles > 0)
        ) return tank;
        return null;
    }
}
