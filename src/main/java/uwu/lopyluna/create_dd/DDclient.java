package uwu.lopyluna.create_dd;

import net.fabricmc.api.ClientModInitializer;
import uwu.lopyluna.create_dd.init.DDPartialModels;

public class DDclient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		DDPartialModels.init();
	}
}
