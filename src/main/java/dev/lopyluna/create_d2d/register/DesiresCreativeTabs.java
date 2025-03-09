package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.lopyluna.create_d2d.DesiresCreate.MOD_ID;

@SuppressWarnings("all")
public class DesiresCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_CREATIVE_TAB = REG.register("base", () -> CreativeModeTab.builder()
            .title(Component.translatableWithFallback("itemGroup.create_d2d.base", "DnDesires"))
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
            .icon(DesiresBlocks.HYDRAULIC_PRESS::asStack)
            .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PALETTES_CREATIVE_TAB = REG.register("palettes", () -> CreativeModeTab.builder()
            .title(Component.translatableWithFallback("itemGroup.create_d2d.palettes", "DnDesires Palette Blocks"))
            .withTabsBefore(BASE_CREATIVE_TAB.getKey())
            .icon(DesiresBlocks.PROPELLER::asStack)
            .build());

    public static void register(IEventBus modEventBus) {
        REG.register(modEventBus);
    }
}
