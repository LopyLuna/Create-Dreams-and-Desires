package uwu.lopyluna.create_flavored.worldgen;

import com.simibubi.create.foundation.data.DynamicDataProvider;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.infrastructure.worldgen.OreFeatureConfigEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import uwu.lopyluna.create_flavored.Flavoredcreate;
import uwu.lopyluna.create_flavored.block.YIPPEE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class YummyOreFeatures {

    private static final Predicate<BiomeLoadingEvent> OVERWORLD_BIOMES = event -> {
        Biome.BiomeCategory category = event.getCategory();
        return category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NONE;
    };

    public static final OreFeatureConfigEntry TIN_ORE =
            create("tin_ore", 8, 10, -45, 85)
                    .biomeExt()
                    .predicate(OVERWORLD_BIOMES)
                    .parent()
                    .standardDatagenExt()
                    .withBlocks(Couple.create(YIPPEE.tin_ore, YIPPEE.deepslate_tin_ore))
                    .parent();


    private static OreFeatureConfigEntry create(String name, int clusterSize, float frequency,
                                                int minHeight, int maxHeight) {
        ResourceLocation id = Flavoredcreate.asResource(name);
        OreFeatureConfigEntry configDrivenFeatureEntry = new OreFeatureConfigEntry(id, clusterSize, frequency, minHeight, maxHeight);
        return configDrivenFeatureEntry;
    }

    public static void init() {}

    public static void modifyBiomes(BiomeLoadingEvent event) {
        for (OreFeatureConfigEntry entry : OreFeatureConfigEntry.ALL.values()) {
            entry.biomeExt().modifyBiomes(event, BuiltinRegistries.PLACED_FEATURE);
        }
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();

        //

        Map<ResourceLocation, ConfiguredFeature<?, ?>> configuredFeatures = new HashMap<>();
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            OreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
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
        for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
            OreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
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
