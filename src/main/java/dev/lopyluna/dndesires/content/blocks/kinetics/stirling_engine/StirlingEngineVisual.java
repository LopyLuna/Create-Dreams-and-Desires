package dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class StirlingEngineVisual extends AbstractBlockEntityVisual<StirlingEngineBE> implements SimpleDynamicVisual {

    protected TransformedInstance piston;
    protected TransformedInstance linkage;

    private Float lastAngle = Float.NaN;
    //private Direction.Axis lastAxis = null;

    protected TransformedInstance frame;

    public StirlingEngineVisual(VisualizationContext ctx, StirlingEngineBE blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);
        piston = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.ENGINE_PISTON)).createInstance();
        linkage = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.ENGINE_LINKAGE)).createInstance();

        animate();

        var facing = blockState.getValue(StirlingEngineBlock.FACING);
        var angle = AngleHelper.rad(AngleHelper.horizontalAngle(facing));

        frame = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.FRAME)).createInstance();
        frame.setIdentityTransform()
                .translate(getVisualPosition())
                .nudge(pos.hashCode())
                .rotateCentered(angle, Direction.UP)
                .translate(0, 0, -1)
        ;
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(piston);
        consumer.accept(linkage);
        consumer.accept(frame);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(piston, linkage, frame);
    }

    @Override
    protected void _delete() {
        piston.delete();
        linkage.delete();
        frame.delete();
    }

    @Override
    public void beginFrame(Context ctx) {
        animate();
    }

    private void animate() {
        var angle = blockEntity.getTargetAngle();

        if (Objects.equals(angle, lastAngle)) return;

        lastAngle = angle;

        if (angle == null) {
            piston.setVisible(false);
            linkage.setVisible(false);
            return;
        } else {
            piston.setVisible(true);
            linkage.setVisible(true);
        }

        var facing = StirlingEngineBlock.getFacing(blockState);
        var facingAxis = facing.getAxis();

        boolean roll90 = !facingAxis.isHorizontal();
        float sine = Mth.sin(angle);
        float sine2 = Mth.sin(angle - Mth.HALF_PI);
        float piston = ((1 - sine) / 4) * 24 / 16f;

        transformed(this.piston, facing, roll90)
                .translate(0, piston, 0)
                .setChanged();

        transformed(this.linkage, facing, roll90)
                .center()
                .translate(0, 1, 0)
                .uncenter()
                .translate(0, piston, 0)
                .translate(0, 4 / 16f, 8 / 16f)
                .rotateXDegrees(sine2 * 23f)
                .translate(0, -4 / 16f, -8 / 16f)
                .setChanged();
    }

    protected TransformedInstance transformed(TransformedInstance modelData, Direction facing, boolean roll90) {
        return modelData.setIdentityTransform()
                .translate(getVisualPosition())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing) + 90)
                .rotateYDegrees(roll90 ? -90 : 0)
                .uncenter();
    }

}
