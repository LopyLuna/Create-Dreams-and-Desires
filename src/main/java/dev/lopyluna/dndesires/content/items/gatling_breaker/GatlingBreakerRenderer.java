package dev.lopyluna.dndesires.content.items.gatling_breaker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndesires.DnDesires;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static java.lang.Math.max;

public class GatlingBreakerRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel CORE = PartialModel.of(DnDesires.loc("item/gatling_breaker/core"));
    protected static final PartialModel CORE_GLOW = PartialModel.of(DnDesires.loc("item/gatling_breaker/core_glow"));
    protected static final PartialModel GEAR = PartialModel.of(DnDesires.loc("item/gatling_breaker/gear"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (!(stack.getItem() instanceof GatlingBreakerItem item) || player == null) return;

        float worldTime = AnimationTickHolder.getRenderTime() / 20;

        renderer.renderSolid(model.getOriginalModel(), light);

        boolean mainHand = player.getMainHandItem() == stack;
        boolean offHand = player.getOffhandItem() == stack;
        float animation = 1.1f - ((float) item.shotSpeed / 20f);

        float multiplier;
        if (mainHand || offHand) multiplier = animation;
        else multiplier = Mth.sin(worldTime * 5);

        int lightIntensity = (int) (15 * Mth.clamp(multiplier, 0, 1));
        int glowLight = LightTexture.pack(lightIntensity, max(lightIntensity, 4));
        renderer.renderSolidGlowing(CORE.get(), glowLight);
        renderer.renderGlowing(CORE_GLOW.get(), glowLight);

        float angle = worldTime * -25;
        if (mainHand || offHand) angle += 360;

        angle %= 360;
        float offset = -(3f / 16f);
        ms.translate(0, offset, 0);
        ms.mulPose(Axis.ZP.rotationDegrees(angle));
        ms.translate(0, -offset, 0);
        renderer.render(GEAR.get(), light);
    }
}
