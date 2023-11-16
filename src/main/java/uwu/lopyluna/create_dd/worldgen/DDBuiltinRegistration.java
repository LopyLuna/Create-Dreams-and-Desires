package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.worldgen.FeatureShits.DDOreFeatureConfigEntry;

import java.util.Map;

public class DDBuiltinRegistration {
    private static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, DDCreate.MOD_ID);
    private static final DeferredRegister<PlacedFeature> PLACED_FEATURE_REGISTER = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, DDCreate.MOD_ID);
    private static final DeferredRegister<BiomeModifier> BIOME_MODIFIER_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, DDCreate.MOD_ID);

    static {
        for (Map.Entry<ResourceLocation, DDOreFeatureConfigEntry> entry : DDOreFeatureConfigEntry.ALL.entrySet()) {
            ResourceLocation id = entry.getKey();
            if (id.getNamespace().equals(DDCreate.MOD_ID)) {
                DDOreFeatureConfigEntry.DatagenExtension datagenExt = entry.getValue().datagenExt();
                if (datagenExt != null) {
                    CONFIGURED_FEATURE_REGISTER.register(id.getPath(), () -> datagenExt.createConfiguredFeature(BuiltinRegistries.ACCESS));
                    PLACED_FEATURE_REGISTER.register(id.getPath(), () -> datagenExt.createPlacedFeature(BuiltinRegistries.ACCESS));
                    BIOME_MODIFIER_REGISTER.register(id.getPath(), () -> datagenExt.createBiomeModifier(BuiltinRegistries.ACCESS));
                }
            }
        }
    }

    public static void register(IEventBus modEventBus) {
        CONFIGURED_FEATURE_REGISTER.register(modEventBus);
        PLACED_FEATURE_REGISTER.register(modEventBus);
        BIOME_MODIFIER_REGISTER.register(modEventBus);
    }
}
