package dev.lopyluna.dndesires.compat;

import dev.lopyluna.dndesires.content.blocks.logistics.bore_block.BoreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class LoadedMods {
    public static boolean SABLE = false;
    public static Function<BlockBehaviour.Properties, BoreBlock> sableBoreMaker;
}
