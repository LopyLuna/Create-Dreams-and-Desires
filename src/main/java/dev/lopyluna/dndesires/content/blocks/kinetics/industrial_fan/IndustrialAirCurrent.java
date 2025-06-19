package dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessing;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import dev.lopyluna.dndesires.mixins.AirCurrentClientAccessor;
import dev.lopyluna.dndesires.mixins.ServerGamePacketListenerImplAccessor;
import dev.lopyluna.dndesires.register.DesiresConfigs;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class IndustrialAirCurrent extends AirCurrent {
    public IndustrialAirCurrent(IAirCurrentSource source) {
        super(source);
    }

    @Override
    protected void tickAffectedEntities(Level level) {
        for (var iterator = caughtEntities.iterator(); iterator.hasNext(); ) {
            var entity = iterator.next();
            if (!entity.isAlive() || !entity.getBoundingBox().intersects(bounds) || isPlayerCreativeFlying(entity)) {
                iterator.remove();
                continue;
            }

            var flow = (pushing ? direction : direction.getOpposite()).getNormal();
            var speed = Math.abs(source.getSpeed());
            var sneakModifier = entity.isShiftKeyDown() ? 4096f : 512f;
            var entityDistance = VecHelper.alignedDistanceToFace(entity.position(), source.getAirCurrentPos(), direction);
            var entityDistanceOld = entity.position().distanceTo(VecHelper.getCenterOf(source.getAirCurrentPos()));
            var acceleration = (float) (speed / sneakModifier / (entityDistanceOld / maxDistance));
            var previousMotion = entity.getDeltaMovement();
            var maxAcceleration = 5;

            var xIn = Mth.clamp(flow.getX() * acceleration - previousMotion.x, -maxAcceleration, maxAcceleration);
            var yIn = Mth.clamp(flow.getY() * acceleration - previousMotion.y, -maxAcceleration, maxAcceleration);
            var zIn = Mth.clamp(flow.getZ() * acceleration - previousMotion.z, -maxAcceleration, maxAcceleration);

            entity.setDeltaMovement(previousMotion.add(new Vec3(xIn, yIn, zIn).scale(1 / 8f)));
            entity.fallDistance = 0;
            if (CatnipServices.PLATFORM.getEnv().isClient()) AirCurrentClientAccessor.enableClientPlayerSound(entity, Mth.clamp(speed / 128f * .4f, 0.01f, .4f));

            if (entity instanceof ServerPlayer serverPlayer) ((ServerGamePacketListenerImplAccessor) serverPlayer.connection).aboveGroundTickCount(0);

            var processingType = getTypeAt((float) entityDistance);

            if (processingType == null) continue;

            if (entity instanceof ItemEntity itemEntity) {
                if (level != null && level.isClientSide) {
                    processingType.spawnProcessingParticles(level, entity.position());
                    continue;
                }
                if (FanProcessing.canProcess(itemEntity, processingType))
                    if (applyProcessing(itemEntity, level, processingType) && source instanceof IndustrialFanBE fan) fan.award(AllAdvancements.FAN_PROCESSING);
                continue;
            }
            if (level != null) processingType.affectEntity(entity, level);
        }
    }

    @Override
    public void tickAffectedHandlers() {
        for (Pair<TransportedItemStackHandlerBehaviour, FanProcessingType> pair : affectedItemHandlers) {
            var handler = pair.getKey();
            var level = handler.getWorld();
            var processingType = pair.getRight();
            if (processingType == null) continue;
            handler.handleProcessingOnAllItems(transported -> {
                if (level.isClientSide) {
                    processingType.spawnProcessingParticles(level, handler.getWorldPositionOf(transported));
                    return TransportedItemStackHandlerBehaviour.TransportedResult.doNothing();
                }
                var applyProcessing = applyProcessing(transported, level, processingType);
                if (!applyProcessing.doesNothing() && source instanceof IndustrialFanBE fan) fan.award(AllAdvancements.FAN_PROCESSING);
                return applyProcessing;
            });
        }
    }

    public static boolean applyProcessing(ItemEntity entity, Level level, FanProcessingType type) {
        if (decrementProcessingTime(entity, type) != 0) return false;
        var stacks = type.process(entity.getItem(), level);
        if (stacks == null) return false;
        if (stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        entity.setItem(stacks.removeFirst());
        for (ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entity.getDeltaMovement());
            level.addFreshEntity(entityIn);
        }
        return true;
    }

    public static TransportedItemStackHandlerBehaviour.TransportedResult applyProcessing(TransportedItemStack transported, Level level, FanProcessingType type) {
        var ignore = TransportedItemStackHandlerBehaviour.TransportedResult.doNothing();
        if (transported.processedBy != type) {
            transported.processedBy = type;
            int timeModifierForStackSize = ((transported.stack.getCount() - 1) / 16) + 1;
            transported.processingTime = (DesiresConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            if (!type.canProcess(transported.stack, level)) transported.processingTime = -1;
            return ignore;
        }
        if (transported.processingTime == -1) return ignore;
        if (transported.processingTime-- > 0) return ignore;

        var stacks = type.process(transported.stack, level);
        if (stacks == null) return ignore;

        List<TransportedItemStack> transportedStacks = new ArrayList<>();
        for (ItemStack additional : stacks) {
            var newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedStacks.add(newTransported);
        }
        return TransportedItemStackHandlerBehaviour.TransportedResult.convertTo(transportedStacks);
    }

    private static int decrementProcessingTime(ItemEntity entity, FanProcessingType type) {
        var nbt = entity.getPersistentData();
        if (!nbt.contains("CreateData")) nbt.put("CreateData", new CompoundTag());
        var createData = nbt.getCompound("CreateData");
        if (!createData.contains("Processing")) createData.put("Processing", new CompoundTag());
        var processing = createData.getCompound("Processing");
        if (!processing.contains("Type") || AllFanProcessingTypes.parseLegacy(processing.getString("Type")) != type) {
            var key = CreateBuiltInRegistries.FAN_PROCESSING_TYPE.getKey(type);
            if (key == null) throw new IllegalArgumentException("Could not get id for FanProcessingType " + type + "!");
            processing.putString("Type", key.toString());
            int timeModifierForStackSize = ((entity.getItem().getCount() - 1) / 16) + 1;
            int processingTime = (DesiresConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            processing.putInt("Time", processingTime);
        }
        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }
}
