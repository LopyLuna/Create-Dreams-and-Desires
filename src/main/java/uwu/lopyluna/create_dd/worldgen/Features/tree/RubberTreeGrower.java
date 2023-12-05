package uwu.lopyluna.create_dd.worldgen.Features.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.worldgen.Features.DDFeatures;

import java.util.Random;

public class RubberTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
        return DDFeatures.TreeConfiguredFeature.RUBBER_TREE;
    }
}
