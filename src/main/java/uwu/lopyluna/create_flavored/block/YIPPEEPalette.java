package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.AllCreativeModeTabs;
import uwu.lopyluna.create_flavored.Flavoredcreate;

import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;


public class YIPPEEPalette {

    static {
        Flavoredcreate.REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.PALETTES_CREATIVE_TAB);
    }

    static {
        YIPPEEPaletteStoneTypes.register(REGISTRATE);
    }

    public static void register() {}
}
