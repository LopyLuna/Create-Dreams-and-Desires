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

    public RadiantDrillInstance(MaterialManager materialManager, RadiantDrillBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = referenceState.getValue(BlockStateProperties.FACING);
        getRotatingGlowMaterial().getModel(DDBlockPartialModel.RADIANT_DRILL_HEAD_GLOW, referenceState, facing);
        return getRotatingMaterial().getModel(DDBlockPartialModel.RADIANT_DRILL_HEAD, referenceState, facing);
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
