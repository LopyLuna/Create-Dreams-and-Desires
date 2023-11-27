package uwu.lopyluna.create_dd.worldgen.Features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import uwu.lopyluna.create_dd.block.DDBlocks;

import java.util.List;

import static uwu.lopyluna.create_dd.worldgen.Features.DDFeatures.TreeConfiguredFeature.RUBBER_SPAWN;

public class DDFeatures {


    public static class TreeConfiguredFeature {
        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RUBBER_TREE =
                FeatureUtils.register("rubber", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DDBlocks.rubber_log.get()),
                        new FancyTrunkPlacer(6, 12, 0),
                        BlockStateProvider.simple(DDBlocks.rubber_leaves.get()),
                        new PineFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), UniformInt.of(3, 3)),
                        new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build());

        public static final Holder<PlacedFeature> RUBBER_CHECKED = PlacementUtils.register("rubber_checked", RUBBER_TREE,
                PlacementUtils.filteredByBlockSurvival(DDBlocks.rubber_sapling.get()));

        public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> RUBBER_SPAWN =
                FeatureUtils.register("rubber_spawn", Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(RUBBER_CHECKED, 0.5F)), RUBBER_CHECKED));

    }

    public static class TreePlacedFeature {

        public static final Holder<PlacedFeature> RUBBER_PLACED = PlacementUtils.register("rubber_placed",
                RUBBER_SPAWN, VegetationPlacements.treePlacement(
                            PlacementUtils.countExtra(0, 0.01f, 1)));
    }
}
