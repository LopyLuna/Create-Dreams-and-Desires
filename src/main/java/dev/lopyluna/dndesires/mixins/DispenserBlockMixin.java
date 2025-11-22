package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DispenserBlock.class)
public abstract class DispenserBlockMixin extends BaseEntityBlock implements IWrenchable {
    @Shadow @Final public static DirectionProperty FACING;
    protected DispenserBlockMixin(Properties p) { super(p); }

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    protected void useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getMainHandItem().is(Tags.Items.TOOLS_WRENCH) || player.getOffhandItem().is(Tags.Items.TOOLS_WRENCH)) cir.setReturnValue(InteractionResult.PASS);
    }

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) cir.setReturnValue(cir.getReturnValue().setValue(FACING, context.getNearestLookingDirection()));
    }
}
