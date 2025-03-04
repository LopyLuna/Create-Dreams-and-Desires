package dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.lopyluna.create_d2d.register.client.DesiresPartialModels;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.function.Consumer;

public class PoweredFlywheelVisual extends KineticBlockEntityVisual<PoweredFlywheelBE> implements SimpleDynamicVisual {
    protected final RotatingInstance shaft;
    protected final TransformedInstance wheel;
    protected float lastAngle = Float.NaN;

    protected final Matrix4f baseTransform = new Matrix4f();

    public PoweredFlywheelVisual(VisualizationContext context, PoweredFlywheelBE blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        var axis = rotationAxis();
        shaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
                .createInstance();

        shaft.setup(PoweredFlywheelVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(axis)
                .setChanged();

        wheel = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.FLYWHEEL))
                .createInstance();

        var facing = blockState.getValue(PoweredFlywheelBlock.FACING);

        wheel.translate(getVisualPosition())
                .center()
                .rotate(new Quaternionf().rotateTo(1, 0, 0, facing.getStepX(), facing.getStepY(), facing.getStepZ()));

        baseTransform.set(wheel.pose);

        animate(blockEntity.angle);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {

        float partialTicks = ctx.partialTick();

        float speed = blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - lastAngle) < 0.001)
            return;

        animate(angle);

        lastAngle = angle;
    }

    private void animate(float angle) {
        wheel.setTransform(baseTransform)
                .rotateX(AngleHelper.rad(angle))
                .uncenter()
                .setChanged();
    }

    @Override
    public void update(float pt) {
        shaft.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(shaft, wheel);
    }

    @Override
    protected void _delete() {
        shaft.delete();
        wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(shaft);
        consumer.accept(wheel);
    }
}
