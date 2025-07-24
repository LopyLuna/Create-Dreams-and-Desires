package dev.lopyluna.dndesires.content.blocks.kinetics.multimeter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public class MultiMeterRenderer extends ShaftRenderer<MultiMeterBE> {

	public MultiMeterRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(MultiMeterBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		var level = be.getLevel();
		var pos = be.getBlockPos();
		var state = be.getBlockState();

        var model = DesiresPartialModels.MULTIMETER_HEAD;
		var headBuffer = CachedBuffers.partial(model, state);
		var dialBuffer = CachedBuffers.partial(AllPartialModels.GAUGE_DIAL, state);

		float dialPivot = 5.75f / 16;
		float progress = Mth.lerp(partialTicks, be.prevDialState, be.dialState);

		for (var facing : Iterate.directions) {
			if (state.getBlock() instanceof MultiMeterBlock block && block.shouldntRenderHeadOnFace(level, pos, state, facing)) continue;

			var vb = buffer.getBuffer(RenderType.solid());
			rotateBufferTowards(dialBuffer, facing)
					.translate(0, dialPivot, dialPivot)
					.rotate((float) (Math.PI / 2 * -progress), Direction.EAST)
					.translate(0, -dialPivot, -dialPivot)
					.light(light)
					.renderInto(ms, vb);

			rotateBufferTowards(headBuffer, facing)
					.light(light)
					.renderInto(ms, vb);
		}
	}

	protected SuperByteBuffer rotateBufferTowards(SuperByteBuffer buffer, Direction target) {
		return buffer.rotateCentered((float) ((-target.toYRot() - 90) / 180 * Math.PI), Direction.UP);
	}
}
