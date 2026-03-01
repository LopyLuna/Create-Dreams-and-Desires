package dev.lopyluna.dndesires.content.blocks.kinetics.golden_mixer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.minecraft.core.Direction;

import java.util.function.Consumer;

public class GoldenMixerVisual extends SingleAxisRotatingVisual<GoldenMixerBE> implements SimpleDynamicVisual {
    private final RotatingInstance mixerHead;
    private final OrientedInstance mixerPole;
    private final GoldenMixerBE mixer;

    public GoldenMixerVisual(VisualizationContext context, GoldenMixerBE blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL));
        this.mixer = blockEntity;

        mixerHead = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(DesiresPartialModels.GOLDEN_MIXER_HEAD)).createInstance();
        mixerHead.setRotationAxis(Direction.Axis.Y);

        mixerPole = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(DesiresPartialModels.GOLDEN_MIXER_POLE)).createInstance();
        animate(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        animate(ctx.partialTick());
    }

    private void animate(float pt) {
        float renderedHeadOffset = mixer.getRenderedHeadOffset(pt);
        transformPole(renderedHeadOffset);
        transformHead(renderedHeadOffset);
    }

    private void transformHead(float renderedHeadOffset) {
        float speed = mixer.getRenderedHeadRotationSpeed();
        mixerHead.setPosition(getVisualPosition())
            .nudge(0, -renderedHeadOffset, 0)
            .setRotationalSpeed(speed * 2 * RotatingInstance.SPEED_MULTIPLIER)
            .setChanged();
    }

    private void transformPole(float renderedHeadOffset) {
        mixerPole.position(getVisualPosition())
            .translatePosition(0, -renderedHeadOffset, 0)
            .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);

        relight(pos.below(), mixerHead);
        relight(mixerPole);
    }

    @Override
    protected void _delete() {
        super._delete();
        mixerHead.delete();
        mixerPole.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(mixerHead);
        consumer.accept(mixerPole);
    }
}
