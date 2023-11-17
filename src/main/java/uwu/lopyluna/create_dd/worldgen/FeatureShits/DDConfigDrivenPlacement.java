package uwu.lopyluna.create_dd.worldgen.FeatureShits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import java.util.Random;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.worldgen.DDPlacementModifiers;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"all"})
public class DDConfigDrivenPlacement extends PlacementModifier {
    public static final Codec<DDConfigDrivenPlacement> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                DDOreFeatureConfigEntry.CODEC
                        .fieldOf("entry")
                        .forGetter(DDConfigDrivenPlacement::getEntry)
        ).apply(instance, DDConfigDrivenPlacement::new);
    });

    private final DDOreFeatureConfigEntry entry;

    public DDConfigDrivenPlacement(DDOreFeatureConfigEntry entry) {
        this.entry = entry;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos) {
        int count = getCount(getFrequency(), random);
        if (count == 0) {
            return Stream.empty();
        }

        int minY = getMinY();
        int maxY = getMaxY();

        return IntStream.range(0, count)
                .mapToObj(i -> pos)
                .map(p -> {
                    int x = random.nextInt(16) + p.getX();
                    int z = random.nextInt(16) + p.getZ();
                    int y = Mth.randomBetweenInclusive(random, minY, maxY);
                    return new BlockPos(x, y, z);
                });
    }

    public int getCount(float frequency, Random random) {
        int floored = Mth.floor(frequency);
        return floored + (random.nextFloat() < (frequency - floored) ? 1 : 0);
    }

    @Override
    public PlacementModifierType<?> type() {
        return DDPlacementModifiers.CONFIG_DRIVEN.get();
    }

    public DDOreFeatureConfigEntry getEntry() {
        return entry;
    }

    public float getFrequency() {
        if (DDConfigs.common().worldGen.disable.get())
            return 0;
        return entry.frequency.getF();
    }

    public int getMinY() {
        return entry.minHeight.get();
    }

    public int getMaxY() {
        return entry.maxHeight.get();
    }
}
