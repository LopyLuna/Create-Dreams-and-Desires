package dev.lopyluna.dndesires.mixins.compat.sable;

import dev.lopyluna.dndesires.content.blocks.logistics.bore_block.BoreBlock;
import dev.ryanhcode.sable.api.block.BlockSubLevelCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("NullableProblems")
@Mixin(BoreBlock.class)
public abstract class BoreBlockMixin extends Block implements BlockSubLevelCollisionShape {
    public BoreBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getSubLevelCollisionShape(BlockGetter blockGetter, BlockState state) {
        return Block.box(7, 7, 7, 9, 9, 9);
    }
}
