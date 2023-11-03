package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing;

import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DDFanProcessingTypeRegistry {
    private static final Map<ResourceLocation, InterfaceIndustrialProcessingType> TYPES = new Object2ReferenceOpenHashMap<>();
    private static final Map<InterfaceIndustrialProcessingType, ResourceLocation> IDS = new Reference2ObjectOpenHashMap<>();
    private static final List<InterfaceIndustrialProcessingType> SORTED_TYPES = new ReferenceArrayList<>();
    private static final List<InterfaceIndustrialProcessingType> SORTED_TYPES_VIEW = Collections.unmodifiableList(SORTED_TYPES);

    public static void register(ResourceLocation id, InterfaceIndustrialProcessingType type) {
        if (TYPES.put(id, type) != null) {
            throw new IllegalArgumentException("Tried to override FanProcessingType registration for id '" + id + "'. This is not supported!");
        }
        ResourceLocation prevId = IDS.put(type, id);
        if (prevId != null) {
            throw new IllegalArgumentException("Tried to register same FanProcessingType instance for multiple ids '" + prevId + "' and '" + id + "'. This is not supported!");
        }
        insertSortedType(type, id);
    }

    private static void insertSortedType(InterfaceIndustrialProcessingType type, ResourceLocation id) {
        int index = Collections.binarySearch(SORTED_TYPES, type, (type1, type2) -> type2.getPriority() - type1.getPriority());
        if (index >= 0) {
            throw new IllegalStateException();
        }
        SORTED_TYPES.add(-index - 1, type);
    }

    @Nullable
    public static InterfaceIndustrialProcessingType getType(ResourceLocation id) {
        return TYPES.get(id);
    }

    public static InterfaceIndustrialProcessingType getTypeOrThrow(ResourceLocation id) {
        InterfaceIndustrialProcessingType type = getType(id);
        if (type == null) {
            throw new IllegalArgumentException("Could not get FanProcessingType for id '" + id + "'!");
        }
        return type;
    }

    @Nullable
    public static ResourceLocation getId(InterfaceIndustrialProcessingType type) {
        return IDS.get(type);
    }

    public static ResourceLocation getIdOrThrow(InterfaceIndustrialProcessingType type) {
        ResourceLocation id = getId(type);
        if (id == null) {
            throw new IllegalArgumentException("Could not get id for FanProcessingType " + type + "!");
        }
        return id;
    }

    public static List<InterfaceIndustrialProcessingType> getSortedTypesView() {
        return SORTED_TYPES_VIEW;
    }
}
