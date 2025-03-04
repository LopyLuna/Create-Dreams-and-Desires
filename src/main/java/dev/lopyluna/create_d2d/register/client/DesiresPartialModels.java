package dev.lopyluna.create_d2d.register.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.lopyluna.create_d2d.DesiresCreate;
import net.createmod.catnip.lang.Lang;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public class DesiresPartialModels {
    public static final PartialModel
            PROPELLER = block("propeller/block"),
            FRAME = block("stirling_engine/frame"),
            ENGINE_PISTON = block("stirling_engine/piston"), ENGINE_LINKAGE = block("stirling_engine/linkage"),
            FLYWHEEL = block("powered_flywheel/flywheel")
    ;
    public static final Map<DyeColor, PartialModel> DYED_PROPELLERS = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) DYED_PROPELLERS.put(color, block(Lang.asId(color.name()) + "_propeller"));
    }

    private static PartialModel block(String path) {
        return PartialModel.of(DesiresCreate.loc("block/" + path));
    }
    public static void init() {
    }
}
