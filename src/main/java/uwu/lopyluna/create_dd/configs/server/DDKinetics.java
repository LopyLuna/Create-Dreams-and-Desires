package uwu.lopyluna.create_dd.configs.server;

import uwu.lopyluna.create_dd.configs.DDConfigBase;

public class DDKinetics extends DDConfigBase {
    int maxHeight = 2048;


    public final ConfigFloat crankHungerMultiplier = f(.01f, 0, 1, "crankHungerMultiplier", DDKinetics.Comments.crankHungerMultiplier);

    public final ConfigGroup fan = group(1, "industrialFan", "Industrial Fan");
    public final ConfigInt fanPushDistance = i(30, 5, "fanPushDistance", DDKinetics.Comments.fanPushDistance);
    public final ConfigInt fanPullDistance = i(30, 5, "fanPullDistance", DDKinetics.Comments.fanPullDistance);
    public final ConfigInt fanBlockCheckRate = i(30, 10, "fanBlockCheckRate", DDKinetics.Comments.fanBlockCheckRate);
    public final ConfigInt fanRotationArgmax = i(256, 64, "fanRotationArgmax", DDKinetics.Comments.rpm, DDKinetics.Comments.fanRotationArgmax);
    public final ConfigInt fanProcessingTime = i(75, 0, "fanProcessingTime", DDKinetics.Comments.fanProcessingTime);

    public final ConfigGroup RSPanels = group(1, "RSPanels", "Radiant/Shadow Panels");
    public final ConfigInt PanelBlockCheckRate = i(10, 10, "PanelBlockCheckRate", DDKinetics.Comments.PannelBlockCheckRate);

    public final ConfigInt PanelMAXHeight = i(10, 10, "PanelMAXHeight", DDKinetics.Comments.PanelMAXHeight);

    //0 - 4000 - Radiant / Solar
    public final ConfigInt day_min_time = i(0, 0, 24000, "day_min_time",
            Comments.day_min_time);
    public final ConfigInt day_max_time = i(4000, 0, 24000, "day_max_time",
            Comments.day_max_time);

    //4000 - 8000 - Radiant / Solar
    public final ConfigInt noon_min_time = i(4000, 0, 24000, "noon_min_time",
            Comments.noon_min_time);
    public final ConfigInt noon_max_time = i(8000, 0, 24000, "noon_max_time",
            Comments.noon_max_time);

    //8000 - 12000 - Radiant / Solar
    public final ConfigInt sunset_min_time = i(8000, 0, 24000, "sunset_min_time",
            Comments.sunset_min_time);
    public final ConfigInt sunset_max_time = i(12000, 0, 24000, "sunset_max_time",
            Comments.sunset_max_time);

    //12000 - 16000 - Shadow / Lunar
    public final ConfigInt night_min_time = i(12000, 0, 24000, "night_min_time",
            Comments.night_min_time);
    public final ConfigInt night_max_time = i(16000, 0, 24000, "night_max_time",
            Comments.night_max_time);

    //16000 - 20000 - Shadow / Lunar
    public final ConfigInt midnight_min_time = i(16000, 0, 24000, "midnight_min_time",
            Comments.midnight_min_time);
    public final ConfigInt midnight_max_time = i(20000, 0, 24000, "midnight_max_time",
            Comments.midnight_max_time);

    //20000 - 24000 - Shadow / Lunar
    public final ConfigInt sunrise_min_time = i(20000, 0, 24000, "sunrise_min_time",
            Comments.sunrise_min_time);
    public final ConfigInt sunrise_max_time = i(24000, 0, 24000, "sunrise_max_time",
            Comments.sunrise_max_time);


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
        static String PannelBlockCheckRate = "Game ticks checking for Panels updating there Generated Rotation.";
        static String day_min_time = "Day Min Time";
        static String day_max_time = "Day Max Time";
        static String noon_min_time = "Noon Min Time";
        static String noon_max_time = "Noon Max Time";
        static String sunset_min_time = "Sunset Min Time";
        static String sunset_max_time = "Sunset Max Time";
        static String night_min_time = "Night Min Time";
        static String night_max_time = "Night Max Time";
        static String midnight_min_time = "Midnight Min Time";
        static String midnight_max_time = "Midnight Max Time";
        static String sunrise_min_time = "Sunrise Min Time";
        static String sunrise_max_time = "Sunrise Max Time";
        static String PanelMAXHeight = "Panels Max Height";
    }
}
