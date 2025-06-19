package dev.lopyluna.dndesires.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AirCurrent.class)
public class AirCurrentMixin {
    @WrapOperation(method = "getFlowLimit", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/fan/AirCurrent;shouldAlwaysPass(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean getFlowLimit(BlockState state, Operation<Boolean> original, Level level, BlockPos start, float max, Direction facing) {
        if (level.getBlockState(start).is(DesiresBlocks.INDUSTRIAL_FAN)) return DesiresTags.BlockTags.INDUSTRIAL_FAN_TRANSPARENT.is(state);
        return original.call(state);
    }
}
