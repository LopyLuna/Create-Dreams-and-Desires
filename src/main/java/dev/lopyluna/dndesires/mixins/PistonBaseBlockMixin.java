package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin extends DirectionalBlock implements IWrenchable {
    protected PistonBaseBlockMixin(Properties p) { super(p); }


    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) cir.setReturnValue(cir.getReturnValue().setValue(FACING, context.getNearestLookingDirection()));
    }
}
