package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;

public class DDBiomeModifiers {
    public static final ResourceKey<BiomeModifier>
            TIN_ORE = key("tin_ore"),
            GABBRO_BLOB = key("gabbro_blob"),
            STRIATED_ORES_OVERWORLD = key("striated_ores_overworld"),
            STRIATED_ORES_SAVANNA = key("striated_ores_savanna"),
            STRIATED_ORES_BADLANDS = key("striated_ores_badlands"),
            STRIATED_ORES_OCEAN = key("striated_ores_ocean"),
            STRIATED_RAW_ORES_OVERWORLD = key("striated_raw_ores_overworld");

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, DDCreate.asResource(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);
        HolderSet<Biome> isOverworld = biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD);
        HolderSet<Biome> isSavanna = biomeLookup.getOrThrow(BiomeTags.IS_SAVANNA);
        HolderSet<Biome> isBadlands = biomeLookup.getOrThrow(BiomeTags.IS_BADLANDS);
        HolderSet<Biome> isOcean = biomeLookup.getOrThrow(BiomeTags.IS_OCEAN);

        HolderGetter<PlacedFeature> featureLookup = ctx.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> tin_ore = featureLookup.getOrThrow(DDPlacedFeatures.TIN_ORE);
        Holder<PlacedFeature> gabbro_blob = featureLookup.getOrThrow(DDPlacedFeatures.GABBRO_BLOB);
        Holder<PlacedFeature> striated_ores_overworld = featureLookup.getOrThrow(DDPlacedFeatures.STRIATED_ORES_OVERWORLD);
        Holder<PlacedFeature> striated_ores_savanna = featureLookup.getOrThrow(DDPlacedFeatures.STRIATED_ORES_SAVANNA);
        Holder<PlacedFeature> striated_ores_badlands = featureLookup.getOrThrow(DDPlacedFeatures.STRIATED_ORES_BADLANDS);
        Holder<PlacedFeature> striated_ores_ocean = featureLookup.getOrThrow(DDPlacedFeatures.STRIATED_ORES_OCEAN);
        Holder<PlacedFeature> striated_raw_ores_overworld = featureLookup.getOrThrow(DDPlacedFeatures.STRIATED_RAW_ORES_OVERWORLD);

        ctx.register(TIN_ORE, addOre(isOverworld, tin_ore));
        ctx.register(GABBRO_BLOB, addOre(isOverworld, gabbro_blob));
        ctx.register(STRIATED_ORES_OVERWORLD, addOre(isOverworld, striated_ores_overworld));
        ctx.register(STRIATED_ORES_SAVANNA, addOre(isSavanna, striated_ores_savanna));
        ctx.register(STRIATED_ORES_BADLANDS, addOre(isBadlands, striated_ores_badlands));
        ctx.register(STRIATED_ORES_OCEAN, addOre(isOcean, striated_ores_ocean));
        ctx.register(STRIATED_RAW_ORES_OVERWORLD, addOre(isOverworld, striated_raw_ores_overworld));
    }

    private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addOre(HolderSet<Biome> biomes, Holder<PlacedFeature> feature) {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(feature), GenerationStep.Decoration.UNDERGROUND_ORES);
    }
}