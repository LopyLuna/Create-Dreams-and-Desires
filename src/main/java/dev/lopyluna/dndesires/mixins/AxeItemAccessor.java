package dev.lopyluna.dndesires.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {
    @Invoker("playerHasShieldUseIntent")
    static boolean playerHasShieldUseIntent(UseOnContext context) {
        throw new AssertionError();
    }
    @Invoker("evaluateNewBlockState")
    Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, Player player, BlockState state, UseOnContext context);
}
