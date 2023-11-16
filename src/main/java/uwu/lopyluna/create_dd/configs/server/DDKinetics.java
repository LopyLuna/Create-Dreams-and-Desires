package uwu.lopyluna.create_dd.configs.server;

import uwu.lopyluna.create_dd.configs.DDConfigBase;

public class DDKinetics extends DDConfigBase {


    public final ConfigGroup fan = group(1, "industrialFan", "Industrial Fan");
    public final ConfigFloat crankHungerMultiplier = f(.01f, 0, 1, "crankHungerMultiplier", DDKinetics.Comments.crankHungerMultiplier);
    public final ConfigInt fanPushDistance = i(30, 5, "fanPushDistance", DDKinetics.Comments.fanPushDistance);
    public final ConfigInt fanPullDistance = i(30, 5, "fanPullDistance", DDKinetics.Comments.fanPullDistance);
    public final ConfigInt fanBlockCheckRate = i(30, 10, "fanBlockCheckRate", DDKinetics.Comments.fanBlockCheckRate);
    public final ConfigInt fanRotationArgmax = i(256, 64, "fanRotationArgmax", DDKinetics.Comments.rpm, DDKinetics.Comments.fanRotationArgmax);
    public final ConfigInt fanProcessingTime = i(75, 0, "fanProcessingTime", DDKinetics.Comments.fanProcessingTime);

    public final DDStress stressValues = nested(1, DDStress::new, DDKinetics.Comments.stress);

    @Override
    public String getName() {
        return "kinetics";
    }


    private static class Comments {
    static String stress = "Fine tune the kinetic stats of individual components";
        static String fanPushDistance = "Maximum distance in blocks Fans can push entities.";
        static String fanPullDistance = "Maximum distance in blocks from where Fans can pull entities.";
        static String fanBlockCheckRate = "Game ticks between Fans checking for anything blocking their air flow.";
        static String fanRotationArgmax = "Rotation speed at which the maximum stats of fans are reached.";
        static String fanProcessingTime = "Game ticks required for a Fan-based processing recipe to take effect.";
        static String rpm = "[in Revolutions per Minute]";
        static String crankHungerMultiplier = "multiplier used for calculating exhaustion from speed when a crank is turned.";
    }
}
