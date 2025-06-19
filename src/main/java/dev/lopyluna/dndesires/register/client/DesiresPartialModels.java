package dev.lopyluna.dndesires.register.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.dndesires.DnDesires;
import net.createmod.catnip.lang.Lang;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public class DesiresPartialModels {
    public static final PartialModel
            INDUSTRIAL_FAN_POWER = block("industrial_fan/cog"),
            INDUSTRIAL_FAN_INNER = block("industrial_fan/propeller"),
            HYDRAULIC_HEAD = block("hydraulic_press/head"),
            FRAME = block("stirling_engine/frame"),
            ENGINE_PISTON = block("stirling_engine/piston"), ENGINE_LINKAGE = block("stirling_engine/linkage"),
            FLYWHEEL = block("powered_flywheel/flywheel")
    ;
    public static final Map<DyeColor, PartialModel> DYED_PROPELLERS = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) DYED_PROPELLERS.put(color, block(Lang.asId(color.name()) + "_propeller"));
    }

    private static PartialModel block(String path) {
        return PartialModel.of(DnDesires.loc("block/" + path));
    }
    public static void init() {
    }
}
