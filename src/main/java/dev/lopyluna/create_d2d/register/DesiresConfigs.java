package dev.lopyluna.create_d2d.register;

import com.simibubi.create.api.stress.BlockStressValues;
import dev.lopyluna.create_d2d.content.configs.DClient;
import dev.lopyluna.create_d2d.content.configs.DCommon;
import dev.lopyluna.create_d2d.content.configs.DServer;
import dev.lopyluna.create_d2d.content.configs.server.kinetics.DStress;
import net.createmod.catnip.config.ConfigBase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DesiresConfigs {

    private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static DClient client;
    private static DCommon common;
    private static DServer server;

    public static DClient client() { return client; }
    public static DCommon common() { return common; }
    public static DServer server() { return server; }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register(ModLoadingContext context, ModContainer container) {
        client = register(DClient::new, ModConfig.Type.CLIENT);
        common = register(DCommon::new, ModConfig.Type.COMMON);
        server = register(DServer::new, ModConfig.Type.SERVER);

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet()) container.registerConfig(pair.getKey(), pair.getValue().specification);

        DStress stress = server().kinetics.stressValues;
        BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
        BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (ConfigBase config : CONFIGS.values()) if (config.specification == event.getConfig().getSpec()) config.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (ConfigBase config : CONFIGS.values()) if (config.specification == event.getConfig().getSpec()) config.onReload();
    }

}
