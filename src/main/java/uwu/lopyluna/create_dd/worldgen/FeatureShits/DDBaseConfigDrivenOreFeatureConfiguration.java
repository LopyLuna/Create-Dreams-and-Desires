package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DDBaseConfigDrivenOreFeatureConfiguration implements FeatureConfiguration {
    protected final DDOreFeatureConfigEntry entry;
    protected final float discardChanceOnAirExposure;

    public DDBaseConfigDrivenOreFeatureConfiguration(DDOreFeatureConfigEntry entry, float discardChance) {
        this.entry = entry;
        this.discardChanceOnAirExposure = discardChance;
    }

    public DDOreFeatureConfigEntry getEntry() {
        return entry;
    }

    public int getClusterSize() {
        return entry.clusterSize.get();
    }

    public float getDiscardChanceOnAirExposure() {
        return discardChanceOnAirExposure;
    }
}
