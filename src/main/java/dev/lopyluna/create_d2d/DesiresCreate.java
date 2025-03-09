package dev.lopyluna.create_d2d;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.create_d2d.register.*;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@Mod(DesiresCreate.MOD_ID)
public class DesiresCreate {
    public static final String NAME = "Create: Desires 2 Dreams";
    public static final String MOD_ID = "create_d2d";

    public static final CreateRegistrate REG = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    static {
        REG.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(create(item))));
    }

    public DesiresCreate(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        REG.registerEventListeners(modEventBus);

        DesiresCreativeTabs.register(modEventBus);
        DesiresTags.init();
        DesiresItems.register();
        DesiresBlocks.register();
        DesiresBETypes.register();
        DesiresFluids.register();
        DesiresStoneTypes.register(REG);

        DesiresConfigs.register(modLoadingContext, modContainer);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(DesiresCreate::init);
        //modEventBus.addListener(DesiresCreate::onRegister);
        modEventBus.addListener(EventPriority.LOWEST, DesiresDatagen::gatherData);
    }
    public static void init(final FMLCommonSetupEvent event) {
        DesiresFluids.registerFluidInteractions();
    }
    public static void onRegister(final RegisterEvent event) {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(DesiresCreativeTabs.BASE_CREATIVE_TAB.getKey())) {
            for (RegistryEntry<Item, Item> entry : REG.getAll(Registries.ITEM)) {
                Item item = entry.get();
                if (item instanceof BlockItem) continue;
                if (item instanceof BucketItem) continue;
                event.accept(item);
            }
            for (RegistryEntry<Block, Block> entry : REG.getAll(Registries.BLOCK)) {
                var block = entry.get();
                var stack = block.asItem().getDefaultInstance();
                if (block.asItem() == Items.AIR) continue;
                if (DesiresTags.ItemTags.DYED_BLOCKS.matches(stack)) continue;
                if (stack.is(DesiresTags.ItemTags.PALETTE_BLOCKS.tag)) continue;
                event.accept(block);
            }
            for (RegistryEntry<Fluid, Fluid> entry : REG.getAll(Registries.FLUID)) {
                var fluid = entry.get();
                if (fluid.defaultFluidState().isSource()) {
                    event.accept(fluid.getBucket());
                }
            }
        }
        if (event.getTabKey().equals(DesiresCreativeTabs.PALETTES_CREATIVE_TAB.getKey())) {
            for (RegistryEntry<Block, Block> entry : REG.getAll(Registries.BLOCK)) {
                var block = entry.get();
                var stack = block.asItem().getDefaultInstance();
                if (block.asItem() == Items.AIR) continue;
                if (!(stack.is(DesiresTags.ItemTags.PALETTE_BLOCKS.tag) || DesiresTags.ItemTags.DYED_BLOCKS.matches(stack))) continue;
                event.accept(stack);
            }
        }
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
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof IRotate || block instanceof StirlingEngineBlock) return new KineticStats(block);
        }
        return null;
    }
}
