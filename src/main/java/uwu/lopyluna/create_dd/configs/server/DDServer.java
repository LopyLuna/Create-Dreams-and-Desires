package uwu.lopyluna.create_dd.configs.server;

import com.simibubi.create.foundation.config.ConfigBase;

public class DDServer extends ConfigBase {
    int maxHeight = 2048;
    int minHeight = -2048;

    public final ConfigGroup infrastructure = group(0, "infrastructure", DDServer.Comments.infrastructure);

    //recipe group
    public final ConfigGroup recipe = group(1, "recipe", DDServer.Comments.recipe);

    //compound recipe group
    public final ConfigGroup compound_recipe = group(2, "recipe", DDServer.Comments.compound_recipe);

    public final ConfigBool blaze_gold_recipe = b(true, "blaze_gold_recipe",
            DDServer.Comments.blaze_gold_recipe);

    public final ConfigBool refined_radiance_recipe = b(true, "refined_radiance_recipe",
            DDServer.Comments.refined_radiance_recipe);
    public final ConfigInt refined_radiance_min = i(-32, minHeight, maxHeight, "refined_radiance_min",
            DDServer.Comments.refined_radiance_min);
    public final ConfigInt refined_radiance_light_level = i(15, 0, 15, "refined_radiance_light_level",
            DDServer.Comments.refined_radiance_light_level);

    public final ConfigBool shadow_steel_recipe = b(true, "shadow_steel_recipe",
            DDServer.Comments.shadow_steel_recipe);
    public final ConfigInt shadow_steel_min = i(-10, minHeight, maxHeight, "shadow_steel_min",
            DDServer.Comments.shadow_steel_min);


    public final ConfigBool stargaze_singularity_recipe = b(true, "stargaze_singularity_recipe",
            DDServer.Comments.stargaze_singularity_recipe);
    public final ConfigInt stargaze_singularity_min_time = i(16000, 0, 24000, "stargaze_singularity_min_time",
            DDServer.Comments.stargaze_singularity_min_time);
    public final ConfigInt stargaze_singularity_max_time = i(20000, 0, 24000, "stargaze_singularity_max_time",
            DDServer.Comments.stargaze_singularity_max_time);
    public final ConfigFloat stargaze_singularity_max_height_division = f(1.25f, -256.0f, 256.0f, "stargaze_singularity_max_height_division",
            DDServer.Comments.stargaze_singularity_max_height_division);




    @Override
    public String getName() {
        return "server";
    }

    private static class Comments {
        static String infrastructure = "The Backbone of Create Dream n' Desire's";
        static String recipe = "Recipes";
        static String compound_recipe = "Compound Recipes";
        static String blaze_gold_recipe = "Blaze Brass Recipe";
        static String refined_radiance_recipe = "Refined Radiance Recipe";
        static String refined_radiance_min = "Shadow Steel Recipe Require Min Height";
        static String refined_radiance_light_level = "Shadow Steel Recipe Require Light Level";
        static String shadow_steel_recipe = "Shadow Steel Recipe";
        static String shadow_steel_min = "Shadow Steel Recipe Require Min Height";
        static String stargaze_singularity_recipe = "Stargaze Recipe";;
        static String stargaze_singularity_min_time = "Stargaze Recipe Require Min Time";
        static String stargaze_singularity_max_time = "Stargaze Recipe Require Max Time";
        static String stargaze_singularity_max_height_division = "Stargaze Recipe Require Min of Max Height Division";

    }
}
