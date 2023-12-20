package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.render.RenderTypes;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

import static net.minecraft.util.Mth.sin;
import static net.minecraft.util.Mth.square;

public class RadiantInstance extends KineticBlockEntityInstance<RadiantPanelBlockEntity> implements DynamicInstance {

    private final OrientedData panel_glow;
    private final OrientedData panel_active;
    private final OrientedData panel;
    protected final RotatingData cog;

    public RadiantInstance(MaterialManager materialManager, RadiantPanelBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        panel_glow = materialManager.transparent(RenderTypes.getAdditive())
                .material(Materials.ORIENTED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL_GLOW, blockState)
                .createInstance();

        panel_active = materialManager.solid(RenderTypes.getGlowingSolid())
                .material(Materials.ORIENTED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL, blockState)
                .createInstance();

        panel = materialManager.defaultSolid()
                .material(Materials.ORIENTED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL, blockState)
                .createInstance();

        cog = materialManager.cutout(RenderTypes.getGlowingSolid())
                .material(AllMaterialSpecs.ROTATING)
                .getModel(DDBlockPartialModel.RADIANT_COG, blockState)
                .createInstance();

        setup(cog);

        transformModels();
    }
    @Override
    public void beginFrame() {transformModels();}

    private void transformModels() {
        float y = sin(blockEntity.y / 1000 * 8);
        float disable = 3 * (square(10) * 7);

        if (RadiantPanelBlockEntity.active || RadiantPanelBlockEntity.weak_active) {
            panel_active.setPosition(getInstancePosition()).nudge(blockEntity.x, y, blockEntity.z);
            panel_glow.setPosition(getInstancePosition()).nudge(blockEntity.x, y, blockEntity.z);
            panel.setPosition(getInstancePosition()).nudge(disable, disable, disable);
        } else {
            panel.setPosition(getInstancePosition());
            panel_active.setPosition(getInstancePosition()).nudge(disable, disable, disable);
            panel_glow.setPosition(getInstancePosition()).nudge(disable, disable, disable);
        }
    }

    @Override
    public void update() {
        updateRotation(cog);
    }


    @Override
    public void updateLight() {
        super.updateLight();

        relight(pos, panel_glow, panel, panel_active, cog);
    }

    @Override
    public void remove() {
        panel_glow.delete();
        panel_active.delete();
        panel.delete();
        cog.delete();
    }

}
