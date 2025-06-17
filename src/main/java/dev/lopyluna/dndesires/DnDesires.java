package dev.lopyluna.dndesires;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.dndesires.content.utils.DnDesiresRegistry;
import dev.lopyluna.dndesires.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import static dev.lopyluna.dndesires.register.DesiresCreativeTabs.BASE_TAB;

@SuppressWarnings("unused")
@Mod(DnDesires.MOD_ID)
public class DnDesires {
    public static final String NAME = "DnDesires";
    public static final String MOD_ID = "dndesires";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static DnDesiresRegistry REGISTER = new DnDesiresRegistry(MOD_ID);
    public static CreateRegistrate REG = CreateRegistrate.create(MOD_ID);

    static {
        REG.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(create(item))));
    }

    public DnDesires(IEventBus modEventBus, ModContainer modContainer) {
        REGISTER.register(modEventBus);
        DesiresCreativeTabs.register();
        var context = ModLoadingContext.get();
        REG.registerEventListeners(modEventBus);
        REG.defaultCreativeTab(BASE_TAB, "base_tab");

        DesiresLangPartial.addTranslations();
        DesiresTags.init();
        DesiresItems.register();
        DesiresBlocks.register();
        DesiresBETypes.register();
        DesiresFluids.register();
        DesiresStoneTypes.register(REG);
        DesiresRotationPropagation.register();
        DesiresRecipeTypes.register(modEventBus);

        DesiresConfigs.register(context, modContainer);

        modEventBus.addListener(DesiresCreativeTabs::addCreative);
        modEventBus.addListener(DnDesires::init);
        modEventBus.addListener(EventPriority.HIGHEST, DesiresDatagen::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, DesiresDatagen::gatherData);

        if (ModList.get().isLoaded("capix")) {
            net.yeoxuhang.capix.api.CapixApi.registerCape(MOD_ID, "DnDesires 1mil downloads",
                    "https://github.com/LopyLuna/Obelisb/blob/1.21/dndesire_one_mil_cape.png?raw=true",
                    "https://raw.githubusercontent.com/LopyLuna/LunaResources/refs/heads/main/dndesires1mil.txt");
        }
    }

    public static void init(final FMLCommonSetupEvent event) {
        DesiresFluids.registerFluidInteractions();
    }

    public static LangBuilder lang() {
        return new LangBuilder(MOD_ID);
    }
    public static ResourceLocation loc(String loc) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, loc);
    }
    public static ResourceLocation emptyLoc() {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "empty");
    }

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof Block block)
            if (block instanceof IRotate || block instanceof StirlingEngineBlock) return new KineticStats(block);
        return null;
    }
}
