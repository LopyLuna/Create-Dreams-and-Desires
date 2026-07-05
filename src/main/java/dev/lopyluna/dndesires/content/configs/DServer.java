package dev.lopyluna.dndesires.content.configs;

import dev.lopyluna.dndesires.content.configs.server.DKinetics;
import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DServer extends ConfigBase {
    public final ConfigGroup server = group(0,
            "server", "Configs for the World");
    public final ConfigInt chanceForOreStone = i(25, 1, 100, "chanceForOreStone", Comments.chanceForOreStone);
    public final ConfigInt chanceForArtificialOreStone = i(5, 0, 100, "chanceForArtificialOreStone", Comments.chanceForArtificialOreStone);
    public final ConfigInt handheldSawAirEfficiencyMultiplier = i(4, 1, 8, "handheldSawAirEfficiencyMultiplier", Comments.handheldSawAirEfficiencyMultiplier);
    public final ConfigBool handheldSawConsumesAirPerTreeBlock = b(true, "handheldSawConsumesAirPerTreeBlock", Comments.handheldSawConsumesAirPerTreeBlock);
    public final ConfigBool handheldSawOnlyLogsConsumeAirWhenDeforesting = b(true, "handheldSawOnlyLogsConsumeAirWhenDeforesting", Comments.handheldSawOnlyLogsConsumeAirWhenDeforesting);
    
    public final DKinetics kinetics = nested(0, DKinetics::new, "Parameters and abilities of Create: Desires 2 Dream's kinetic mechanisms");

    @Override
    public @NotNull String getName() {
        return "server";
    }

    private static class Comments {
        static String chanceForOreStone = "Chance for and Ore Stone to spawn when on top of Bedrock while Milkshake Stone Generating";
        static String chanceForArtificialOreStone = "Chance for and Ore Stone to spawn when on top of Artificial Bedrock while Milkshake Stone Generating";
        static String handheldSawAirEfficiencyMultiplier = "Multiplier for how many uses the Handheld Saw gets from a Backtank. Higher values reduce air usage, but air cost cannot go below 1 per use.";
        static String handheldSawConsumesAirPerTreeBlock = "If enabled, the Handheld Saw can consume air for blocks broken during automatic tree cutting. If disabled, only the first manually broken block consumes air.";
        static String handheldSawOnlyLogsConsumeAirWhenDeforesting = "If enabled, only logs and roots consume air during automatic tree cutting. Leaves, vines, and other tree attachments will not consume air. If disabled, every automatically broken tree block can consume air.";
);
    }
}
