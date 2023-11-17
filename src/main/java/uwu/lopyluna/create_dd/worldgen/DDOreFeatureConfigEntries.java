package uwu.lopyluna.create_dd.worldgen;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.DynamicDataProvider;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.infrastructure.worldgen.AllLayerPatterns;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.worldgen.FeatureShits.DDOreFeatureConfigEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings({"all"})
public class DDOreFeatureConfigEntries {
    
    private static final Predicate<BiomeLoadingEvent> OVERWORLD_BIOMES = event -> {
        Biome.BiomeCategory category = event.getCategory();
        return category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NONE;
    };
    private static final Predicate<BiomeLoadingEvent> IS_SAVANNA = event -> {
        Biome.BiomeCategory category = event.getCategory();
        return category == Biome.BiomeCategory.SAVANNA;
    };
    private static final Predicate<BiomeLoadingEvent> IS_BADLANDS = event -> {
        Biome.BiomeCategory category = event.getCategory();
        return category == Biome.BiomeCategory.MESA;
    };
    private static final Predicate<BiomeLoadingEvent> IS_OCEAN = event -> {
        Biome.BiomeCategory category = event.getCategory();
        return category == Biome.BiomeCategory.OCEAN;
    };
    
    public static final DDOreFeatureConfigEntry TIN_ORE =
            create("tin_ore", 10, 10, -45, 128)
                    .biomeExt()
                    .predicate(OVERWORLD_BIOMES)
                    .parent()
                    .standardDatagenExt()
                    .withBlocks(Couple.create(DDBlocks.tin_ore, DDBlocks.deepslate_tin_ore))
                    .parent();

    public static final DDOreFeatureConfigEntry GABBRO_BLOB =
            create("gabbro_blob", 32, 5, -64, 24)
                    .biomeExt()
                    .predicate(OVERWORLD_BIOMES)
                    .parent()
                    .standardDatagenExt()
                    .withBlocks(Couple.create(AllPaletteStoneTypes.GRANITE.getBaseBlock(), DDPaletteStoneTypes.gabbro.getBaseBlock()))
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_OVERWORLD =
            create("striated_ores_overworld", 32, 1 / 15f, 40, 90)
                    .biomeExt()
                    .predicate(OVERWORLD_BIOMES)
                    .parent()
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.CASSITERITE)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_SAVANNA =
            create("striated_ores_savanna", 64, 1 / 5f, 40, 90)
                    .biomeExt()
                    .predicate(IS_SAVANNA)
                    .parent()
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.GABBRO)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_BADLANDS =
            create("striated_ores_badlands", 64, 1 / 6.5f, 40, 90)
                    .biomeExt()
                    .predicate(IS_BADLANDS)
                    .parent()
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.GABBRO)
                    .withLayerPattern(DDLayerPatterns.RAW_OCHRESTONE)
                    .withLayerPattern(AllLayerPatterns.OCHRESTONE)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_OCEAN =
            create("striated_ores_ocean", 64, 1 / 48f, 40, 90)
                    .biomeExt()
                    .predicate(IS_OCEAN)
                    .parent()
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.WEATHERED_LIMESTONE)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_RAW_ORES_OVERWORLD =
            create("striated_raw_ores_overworld", 24, 1 / 25f, 20, 90)
                    .biomeExt()
                    .predicate(OVERWORLD_BIOMES)
                    .parent()
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.RAW_CASSITERITE)
                    .withLayerPattern(DDLayerPatterns.RAW_SCORIA)
                    .withLayerPattern(DDLayerPatterns.RAW_CINNABAR)
                    .withLayerPattern(DDLayerPatterns.RAW_MAGNETITE)
                    .withLayerPattern(DDLayerPatterns.RAW_MALACHITE)
                    .withLayerPattern(DDLayerPatterns.RAW_OCHRESTONE)
                    .parent();

    //

    private static DDOreFeatureConfigEntry create(String name, int clusterSize, float frequency,
                                                int minHeight, int maxHeight) {
        ResourceLocation id = DDCreate.asResource(name);
        return new DDOreFeatureConfigEntry(id, clusterSize, frequency, minHeight, maxHeight);
    }

    public static void fillConfig(ForgeConfigSpec.Builder builder, String namespace) {
        DDOreFeatureConfigEntry.ALL
                .forEach((id, entry) -> {
                    if (id.getNamespace().equals(namespace)) {
                        builder.push(entry.getName());
                        entry.addToConfig(builder);
                        builder.pop();
                    }
                });
    }

    public static void init() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();

        //

        Map<ResourceLocation, ConfiguredFeature<?, ?>> configuredFeatures = new HashMap<>();
        for (Map.Entry<ResourceLocation, DDOreFeatureConfigEntry> entry : DDOreFeatureConfigEntry.ALL.entrySet()) {
            DDOreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
            if (datagenExt != null) {
                configuredFeatures.put(entry.getKey(), datagenExt.createConfiguredFeature(registryAccess));
            }
        }

        DynamicDataProvider<ConfiguredFeature<?, ?>> configuredFeatureProvider = DynamicDataProvider.create(generator, "Create's Configured Features", registryAccess, Registry.CONFIGURED_FEATURE_REGISTRY, configuredFeatures);
        if (configuredFeatureProvider != null) {
            generator.addProvider(configuredFeatureProvider);
        }

        //

        Map<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>();
        for (Map.Entry<ResourceLocation, DDOreFeatureConfigEntry> entry : DDOreFeatureConfigEntry.ALL.entrySet()) {
            DDOreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
            if (datagenExt != null) {
                placedFeatures.put(entry.getKey(), datagenExt.createPlacedFeature(registryAccess));
            }
        }

        DynamicDataProvider<PlacedFeature> placedFeatureProvider = DynamicDataProvider.create(generator, "Create's Placed Features", registryAccess, Registry.PLACED_FEATURE_REGISTRY, placedFeatures);
        if (placedFeatureProvider != null) {
            generator.addProvider(placedFeatureProvider);
        }
    }
}
