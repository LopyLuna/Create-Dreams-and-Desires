package uwu.lopyluna.create_dd.worldgen.ponder_dim;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import uwu.lopyluna.create_dd.DDCreate;

public class Pondering {
    public static final ResourceKey<Level> PONDER = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(DDCreate.MOD_ID, "ponder"));
    public static final ResourceKey<DimensionType> PONDER_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(DDCreate.MOD_ID, "ponder"));

    public static void register() {
        System.out.println("Pondering Dimensions " + DDCreate.MOD_ID);
    }
}
