package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import uwu.lopyluna.create_dd.configs.DDConfigBase;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.infrastructure.worldgen.LayerPattern;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.worldgen.DDFeatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"all"})
public class DDOreFeatureConfigEntry extends DDConfigBase {
    public static final Map<ResourceLocation, DDOreFeatureConfigEntry> ALL = new HashMap<>();

    public static final Codec<DDOreFeatureConfigEntry> CODEC = ResourceLocation.CODEC
            .comapFlatMap(DDOreFeatureConfigEntry::read, entry -> entry.id);

    public final ResourceLocation id;
    public final ConfigInt clusterSize;
    public final ConfigFloat frequency;
    public final ConfigInt minHeight;
    public final ConfigInt maxHeight;

    private DDOreFeatureConfigEntry.DatagenExtension datagenExt;

    public DDOreFeatureConfigEntry(ResourceLocation id, int clusterSize, float frequency, int minHeight, int maxHeight) {
        this.id = id;

        this.clusterSize = i(clusterSize, 0, "clusterSize");
        this.frequency = f(frequency, 0, 512, "frequency", "Amount of clusters generated per Chunk.",
                "  >1 to spawn multiple.", "  <1 to make it a chance.", "  0 to disable.");
        this.minHeight = i(minHeight, "minHeight");
        this.maxHeight = i(maxHeight, "maxHeight");

        ALL.put(id, this);
    }

    @Nullable
    public DDOreFeatureConfigEntry.StandardDatagenExtension standardDatagenExt() {
        if (datagenExt == null) {
            datagenExt = new DDOreFeatureConfigEntry.StandardDatagenExtension();
        }
        if (datagenExt instanceof DDOreFeatureConfigEntry.StandardDatagenExtension standard) {
            return standard;
        }
        return null;
    }

    @Nullable
    public DDOreFeatureConfigEntry.LayeredDatagenExtension layeredDatagenExt() {
        if (datagenExt == null) {
            datagenExt = new DDOreFeatureConfigEntry.LayeredDatagenExtension();
        }
        if (datagenExt instanceof DDOreFeatureConfigEntry.LayeredDatagenExtension layered) {
            return layered;
        }
        return null;
    }

    @Nullable
    public DDOreFeatureConfigEntry.DatagenExtension datagenExt() {
        if (datagenExt != null) {
            return datagenExt;
        }
        return null;
    }

    public void addToConfig(ForgeConfigSpec.Builder builder) {
        registerAll(builder);
    }

    @Override
    public String getName() {
        return id.getPath();
    }

    public static DataResult<DDOreFeatureConfigEntry> read(ResourceLocation id) {
        DDOreFeatureConfigEntry entry = ALL.get(id);
        if (entry != null) {
            return DataResult.success(entry);
        } else {
            return DataResult.error("Not a valid DDOreFeatureConfigEntry: " + id);
        }
    }

    public abstract class DatagenExtension {
        public TagKey<Biome> biomeTag;

        public DDOreFeatureConfigEntry.DatagenExtension biomeTag(TagKey<Biome> biomes) {
            this.biomeTag = biomes;
            return this;
        }

        public abstract ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess);

        public PlacedFeature createPlacedFeature(RegistryAccess registryAccess) {
            Registry<ConfiguredFeature<?, ?>> featureRegistry = registryAccess.registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
            Holder<ConfiguredFeature<?, ?>> featureHolder = featureRegistry.getOrCreateHolderOrThrow(ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, id));
            return new PlacedFeature(featureHolder, List.of(new DDConfigDrivenPlacement(DDOreFeatureConfigEntry.this)));
        }

        public BiomeModifier createBiomeModifier(RegistryAccess registryAccess) {
            Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
            Registry<PlacedFeature> featureRegistry = registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
            HolderSet<Biome> biomes = new HolderSet.Named<>(biomeRegistry, biomeTag);
            Holder<PlacedFeature> featureHolder = featureRegistry.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, id));
            return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(featureHolder), GenerationStep.Decoration.UNDERGROUND_ORES);
        }

        public DDOreFeatureConfigEntry parent() {
            return DDOreFeatureConfigEntry.this;
        }
    }

    public class StandardDatagenExtension extends DDOreFeatureConfigEntry.DatagenExtension {
        public NonNullSupplier<? extends Block> block;
        public NonNullSupplier<? extends Block> deepBlock;
        public NonNullSupplier<? extends Block> netherBlock;

        public DDOreFeatureConfigEntry.StandardDatagenExtension withBlock(NonNullSupplier<? extends Block> block) {
            this.block = block;
            this.deepBlock = block;
            return this;
        }

        public DDOreFeatureConfigEntry.StandardDatagenExtension withBlocks(Couple<NonNullSupplier<? extends Block>> blocks) {
            this.block = blocks.getFirst();
            this.deepBlock = blocks.getSecond();
            return this;
        }

        public DDOreFeatureConfigEntry.StandardDatagenExtension withNetherBlock(NonNullSupplier<? extends Block> block) {
            this.netherBlock = block;
            return this;
        }

        @Override
        public DDOreFeatureConfigEntry.StandardDatagenExtension biomeTag(TagKey<Biome> biomes) {
            super.biomeTag(biomes);
            return this;
        }

        @Override
        public ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess) {
            List<OreConfiguration.TargetBlockState> targetStates = new ArrayList<>();
            if (block != null)
                targetStates.add(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, block.get()
                        .defaultBlockState()));
            if (deepBlock != null)
                targetStates.add(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepBlock.get()
                        .defaultBlockState()));
            if (netherBlock != null)
                targetStates.add(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, netherBlock.get()
                        .defaultBlockState()));

            DDConfigDrivenOreFeatureConfiguration config = new DDConfigDrivenOreFeatureConfiguration(DDOreFeatureConfigEntry.this, 0, targetStates);
            return new ConfiguredFeature<>(DDFeatures.STANDARD_ORE.get(), config);
        }
    }

    public class LayeredDatagenExtension extends DDOreFeatureConfigEntry.DatagenExtension {
        public final List<NonNullSupplier<LayerPattern>> layerPatterns = new ArrayList<>();

        public DDOreFeatureConfigEntry.LayeredDatagenExtension withLayerPattern(NonNullSupplier<LayerPattern> pattern) {
            this.layerPatterns.add(pattern);
            return this;
        }

        @Override
        public DDOreFeatureConfigEntry.LayeredDatagenExtension biomeTag(TagKey<Biome> biomes) {
            super.biomeTag(biomes);
            return this;
        }

        @Override
        public ConfiguredFeature<?, ?> createConfiguredFeature(RegistryAccess registryAccess) {
            List<LayerPattern> layerPatterns = this.layerPatterns.stream()
                    .map(NonNullSupplier::get)
                    .toList();

            DDConfigDrivenLayeredOreFeatureConfiguration config = new DDConfigDrivenLayeredOreFeatureConfiguration(DDOreFeatureConfigEntry.this, 0, layerPatterns);
            return new ConfiguredFeature<>(DDFeatures.LAYERED_ORE.get(), config);
        }
    }
}