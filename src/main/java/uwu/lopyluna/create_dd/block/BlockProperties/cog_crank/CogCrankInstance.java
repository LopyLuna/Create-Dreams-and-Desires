package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;


public class CogCrankInstance extends SingleRotatingInstance<CogCrankBlockEntity> implements DynamicInstance {

    private final ModelData crank;
    private final Direction facing;
    
    public CogCrankInstance(MaterialManager modelManager, CogCrankBlockEntity blockEntity) {
        super(modelManager, blockEntity);
        facing = blockState.getValue(BlockStateProperties.FACING);
        Instancer<ModelData> model = blockEntity.getRenderedHandleInstance(getTransformMaterial());
        crank = model.createInstance();
        rotateCrank();
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        Direction facing = blockState.getValue(CogCrankBlock.FACING);

        return getRotatingMaterial().getModel(AllPartialModels.SHAFTLESS_COGWHEEL, blockState, facing, rotateToFace(facing));
    }

    private Supplier<PoseStack> rotateToFace(Direction facing) {
        return () -> {
            PoseStack stack = new PoseStack();
            TransformStack stacker = TransformStack.cast(stack)
                    .centre();

            if (facing.getAxis() == Direction.Axis.X) stacker.rotateZ(90);
            else if (facing.getAxis() == Direction.Axis.Z) stacker.rotateX(90);

            stacker.unCentre();
            return stack;
        };
    }

    public void beginFrame() {
        if (crank == null)
            return;
        
        rotateCrank();
    }
    private void rotateCrank() {
        Direction.Axis axis = facing.getAxis();
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
        if (blockEntity.shouldRenderCog())
            super.remove();
        if (crank != null)
            crank.delete();
    }
    @Override
    public void update() {
        if (blockEntity.shouldRenderCog())
            super.update();
    }

    @Override
    public void updateLight() {
        if (blockEntity.shouldRenderCog())
            super.updateLight();
        if (crank != null)
            relight(pos, crank);
    }
}
