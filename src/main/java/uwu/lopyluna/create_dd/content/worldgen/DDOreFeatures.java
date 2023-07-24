package uwu.lopyluna.create_dd.content.worldgen;

import java.util.HashMap;
import java.util.Map;

import com.simibubi.create.foundation.data.DynamicDataProvider;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.infrastructure.worldgen.OreFeatureConfigEntry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import uwu.lopyluna.create_dd.DDcreate;
import uwu.lopyluna.create_dd.init.DDBlocks;

public class DDOreFeatures {
	public static final OreFeatureConfigEntry ZINC_ORE =
			create("tin_ore", 12, 8, -63, 70)
					.standardDatagenExt()
					.withBlocks(Couple.create(DDBlocks.tin_ore, DDBlocks.deepslate_tin_ore))
					.biomeTag(BiomeTags.IS_OVERWORLD)
					.parent();
	public static final OreFeatureConfigEntry STRIATED_ORES_OVERWORLD =
			create("dd_striated_ores_overworld", 32, 1 / 18f, -30, 70)
					.layeredDatagenExt()
					.withLayerPattern(DDLayerPatterns.potassic)
					.withLayerPattern(DDLayerPatterns.gabbro)
					.withLayerPattern(DDLayerPatterns.weathered_limestone)
					.biomeTag(BiomeTags.IS_OVERWORLD)
					.parent();
	private static OreFeatureConfigEntry create(String name, int clusterSize, float frequency,
												int minHeight, int maxHeight) {
		ResourceLocation id = DDcreate.asResource(name);
		OreFeatureConfigEntry configDrivenFeatureEntry = new OreFeatureConfigEntry(id, clusterSize, frequency, minHeight, maxHeight);
		return configDrivenFeatureEntry;
	}

	public static void fillConfig(ForgeConfigSpec.Builder builder, String namespace) {
		OreFeatureConfigEntry.ALL
				.forEach((id, entry) -> {
					if (id.getNamespace().equals(namespace)) {
						builder.push(entry.getName());
						entry.addToConfig(builder);
						builder.pop();
					}
				});
	}

	public static void init() {}

	public static void modifyBiomes() {
		for (OreFeatureConfigEntry entry : OreFeatureConfigEntry.ALL.values()) {
			OreFeatureConfigEntry.DatagenExtension ext = entry.datagenExt();
			if (ext != null) {
				ext.modifyBiomes();
			}
		}
	}

	public static void gatherData(FabricDataGenerator generator) {
		RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();

		//

		Map<ResourceLocation, ConfiguredFeature<?, ?>> configuredFeatures = new HashMap<>();
		for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
			OreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
			if (datagenExt != null) {
				configuredFeatures.put(entry.getKey(), datagenExt.createConfiguredFeature(registryAccess));
			}
		}

		DynamicDataProvider<ConfiguredFeature<?, ?>> configuredFeatureProvider = DynamicDataProvider.create(generator, DDcreate.NAME + "'s Configured Features", registryAccess, Registry.CONFIGURED_FEATURE_REGISTRY, configuredFeatures);
		if (configuredFeatureProvider != null) {
			generator.addProvider(true, configuredFeatureProvider);
		}

		//

		Map<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>();
		for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
			OreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
			if (datagenExt != null) {
				placedFeatures.put(entry.getKey(), datagenExt.createPlacedFeature(registryAccess));
			}
		}

		DynamicDataProvider<PlacedFeature> placedFeatureProvider = DynamicDataProvider.create(generator, DDcreate.NAME + "'s Placed Features", registryAccess, Registry.PLACED_FEATURE_REGISTRY, placedFeatures);
		if (placedFeatureProvider != null) {
			generator.addProvider(true, placedFeatureProvider);
		}

		//

		// fabric: handled in-code at DatagenExtension.modifyBiomes
//		Map<ResourceLocation, BiomeModifier> biomeModifiers = new HashMap<>();
//		for (Map.Entry<ResourceLocation, OreFeatureConfigEntry> entry : OreFeatureConfigEntry.ALL.entrySet()) {
//			DatagenExtension datagenExt = entry.getValue().datagenExt();
//			if (datagenExt != null) {
//				biomeModifiers.put(entry.getKey(), datagenExt.createBiomeModifier(registryAccess));
//			}
//		}
//
//		DynamicDataProvider<BiomeModifier> biomeModifierProvider = DynamicDataProvider.create(generator, "Create's Biome Modifiers", registryAccess, Keys.BIOME_MODIFIERS, biomeModifiers);
//		if (biomeModifierProvider != null) {
//			generator.addProvider(true, biomeModifierProvider);
//		}
	}
	public static void registerConfiguredFeatures() {
		DDcreate.LOGGER.debug("Registering the ModConfiguredFeatures for " + DDcreate.ID);
	}
}
