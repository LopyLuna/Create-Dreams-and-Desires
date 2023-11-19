package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.List;

import static net.minecraft.data.worldgen.placement.PlacementUtils.register;

public class DDPlacedFeatures {
    public static final ResourceKey<PlacedFeature>
            TIN_ORE = key("tin_ore"),
            GABBRO_BLOB = key("gabbro_blob"),
            STRIATED_ORES_OVERWORLD = key("striated_ores_overworld"),
            STRIATED_ORES_SAVANNA = key("striated_ores_savanna"),
            STRIATED_ORES_BADLANDS = key("striated_ores_badlands"),
            STRIATED_ORES_OCEAN = key("striated_ores_ocean"),
            STRIATED_RAW_ORES_OVERWORLD = key("striated_raw_ores_overworld");

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, DDCreate.asResource(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> tin_ore = featureLookup.getOrThrow(DDConfiguredFeatures.TIN_ORE);
        Holder<ConfiguredFeature<?, ?>> gabbro_blob = featureLookup.getOrThrow(DDConfiguredFeatures.GABBRO_BLOB);
        Holder<ConfiguredFeature<?, ?>> striated_ores_overworld = featureLookup.getOrThrow(DDConfiguredFeatures.STRIATED_ORES_OVERWORLD);
        Holder<ConfiguredFeature<?, ?>> striated_ores_savanna = featureLookup.getOrThrow(DDConfiguredFeatures.STRIATED_ORES_SAVANNA);
        Holder<ConfiguredFeature<?, ?>> striated_ores_badlands = featureLookup.getOrThrow(DDConfiguredFeatures.STRIATED_ORES_BADLANDS);
        Holder<ConfiguredFeature<?, ?>> striated_ores_ocean = featureLookup.getOrThrow(DDConfiguredFeatures.STRIATED_ORES_OCEAN);
        Holder<ConfiguredFeature<?, ?>> striated_raw_ores_overworld = featureLookup.getOrThrow(DDConfiguredFeatures.STRIATED_RAW_ORES_OVERWORLD);

        register(ctx, TIN_ORE, tin_ore, placement(CountPlacement.of(10), -45, 128));
        register(ctx, GABBRO_BLOB, gabbro_blob, placement(CountPlacement.of(5), -64, 24));
        register(ctx, STRIATED_ORES_OVERWORLD, striated_ores_overworld, placement(RarityFilter.onAverageOnceEvery(16), 30, 128));
        register(ctx, STRIATED_ORES_SAVANNA, striated_ores_savanna, placement(RarityFilter.onAverageOnceEvery(24), 30, 100));
        register(ctx, STRIATED_ORES_BADLANDS, striated_ores_badlands, placement(RarityFilter.onAverageOnceEvery(24), 30, 120));
        register(ctx, STRIATED_ORES_OCEAN, striated_ores_ocean, placement(RarityFilter.onAverageOnceEvery(18), 20, 80));
        register(ctx, STRIATED_RAW_ORES_OVERWORLD, striated_raw_ores_overworld, placement(RarityFilter.onAverageOnceEvery(9), 0, 200));
    }

    private static List<PlacementModifier> placement(PlacementModifier frequency, int minHeight, int maxHeight) {
        return List.of(
                frequency,
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                DDConfigPlacementFilter.INSTANCE
        );
    }
}