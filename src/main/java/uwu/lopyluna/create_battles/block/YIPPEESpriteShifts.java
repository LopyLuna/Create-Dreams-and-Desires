package uwu.lopyluna.create_battles.block;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import uwu.lopyluna.create_battles.Battlescreate;


public class YIPPEESpriteShifts {




    public static final CTSpriteShiftEntry
            MITHRIL_CASING = omni("mithril_casing"),
            MITHRIL_ENCASED_COGWHEEL_SIDE = vertical("mithril_encased_cogwheel_side"),
            MITHRIL_ENCASED_COGWHEEL_OTHERSIDE = horizontal("mithril_encased_cogwheel_side"),
            BRONZE_CASING = omni("bronze_casing"),
            BRONZE_ENCASED_COGWHEEL_SIDE = vertical("bronze_encased_cogwheel_side"),
            BRONZE_ENCASED_COGWHEEL_OTHERSIDE = horizontal("bronze_encased_cogwheel_side");




    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }
    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, Battlescreate.asResource("block/" + blockTextureName), Battlescreate.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
