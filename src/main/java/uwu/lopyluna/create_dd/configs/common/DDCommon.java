package uwu.lopyluna.create_dd.configs.common;

import com.simibubi.create.foundation.config.ConfigBase;

public class DDCommon extends ConfigBase {
    int maxHeight = 2048;
    int minHeight = -2048;

    //worldgen group
    public final ConfigGroup worldGen = group(0, "worldgen",
            DDCommon.Comments.worldGen);

    //tin ore group
    public final ConfigGroup tin_ore = group(1, "tin_ore",
            DDCommon.Comments.tin_ore);
    public final ConfigInt tin_ore_cluster = i(12, 0, 128, "tin_ore_cluster",
            DDCommon.Comments.tin_ore_cluster);
    public final ConfigInt tin_ore_frequency = i(10, 0, 128, "tin_ore_frequency",
            DDCommon.Comments.tin_ore_frequency);
    public final ConfigInt tin_ore_min = i(-45, minHeight, maxHeight, "tin_ore_min",
            DDCommon.Comments.tin_ore_min);
    public final ConfigInt tin_ore_max = i(128, minHeight, maxHeight, "tin_ore_min",
            DDCommon.Comments.tin_ore_max);
    public final ConfigBool tin_ore_gen = b(true, "tin_ore_gen",
            DDCommon.Comments.tin_ore_gen);

    //mix stone blobs group
    public final ConfigGroup stone_blob = group(1, "stone_blob",
            DDCommon.Comments.stone_blob);
    public final ConfigInt stone_blob_cluster = i(48, 0, 128, "stone_blob_cluster",
            DDCommon.Comments.stone_blob_cluster);
    public final ConfigInt stone_blob_frequency = i(16, 0, 128, "stone_blob_frequency",
            DDCommon.Comments.stone_blob_frequency);
    public final ConfigInt stone_blob_min = i(-64, minHeight, maxHeight, "stone_blob_min",
            DDCommon.Comments.stone_blob_min);
    public final ConfigInt stone_blob_max = i(256, minHeight, maxHeight, "stone_blob_max",
            DDCommon.Comments.stone_blob_max);
    public final ConfigBool stone_blob_gen = b(true, "stone_blob_gen",
            DDCommon.Comments.stone_blob_gen);

    //limestone blobs group
    public final ConfigGroup limestone_blob = group(1, "limestone_blob",
            DDCommon.Comments.limestone_blob);
    public final ConfigInt limestone_blob_cluster = i(32, 0, 128, "limestone_blob_cluster",
            DDCommon.Comments.limestone_blob_cluster);
    public final ConfigInt limestone_blob_frequency = i(2, 0, 128, "limestone_blob_frequency",
            DDCommon.Comments.limestone_blob_frequency);
    public final ConfigInt limestone_blob_min = i(-64, minHeight, maxHeight, "limestone_blob_min",
            DDCommon.Comments.limestone_blob_min);
    public final ConfigInt limestone_blob_max = i(24, minHeight, maxHeight, "limestone_blob_max",
            DDCommon.Comments.limestone_blob_max);
    public final ConfigBool limestone_blob_gen = b(true, "limestone_blob_gen",
            DDCommon.Comments.limestone_blob_gen);

    //weathered_limestone blobs group
    public final ConfigGroup weathered_limestone_blob = group(1, "weathered_limestone_blob",
            DDCommon.Comments.weathered_limestone_blob);
    public final ConfigInt weathered_limestone_blob_cluster = i(24, 0, 128, "weathered_limestone_blob_cluster",
            DDCommon.Comments.weathered_limestone_blob_cluster);
    public final ConfigInt weathered_limestone_blob_frequency = i(2, 0, 128, "weathered_limestone_blob_frequency",
            DDCommon.Comments.weathered_limestone_blob_frequency);
    public final ConfigInt weathered_limestone_blob_min = i(-64, minHeight, maxHeight, "weathered_limestone_blob_min",
            DDCommon.Comments.weathered_limestone_blob_min);
    public final ConfigInt weathered_limestone_blob_max = i(0, minHeight, maxHeight, "weathered_limestone_blob_max",
            DDCommon.Comments.weathered_limestone_blob_max);
    public final ConfigBool weathered_limestone_blob_gen = b(true, "weathered_limestone_blob_gen",
            DDCommon.Comments.weathered_limestone_blob_gen);

