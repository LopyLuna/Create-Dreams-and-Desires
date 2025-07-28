package dev.lopyluna.dndesires.content.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface IOnBlockBreak {
    void onBlockBreak(ItemStack pStack, LevelAccessor pLevel, BlockPos pBreakPos, BlockState pState, Player pBreaker);
}
