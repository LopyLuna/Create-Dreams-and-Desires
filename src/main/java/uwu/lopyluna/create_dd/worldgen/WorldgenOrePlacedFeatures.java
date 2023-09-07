package uwu.lopyluna.create_dd.worldgen;

import uwu.lopyluna.create_dd.DDCreate;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class WorldgenOrePlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, DDCreate.MOD_ID);


    public static final RegistryObject<PlacedFeature> TIN_ORE_PLACED = PLACED_FEATURES.register("tin_ore_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.tin_ore.getHolder().get(),
                    commonOrePlacement(10, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-45), VerticalAnchor.aboveBottom(128)))));

    public static final RegistryObject<PlacedFeature> STONE_BLOBS_PLACED = PLACED_FEATURES.register("stone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.stone_blobs.getHolder().get(),
                    commonOrePlacement(16, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(256)))));

    public static final RegistryObject<PlacedFeature> LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.limestone_blobs.getHolder().get(),
                    commonOrePlacement(2, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(24)))));

    public static final RegistryObject<PlacedFeature> WEATHERED_LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("weathered_limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.weathered_limestone_blobs.getHolder().get(),
                    commonOrePlacement(2, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(0)))));

    public static final RegistryObject<PlacedFeature> GABBRO_BLOBS_PLACED = PLACED_FEATURES.register("gabbro_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.gabbro_blobs.getHolder().get(),
                    commonOrePlacement(1, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(24)))));

    public static final RegistryObject<PlacedFeature> ERODED_LIMESTONE_BLOBS_PLACED = PLACED_FEATURES.register("eroded_limestone_blobs_placed",
            () -> new PlacedFeature(WorldgenOreFeatures.eroded_limestone_blobs.getHolder().get(),
                    commonOrePlacement(4, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(256)))));

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
