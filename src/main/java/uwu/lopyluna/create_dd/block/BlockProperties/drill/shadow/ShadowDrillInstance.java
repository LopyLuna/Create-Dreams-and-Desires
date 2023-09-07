package uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

public class ShadowDrillInstance extends SingleRotatingInstance<ShadowDrillBlockEntity> {

    public ShadowDrillInstance(MaterialManager materialManager, ShadowDrillBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = referenceState.getValue(BlockStateProperties.FACING);
        return getRotatingMaterial().getModel(DDBlockPartialModel.SHADOW_DRILL_HEAD, referenceState, facing);
    }
}
