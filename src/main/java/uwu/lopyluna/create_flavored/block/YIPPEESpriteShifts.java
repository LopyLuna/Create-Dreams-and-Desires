package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import uwu.lopyluna.create_flavored.Flavoredcreate;


public class YIPPEESpriteShifts {




    public static final CTSpriteShiftEntry
            MITHRIL_CASING = omni("mithril_casing"),
            BRONZE_CASING = omni("bronze_casing"),
            ZINC_CASING = omni("zinc_casing"),
            MOSSY_ANDESITE_CASING = omni("mossy_andesite_casing"),
            HYDRAULIC_CASING = omni("hydraulic_casing"),
            INDUSTRIAL_CASING = omni("industrial_casing"),
            OVERBURDEN_CASING = omni("overburden_casing");




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
        return CTSpriteShifter.getCT(type, Flavoredcreate.asResource("block/" + blockTextureName), Flavoredcreate.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
