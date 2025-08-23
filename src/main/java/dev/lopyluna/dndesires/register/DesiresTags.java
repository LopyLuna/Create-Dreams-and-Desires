package dev.lopyluna.dndesires.register;

import com.simibubi.create.Create;
import dev.lopyluna.dndesires.DnDesires;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public class DesiresTags {
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }
    public static <T> TagKey<T> commonTag(Registry<T> registry, String path) {
        return optionalTag(registry, ResourceLocation.fromNamespaceAndPath("c", path));
    }
    public static <T> TagKey<T> modTag(Registry<T> registry, String path) {
        return optionalTag(registry, DnDesires.loc(path));
    }
    public static TagKey<Fluid> commonFluidTag(String path) {
        return commonTag(BuiltInRegistries.FLUID, path);
    }
    public static TagKey<Block> commonBlockTag(String path) {
        return commonTag(BuiltInRegistries.BLOCK, path);
    }
    public static TagKey<Item> commonItemTag(String path) {
        return commonTag(BuiltInRegistries.ITEM, path);
    }
    public static TagKey<Fluid> modFluidTag(String path) {
        return modTag(BuiltInRegistries.FLUID, path);
    }
    public static TagKey<Block> modBlockTag(String path) {
        return modTag(BuiltInRegistries.BLOCK, path);
    }
    public static TagKey<Item> modItemTag(String path) {
        return modTag(BuiltInRegistries.ITEM, path);
    }

    public enum NameSpace {
        MOD(MOD_ID, false, true),
        COMMON("c"),
        CREATE(Create.ID);

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;
        NameSpace(String id) {
            this(id, true, false);
        }
        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum FluidTags {
        FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING(NameSpace.MOD, "fan_processing_catalysts/dragon_breathing"),
        FAN_PROCESSING_CATALYSTS_SANDING(NameSpace.MOD, "fan_processing_catalysts/sanding"),
        FAN_PROCESSING_CATALYSTS_FREEZING(NameSpace.MOD, "fan_processing_catalysts/freezing"),
        FAN_PROCESSING_CATALYSTS_SEETHING(NameSpace.MOD, "fan_processing_catalysts/seething"),
        INDUSTRIAL_FAN_HEATER,
        INDUSTRIAL_FAN_TRANSPARENT
        ;
        public final TagKey<Fluid> tag;
        public final boolean alwaysDatagen;

        FluidTags() { this(NameSpace.MOD); }
        FluidTags(NameSpace namespace) { this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        FluidTags(NameSpace namespace, String path) { this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        FluidTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) { this(namespace, null, optional, alwaysDatagen); }
        FluidTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) tag = optionalTag(BuiltInRegistries.FLUID, id);
            else tag = net.minecraft.tags.FluidTags.create(id);
            this.alwaysDatagen = alwaysDatagen;
        }
        @SuppressWarnings("deprecation")
        public boolean is(Fluid fluid) { return fluid.builtInRegistryHolder().is(tag); }
        public boolean is(ItemStack stack) { return stack != null && stack.getItem() instanceof BucketItem bucket && is(bucket.content); }
        public boolean is(ItemLike item) { return item instanceof BucketItem bucket && is(bucket.content); }
        public boolean is(FluidState state) {return state.is(tag);}
        public boolean is(TagKey<Fluid> tag) {return tag==this.tag;}

        private static void init() {}
    }

    public enum BlockTags {
        FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING(NameSpace.MOD, "fan_processing_catalysts/dragon_breathing"),
        FAN_PROCESSING_CATALYSTS_SANDING(NameSpace.MOD, "fan_processing_catalysts/sanding"),
        FAN_PROCESSING_CATALYSTS_FREEZING(NameSpace.MOD, "fan_processing_catalysts/freezing"),
        FAN_PROCESSING_CATALYSTS_SEETHING(NameSpace.MOD, "fan_processing_catalysts/seething"),
        FAN_CATALYSTS_DRAGON_SUPPORT,
        INDUSTRIAL_FAN_HEATER,
        INDUSTRIAL_FAN_TRANSPARENT,
        DYED_BLOCKS,
        ARTIFICIAL_ORE_GENERATOR,
        ORE_GENERATOR,
        WEAK_FURNACE,
        STRONG_FURNACE,
        SUPER_STRONG_FURNACE,
        MINEABLE_WITH_DRILL,
        EXCAVATION_DRILL_VEIN_VALID,
        EXCAVATION_DRILL_VEIN_LARGE,
        ;
        public final TagKey<Block> tag;
        public final boolean alwaysDatagen;

        BlockTags() { this(NameSpace.MOD); }
        BlockTags(NameSpace namespace) { this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        BlockTags(NameSpace namespace, String path) { this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        BlockTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) { this(namespace, null, optional, alwaysDatagen); }
        BlockTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) tag = optionalTag(BuiltInRegistries.BLOCK, id);
            else tag = net.minecraft.tags.BlockTags.create(id);
            this.alwaysDatagen = alwaysDatagen;
        }
        @SuppressWarnings("deprecation")
        public boolean is(Block block) { return block.builtInRegistryHolder().is(tag); }
        public boolean is(ItemStack stack) { return stack != null && stack.getItem() instanceof BlockItem blockItem && is(blockItem.getBlock()); }
        public boolean is(ItemLike item) { return item instanceof BlockItem blockItem && is(blockItem.getBlock()); }
        public boolean is(BlockState state) {return state.is(tag);}
        public boolean is(TagKey<Block> tag) {return tag==this.tag;}

        private static void init() {}
    }

    public enum ItemTags {
        DYED_BLOCKS,
        PALETTE_BLOCKS
        ;
        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        ItemTags() { this(NameSpace.MOD); }
        ItemTags(NameSpace namespace) { this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        ItemTags(NameSpace namespace, String path) { this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault); }
        ItemTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) { this(namespace, null, optional, alwaysDatagen); }
        ItemTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) tag = optionalTag(BuiltInRegistries.ITEM, id);
            else tag = net.minecraft.tags.ItemTags.create(id);
            this.alwaysDatagen = alwaysDatagen;
        }
        @SuppressWarnings("deprecation")
        public boolean matches(Item item) { return item.builtInRegistryHolder().is(tag); }
        public boolean matches(ItemStack stack) { return stack.is(tag); }
        private static void init() {}
    }

    public static void init() {
        FluidTags.init();
        BlockTags.init();
        ItemTags.init();
    }
}
