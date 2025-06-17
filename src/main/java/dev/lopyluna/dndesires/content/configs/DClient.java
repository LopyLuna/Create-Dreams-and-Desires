package dev.lopyluna.dndesires.content.configs;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DClient extends ConfigBase {
    public final ConfigGroup client = group(0,
            "client", "Configs for the Client");

    // custom fluid fog
    public final ConfigGroup fluidFogSettings = group(1, "fluidFogSettings", Comments.fluidFogSettings);
    public final ConfigFloat sapTransparencyMultiplier = f(1, .125f, 256, "sap", Comments.sapTransparencyMultiplier);
    public final ConfigFloat chocolateTransparencyMultiplier = f(1, .125f, 256, "chocolateTransparencyMultiplier", Comments.chocolateTransparencyMultiplier);
    public final ConfigFloat vanillaTransparencyMultiplier = f(1, .125f, 256, "vanillaTransparencyMultiplier", Comments.vanillaTransparencyMultiplier);
    public final ConfigFloat glowberryTransparencyMultiplier = f(1, .125f, 256, "glowberryTransparencyMultiplier", Comments.glowberryTransparencyMultiplier);
    public final ConfigFloat strawberryTransparencyMultiplier = f(1, .125f, 256, "strawberryTransparencyMultiplier", Comments.strawberryTransparencyMultiplier);
    public final ConfigFloat pumpkinTransparencyMultiplier = f(1, .125f, 256, "pumpkinTransparencyMultiplier", Comments.pumpkinTransparencyMultiplier);

    @Override
    public @NotNull String getName() {
        return "client";
    }

    private static class Comments {
        static String equipments = "Configure Equipment settings";
        static String invertDeforesterSawFunction = "Invert Deforester Saw activation function";
        static String invertExcavationDrillFunction = "Invert Excavation Drill activation function";
        static String disableBlocksVoidZapperMessage = "Disables the blocks will be voided warning for the Block Zapper";

        static String propagatorDebug = "Debug Rotation Propagator";

        static String fluidFogSettings = "Configure your vision range when submerged in Create Dream n' Desire's custom fluids";
        static String sapTransparencyMultiplier = "The vision range through Sap will be multiplied by this factor";
        static String chocolateTransparencyMultiplier = "The vision range through Chocolate Milkshake will be multiplied by this factor";
        static String vanillaTransparencyMultiplier = "The vision range through Vanilla Milkshake will be multiplied by this factor";
        static String glowberryTransparencyMultiplier = "The vision range through Glowberry Milkshake will be multiplied by this factor";
        static String strawberryTransparencyMultiplier = "The vision range through Strawberry Milkshake will be multiplied by this factor";
        static String pumpkinTransparencyMultiplier = "The vision range through Pumpkin Milkshake will be multiplied by this factor";


        static String ponder = "Ponder settings";
    }
}
