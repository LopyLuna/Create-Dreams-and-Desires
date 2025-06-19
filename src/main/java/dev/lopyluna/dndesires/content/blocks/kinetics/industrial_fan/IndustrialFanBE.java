package dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.logistics.chute.ChuteBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresConfigs;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IndustrialFanBE extends GeneratingKineticBlockEntity implements IAirCurrentIndustrialSource {

    protected boolean isGenerator;
    protected boolean updateGenerator;
    public IndustrialAirCurrent airCurrent;
    protected int airCurrentUpdateCooldown;
    protected int entitySearchCooldown;
    protected boolean updateAirFlow;

    public IndustrialFanBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        isGenerator = false;
        updateGenerator = false;
        airCurrent = new IndustrialAirCurrent(this);
        updateAirFlow = true;
    }

    @Override
    public @Nullable AirCurrent getAirCurrent() {
        return airCurrent;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.ENCASED_FAN, AllAdvancements.FAN_PROCESSING);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (!wasMoved) isGenerator = compound.getBoolean("Generating");
        if (clientPacket) airCurrent.rebuild();
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("Generating", isGenerator);
        super.write(compound, registries, clientPacket);
    }

    @Override
    public float calculateAddedStressCapacity() {
        return isGenerator ? super.calculateAddedStressCapacity() : 0;
    }

    @Override
    public float calculateStressApplied() {
        return isGenerator ? 0 : super.calculateStressApplied();
    }

    @Override
    public float getGeneratedSpeed() {
        return isGenerator ? 64 : 0;
    }

    public void queueGeneratorUpdate() {
        updateGenerator = true;
    }

    public void updateGenerator() {
        var blockState = getBlockState();
        var shouldGenerate = DesiresBlocks.INDUSTRIAL_FAN.has(blockState);

        if (shouldGenerate && blockState.getValue(IndustrialFanBlock.FACING) != Direction.DOWN && !blockBelowIsHot())
            shouldGenerate = false;

        if (shouldGenerate) shouldGenerate = level != null && blockBelowIsHot() && (level.hasSignal(worldPosition, Direction.DOWN) || level.hasNeighborSignal(worldPosition.below())) && blockState.getValue(IndustrialFanBlock.FACING) == Direction.DOWN;

        if (shouldGenerate == isGenerator) return;
        isGenerator = shouldGenerate;
        updateGeneratedRotation();
    }

    public boolean blockBelowIsHot() {
        assert level != null;
        var fluidState = level.getFluidState(worldPosition.below());
        if (DesiresTags.FluidTags.INDUSTRIAL_FAN_HEATER.is(fluidState)) return true;
        var blockState = level.getBlockState(worldPosition.below());
        if (DesiresTags.BlockTags.INDUSTRIAL_FAN_HEATER.is(blockState)) return !blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) || blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING);
        return false;
    }

    @Override
    public @Nullable Level getAirCurrentWorld() {
        return level;
    }

    @Override
    public @NotNull BlockPos getAirCurrentPos() {
        return worldPosition;
    }

    @Override
    public @NotNull Direction getAirflowOriginSide() {
        return this.getBlockState().getValue(IndustrialFanBlock.FACING);
    }

    @Override
    public @Nullable Direction getAirFlowDirection() {
        var speed = getSpeed();
        if (speed == 0) return null;
        var facing = getBlockState().getValue(BlockStateProperties.FACING);
        speed = convertToDirection(speed, facing);
        return speed > 0 ? facing : facing.getOpposite();
    }

    @Override
    public void remove() {
        super.remove();
        updateChute();
    }

    @Override
    public boolean isSourceRemoved() {
        return remove;
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        updateAirFlow = true;
        updateChute();
    }

    public void updateChute() {
        assert level != null;
        var direction = getBlockState().getValue(IndustrialFanBlock.FACING);
        if (!direction.getAxis().isVertical()) return;
        var poweredChute = level.getBlockEntity(worldPosition.relative(direction));
        if (!(poweredChute instanceof ChuteBlockEntity chuteBE)) return;
        if (direction == Direction.DOWN) chuteBE.updatePull();
        else chuteBE.updatePush(1);
    }

    public void blockInFrontChanged() {
        updateAirFlow = true;
    }

    @Override
    public void tick() {
        super.tick();
        assert level != null;
        var server = !level.isClientSide || isVirtual();

        if (server && airCurrentUpdateCooldown-- <= 0) {
            airCurrentUpdateCooldown = DesiresConfigs.server().kinetics.fanBlockCheckRate.get();
            updateAirFlow = true;
        }

        if (updateAirFlow) {
            updateAirFlow = false;
            airCurrent.rebuild();
            if (airCurrent.maxDistance > 0) award(AllAdvancements.ENCASED_FAN);
            sendData();
        }

        if (updateGenerator) {
            updateGenerator = false;
            updateGenerator();
        }

        if (isGenerator || getSpeed() == 0) return;

        if (entitySearchCooldown-- <= 0) {
            entitySearchCooldown = 5;
            airCurrent.findEntities();
        }
        airCurrent.tick();
    }
}
