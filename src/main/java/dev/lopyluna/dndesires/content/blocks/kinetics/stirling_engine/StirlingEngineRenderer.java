package dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class StirlingEngineRenderer extends SafeBlockEntityRenderer<StirlingEngineBE> {
    @SuppressWarnings("unused") public StirlingEngineRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(StirlingEngineBE be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        var angle = be.getTargetAngle();
        if (angle == null) return;
        var state = be.getBlockState();
        var vb = bufferSource.getBuffer(RenderType.solid());
        var facing = StirlingEngineBlock.getFacing(state);
        var angle2 = AngleHelper.rad(AngleHelper.horizontalAngle(facing));

        CachedBuffers.partial(DesiresPartialModels.FRAME, state)
                .rotateCentered(angle2, Direction.UP)
                .translate(0, 0, -1)
                .light(light)
                .renderInto(ms, vb);

        var facingAxis = facing.getAxis();

        boolean roll90 = !facingAxis.isHorizontal();
        float sine = Mth.sin(angle);
        float sine2 = Mth.sin(angle - Mth.HALF_PI);
        float piston = ((1 - sine) / 4) * 24 / 16f;

        transformed(DesiresPartialModels.ENGINE_PISTON, state, facing, roll90)
                .translate(0, piston, 0)
                .light(light)
                .renderInto(ms, vb);

        transformed(DesiresPartialModels.ENGINE_LINKAGE, state, facing, roll90)
                .center()
                .translate(0, 1, 0)
                .uncenter()
                .translate(0, piston, 0)
                .translate(0, 4 / 16f, 8 / 16f)
                .rotateXDegrees(sine2 * 23f)
                .translate(0, -4 / 16f, -8 / 16f)
                .light(light)
                .renderInto(ms, vb);
    }

    private SuperByteBuffer transformed(PartialModel model, BlockState blockState, Direction facing, boolean roll90) {
        return CachedBuffers.partial(model, blockState)
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing) + 90)
                .rotateYDegrees(roll90 ? -90 : 0)
                .uncenter();
    }

    public int getViewDistance() {
        return 128;
    }
}
