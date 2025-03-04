package dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.lopyluna.create_d2d.register.client.DesiresPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class PoweredFlywheelRenderer extends KineticBlockEntityRenderer<PoweredFlywheelBE> {

    public PoweredFlywheelRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(PoweredFlywheelBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;
        BlockState state = be.getBlockState();
        float speed = be.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = be.angle + speed * partialTicks;
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderFlywheel(be, ms, light, state, angle, vb);
    }

    private void renderFlywheel(PoweredFlywheelBE be, PoseStack ms, int light, BlockState blockState, float angle, VertexConsumer vb) {
        SuperByteBuffer wheel = CachedBuffers.partialFacing(DesiresPartialModels.FLYWHEEL, blockState, blockState.getValue(PoweredFlywheelBlock.FACING));
        kineticRotationTransform(wheel, be, getRotationAxisOf(be), AngleHelper.rad(angle), light);
        wheel.renderInto(ms, vb);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(PoweredFlywheelBE be, BlockState state) {
        return CachedBuffers.partialFacing(DesiresPartialModels.FLYWHEEL, state, state.getValue(PoweredFlywheelBlock.FACING).getOpposite());
    }

    @Override
    protected BlockState getRenderedBlockState(PoweredFlywheelBE be) {
        return shaft(getRotationAxisOf(be));
    }
}
