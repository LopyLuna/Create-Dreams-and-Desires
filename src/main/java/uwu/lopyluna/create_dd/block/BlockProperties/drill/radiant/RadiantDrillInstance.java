package uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.render.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

public class RadiantDrillInstance extends SingleRotatingInstance<RadiantDrillBlockEntity> {

    protected RotatingData rotatingModel1;
    protected RotatingData rotatingModel2;

    public RadiantDrillInstance(MaterialManager materialManager, RadiantDrillBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        rotatingModel1 = setup(getModel1().createInstance());
        rotatingModel2 = setup(getModel2().createInstance());
    }

    @Override
    public void update() {
        updateRotation(rotatingModel1);
        updateRotation(rotatingModel2);
    }

    @Override
    public void updateLight() {
        relight(pos, rotatingModel1);
        relight(pos, rotatingModel2);
    }

    @Override
    public void remove() {
        rotatingModel1.delete();
        rotatingModel2.delete();
    }

    protected BlockState getRenderedBlockState() {
        return blockState;
    }

    protected Instancer<RotatingData> getModel1() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = referenceState.getValue(BlockStateProperties.FACING);
        return getRotatingMaterial().getModel(DDBlockPartialModel.RADIANT_DRILL_HEAD, referenceState, facing);

    }
    protected Instancer<RotatingData> getModel2() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = referenceState.getValue(BlockStateProperties.FACING);
        return getRotatingGlowMaterial().getModel(DDBlockPartialModel.RADIANT_DRILL_HEAD_GLOW, referenceState, facing);

    }

    @Override
    protected Material<RotatingData> getRotatingMaterial() {
        return materialManager.defaultSolid()
                .material(AllMaterialSpecs.ROTATING);
    }

    protected Material<RotatingData> getRotatingGlowMaterial() {
        return materialManager.transparent(RenderTypes.getAdditive())
                .material(AllMaterialSpecs.ROTATING);
    }
}
