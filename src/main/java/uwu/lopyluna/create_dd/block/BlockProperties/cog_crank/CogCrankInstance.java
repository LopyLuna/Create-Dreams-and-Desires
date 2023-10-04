package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public class CogCrankInstance extends SingleRotatingInstance<CogCrankBlockEntity> implements DynamicInstance {
    
    private ModelData crank;
    protected RotatingData rotatingModel;
    private Direction.Axis axis;
    
    public CogCrankInstance(MaterialManager modelManager, CogCrankBlockEntity blockEntity) {
        super(modelManager, blockEntity);
        axis = blockState.getValue(BlockStateProperties.AXIS);
        crank = getTransformMaterial().getModel(blockState).createInstance();
        rotateCrank();
    }
    protected Instancer<RotatingData> getCogModel() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = Direction.fromAxisAndDirection(referenceState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
        PartialModel partial = AllPartialModels.SHAFTLESS_COGWHEEL;
        return getRotatingMaterial().getModel(partial, referenceState, facing, () -> {
            PoseStack poseStack = new PoseStack();
            TransformStack.cast(poseStack)
                    .centre()
                    .rotateToFace(facing)
                    .multiply(Vector3f.XN.rotationDegrees(90))
                    .unCentre();
            return poseStack;
        });
    }
    @Override
    public void beginFrame() {
        if (crank == null)
            return;
        
        rotateCrank();
    }
    private void rotateCrank() {
        float angle = blockEntity.getIndependentAngle(AnimationTickHolder.getPartialTicks());
        crank.loadIdentity()
                .translate(getInstancePosition())
                .centre()
                .rotate(Direction.get(Direction.AxisDirection.POSITIVE, axis), angle)
                .unCentre();
    }
    @Override
    public void init() {
        rotatingModel = setup(getCogModel().createInstance());
        if (blockEntity.shouldRenderShaft())
            super.init();
    }
    
    @Override
    public void remove() {
        rotatingModel.delete();
        if (blockEntity.shouldRenderShaft())
            super.remove();
        if (crank != null)
            crank.delete();
    }
    @Override
    public void update() {
        updateRotation(rotatingModel);
        if (blockEntity.shouldRenderShaft())
            super.update();
    }
    
    @Override
    public void updateLight() {
        relight(pos, rotatingModel);
        if (blockEntity.shouldRenderShaft())
            super.updateLight();
        if (crank != null)
            relight(pos, crank);
    }
}
