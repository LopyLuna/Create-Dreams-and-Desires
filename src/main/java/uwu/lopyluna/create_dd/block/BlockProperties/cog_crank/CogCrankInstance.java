package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;


public class CogCrankInstance extends SingleRotatingInstance<CogCrankBlockEntity> implements DynamicInstance {

    private final ModelData crank;
    private final Direction.Axis Axis;
    protected RotatingData cog;

    public CogCrankInstance(MaterialManager modelManager, CogCrankBlockEntity blockEntity) {
        super(modelManager, blockEntity);
        Axis = blockState.getValue(BlockStateProperties.AXIS);

        cog = setup(getRotatingMaterial().getModel(AllPartialModels.SHAFTLESS_COGWHEEL).createInstance());
        crank = getTransformMaterial().getModel(blockState).createInstance();
        rotateCrank();
    }

    public void beginFrame() {
        if (crank == null)
            return;

        rotateCrank();
    }

    private void rotateCrank() {
        Direction.Axis axis = Axis;
        float angle = blockEntity.getIndependentAngle(AnimationTickHolder.getPartialTicks());

        crank.loadIdentity()
                .translate(getInstancePosition())
                .centre()
                .rotate(Direction.get(Direction.AxisDirection.POSITIVE, axis), angle)
                .unCentre();
    }
    @Override
    public void init() {
        if (blockEntity.shouldRenderCog())
            super.init();
    }

    @Override
    public void remove() {
        if (blockEntity.shouldRenderCog()) {
            super.remove();
            if (cog != null)
                cog.delete();
            if (crank != null)
                crank.delete();
        }
    }
    @Override
    public void update() {
        if (blockEntity.shouldRenderCog()) {
            super.update();
            if (cog != null)
                updateRotation(cog);
        }
    }

    @Override
    public void updateLight() {
        if (blockEntity.shouldRenderCog()) {
            super.updateLight();
            if (cog != null)
                relight(pos, cog);
            if (crank != null)
                relight(pos, crank);
        }
    }
}
