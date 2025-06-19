package dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PoweredFlywheelBE extends GeneratingKineticBlockEntity {

    public Direction engineDirection;
    public BlockPos enginePos;
    public float engineEfficiency;
    public int movementDirection;
    public int initialTicks;
    public Block capacityKey;

    LerpedFloat visualSpeed = LerpedFloat.linear();
    float angle;

    public PoweredFlywheelBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        movementDirection = 1;
        initialTicks = 3;
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    @Override
    public void tick() {
        super.tick();
        if (initialTicks > 0) initialTicks--;

        var state = getBlockState();
        if (level != null && level instanceof ServerLevel serverLevel && state.getBlock() instanceof PoweredFlywheelBlock block)
            block.simpleTick(state, serverLevel, getBlockPos(), state.getValue(PoweredFlywheelBlock.FACING), state.getValue(PoweredFlywheelBlock.AXIS));

        if (level == null || !level.isClientSide) return;

        float targetSpeed = getSpeed();
        visualSpeed.updateChaseTarget(targetSpeed);
        visualSpeed.tickChaser();
        angle += visualSpeed.getValue() * 3 / 10f;
        angle %= 360;
    }

    public void update(Direction sourceDirection, BlockPos sourcePos, int direction, float efficiency) {
        enginePos = worldPosition.subtract(sourcePos);
        engineDirection = sourceDirection;
        float prev = engineEfficiency;
        engineEfficiency = efficiency;
        int prevDirection = this.movementDirection;
        if (Mth.equal(efficiency, prev) && prevDirection == direction) return;
        if (level == null) return;
        capacityKey = level.getBlockState(sourcePos).getBlock();
        this.movementDirection = direction;
        updateGeneratedRotation();
    }

    public void remove(BlockPos sourcePos) {
        if (!isPoweredBy(sourcePos)) return;

        enginePos = null;
        engineDirection = null;
        engineEfficiency = 0;
        movementDirection = 0;
        capacityKey = null;
        updateGeneratedRotation();
    }

    public boolean canBePoweredBy(BlockPos globalPos) {
        return initialTicks == 0 && (enginePos == null || isPoweredBy(globalPos));
    }

    public boolean isPoweredBy(BlockPos globalPos) {
        BlockPos key = worldPosition.subtract(globalPos);
        return key.equals(enginePos);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("Direction", movementDirection);
        if (initialTicks > 0)
            compound.putInt("Warmup", initialTicks);
        if (enginePos != null && capacityKey != null) {
            compound.put("EnginePos", NbtUtils.writeBlockPos(enginePos));
            compound.putFloat("EnginePower", engineEfficiency);
            compound.putString("EngineType", RegisteredObjectsHelper.getKeyOrThrow(capacityKey).toString());
        }
        if (engineDirection != null) NBTHelper.writeEnum( compound, "EngineDirection", engineDirection);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (clientPacket) visualSpeed.chase(getGeneratedSpeed(), 1 / 64f, LerpedFloat.Chaser.EXP);
        movementDirection = compound.getInt("Direction");
        initialTicks = compound.getInt("Warmup");
        enginePos = null;
        engineDirection = null;
        engineEfficiency = 0;

        if (compound.contains("EnginePos")) {
            enginePos = NBTHelper.readBlockPos(compound, "EnginePos");
            engineEfficiency = compound.getFloat("EnginePower");
            capacityKey = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(compound.getString("EngineType")));
        }
        if (compound.contains("EngineDirection")) engineDirection = NBTHelper.readEnum(compound, "EngineDirection", Direction.class);
    }

    @Override
    public float getGeneratedSpeed() {
        return getCombinedCapacity() > 0 ? movementDirection * 8 * getSpeedModifier() : 0;
    }

    private float getCombinedCapacity() {
        return capacityKey == null ? 0 : (engineEfficiency * (float) BlockStressValues.getCapacity(capacityKey));
    }

    private int getSpeedModifier() {
        return (int) (1 + (engineEfficiency * 4));
    }

    @Override
    public float calculateAddedStressCapacity() {
        float capacity = getCombinedCapacity();
        this.lastCapacityProvided = capacity;
        return capacity;
    }

    @Override
    public int getRotationAngleOffset(Direction.Axis axis) {
        int combinedCords = axis.choose(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        return super.getRotationAngleOffset(axis) + (combinedCords % 2 == 0 ? 180 : 0);
    }

    public boolean addToEngineTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }
}
