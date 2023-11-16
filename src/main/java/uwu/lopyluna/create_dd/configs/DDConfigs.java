package uwu.lopyluna.create_dd.configs;

import com.simibubi.create.content.kinetics.BlockStressValues;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import uwu.lopyluna.create_dd.configs.client.DDClient;
import uwu.lopyluna.create_dd.configs.common.DDCommon;
import uwu.lopyluna.create_dd.configs.server.DDServer;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"all"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDConfigs {
    private static final Map<ModConfig.Type, DDConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static DDClient client;
    private static DDCommon common;
    private static DDServer server;

    public static DDClient client() {
        return client;
    }

    public static DDCommon common() {
        return common;
    }

    public static DDServer server() {
        return server;
    }

    public static DDConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends DDConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register(ModLoadingContext context) {
        client = register(DDClient::new, ModConfig.Type.CLIENT);
        common = register(DDCommon::new, ModConfig.Type.COMMON);
        server = register(DDServer::new, ModConfig.Type.SERVER);

        for (Map.Entry<ModConfig.Type, DDConfigBase> pair : CONFIGS.entrySet())
            context.registerConfig(pair.getKey(), pair.getValue().specification);

        BlockStressValues.registerProvider(context.getActiveNamespace(), server().kinetics.stressValues);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for (DDConfigBase config : CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for (DDConfigBase config : CONFIGS.values())
            if (config.specification == event.getConfig()
                    .getSpec())
                config.onReload();
    }

}
