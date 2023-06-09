package uwu.lopyluna.create_flavored.entity.Bobert;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SmartBuildercrackinesslayer extends RenderLayer<SmartBuilderEntity, SmartBuilderModel<SmartBuilderEntity>> {
    private static final Map<SmartBuilderEntity.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(SmartBuilderEntity.Crackiness.LOW, new ResourceLocation("textures/entity/bobert/bobert_crackiness_low.png"), SmartBuilderEntity.Crackiness.MEDIUM, new ResourceLocation("textures/entity/bobert/bobert_crackiness_medium.png"), SmartBuilderEntity.Crackiness.HIGH, new ResourceLocation("textures/entity/bobert/bobert_crackiness_high.png"));

    public SmartBuildercrackinesslayer(RenderLayerParent<SmartBuilderEntity, SmartBuilderModel<SmartBuilderEntity>> pRenderer) {
        super(pRenderer);
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, SmartBuilderEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            SmartBuilderEntity.Crackiness smartbuilder$crackiness = pLivingEntity.getCrackiness();
            if (smartbuilder$crackiness != SmartBuilderEntity.Crackiness.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(smartbuilder$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
