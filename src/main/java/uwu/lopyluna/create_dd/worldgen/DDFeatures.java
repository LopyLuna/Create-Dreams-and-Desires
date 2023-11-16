package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.worldgen.FeatureShits.DDLayeredOreFeature;
import uwu.lopyluna.create_dd.worldgen.FeatureShits.DDStandardOreFeature;

public class DDFeatures {
    private static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, DDCreate.MOD_ID);

    public static final RegistryObject<DDStandardOreFeature> STANDARD_ORE = REGISTER.register("standard_ore", DDStandardOreFeature::new);
    public static final RegistryObject<DDLayeredOreFeature> LAYERED_ORE = REGISTER.register("layered_ore", DDLayeredOreFeature::new);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
