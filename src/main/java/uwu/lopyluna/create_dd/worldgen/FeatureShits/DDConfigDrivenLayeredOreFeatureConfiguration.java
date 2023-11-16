package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.infrastructure.worldgen.LayerPattern;

import java.util.List;

@SuppressWarnings({"all"})
public class DDConfigDrivenLayeredOreFeatureConfiguration extends DDBaseConfigDrivenOreFeatureConfiguration {
    public static final Codec<DDConfigDrivenLayeredOreFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                DDOreFeatureConfigEntry.CODEC
                        .fieldOf("entry")
                        .forGetter(config -> config.entry),
                Codec.floatRange(0.0F, 1.0F)
                        .fieldOf("discard_chance_on_air_exposure")
                        .forGetter(config -> config.discardChanceOnAirExposure),
                Codec.list(LayerPattern.CODEC)
                        .fieldOf("layer_patterns")
                        .forGetter(config -> config.layerPatterns)
        ).apply(instance, DDConfigDrivenLayeredOreFeatureConfiguration::new);
    });

    private final List<LayerPattern> layerPatterns;

    public DDConfigDrivenLayeredOreFeatureConfiguration(DDOreFeatureConfigEntry entry, float discardChance, List<LayerPattern> layerPatterns) {
        super(entry, discardChance);
        this.layerPatterns = layerPatterns;
    }

    public List<LayerPattern> getLayerPatterns() {
        return layerPatterns;
    }
}
