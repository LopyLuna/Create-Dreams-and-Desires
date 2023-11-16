package uwu.lopyluna.create_dd.worldgen;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.DynamicDataProvider;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.worldgen.FeatureShits.DDOreFeatureConfigEntry;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"all"})
public class DDOreFeatureConfigEntries {
    public static final DDOreFeatureConfigEntry TIN_ORE =
            create("tin_ore", 10, 10, -45, 128)
                    .standardDatagenExt()
                    .withBlocks(Couple.create(DDBlocks.tin_ore, DDBlocks.deepslate_tin_ore))
                    .biomeTag(BiomeTags.IS_OVERWORLD)
                    .parent();

    public static final DDOreFeatureConfigEntry GABBRO_BLOB =
            create("gabbro_blob", 32, 5, -64, 24)
                    .standardDatagenExt()
                    .withBlocks(Couple.create(AllPaletteStoneTypes.GRANITE.getBaseBlock(), DDPaletteStoneTypes.gabbro.getBaseBlock()))
                    .biomeTag(BiomeTags.IS_OVERWORLD)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_OVERWORLD =
            create("striated_ores_overworld", 32, 1 / 9f, 40, 90)
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.CASSITERITE)
                    .biomeTag(BiomeTags.IS_OVERWORLD)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_SAVANNA =
            create("striated_ores_savanna", 64, 1 / 48f, 40, 90)
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.GABBRO)
                    .biomeTag(BiomeTags.IS_SAVANNA)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_BADLANDS =
            create("striated_ores_badlands", 64, 1 / 48f, 40, 90)
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.GABBRO)
                    .biomeTag(BiomeTags.IS_BADLANDS)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_ORES_OCEAN =
            create("striated_ores_ocean", 64, 1 / 48f, 40, 90)
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.WEATHERED_LIMESTONE)
                    .biomeTag(BiomeTags.IS_OCEAN)
                    .parent();

    public static final DDOreFeatureConfigEntry STRIATED_RAW_ORES_OVERWORLD =
            create("striated_raw_ores_overworld", 24, 1 / 3f, 20, 90)
                    .layeredDatagenExt()
                    .withLayerPattern(DDLayerPatterns.RAW_CASSITERITE)
                    .withLayerPattern(DDLayerPatterns.RAW_SCORIA)
                    .withLayerPattern(DDLayerPatterns.RAW_CINNABAR)
                    .withLayerPattern(DDLayerPatterns.RAW_MAGNETITE)
                    .withLayerPattern(DDLayerPatterns.RAW_MALACHITE)
                    .withLayerPattern(DDLayerPatterns.RAW_OCHRESTONE)
                    .biomeTag(BiomeTags.IS_OVERWORLD)
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
            generator.addProvider(true, configuredFeatureProvider);
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
            generator.addProvider(true, placedFeatureProvider);
        }

        //

        Map<ResourceLocation, BiomeModifier> biomeModifiers = new HashMap<>();
        for (Map.Entry<ResourceLocation, DDOreFeatureConfigEntry> entry : DDOreFeatureConfigEntry.ALL.entrySet()) {
            DDOreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
            if (datagenExt != null) {
                biomeModifiers.put(entry.getKey(), datagenExt.createBiomeModifier(registryAccess));
            }
        }

        DynamicDataProvider<BiomeModifier> biomeModifierProvider = DynamicDataProvider.create(generator, "Create's Biome Modifiers", registryAccess, ForgeRegistries.Keys.BIOME_MODIFIERS, biomeModifiers);
        if (biomeModifierProvider != null) {
            generator.addProvider(true, biomeModifierProvider);
        }
    }
}
