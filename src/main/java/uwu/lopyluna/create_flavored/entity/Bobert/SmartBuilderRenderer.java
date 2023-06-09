package uwu.lopyluna.create_flavored.entity.Bobert;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_flavored.entity.bobertModelLayer;

@OnlyIn(Dist.CLIENT)
public class SmartBuilderRenderer extends MobRenderer<SmartBuilderEntity, SmartBuilderModel<SmartBuilderEntity>> {
    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation("textures/entity/bobert/bobert.png");

    public SmartBuilderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SmartBuilderModel<>(renderManager.bakeLayer(bobertModelLayer.SMART_BUILDER)), 0.2F);
        this.addLayer(new SmartBuildercrackinesslayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(SmartBuilderEntity pEntity) {
        return GOLEM_LOCATION;
    }

    protected void setupRotations(SmartBuilderEntity pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        if (!((double)pEntityLiving.animationSpeed < 0.01D)) {
            float f = 13.0F;
            float f1 = pEntityLiving.animationPosition - pEntityLiving.animationSpeed * (1.0F - pPartialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }
}