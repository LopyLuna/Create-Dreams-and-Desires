package uwu.lopyluna.create_dd.init;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.Create;

import uwu.lopyluna.create_dd.content.decoration.DDPaletteStoneTypes;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;

public class DDPalette {
	static {
		Create.REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.PALETTES_CREATIVE_TAB);
	}

	static {
		DDPaletteStoneTypes.register(REGISTRATE);
	}

	public static void register() {}
}
