package dev.lopyluna.dndesires.content.configs.server;

import dev.lopyluna.dndesires.content.configs.server.kinetics.DStress;
import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DKinetics extends ConfigBase {

    public final ConfigGroup fan = group(1, "industrialFan", "Industrial Fan");
    public final ConfigInt fanPushDistance = i(30, 5, "fanPushDistance",
            "Maximum distance in blocks Fans can push entities.");
    public final ConfigInt fanPullDistance = i(30, 5, "fanPullDistance",
            "Maximum distance in blocks from where Fans can pull entities.");
    public final ConfigInt fanBlockCheckRate = i(30, 10, "fanBlockCheckRate",
            "Game ticks between Fans checking for anything blocking their air flow.");
    public final ConfigInt fanRotationArgmax = i(256, 64, "fanRotationArgmax",
            "[in Revolutions per Minute]", "Rotation speed at which the maximum stats of fans are reached.");
    public final ConfigInt fanProcessingTime = i(75, 0, "fanProcessingTime",
            "Game ticks required for a Fan-based processing recipe to take effect.");

    public final DStress stressValues = nested(1, DStress::new, "Fine tune the kinetic stats of individual components");

    @Override
    public @NotNull String getName() {
        return "kinetics";
    }
}
