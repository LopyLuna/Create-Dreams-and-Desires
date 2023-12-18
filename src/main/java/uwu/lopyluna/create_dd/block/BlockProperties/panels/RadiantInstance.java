package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.render.RenderTypes;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

import static net.minecraft.util.Mth.sin;

public class RadiantInstance extends KineticBlockEntityInstance<RadiantPanelBlockEntity> implements DynamicInstance {

    private final ModelData panel_glow;
    private final ModelData panel_active;
    private final ModelData panel;
    protected final RotatingData cog;

    public RadiantInstance(MaterialManager materialManager, RadiantPanelBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        panel_glow = materialManager.transparent(RenderTypes.getAdditive())
                .material(Materials.TRANSFORMED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL_GLOW, blockState)
                .createInstance();

        panel_active = materialManager.solid(RenderTypes.getGlowingSolid())
                .material(Materials.TRANSFORMED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL, blockState)
                .createInstance();

        panel = materialManager.defaultSolid()
                .material(Materials.TRANSFORMED)
                .getModel(DDBlockPartialModel.RADIANT_PANEL, blockState)
                .createInstance();

        cog = materialManager.cutout(RenderTypes.getGlowingSolid())
                .material(AllMaterialSpecs.ROTATING)
                .getModel(DDBlockPartialModel.RADIANT_COG, blockState)
                .createInstance();

        setup(cog);
    }
    @Override
    public void beginFrame() {
    if (RadiantPanelBlockEntity.active || RadiantPanelBlockEntity.weak_active) {
        float y = sin(blockEntity.y / 1000 * 8);
        panel_active.loadIdentity().translate(blockEntity.x + pos.getX(), y + pos.getY(), blockEntity.z + pos.getZ());
        panel_glow.loadIdentity().translate(blockEntity.x + pos.getX(), y + pos.getY(), blockEntity.z + pos.getZ());
        panel.setEmptyTransform();
    } else {
        panel.loadIdentity().translate(blockEntity.x + pos.getX(),  pos.getY(), blockEntity.z + pos.getZ());
        panel_active.setEmptyTransform();
        panel_glow.setEmptyTransform();
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
