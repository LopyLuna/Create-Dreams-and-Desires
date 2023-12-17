package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

public class ShadowCogInstance extends SingleRotatingInstance<ShadowPanelBlockEntity> {

    public ShadowCogInstance(MaterialManager materialManager, ShadowPanelBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(DDBlockPartialModel.SHADOW_COG, blockEntity.getBlockState());
    }
}
