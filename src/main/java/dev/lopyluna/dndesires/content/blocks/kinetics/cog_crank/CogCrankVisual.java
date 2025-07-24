package dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank;

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
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Quaternionf;

import java.util.function.Consumer;

public class CogCrankVisual extends KineticBlockEntityVisual<CogCrankBE> implements SimpleDynamicVisual {
    private final RotatingInstance rotatingModel;
    private final TransformedInstance crank;

    public CogCrankVisual(VisualizationContext modelManager, CogCrankBE be, float partialTick) {
        super(modelManager, be, partialTick);
        var isLarge = be.isLarge();

        crank = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(DesiresPartialModels.COG_CRANK_HANDLE)).createInstance();
        rotateCrank(partialTick);

        rotatingModel = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(isLarge ? AllPartialModels.SHAFTLESS_LARGE_COGWHEEL : AllPartialModels.SHAFTLESS_COGWHEEL)).createInstance();
        rotatingModel.setup(blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(blockState.getValue(BlockStateProperties.AXIS))
                .setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        rotateCrank(ctx.partialTick());
    }

    private void rotateCrank(float pt) {
        var axis = blockState.getValue(BlockStateProperties.AXIS);
        var facing = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        var angle = blockEntity.getIndependentAngle(pt);

        crank.setIdentityTransform()
                .translate(getVisualPosition())
                .center()
                .rotate(angle, facing)
                .rotate(new Quaternionf().rotateTo(0, 0, -1, facing.getStepX(), facing.getStepY(), facing.getStepZ()))
                .uncenter()
                .setChanged();
    }



    @Override
    protected void _delete() {
        crank.delete();
        rotatingModel.delete();
    }

    @Override
    public void update(float pt) {
        rotatingModel.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(crank, rotatingModel);
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(crank);
        consumer.accept(rotatingModel);
    }
}
