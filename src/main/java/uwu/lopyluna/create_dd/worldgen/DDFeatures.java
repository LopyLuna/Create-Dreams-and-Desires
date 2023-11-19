package uwu.lopyluna.create_dd.worldgen;

import com.simibubi.create.infrastructure.worldgen.LayeredOreFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.DDCreate;

public class DDFeatures {
    private static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, DDCreate.MOD_ID);

    public static final RegistryObject<LayeredOreFeature> LAYERED_ORE = REGISTER.register("layered_ore", () -> new LayeredOreFeature());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
