package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllCreativeModeTabs;
import dev.lopyluna.dndesires.content.items.packages.BurstPackageItem;
import net.minecraft.core.registries.Registries;
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
        if (event.getTabKey().equals(DesiresCreativeTabs.BASE_TAB.getKey())) for (var entry : REG.getAll(Registries.ITEM)) if (entry.get() instanceof BurstPackageItem item) event.remove(item.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}
