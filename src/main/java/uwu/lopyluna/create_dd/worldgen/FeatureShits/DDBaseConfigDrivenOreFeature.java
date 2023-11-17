package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.function.Function;

@SuppressWarnings({"all"})
public abstract class DDBaseConfigDrivenOreFeature<FC extends DDBaseConfigDrivenOreFeatureConfiguration> extends Feature<FC> {
    public DDBaseConfigDrivenOreFeature(Codec<FC> configCodec) {
        super(configCodec);
    }

    public boolean canPlaceOre(BlockState pState, Function<BlockPos, BlockState> pAdjacentStateAccessor,
                               Random pRandom, DDBaseConfigDrivenOreFeatureConfiguration pConfig, OreConfiguration.TargetBlockState pTargetState,
                               BlockPos.MutableBlockPos pMatablePos) {
        if (!pTargetState.target.test(pState, pRandom))
            return false;
        if (shouldSkipAirCheck(pRandom, pConfig.getDiscardChanceOnAirExposure()))
            return true;

        return !isAdjacentToAir(pAdjacentStateAccessor, pMatablePos);
    }

    protected boolean shouldSkipAirCheck(Random pRandom, float pChance) {
        return pChance <= 0 ? true : pChance >= 1 ? false : pRandom.nextFloat() >= pChance;
    }
}
