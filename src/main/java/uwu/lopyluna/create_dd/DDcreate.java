package uwu.lopyluna.create_dd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.foundation.DDCommonEvents;
import uwu.lopyluna.create_dd.foundation.DDItemTabs;
import uwu.lopyluna.create_dd.init.DDBlocks;
import uwu.lopyluna.create_dd.init.DDFluids;
import uwu.lopyluna.create_dd.init.DDItems;

public class DDcreate implements ModInitializer {
	public static final String ID = "create_dd";
	public static final String NAME = "Create: Dreams & Desires";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping()
			.create();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);

		DDItemTabs.init();
		DDFluids.registerFluidInteractions();
		DDCommonEvents.register();
		DDItems.register();
		DDFluids.register();
		DDBlocks.register();
		REGISTRATE.register();
	}
	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
	public static CreateRegistrate registrate() {
		return REGISTRATE;
	}
}
