package uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.RenderTypes;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

public class RadiantDrillRenderer extends KineticBlockEntityRenderer<RadiantDrillBlockEntity> {

    public RadiantDrillRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(RadiantDrillBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(DDBlockPartialModel.RADIANT_DRILL_HEAD, state);
    }
    protected SuperByteBuffer getRotatedModelGlow(RadiantDrillBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(DDBlockPartialModel.RADIANT_DRILL_HEAD_GLOW, state).disableDiffuse();
    }

    @Override
    protected void renderSafe(RadiantDrillBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        if (type != null) {
            renderRotatingBuffer(be, getRotatedModel(be, state), ms, buffer.getBuffer(RenderType.solid()), LightTexture.FULL_BRIGHT);
            renderRotatingBuffer(be, getRotatedModelGlow(be, state), ms, buffer.getBuffer(RenderTypes.getAdditive()), LightTexture.FULL_BRIGHT);
        }
    }

    public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource buffer) {
        BlockState state = context.state;
        SuperByteBuffer drill = CachedBufferer.partial(DDBlockPartialModel.RADIANT_DRILL_HEAD, state);
        SuperByteBuffer drillg = CachedBufferer.partial(DDBlockPartialModel.RADIANT_DRILL_HEAD_GLOW, state);
        PoseStack ms = matrices.getViewProjection();

        Direction facing = state.getValue(RadiantDrillBlock.FACING);

        float speed = (float) (context.contraption.stalled || !VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite()) ? context.getAnimationSpeed() : 0);
        float time = AnimationTickHolder.getRenderTime() / 20;
        float angle = (float) (((time * speed) % 360));

        ms.pushPose();
        drill.transform(matrices.getModel())
                .centre()
                .rotateY(AngleHelper.horizontalAngle(facing))
                .rotateX(AngleHelper.verticalAngle(facing))
                .rotateZ(angle)
                .unCentre()
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
        drillg.transform(matrices.getModel())
                .centre()
                .rotateY(AngleHelper.horizontalAngle(facing))
                .rotateX(AngleHelper.verticalAngle(facing))
                .rotateZ(angle)
                .unCentre()
                .light(LightTexture.FULL_BRIGHT)
                .disableDiffuse()
                .renderInto(ms, buffer.getBuffer(RenderTypes.getAdditive()));
        ms.popPose();
    }

}
