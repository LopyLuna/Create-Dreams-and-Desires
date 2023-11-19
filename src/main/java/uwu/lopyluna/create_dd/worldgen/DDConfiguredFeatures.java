package uwu.lopyluna.create_dd.worldgen;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.infrastructure.worldgen.AllLayerPatterns;
import com.simibubi.create.infrastructure.worldgen.LayerPattern;
import com.simibubi.create.infrastructure.worldgen.LayeredOreConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

import java.util.List;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class DDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>>
            TIN_ORE = key("tin_ore"),
            GABBRO_BLOB = key("gabbro_blob"),
            STRIATED_ORES_OVERWORLD = key("striated_ores_overworld"),
            STRIATED_ORES_SAVANNA = key("striated_ores_savanna"),
            STRIATED_ORES_BADLANDS = key("striated_ores_badlands"),
            STRIATED_ORES_OCEAN = key("striated_ores_ocean"),
            STRIATED_RAW_ORES_OVERWORLD = key("striated_raw_ores_overworld");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, DDCreate.asResource(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        RuleTest stoneOreReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> tinTargetStates = List.of(
                OreConfiguration.target(stoneOreReplaceables, DDBlocks.tin_ore.get()
                        .defaultBlockState()),
                OreConfiguration.target(deepslateOreReplaceables, DDBlocks.deepslate_tin_ore.get()
                        .defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> gabbroTargetStates = List.of(
                OreConfiguration.target(stoneOreReplaceables, AllPaletteStoneTypes.GRANITE.getBaseBlock().get()
                        .defaultBlockState()),
                OreConfiguration.target(deepslateOreReplaceables, DDPaletteStoneTypes.gabbro.getBaseBlock().get()
                        .defaultBlockState())
        );

        register(ctx, TIN_ORE, Feature.ORE, new OreConfiguration(tinTargetStates, 12));
        register(ctx, GABBRO_BLOB, Feature.ORE, new OreConfiguration(gabbroTargetStates, 32));

        List<LayerPattern> striated_ores_overworld = List.of(
                DDLayerPatterns.CASSITERITE.get()
        );
        List<LayerPattern> striated_ores_savanna = List.of(
                DDLayerPatterns.GABBRO.get()
        );
        List<LayerPattern> striated_ores_badlands = List.of(
                DDLayerPatterns.GABBRO.get(),
                DDLayerPatterns.RAW_OCHRESTONE.get(),
                AllLayerPatterns.OCHRESTONE.get()
        );
        List<LayerPattern> striated_ores_ocean = List.of(
                DDLayerPatterns.WEATHERED_LIMESTONE.get()
        );
        List<LayerPattern> striated_raw_ores_overworld = List.of(
                DDLayerPatterns.RAW_CASSITERITE.get(),
                DDLayerPatterns.RAW_SCORIA.get(),
                DDLayerPatterns.RAW_CINNABAR.get(),
                DDLayerPatterns.RAW_MAGNETITE.get(),
                DDLayerPatterns.RAW_MALACHITE.get(),
                DDLayerPatterns.RAW_OCHRESTONE.get()
        );

        register(ctx, STRIATED_ORES_OVERWORLD, DDFeatures.LAYERED_ORE.get(), new LayeredOreConfiguration(striated_ores_overworld, 32, 0));
        register(ctx, STRIATED_ORES_SAVANNA, DDFeatures.LAYERED_ORE.get(), new LayeredOreConfiguration(striated_ores_savanna, 64, 0));
        register(ctx, STRIATED_ORES_BADLANDS, DDFeatures.LAYERED_ORE.get(), new LayeredOreConfiguration(striated_ores_badlands, 64, 0));
        register(ctx, STRIATED_ORES_OCEAN, DDFeatures.LAYERED_ORE.get(), new LayeredOreConfiguration(striated_ores_ocean, 64, 0));
        register(ctx, STRIATED_RAW_ORES_OVERWORLD, DDFeatures.LAYERED_ORE.get(), new LayeredOreConfiguration(striated_raw_ores_overworld, 24, 0));
    }
}