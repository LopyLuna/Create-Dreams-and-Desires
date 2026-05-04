package dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class SpudSentryRenderer extends KineticBlockEntityRenderer<SpudSentryBE> {
    public SpudSentryRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SpudSentryBE be, float pt, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, pt, ms, buffer, light, overlay);
        var state = be.getBlockState();
        var xRot = be.lerpX.getValue(pt);
        var yRot = be.lerpY.getValue(pt);

        VertexConsumer builder = buffer.getBuffer(RenderType.cutout());

        var msLocal = new PoseStack();
        var msr = TransformStack.of(msLocal);


        msr.pushPose();
        msr.center();
        SuperByteBuffer base = CachedBuffers.partial(DesiresPartialModels.SPUD_BASE, state)
                .light(light);

        msr.rotateYDegrees(-yRot+180);
        msr.scale(1.01f);
        msr.uncenter();
        base.transform(msLocal).renderInto(ms, builder);
        msr.popPose();


        msr.pushPose();
        float px = 8f/16f, py = 22f/16f, pz = 10f/16f; //Pivot Facing North
        SuperByteBuffer cannon = CachedBuffers.partial(DesiresPartialModels.SPUD_CANNON, state)
                .light(light);

        msr.center();

        msr.rotateYDegrees(-yRot + 180);

        msr.uncenter();
        msr.translate(px, py, pz);
        msr.rotateXDegrees(-xRot);
        msr.translate(-px, -py, -pz);

        cannon.transform(msLocal).renderInto(ms, builder);
        msr.popPose();
    }

    @Override
    protected SuperByteBuffer getRotatedModel(SpudSentryBE be, BlockState state) {
        //if (VisualizationManager.supportsVisualization(be.getLevel())) return;
        return CachedBuffers.partial(DesiresPartialModels.SPUD_GEAR, state);
    }
}
