package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.RenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

@SuppressWarnings({"all"})
public class RadiantPanelRenderer extends KineticBlockEntityRenderer<RadiantPanelBlockEntity> {

    public RadiantPanelRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(RadiantPanelBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        VertexConsumer vbGT = buffer.getBuffer(RenderTypes.getGlowingTranslucent());
        VertexConsumer vbG = buffer.getBuffer(RenderTypes.getGlowingSolid());
        VertexConsumer vbA = buffer.getBuffer(RenderTypes.getAdditive());

        ms.pushPose();
        standardKineticRotationTransform(CachedBufferer.partial(DDBlockPartialModel.RADIANT_COG, state), be, LightTexture.FULL_BRIGHT).renderInto(ms, vbGT);
        ms.popPose();

        ms.pushPose();
        if (be.active || be.weak_active) {
            CachedBufferer.partial(DDBlockPartialModel.RADIANT_PANEL_GLOW, state)
                    .centre()
                    .unCentre()
                    .light(LightTexture.FULL_BRIGHT)
                    .disableDiffuse()
                    .renderInto(ms, vbA);

            CachedBufferer.partial(DDBlockPartialModel.RADIANT_PANEL, state)
                    .centre()
                    .unCentre()
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, vbG);
        }
        if (!be.active && !be.weak_active) {
            CachedBufferer.partial(DDBlockPartialModel.RADIANT_PANEL, state)
                    .centre()
                    .unCentre()
                    .light(LevelRenderer.getLightColor(be.getLevel(), state, be.getBlockPos()))
                    .renderInto(ms, vb);
        }
        ms.popPose();

    }



}
