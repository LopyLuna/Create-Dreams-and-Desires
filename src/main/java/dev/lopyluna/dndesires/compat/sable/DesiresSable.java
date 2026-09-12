package dev.lopyluna.dndesires.compat.sable;

import dev.lopyluna.dndesires.compat.LoadedMods;
import dev.lopyluna.dndesires.content.blocks.logistics.bore_block.BoreBlock;
import dev.ryanhcode.sable.api.block.BlockSubLevelCollisionShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class DesiresSable {
    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        LoadedMods.sableBoreMaker = SableBoreBlock::new;
    }

    public static class SableBoreBlock extends BoreBlock implements BlockSubLevelCollisionShape {
        public SableBoreBlock(Properties properties) {
            super(properties,true);
        }

        @Override
        public VoxelShape getSubLevelCollisionShape(BlockGetter blockGetter, BlockState state) {
            return collisionShapes[state.getValue(BORE_STATE)];
        }
    }
}
