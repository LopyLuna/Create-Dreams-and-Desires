package dev.lopyluna.create_d2d.content.blocks.stirling_engine;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.PosedInstance;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.create_d2d.register.client.DesiresPartialModels;
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
    private Direction.Axis lastAxis = null;

    protected PosedInstance frame;

    public StirlingEngineVisual(VisualizationContext ctx, StirlingEngineBE blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);

        Direction facing = blockState.getValue(StirlingEngineBlock.FACING);

        frame = instancerProvider().instancer(InstanceTypes.POSED, Models.partial(DesiresPartialModels.FRAME)).createInstance();

        float angle = AngleHelper.rad(AngleHelper.horizontalAngle(facing));

        frame.setIdentityTransform()
                .translate(getVisualPosition())
                .nudge(pos.hashCode())
                .center()
                .rotate(angle, Direction.UP)
                .uncenter()
                .translate(0, 0, -1);

        piston = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.ENGINE_PISTON))
                .createInstance();
        linkage = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.ENGINE_LINKAGE))
                .createInstance();

        animate();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(frame);
        consumer.accept(piston);
        consumer.accept(linkage);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(frame, piston, linkage);
    }

    @Override
    protected void _delete() {
        frame.delete();
        piston.delete();
        linkage.delete();
    }

    @Override
    public void beginFrame(Context ctx) {
        animate();
    }

    private void animate() {
        Float angle = blockEntity.getTargetAngle();
        Direction.Axis axis = Direction.Axis.Y;

        PoweredFlywheelBE shaft = blockEntity.getFlywheel();
        if (shaft != null)
            axis = KineticBlockEntityRenderer.getRotationAxisOf(shaft);

        if (Objects.equals(angle, lastAngle) && lastAxis == axis) {
            return;
        }

        lastAngle = angle;
        lastAxis = axis;

        if (angle == null) {
            piston.setVisible(false);
            linkage.setVisible(false);
            return;
        } else {
            piston.setVisible(true);
            linkage.setVisible(true);
        }

        Direction facing = StirlingEngineBlock.getFacing(blockState);
        Direction.Axis facingAxis = facing.getAxis();

        boolean roll90 = facingAxis.isHorizontal() && axis == Direction.Axis.Y || facingAxis.isVertical() && axis == Direction.Axis.Z;
        float sine = Mth.sin(angle);
        float sine2 = Mth.sin(angle - Mth.HALF_PI);
        float piston = ((1 - sine) / 4) * 24 / 16f;

        transformed(this.piston, facing, roll90)
                .translate(0, piston, 0)
                .setChanged();

        transformed(linkage, facing, roll90)
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
