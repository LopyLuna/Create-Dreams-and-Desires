package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.AllFluids;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AllFluids.class, remap = false)
public class AllFluidsMixin {
    @Inject(method = "getLavaInteraction(Lnet/minecraft/world/level/material/FluidState;)Lnet/minecraft/world/level/block/state/BlockState;", at=@At("HEAD"))
    private static void getLavaInteraction$D2D(FluidState fluidState, CallbackInfoReturnable<BlockState> cir) {
        //var state = DesiresFluids.getLavaInteraction(fluidState);
        //if (state != null) cir.setReturnValue(state);
    }
}
