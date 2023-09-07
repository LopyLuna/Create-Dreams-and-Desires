package uwu.lopyluna.create_dd.worldgen;


import com.google.common.base.Suppliers;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;

import java.util.List;
import java.util.function.Supplier;

public class WorldgenOreFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, DDCreate.MOD_ID);

    public static final RuleTest weathered_limestone_replaceable = new TagMatchTest(DDTags.AllBlockTags.weathered_limestone_replaceable.tag);
    public static final RuleTest limestone_replaceable = new TagMatchTest(DDTags.AllBlockTags.limestone_replaceable.tag);
    public static final RuleTest potassic_replaceable = new TagMatchTest(DDTags.AllBlockTags.potassic_replaceable.tag);
    public static final RuleTest gabbro_replaceable = new TagMatchTest(DDTags.AllBlockTags.gabbro_replaceable.tag);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_STONE_BLOBS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(WorldgenOreFeatures.gabbro_replaceable, DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState()),
            OreConfiguration.target(WorldgenOreFeatures.weathered_limestone_replaceable, DDPaletteStoneTypes.weathered_limestone.getBaseBlock().get().defaultBlockState()),
            OreConfiguration.target(WorldgenOreFeatures.potassic_replaceable, DDPaletteStoneTypes.potassic.getBaseBlock().get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_LIMESTONE_BLOBS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get().defaultBlockState()),
            OreConfiguration.target(WorldgenOreFeatures.limestone_replaceable, AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_WEATHERED_LIMESTONE_BLOBS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, DDPaletteStoneTypes.weathered_limestone.getBaseBlock().get().defaultBlockState()),
            OreConfiguration.target(WorldgenOreFeatures.weathered_limestone_replaceable, DDPaletteStoneTypes.weathered_limestone.getBaseBlock().get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ERODED_LIMESTONE_BLOBS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(WorldgenOreFeatures.weathered_limestone_replaceable, DDPaletteStoneTypes.weathered_limestone.getBaseBlock().get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_GABBRO_BLOBS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState()),
            OreConfiguration.target(WorldgenOreFeatures.gabbro_replaceable, DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_TIN_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, DDBlocks.tin_ore.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, DDBlocks.deepslate_tin_ore.get().defaultBlockState())));


    public static final RegistryObject<ConfiguredFeature<?, ?>> stone_blobs = CONFIGURED_FEATURES.register("stone_blobs",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_STONE_BLOBS.get(),48)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> limestone_blobs = CONFIGURED_FEATURES.register("limestone_blobs",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_LIMESTONE_BLOBS.get(),32)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> weathered_limestone_blobs = CONFIGURED_FEATURES.register("weathered_limestone_blobs",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_WEATHERED_LIMESTONE_BLOBS.get(),24)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> gabbro_blobs = CONFIGURED_FEATURES.register("gabbro_blobs",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_GABBRO_BLOBS.get(),48)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> eroded_limestone_blobs = CONFIGURED_FEATURES.register("eroded_blobs",
            () -> new ConfiguredFeature<>(Feature.SCATTERED_ORE, new OreConfiguration(OVERWORLD_ERODED_LIMESTONE_BLOBS.get(),12)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> tin_ore = CONFIGURED_FEATURES.register("tin_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_TIN_ORES.get(),12)));

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}