package uwu.lopyluna.create_dd.block.BlockProperties.bronze_drill;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.YIPPEEPartialModel;

public class BronzeDrillInstance extends SingleRotatingInstance<BronzeDrillBlockEntity> {

    public BronzeDrillInstance(MaterialManager materialManager, BronzeDrillBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        BlockState referenceState = blockEntity.getBlockState();
        Direction facing = referenceState.getValue(BlockStateProperties.FACING);
        return getRotatingMaterial().getModel(YIPPEEPartialModel.BRONZE_DRILL_HEAD, referenceState, facing);
    }
}
