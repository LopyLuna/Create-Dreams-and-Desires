package uwu.lopyluna.create_dd.worldgen;

import uwu.lopyluna.create_dd.DDCreate;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.configs.common.DDCommon;

import java.util.List;

@SuppressWarnings({"unused"})
public class WorldgenOrePlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, DDCreate.MOD_ID);

    public static final RegistryObject<PlacedFeature> TIN_ORE_PLACED = PLACED_FEATURES.register("tin_ore_placed", () -> new PlacedFeature(WorldgenOreFeatures.tin_ore.getHolder().get(),
                commonOrePlacement(DDConfigs.common().tin_ore_frequency.get(), // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().tin_ore_min.get()), VerticalAnchor.absolute(DDConfigs.common().tin_ore_max.get())))));

    public static final RegistryObject<PlacedFeature> STONE_BLOBS_PLACED = PLACED_FEATURES.register("stone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.stone_blobs.getHolder().get(),
                    commonOrePlacement(DDConfigs.common().stone_blob_frequency.get(), // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().stone_blob_min.get()), VerticalAnchor.absolute(DDConfigs.common().stone_blob_max.get())))));

    public static final RegistryObject<PlacedFeature> LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.limestone_blobs.getHolder().get(),
                    commonOrePlacement(DDConfigs.common().limestone_blob_frequency.get(), // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().limestone_blob_min.get()), VerticalAnchor.absolute(DDConfigs.common().limestone_blob_max.get())))));

    public static final RegistryObject<PlacedFeature> WEATHERED_LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("weathered_limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.weathered_limestone_blobs.getHolder().get(),
                    commonOrePlacement(DDConfigs.common().weathered_limestone_blob_frequency.get(), // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().weathered_limestone_blob_min.get()), VerticalAnchor.absolute(DDConfigs.common().weathered_limestone_blob_max.get())))));

    public static final RegistryObject<PlacedFeature> GABBRO_BLOBS_PLACED = PLACED_FEATURES.register("gabbro_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.gabbro_blobs.getHolder().get(),
                    commonOrePlacement(DDConfigs.common().gabbro_blob_frequency.get(), // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().gabbro_blob_min.get()), VerticalAnchor.absolute(DDConfigs.common().gabbro_blob_max.get())))));

    public static final RegistryObject<PlacedFeature> ERODED_LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("eroded_limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.eroded_limestone_blobs.getHolder().get(),
                    commonOrePlacement(DDConfigs.common().eroded_limestone_blob_frequency.get(), // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(DDConfigs.common().eroded_limestone_blob_min.get()), VerticalAnchor.absolute(DDConfigs.common().eroded_limestone_blob_max.get())))));

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }


    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
