package dev.lopyluna.create_d2d.register;

import com.simibubi.create.Create;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import static dev.lopyluna.create_d2d.DesiresCreate.MOD_ID;

public class DesiresTags {
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }
    public static <T> TagKey<T> commonTag(Registry<T> registry, String path) {
        return optionalTag(registry, ResourceLocation.fromNamespaceAndPath("c", path));
    }
    public static TagKey<Block> commonBlockTag(String path) {
        return commonTag(BuiltInRegistries.BLOCK, path);
    }
    public static TagKey<Item> commonItemTag(String path) {
        return commonTag(BuiltInRegistries.ITEM, path);
    }
    public static TagKey<Fluid> commonFluidTag(String path) {
        return commonTag(BuiltInRegistries.FLUID, path);
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

    public enum BlockTags {
        ARTIFICIAL_ORE_GENERATOR,
        ORE_GENERATOR,
        WEAK_FURNACE,
        STRONG_FURNACE,
        SUPER_STRONG_FURNACE,
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

}
