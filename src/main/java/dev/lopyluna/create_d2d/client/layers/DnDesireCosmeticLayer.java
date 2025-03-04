package dev.lopyluna.create_d2d.client.layers;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class DnDesireCosmeticLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final String CAPE_URL = "https://github.com/LopyLuna/Obelisb/blob/1.21/dndesire_one_mil_cape.png?raw=true";
    private static ResourceLocation dynamicCapeTexture;
    public String[] uuidList;
    private final ElytraModel<AbstractClientPlayer> elytraModel;
    public static List<String> DISABLED_COSMETICS = new ArrayList<>();

    public DnDesireCosmeticLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> pRenderer, EntityModelSet pModelSet, String[] uuidList) {
        super(pRenderer);
        this.uuidList = uuidList;
        this.elytraModel = new ElytraModel<>(pModelSet.bakeLayer(ModelLayers.ELYTRA));
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull AbstractClientPlayer pLivingEntity,
                       float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        var uuid = pLivingEntity.getUUID().toString();
        var pModel = this.getParentModel();
        if (!DISABLED_COSMETICS.contains(uuid) && ((Arrays.stream(uuidList).toList().contains(uuid) || "Dev".equals(pLivingEntity.getGameProfile().getName())) && !pLivingEntity.isInvisible())) {
            loadCapeTexture();
            if (dynamicCapeTexture != null) {
                ItemStack itemstack = pLivingEntity.getItemBySlot(EquipmentSlot.CHEST);
                if (itemstack.is(Items.ELYTRA)) {
                    pPoseStack.pushPose();
                    pPoseStack.translate(0.0F, 0.0F, 0.145F);
                    pModel.copyPropertiesTo(elytraModel);
                    elytraModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(pBuffer, RenderType.armorCutoutNoCull(dynamicCapeTexture), itemstack.hasFoil());
                    elytraModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
                    pPoseStack.pushPose();
                    pPoseStack.translate(0.0F, 0.0F, -0.04F);
                    elytraModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
                    pPoseStack.popPose();
                    pPoseStack.popPose();
                } else {
                    pPoseStack.pushPose();
                    pPoseStack.translate(0.0F, 0.0F, 0.145F);
                    double d0 = Mth.lerp(pPartialTicks, pLivingEntity.xCloakO, pLivingEntity.xCloak) - Mth.lerp(pPartialTicks, pLivingEntity.xo, pLivingEntity.getX());
                    double d1 = Mth.lerp(pPartialTicks, pLivingEntity.yCloakO, pLivingEntity.yCloak) - Mth.lerp(pPartialTicks, pLivingEntity.yo, pLivingEntity.getY());
                    double d2 = Mth.lerp(pPartialTicks, pLivingEntity.zCloakO, pLivingEntity.zCloak) - Mth.lerp(pPartialTicks, pLivingEntity.zo, pLivingEntity.getZ());
                    float f = Mth.rotLerp(pPartialTicks, pLivingEntity.yBodyRotO, pLivingEntity.yBodyRot);
                    double d3 = Mth.sin(f * (float) (Math.PI / 180.0));
                    double d4 = -Mth.cos(f * (float) (Math.PI / 180.0));
                    float f1 = (float)d1 * 10.0F;
                    f1 = Mth.clamp(f1, -6.0F, 32.0F);
                    float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                    f2 = Mth.clamp(f2, 0.0F, 150.0F);
                    float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                    f3 = Mth.clamp(f3, -20.0F, 20.0F);
                    if (f2 < 0.0F) f2 = 0.0F;

                    float f4 = Mth.lerp(pPartialTicks, pLivingEntity.oBob, pLivingEntity.bob);
                    f1 += Mth.sin(Mth.lerp(pPartialTicks, pLivingEntity.walkDistO, pLivingEntity.walkDist) * 6.0F) * 32.0F * f4;
                    if (pLivingEntity.isCrouching()) f1 += 25.0F;

                    pPoseStack.mulPose(Axis.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                    pPoseStack.mulPose(Axis.ZP.rotationDegrees(f3 / 2.0F));
                    pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f3 / 2.0F));
                    VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entitySolid(dynamicCapeTexture));
                    pModel.renderCloak(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
                    pPoseStack.pushPose();
                    pPoseStack.translate(0.0F, 0.0F, -0.04F);
                    pModel.renderCloak(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
                    pPoseStack.popPose();
                    pPoseStack.popPose();
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void loadCapeTexture() {
        if (dynamicCapeTexture == null) try {
            HttpURLConnection connection = (HttpURLConnection) new URL(CAPE_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                try (InputStream inputStream = connection.getInputStream()) {
                    NativeImage image = NativeImage.read(inputStream);
                    dynamicCapeTexture = Minecraft.getInstance().getTextureManager()
                            .register("dynamic_cape", new net.minecraft.client.renderer.texture.DynamicTexture(image));
                }
            }
        } catch (Exception ignored) {
        }
    }
}