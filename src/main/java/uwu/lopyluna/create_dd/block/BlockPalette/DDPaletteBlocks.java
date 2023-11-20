package uwu.lopyluna.create_dd.block.BlockPalette;

import uwu.lopyluna.create_dd.creative.DDItemTab;

import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;


public class DDPaletteBlocks {

    static {
        REGISTRATE.setCreativeTab(DDItemTab.BASE_CREATIVE_TAB);
    }







    static {
        DDPaletteStoneTypes.register(REGISTRATE);
    }

    public static void register() {}
}
