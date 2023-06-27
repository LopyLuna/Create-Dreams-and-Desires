package uwu.lopyluna.create_dd.block;

import com.simibubi.create.AllCreativeModeTabs;
import uwu.lopyluna.create_dd.DDcreate;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;


public class YIPPEEPalette {

    static {
        DDcreate.REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.PALETTES_CREATIVE_TAB);
    }

    static {
        YIPPEEPaletteStoneTypes.register(REGISTRATE);
    }

    public static void register() {}
}
