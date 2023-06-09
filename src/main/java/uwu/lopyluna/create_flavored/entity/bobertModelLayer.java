package uwu.lopyluna.create_flavored.entity;

import com.google.common.collect.Sets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class bobertModelLayer {

    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
    public static final ModelLayerLocation SMART_BUILDER = register("smart_builder");

    private static ModelLayerLocation register(String pPath) {
        return register(pPath, "main");
    }

    private static ModelLayerLocation register(String pPath, String pModel) {
        ModelLayerLocation modellayerlocation = createLocation(pPath, pModel);
        if (!ALL_MODELS.add(modellayerlocation)) {
            throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
        } else {
            return modellayerlocation;
        }
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel) { return new ModelLayerLocation(new ResourceLocation("create_flavored", pPath), pModel);
    }
    public static Stream<ModelLayerLocation> getKnownLocations() {
        return ALL_MODELS.stream();
    }
}
