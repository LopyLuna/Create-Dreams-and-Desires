package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing;

import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import uwu.lopyluna.create_dd.access.DDTransportedItemStack;
import uwu.lopyluna.create_dd.access.DDTransportedItemStackHandlerBehaviour;

import java.util.ArrayList;
import java.util.List;

public class DDFanProcessing {
    public static boolean canProcess(ItemEntity entity, InterfaceIndustrialProcessingType type) {
        if (entity.getPersistentData()
                .contains("CreateData")) {
            CompoundTag compound = entity.getPersistentData()
                    .getCompound("CreateData");
            if (compound.contains("Processing")) {
                CompoundTag processing = compound.getCompound("Processing");

                if (IndustrialTypeFanProcessing.parseLegacy(processing.getString("Type")) != type)
                    return type.canProcess(entity.getItem(), entity.level);
                else if (processing.getInt("Time") >= 0)
                    return true;
                else if (processing.getInt("Time") == -1)
                    return false;
            }
        }
        return type.canProcess(entity.getItem(), entity.level);
    }

    public static boolean applyProcessing(ItemEntity entity, InterfaceIndustrialProcessingType type) {
        if (decrementProcessingTime(entity, type) != 0)
            return false;
        List<ItemStack> stacks = type.process(entity.getItem(), entity.level);
        if (stacks == null)
            return false;
        if (stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        entity.setItem(stacks.remove(0));
        for (ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entity.getDeltaMovement());
            entity.level.addFreshEntity(entityIn);
        }
        return true;
    }

    public static DDTransportedItemStackHandlerBehaviour.TransportedResult applyProcessing(DDTransportedItemStack transported, Level world, InterfaceIndustrialProcessingType type) {
        DDTransportedItemStackHandlerBehaviour.TransportedResult ignore = DDTransportedItemStackHandlerBehaviour.TransportedResult.doNothing();
        if (transported.processedBy != type) {
            transported.processedBy = type;
            int timeModifierForStackSize = ((transported.stack.getCount() - 1) / 16) + 1;
            int processingTime =
                    (int) (AllConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            transported.processingTime = processingTime;
            if (!type.canProcess(transported.stack, world))
                transported.processingTime = -1;
            return ignore;
        }
        if (transported.processingTime == -1)
            return ignore;
        if (transported.processingTime-- > 0)
            return ignore;

        List<ItemStack> stacks = type.process(transported.stack, world);
        if (stacks == null)
            return ignore;

        List<DDTransportedItemStack> transportedStacks = new ArrayList<>();
        for (ItemStack additional : stacks) {
            DDTransportedItemStack newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedStacks.add(newTransported);
        }
        return DDTransportedItemStackHandlerBehaviour.TransportedResult.convertTo(transportedStacks);
    }

    private static int decrementProcessingTime(ItemEntity entity, InterfaceIndustrialProcessingType type) {
        CompoundTag nbt = entity.getPersistentData();

        if (!nbt.contains("CreateData"))
            nbt.put("CreateData", new CompoundTag());
        CompoundTag createData = nbt.getCompound("CreateData");

        if (!createData.contains("Processing"))
            createData.put("Processing", new CompoundTag());
        CompoundTag processing = createData.getCompound("Processing");

        if (!processing.contains("Type") || IndustrialTypeFanProcessing.parseLegacy(processing.getString("Type")) != type) {
            processing.putString("Type", DDFanProcessingTypeRegistry.getIdOrThrow(type).toString());
            int timeModifierForStackSize = ((entity.getItem()
                    .getCount() - 1) / 16) + 1;
            int processingTime =
                    (int) (AllConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            processing.putInt("Time", processingTime);
        }

        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }
}