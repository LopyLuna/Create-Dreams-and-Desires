package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.DDCreate;

public class DDPlacementModifiers {
    private static final DeferredRegister<PlacementModifierType<?>> REGISTER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, DDCreate.MOD_ID);

    public static final RegistryObject<PlacementModifierType<DDConfigPlacementFilter>> CONFIG_FILTER = REGISTER.register("config_filter", () -> () -> DDConfigPlacementFilter.CODEC);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
