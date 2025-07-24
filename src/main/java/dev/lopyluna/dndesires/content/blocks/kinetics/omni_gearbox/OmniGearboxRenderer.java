package dev.lopyluna.dndesires.content.blocks.kinetics.omni_gearbox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

import java.util.HashMap;
import java.util.Map;

public class OmniGearboxRenderer extends KineticBlockEntityRenderer<OmniGearboxBE> {
    public OmniGearboxRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(OmniGearboxBE be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        var level = be.getLevel();
        if (level == null) return;
        var pos = be.getBlockPos();
        var state = be.getBlockState();
        var time = AnimationTickHolder.getRenderTime(level);

        Map<Direction, PartialModel> panels = new HashMap<>();
        panels.put(Direction.UP, DesiresPartialModels.TOP_PANEL);
        panels.put(Direction.DOWN, DesiresPartialModels.BOTTOM_PANEL);
        panels.put(Direction.NORTH, DesiresPartialModels.NORTH_PANEL);
        panels.put(Direction.EAST, DesiresPartialModels.EAST_PANEL);
        panels.put(Direction.SOUTH, DesiresPartialModels.SOUTH_PANEL);
        panels.put(Direction.WEST, DesiresPartialModels.WEST_PANEL);

        Map<Direction, Boolean> shafts = new HashMap<>();
        shafts.put(Direction.UP, state.getValue(OmniGearboxBlock.UP_SHAFT));
        shafts.put(Direction.DOWN, state.getValue(OmniGearboxBlock.DOWN_SHAFT));
        shafts.put(Direction.NORTH, state.getValue(OmniGearboxBlock.NORTH_SHAFT));
        shafts.put(Direction.EAST, state.getValue(OmniGearboxBlock.EAST_SHAFT));
        shafts.put(Direction.SOUTH, state.getValue(OmniGearboxBlock.SOUTH_SHAFT));
        shafts.put(Direction.WEST, state.getValue(OmniGearboxBlock.WEST_SHAFT));

        for (var direction : Iterate.directions) {
            if (shafts.get(direction)) {
                var axis = direction.getAxis();
                var shaft = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, direction);
                var speed = be.getSpeed();
                var offset = getRotationOffsetForPosition(be, pos, axis);
                var angle = (time * speed * 3f / 10f) % 360f;

                angle += offset;
                angle = angle / 180f * (float) Math.PI;

                kineticRotationTransform(shaft, be, axis, angle, light);
                shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
            } else CachedBuffers.partial(panels.get(direction), state)
                    .light(LevelRenderer.getLightColor(level, pos))
                    .renderInto(ms, buffer.getBuffer(RenderType.solid()));
        }
    }
}
