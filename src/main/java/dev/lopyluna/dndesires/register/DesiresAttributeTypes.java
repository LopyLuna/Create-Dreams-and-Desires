package dev.lopyluna.dndesires.register;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.simibubi.create.content.logistics.item.filter.attribute.SingletonItemAttribute;
import dev.lopyluna.dndesires.DnDesires;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.BiPredicate;

@SuppressWarnings("unused")
public class DesiresAttributeTypes {
    public static final ItemAttributeType DRAGON_BREATHING = singleton("dragon_breathing", DesiresFanProcessingTypes.DRAGON_BREATHING_TYPE::canProcess);
    public static final ItemAttributeType SANDING = singleton("sanding", DesiresFanProcessingTypes.SANDING_TYPE::canProcess);
    public static final ItemAttributeType FREEZING = singleton("freezing", DesiresFanProcessingTypes.FREEZING_TYPE::canProcess);
    public static final ItemAttributeType SEETHING = singleton("seething", DesiresFanProcessingTypes.SEETHING_TYPE::canProcess);

    private static ItemAttributeType singleton(String id, BiPredicate<ItemStack, Level> predicate) {
        return register(id, new SingletonItemAttribute.Type(type -> new SingletonItemAttribute(type, predicate, id)));
    }

    private static ItemAttributeType register(String id, ItemAttributeType type) {
        return Registry.register(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE, DnDesires.loc(id), type);
    }

    public static void init() {
    }
}
