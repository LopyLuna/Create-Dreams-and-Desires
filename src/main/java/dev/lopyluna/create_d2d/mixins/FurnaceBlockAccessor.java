package dev.lopyluna.create_d2d.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlock.class)
public interface FurnaceBlockAccessor {
    @Invoker("openContainer")
    void openContainer$D2D(Level level, BlockPos pos, Player player);
}
