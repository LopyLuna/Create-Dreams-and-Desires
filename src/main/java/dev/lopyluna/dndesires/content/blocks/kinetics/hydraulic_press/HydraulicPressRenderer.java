package dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.lopyluna.dndesires.content.utils.AABBF;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@ParametersAreNonnullByDefault
public class HydraulicPressRenderer extends KineticBlockEntityRenderer<HydraulicPressBE> {
    public HydraulicPressRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(HydraulicPressBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        renderFluid(be, partialTicks, ms, buffer, light);
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        BlockState blockState = be.getBlockState();
        PressingBehaviour pressingBehaviour = be.pressingBehaviour;

        ms.pushPose();
        float renderedHeadOffset = pressingBehaviour.getRenderedHeadOffset(partialTicks) * pressingBehaviour.mode.headOffset;
        SuperByteBuffer headRender = CachedBuffers.partialFacing(DesiresPartialModels.HYDRAULIC_HEAD, blockState, blockState.getValue(HORIZONTAL_FACING));
        headRender.translate(0, -renderedHeadOffset, 0)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
        ms.popPose();
    }

    protected void renderFluid(HydraulicPressBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light) {
        SmartFluidTankBehaviour tank = be.tank;
        SmartFluidTankBehaviour.TankSegment primaryTank = tank.getPrimaryTank();
        FluidStack fluidStack = primaryTank.getRenderedFluid();
        float level = primaryTank.getFluidLevel().getValue(partialTicks);
        if (!fluidStack.isEmpty() && level != 0) if (!(level < 1f / 256f)) {
            var clampedLevel = Mth.clamp(level * 0.625f, 0, 0.625f);
            var top = fluidStack.getFluid().getFluidType().isLighterThanAir();
            var yMin = 0.8125 - clampedLevel;
            var yMax = yMin + clampedLevel;
            if (top) {
                yMin += 0.625 - clampedLevel;
                yMax += 0.625 - clampedLevel;
            }

            ms.pushPose();
            ms.translate(0, clampedLevel - 0.625f, 0);

            var bb = new AABBF(new AABB(0.09375, yMin, 0.09375, 0.90625, yMax, 0.90625));
            NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack, bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, buffer, ms, light, false, true);
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(HydraulicPressBE be) {
        return true;
    }
    @Override
    protected BlockState getRenderedBlockState(HydraulicPressBE be) {
        return shaft(getRotationAxisOf(be));
    }
}
