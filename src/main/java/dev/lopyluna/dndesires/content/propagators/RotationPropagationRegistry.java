package dev.lopyluna.dndesires.content.propagators;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RotationPropagationRegistry {
    private static final Map<Supplier<Block>, RotationPropagation> handlers = new HashMap<>();

    public static void register(Supplier<Block> block, RotationPropagation handler) {
        handlers.put(block, handler);
    }

    public static RotationPropagation get(Supplier<Block> block) {
        return !handlers.isEmpty() ? handlers.get(block) : null;
    }

    public static RotationPropagation get(Block block) {
        return get(() -> block);
    }

    public static RotationPropagation get(BlockState state) {
        return get(state.getBlock());
    }

    public static RotationPropagation get(Level level, BlockPos pos) {
        return get(level.getBlockState(pos));
    }

    public static RotationPropagation get(KineticBlockEntity be) {
        return get(be.getBlockState());
    }
}
