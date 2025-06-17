package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static dev.lopyluna.dndesires.DnDesires.*;

@SuppressWarnings("all")
public class DesiresCreativeTabs {

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_TAB = REGISTER.creativeTab().register("base_tab", () -> CreativeModeTab.builder()
            .title(Component.translatableWithFallback("itemGroup." + MOD_ID + ".base", NAME))
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
            .icon(DesiresBlocks.HYDRAULIC_PRESS::asStack)
            .build());

    public static void register() {}

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey().equals(DesiresCreativeTabs.BASE_TAB.getKey())) {
        //    for (RegistryEntry<Item, Item> entry : REG.getAll(Registries.ITEM)) {
        //        Item item = entry.get();
        //        if (item instanceof BlockItem) continue;
        //        if (item instanceof BucketItem) continue;
        //        event.accept(item);
        //    }
        //    for (RegistryEntry<Block, Block> entry : REG.getAll(Registries.BLOCK)) {
        //        var block = entry.get();
        //        var stack = block.asItem().getDefaultInstance();
        //        if (block.asItem() == Items.AIR) continue;
        //        if (DesiresTags.ItemTags.DYED_BLOCKS.matches(stack)) continue;
        //        if (stack.is(DesiresTags.ItemTags.PALETTE_BLOCKS.tag)) continue;
        //        event.accept(block);
        //    }
        //    for (RegistryEntry<Fluid, Fluid> entry : REG.getAll(Registries.FLUID)) {
        //        var fluid = entry.get();
        //        if (fluid.defaultFluidState().isSource()) {
        //            event.accept(fluid.getBucket());
        //        }
        //    }
        //}
    }
}
