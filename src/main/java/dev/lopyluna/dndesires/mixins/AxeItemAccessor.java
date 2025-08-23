package dev.lopyluna.dndesires.mixins;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {
    @Invoker("playerHasShieldUseIntent")
    static boolean playerHasShieldUseIntent(UseOnContext context) {
        throw new AssertionError();
    }
}
