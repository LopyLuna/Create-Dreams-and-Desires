package dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CogCrankRenderer extends KineticBlockEntityRenderer<CogCrankBE> {
    public CogCrankRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CogCrankBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (be.shouldRenderCog()) super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getLevel())) return;
        kineticRotationTransform(be.getRenderedHandle(), be, be.getBlockState().getValue(CogCrankBlock.AXIS), be.getIndependentAngle(partialTicks), light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}