    //gabbro blobs group
    public final ConfigGroup gabbro_blob = group(1, "gabbro_blob",
            DDCommon.Comments.gabbro_blob);
    public final ConfigInt gabbro_blob_cluster = i(48, 0, 128, "gabbro_blob_cluster",
            DDCommon.Comments.gabbro_blob_cluster);
    public final ConfigInt gabbro_blob_frequency = i(1, 0, 128, "gabbro_blob_frequency",
            DDCommon.Comments.gabbro_blob_frequency);
    public final ConfigInt gabbro_blob_min = i(-64, minHeight, maxHeight, "gabbro_blob_min",
            DDCommon.Comments.gabbro_blob_min);
    public final ConfigInt gabbro_blob_max = i(24, minHeight, maxHeight, "gabbro_blob_max",
            DDCommon.Comments.gabbro_blob_max);
    public final ConfigBool gabbro_blob_gen = b(true, "gabbro_blob_gen",
            DDCommon.Comments.gabbro_blob_gen);

    //eroded_limestone blobs group
    public final ConfigGroup eroded_limestone_blob = group(1, "eroded_limestone_blob",
            DDCommon.Comments.eroded_limestone_blob);
    public final ConfigInt eroded_limestone_blob_cluster = i(12, 0, 128, "eroded_limestone_blob_cluster",
            DDCommon.Comments.eroded_limestone_blob_cluster);
    public final ConfigInt eroded_limestone_blob_frequency = i(4, 0, 128, "eroded_limestone_blob_frequency",
            DDCommon.Comments.eroded_limestone_blob_frequency);
    public final ConfigInt eroded_limestone_blob_min = i(-64, minHeight, maxHeight, "eroded_limestone_blob_min",
            DDCommon.Comments.eroded_limestone_blob_min);
    public final ConfigInt eroded_limestone_blob_max = i(256, minHeight, maxHeight, "eroded_limestone_blob_max",
            DDCommon.Comments.eroded_limestone_blob_max);
    public final ConfigBool eroded_limestone_blob_gen = b(true, "eroded_limestone_blob_gen",
            DDCommon.Comments.eroded_limestone_blob_gen);




    @Override
    public String getName() {
        return "common";
    }

    private static class Comments {
        static String worldGen = "Modify Create Dream n' Desire's impact on your terrain";

        static String tin_ore = "Tin Ore";
        static String tin_ore_cluster = "Tin Ore Amount";
        static String tin_ore_frequency = "Tin Ore Frequency";
        static String tin_ore_min = "Tin Ore Min Height";
        static String tin_ore_max = "Tin Ore Max Height";
        static String tin_ore_gen = "Generate Tin Ores";

        static String stone_blob = "Mix Stone Blob";
        static String stone_blob_cluster = "Mix Stone Blob Amount";
        static String stone_blob_frequency = "Mix Stone Blob Frequency";
        static String stone_blob_min = "Mix Stone Blob Min Height";
        static String stone_blob_max = "Mix Stone Blob Max Height";
        static String stone_blob_gen = "Generate Mix Stone Blobs";

        static String limestone_blob = "Limestone Blob";
        static String limestone_blob_cluster = "Limestone Blob Amount";
        static String limestone_blob_frequency = "Limestone Blob Frequency";
        static String limestone_blob_min = "Limestone Blob Min Height";
        static String limestone_blob_max = "Limestone Blob Max Height";
        static String limestone_blob_gen = "Generate Limestone Blobs";

        static String weathered_limestone_blob = "Weathered Limestone Blob";
        static String weathered_limestone_blob_cluster = "Weathered Limestone Blob Amount";
        static String weathered_limestone_blob_frequency = "Weathered Limestone Blob Frequency";
        static String weathered_limestone_blob_min = "Weathered Limestone Blob Min Height";
        static String weathered_limestone_blob_max = "Weathered Limestone Blob Max Height";
        static String weathered_limestone_blob_gen = "Generate Weathered Limestone Blobs";

        static String gabbro_blob = "Gabbro Blob";
        static String gabbro_blob_cluster = "Gabbro Blob Amount";
        static String gabbro_blob_frequency = "Gabbro Blob Frequency";
        static String gabbro_blob_min = "Gabbro Blob Min Height";
        static String gabbro_blob_max = "Gabbro Blob Max Height";
        static String gabbro_blob_gen = "Generate Gabbro Blobs";

        static String eroded_limestone_blob = "Eroded Limestone Blob";
        static String eroded_limestone_blob_cluster = "Eroded Limestone Blob Amount";
        static String eroded_limestone_blob_frequency = "Eroded Limestone Blob Frequency";
        static String eroded_limestone_blob_min = "Eroded Limestone Blob Min Height";
        static String eroded_limestone_blob_max = "Eroded Limestone Blob Max Height";
        static String eroded_limestone_blob_gen = "Generate Eroded Limestone Blobs";
    }
}
