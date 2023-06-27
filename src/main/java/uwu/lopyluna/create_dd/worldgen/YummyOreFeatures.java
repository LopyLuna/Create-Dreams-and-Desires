package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import uwu.lopyluna.create_dd.block.YIPPEE;

import java.util.List;

public class YummyOreFeatures {
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_CITRINE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, YIPPEE.tin_ore.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, YIPPEE.deepslate_tin_ore.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> tin_ore = FeatureUtils.register("tin_ore",
            Feature.ORE, new OreConfiguration(OVERWORLD_CITRINE_ORES, 12));
}
