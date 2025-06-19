package dev.lopyluna.dndesires.register.client;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import dev.lopyluna.dndesires.DnDesires;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;

@SuppressWarnings("unused")
public class DesiresSpriteShifts {

    public static CTSpriteShiftEntry horizontal(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry horizontalKryppers(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry vertical(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.VERTICAL, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry rectangle(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.RECTANGLE, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry cross(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.CROSS, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry roof(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry roofStair(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF_STAIR, blockTextureName, connectedTextureName);
    }
    public static CTSpriteShiftEntry omni(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, blockTextureName, connectedTextureName);
    }

    public static CTSpriteShiftEntry horizontal(String texture) {
        return getCT(AllCTTypes.HORIZONTAL, texture, texture);
    }
    public static CTSpriteShiftEntry horizontalKryppers(String texture) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, texture, texture);
    }
    public static CTSpriteShiftEntry vertical(String texture) {
        return getCT(AllCTTypes.VERTICAL, texture, texture);
    }
    public static CTSpriteShiftEntry rectangle(String texture) {
        return getCT(AllCTTypes.RECTANGLE, texture, texture);
    }
    public static CTSpriteShiftEntry cross(String texture) {
        return getCT(AllCTTypes.CROSS, texture, texture);
    }
    public static CTSpriteShiftEntry roof(String texture) {
        return getCT(AllCTTypes.ROOF, texture, texture);
    }
    public static CTSpriteShiftEntry roofStair(String texture) {
        return getCT(AllCTTypes.ROOF_STAIR, texture, texture);
    }
    public static CTSpriteShiftEntry omni(String texture) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, texture, texture);
    }

    public static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DnDesires.loc("block/" + blockTextureName), DnDesires.loc("block/" + connectedTextureName + "_connected"));
    }

    public static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(DnDesires.loc(originalLocation), DnDesires.loc(targetLocation));
    }

    public static void init() {}
}
