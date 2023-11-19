package uwu.lopyluna.create_dd.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import uwu.lopyluna.create_dd.configs.DDConfigs;

public class DDConfigPlacementFilter extends PlacementFilter {
    public static final DDConfigPlacementFilter INSTANCE = new DDConfigPlacementFilter();
    public static final Codec<DDConfigPlacementFilter> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return !DDConfigs.common().worldGen.disable.get();
    }

    @Override
    public PlacementModifierType<?> type() {
        return DDPlacementModifiers.CONFIG_FILTER.get();
    }
}
