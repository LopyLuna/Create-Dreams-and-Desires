package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import uwu.lopyluna.create_dd.DDTags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExcavtionMining {

    public static final ExcavtionMining.Vein NO_VEIN = new ExcavtionMining.Vein(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    @Nonnull
    public static ExcavtionMining.Vein findVein(@Nullable BlockGetter reader, BlockPos pos) {
        if (reader == null)
            return NO_VEIN;

        List<BlockPos> ores = new ArrayList<>();
        List<BlockPos> ore_stones = new ArrayList<>();
        List<BlockPos> valid_excavation = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> frontier = new LinkedList<>();

        BlockState stateAbove = reader.getBlockState(pos.above());

        visited.add(pos);
        BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, 1, 1))
                .forEach(p -> frontier.add(new BlockPos(p)));

        // Find all ores
        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.remove(0);
            if (visited.contains(currentPos))
                continue;
            visited.add(currentPos);

            if (!isOre(reader.getBlockState(currentPos)))
                continue;
            ores.add(currentPos);
            forNeighbours(currentPos, visited, p -> frontier.add(new BlockPos(p)));
        }

        // Find all ore_stones
        visited.clear();
        visited.addAll(ores);
        frontier.addAll(ores);

        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.remove(0);
            if (visited.contains(currentPos))
                continue;
            visited.add(currentPos);

            if (!isOrestone(reader.getBlockState(currentPos)))
                continue;
            ore_stones.add(currentPos);
            forNeighbours(currentPos, visited, p -> frontier.add(new BlockPos(p)));
        }

        // Find all ore_stones
        visited.clear();
        visited.addAll(ores);
        frontier.addAll(ores);
        visited.addAll(ore_stones);
        frontier.addAll(ore_stones);

        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.remove(0);
            if (visited.contains(currentPos))
                continue;
            visited.add(currentPos);

            if (!isValidExcavation(reader.getBlockState(currentPos)))
                continue;
            valid_excavation.add(currentPos);
            forNeighbours(currentPos, visited, p -> frontier.add(new BlockPos(p)));
        }

        return new ExcavtionMining.Vein(ores, ore_stones, valid_excavation);
    }

    private static void forNeighbours(BlockPos pos, Set<BlockPos> visited, Consumer<BlockPos> acceptor) {
        BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, 1, 1))
                .filter(((Predicate<BlockPos>) visited::contains).negate())
                .forEach(acceptor);
    }

    public static boolean isOre(BlockState state) {
        return state.is(Tags.Blocks.ORES);
    }

    private static boolean isOrestone(BlockState state) {
        return state.is(DDTags.AllBlockTags.ore_stones.tag);
    }

    private static boolean isValidExcavation(BlockState state) {
        return state.is(DDTags.AllBlockTags.valid_excavation.tag);
    }

    public static class Vein extends AbstractBlockBreakQueue {
        final List<BlockPos> ores;
        final List<BlockPos> ore_stones;
        final List<BlockPos> valid_excavation;

        public Vein(List<BlockPos> ores, List<BlockPos> ore_stones, List<BlockPos> valid_excavation) {
            this.ores = ores;
            this.ore_stones = ore_stones;
            this.valid_excavation = ore_stones;
        }

        @Override
        public void destroyBlocks(Level world, ItemStack toDamage, @Nullable Player playerEntity,
                                  BiConsumer<BlockPos, ItemStack> drop) {
            ores.forEach(makeCallbackFor(world, 1 / 2f, toDamage, playerEntity, drop));
            ore_stones.forEach(makeCallbackFor(world, 1 / 2f, toDamage, playerEntity, drop));
            valid_excavation.forEach(makeCallbackFor(world, 1 / 2f, toDamage, playerEntity, drop));
        }
    }
}