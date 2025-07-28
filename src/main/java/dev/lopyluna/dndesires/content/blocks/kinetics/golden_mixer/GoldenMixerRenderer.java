package dev.lopyluna.dndesires.content.blocks.kinetics.golden_mixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public class GoldenMixerRenderer extends KineticBlockEntityRenderer<GoldenMixerBE> {
    public GoldenMixerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull GoldenMixerBE be) {
        return true;
    }

    @Override
    protected void renderSafe(GoldenMixerBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;
        var blockState = be.getBlockState();
        var vb = buffer.getBuffer(RenderType.cutoutMipped());

        var superBuffer = CachedBuffers.partial(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL, blockState);
        standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);

        var renderedHeadOffset = be.getRenderedHeadOffset(partialTicks);
        var speed = be.getRenderedHeadRotationSpeed();
        var time = AnimationTickHolder.getRenderTime(be.getLevel());
        var angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        var poleRender = CachedBuffers.partial(DesiresPartialModels.GOLDEN_MIXER_POLE, blockState);
        poleRender.translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vb);

        var headRender = CachedBuffers.partial(DesiresPartialModels.GOLDEN_MIXER_HEAD, blockState);
        headRender.rotateCentered(angle, Direction.UP).translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vb);
    }
}
