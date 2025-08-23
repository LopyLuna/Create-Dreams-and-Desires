package dev.lopyluna.dndesires.content.items.handheld_drill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndesires.DnDesires;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HandheldDrillRenderer extends CustomRenderedItemModelRenderer {

    protected static final PartialModel COG = PartialModel.of(DnDesires.loc("item/handheld_drill/cog"));
    protected static final PartialModel DRILL = PartialModel.of(DnDesires.loc("item/handheld_drill/drill"));

    LerpedFloat lerpSwing;

    public HandheldDrillRenderer() {
        lerpSwing = LerpedFloat.linear();
    }

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer,
                          ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(model.getOriginalModel(), light);
        var angle = 0.5f;
        var mc = Minecraft.getInstance();

        if (mc.player instanceof LocalPlayer player) {
            var hand = player.getMainHandItem();
            var inMainHand = ItemStack.isSameItemSameComponents(hand, stack) && stack.equals(hand) && stack.hashCode() == hand.hashCode();
            var held = mc.options.keyAttack.isDown() || mc.options.keyAttack.consumeClick();
            boolean playerHeldKey = player.getPersistentData().getBoolean("HandheldSawKey");

            if (inMainHand) angle += held ? 2.5f : 0.5f;
            if (playerHeldKey) angle *= 2f;

            lerpSwing.chase((lerpSwing.getValue() + angle) % (36000f), 1f / 32f, LerpedFloat.Chaser.EXP);
            lerpSwing.tickChaser();

            angle = lerpSwing.getValue(AnimationTickHolder.getPartialTicks()) % 360;
            angle *= -2;
        }

        ms.pushPose();
        ms.mulPose(Axis.ZP.rotationDegrees(angle));
        renderer.render(COG.get(), light);
        ms.popPose();
        ms.pushPose();
        ms.mulPose(Axis.ZP.rotationDegrees(-angle));
        renderer.render(DRILL.get(), light);
        ms.popPose();
    }
}
