package uwu.lopyluna.create_flavored.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerDefs {
    static int xTexSize;
    static int yTexSize;
    private final MeshDefinition mesh;
    private final MaterialDefinition material;

    private LayerDefs(int xTexSize, int yTexSize, MeshDefinition pMesh, MaterialDefinition pMaterial) {
        LayerDefs.xTexSize = xTexSize;
        LayerDefs.yTexSize = yTexSize;
        this.mesh = pMesh;
        this.material = pMaterial;
    }

    public ModelPart bakeRoot() {
        return this.mesh.getRoot().bake(xTexSize, yTexSize);
    }

    public static LayerDefs create(MeshDefinition pMesh, int pTexWidth, int pTexHeight) {
        return new LayerDefs(xTexSize, yTexSize, pMesh, new MaterialDefinition(pTexWidth, pTexHeight));
    }
}
