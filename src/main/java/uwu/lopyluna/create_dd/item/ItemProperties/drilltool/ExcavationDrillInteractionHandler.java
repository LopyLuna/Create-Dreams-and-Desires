package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.item.DDItems;

@SuppressWarnings({"all"})
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ExcavationDrillInteractionHandler {

    public static float mainHandAnimation;
    public static float lastMainHandAnimation;


    @SubscribeEvent
    public static void onRenderPlayerHand(RenderHandEvent event) {
        ItemStack heldItem = event.getItemStack();
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        boolean rightHand = event.getHand() == InteractionHand.MAIN_HAND ^ player.getMainArm() == HumanoidArm.LEFT;
        ItemStack offhandItem = !rightHand ? heldItem : rightHand ? heldItem : heldItem;
        assert offhandItem != null;
        boolean notInOffhand = !DDItems.excavation_drill.isIn(offhandItem);
        if (notInOffhand && !DDItems.excavation_drill.isIn(heldItem))
            return;

        PoseStack ms = event.getPoseStack();
        TransformStack msr = TransformStack.cast(ms);
        AbstractClientPlayer abstractclientplayerentity = mc.player;
        RenderSystem.setShaderTexture(0, abstractclientplayerentity.getSkinTextureLocation());

        float flip = rightHand ? 1.0F : -1.0F;
        float swingProgress = event.getSwingProgress();
        boolean excavationDrillItem = heldItem.getItem() instanceof ExcavationDrillItem;
        float equipProgress = excavationDrillItem ? 0 : event.getEquipProgress() / 4;

        ms.pushPose();
        if (event.getHand() == InteractionHand.MAIN_HAND) {

            if (1 - swingProgress > mainHandAnimation && swingProgress > 0)
                mainHandAnimation = 0.95f;
            float animation = Mth.lerp(AnimationTickHolder.getPartialTicks(),
                    ExcavationDrillInteractionHandler.lastMainHandAnimation,
                    ExcavationDrillInteractionHandler.mainHandAnimation);
            animation = animation * animation * animation;

            ms.translate(flip * (0.64000005F - .1f), -0.4F + equipProgress * -0.6F, -0.71999997F + .3f);

            ms.pushPose();
            msr.rotateY(flip * 75.0F);
            ms.translate(flip * -1.0F, 3.6F, 3.5F);
            msr.rotateZ(flip * 120)
                    .rotateX(200)
                    .rotateY(flip * -135.0F);
            ms.translate(flip * 5.6F, 0.0F, 0.0F);
            msr.rotateY(flip * 40.0F);
            ms.translate(flip * 0.05f, -0.3f, -0.3f);

            PlayerRenderer playerrenderer = (PlayerRenderer) mc.getEntityRenderDispatcher()
                    .getRenderer(player);
            if (rightHand)
                playerrenderer.renderRightHand(event.getPoseStack(), event.getMultiBufferSource(),
                        event.getPackedLight(), player);
            else
                playerrenderer.renderLeftHand(event.getPoseStack(), event.getMultiBufferSource(),
                        event.getPackedLight(), player);
            ms.popPose();

            // Render gun
            ms.pushPose();
            ms.translate(flip * -0.1f, 0, -0.3f);
            ItemInHandRenderer firstPersonRenderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();
            ItemTransforms.TransformType transform =
                    rightHand ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
            firstPersonRenderer.renderItem(mc.player, notInOffhand ? heldItem : offhandItem, transform, !rightHand,
                    event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());

            ms.popPose();
        }
        ms.popPose();
        event.setCanceled(true);
    }
}
