package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

@SuppressWarnings({"all"})
public class DDConfigDrivenOreFeatureConfiguration extends DDBaseConfigDrivenOreFeatureConfiguration {
    public static final Codec<DDConfigDrivenOreFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                DDOreFeatureConfigEntry.CODEC
                        .fieldOf("entry")
                        .forGetter(config -> config.entry),
                Codec.floatRange(0.0F, 1.0F)
                        .fieldOf("discard_chance_on_air_exposure")
                        .forGetter(config -> config.discardChanceOnAirExposure),
                Codec.list(OreConfiguration.TargetBlockState.CODEC)
                        .fieldOf("targets")
                        .forGetter(config -> config.targetStates)
        ).apply(instance, DDConfigDrivenOreFeatureConfiguration::new);
    });

    private final List<OreConfiguration.TargetBlockState> targetStates;

    public DDConfigDrivenOreFeatureConfiguration(DDOreFeatureConfigEntry entry, float discardChance, List<OreConfiguration.TargetBlockState> targetStates) {
        super(entry, discardChance);
        this.targetStates = targetStates;
    }

    public List<OreConfiguration.TargetBlockState> getTargetStates() {
        return targetStates;
    }
}
