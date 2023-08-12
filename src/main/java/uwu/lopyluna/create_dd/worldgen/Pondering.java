package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import uwu.lopyluna.create_dd.DDcreate;

public class Pondering {
    public static final ResourceKey<Level> PONDER = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(DDcreate.MOD_ID, "ponder"));
    public static final ResourceKey<DimensionType> PONDER_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,
                    new ResourceLocation(DDcreate.MOD_ID, "ponder"));

    public static void register() {
        System.out.println("Pondering Dimensions " + DDcreate.MOD_ID);
    }
}
