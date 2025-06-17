package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.lopyluna.dndesires.content.propagators.RotationPropagationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = RotationPropagator.class, remap = false)
public class RotationPropagatorMixin {

    @Inject(method = "getRotationSpeedModifier", at = @At("HEAD"), cancellable = true)
    private static void injectSpeedModifier(KineticBlockEntity from, KineticBlockEntity to, CallbackInfoReturnable<Float> cir) {
        var handler = RotationPropagationRegistry.get(from);
        if (handler != null) {
            var result = handler.getRotationSpeedModifier(from, to);
            if (result != null) cir.setReturnValue(result);
        }
    }

    @Inject(method = "getConveyedSpeed", at = @At("HEAD"), cancellable = true)
    private static void injectConveyedSpeed(KineticBlockEntity from, KineticBlockEntity to, CallbackInfoReturnable<Float> cir) {
        var handler = RotationPropagationRegistry.get(from);
        if (handler != null) {
            var result = handler.getConveyedSpeed(from, to);
            if (result != null) cir.setReturnValue(result);
        }
    }

    @Inject(method = "propagateNewSource", at = @At("HEAD"), cancellable = true)
    private static void injectPropagateNewSource(KineticBlockEntity currentTE, CallbackInfo ci) {
        var handler = RotationPropagationRegistry.get(currentTE);
        if (handler != null && handler.propagateNewSource(currentTE)) ci.cancel();
    }

    @Inject(method = "handleRemoved", at = @At("HEAD"), cancellable = true)
    private static void injectHandleRemoved(Level worldIn, BlockPos pos, KineticBlockEntity removedBE, CallbackInfo ci) {
        var handler = RotationPropagationRegistry.get(removedBE);
        if (handler != null && handler.handleRemoved(worldIn, pos, removedBE)) ci.cancel();
    }

    @Inject(method = "getPotentialNeighbourLocations", at = @At("RETURN"), cancellable = true)
    private static void injectPotentialNeighbours(KineticBlockEntity be, CallbackInfoReturnable<List<BlockPos>> cir) {
        var handler = RotationPropagationRegistry.get(be);
        if (handler != null) {
            var result = handler.getPotentialNeighbourLocations(be, cir.getReturnValue());
            if (result != null) cir.setReturnValue(result);
        }
    }
}
