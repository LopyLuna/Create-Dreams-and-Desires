package dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class IndustrialFanRenderer extends KineticBlockEntityRenderer<IndustrialFanBE> {
    public IndustrialFanRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(IndustrialFanBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        var level = be.getLevel();
        //if (level == null || VisualizationManager.supportsVisualization(level)) return;
        if (level == null) return;
        var direction = be.getBlockState().getValue(FACING);
        var vb = buffer.getBuffer(RenderType.cutoutMipped());

        int lightOverall = LevelRenderer.getLightColor(level, be.getBlockPos());
        int lightInFront = LevelRenderer.getLightColor(level, be.getBlockPos().relative(direction));

        var cog = CachedBuffers.partialFacing(DesiresPartialModels.INDUSTRIAL_FAN_POWER, be.getBlockState(), direction.getOpposite());
        var fanInner = CachedBuffers.partialFacing(DesiresPartialModels.INDUSTRIAL_FAN_INNER, be.getBlockState(), direction.getOpposite());

        var time = AnimationTickHolder.getRenderTime(level);
        var speed = be.getSpeed() * 5;
        if (speed > 0) speed = Mth.clamp(speed, 80, 64 * 20);
        if (speed < 0) speed = Mth.clamp(speed, -64 * 20, -80);
        var angle = (time * speed * 3 / 10f) % 360;
        angle = angle / 180f * (float) Math.PI;

        standardKineticRotationTransform(cog, be, lightOverall).renderInto(ms, vb);
        kineticRotationTransform(fanInner, be, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
    }
}
