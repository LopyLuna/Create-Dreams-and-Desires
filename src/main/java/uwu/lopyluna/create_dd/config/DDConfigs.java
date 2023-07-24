package uwu.lopyluna.create_dd.config;

import com.simibubi.create.content.kinetics.BlockStressValues;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.AllConfigs;

import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.commons.lang3.tuple.Pair;

import uwu.lopyluna.create_dd.DDcreate;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class DDConfigs {
	private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	//private static DDclient client;
	private static DDcommon common;
	//private static DDserver server;

	//public static DDclient client() {
	//	return client;
	//}

	public static DDcommon common() {
		return common;
	}

	//public static DDserver server() {
	//	return server;
	//}

	public static ConfigBase byType(ModConfig.Type type) {
		return CONFIGS.get(type);
	}

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
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

	public static void register() {
		//client = register(DDclient::new, ModConfig.Type.CLIENT);
		common = register(DDcommon::new, ModConfig.Type.COMMON);
		//server = register(DDserver::new, ModConfig.Type.SERVER);

		for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
			ModLoadingContext.registerConfig(DDcreate.ID, pair.getKey(), pair.getValue().specification);

		ModConfigEvents.loading(DDcreate.ID).register(AllConfigs::onLoad);
		ModConfigEvents.reloading(DDcreate.ID).register(AllConfigs::onReload);
	}

	public static void onLoad(ModConfig modConfig) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == modConfig
					.getSpec())
				config.onLoad();
	}

	public static void onReload(ModConfig modConfig) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == modConfig
					.getSpec())
				config.onReload();
	}

}
