package uwu.lopyluna.create_flavored.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Map;

public class BobertModelSet {
    private Map<ModelLayerLocation, LayerDefs> roots = ImmutableMap.of();

    public ModelPart bakeLayer(ModelLayerLocation pModelLayerLocation) {
        LayerDefs LayerDefs = this.roots.get(pModelLayerLocation);
        if (LayerDefs == null) {
            throw new IllegalArgumentException("No model for layer " + pModelLayerLocation);
        } else {
            return LayerDefs.bakeRoot();
        }
    }

    public void onResourceManagerReload(ResourceManager pResourceManager) {
        this.roots = ImmutableMap.copyOf(LayerDef.createRoots());
    }
}
