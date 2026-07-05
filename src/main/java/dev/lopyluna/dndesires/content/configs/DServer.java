package dev.lopyluna.dndesires.content.configs;

import dev.lopyluna.dndesires.content.configs.server.DKinetics;
import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DServer extends ConfigBase {
    public final ConfigGroup server = group(0,
            "server", "Configs for the World");
    public final ConfigInt chanceForOreStone = i(25, 1, 100, "chanceForOreStone", Comments.chanceForOreStone);
    public final ConfigInt chanceForArtificialOreStone = i(5, 0, 100, "chanceForArtificialOreStone", Comments.chanceForArtificialOreStone);
    public final ConfigInt handheldSawAirUsageMultiplier = i(4, 1, 64, "handheldSawAirUsageMultiplier", Comments.handheldSawAirUsageMultiplier);

    public final DKinetics kinetics = nested(0, DKinetics::new, "Parameters and abilities of Create: Desires 2 Dream's kinetic mechanisms");

    @Override
    public @NotNull String getName() {
        return "server";
    }

    private static class Comments {
        static String chanceForOreStone = "Chance for and Ore Stone to spawn when on top of Bedrock while Milkshake Stone Generating";
        static String chanceForArtificialOreStone = "Chance for and Ore Stone to spawn when on top of Artificial Bedrock while Milkshake Stone Generating";
        static String handheldSawAirUsageMultiplier = "Multiplier for how many uses the Handheld Saw gets from a Backtank. Higher values make the saw consume less air. Default 4 makes it usually consume 1 air per use";
    }
}
