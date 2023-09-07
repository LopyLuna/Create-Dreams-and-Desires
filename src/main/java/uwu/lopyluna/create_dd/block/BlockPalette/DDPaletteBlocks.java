package uwu.lopyluna.create_dd.block.BlockPalette;

import com.simibubi.create.AllCreativeModeTabs;
import uwu.lopyluna.create_dd.DDCreate;

import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;


public class DDPaletteBlocks {

    static {
        DDCreate.REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.PALETTES_CREATIVE_TAB);
    }








    static {
        DDPaletteStoneTypes.register(REGISTRATE);
    }

    public static void register() {}
}
