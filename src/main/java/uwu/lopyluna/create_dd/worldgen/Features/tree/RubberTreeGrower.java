package uwu.lopyluna.create_dd.worldgen.Features.tree;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.worldgen.Features.DDFeatures;

public class RubberTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pLargeHive) {
        return DDFeatures.TreeConfiguredFeature.RUBBER_TREE;
    }
}
