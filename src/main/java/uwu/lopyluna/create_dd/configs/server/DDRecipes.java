package uwu.lopyluna.create_dd.configs.server;

import uwu.lopyluna.create_dd.configs.DDConfigBase;

@SuppressWarnings({"all"})
public class DDRecipes extends DDConfigBase {
    int maxHeight = 2048;
    int minHeight = -2048;

    public final ConfigBool bulkPressing = b(true, "bulkPressing", DDRecipes.Comments.bulkPressing);
    public final ConfigBool bulkCutting = b(true, "bulkCutting", DDRecipes.Comments.bulkCutting);
    public final ConfigBool allowStonecuttingOnSaw = b(true, "allowStonecuttingOnSaw", DDRecipes.Comments.allowStonecuttingOnSaw);
    public final ConfigBool allowWoodcuttingOnSaw = b(true, "allowWoodcuttingOnSaw", DDRecipes.Comments.allowWoodcuttingOnSaw);

    
    public final ConfigGroup compound_recipe = group(1, "recipe", DDRecipes.Comments.compound_recipe);

    public final ConfigBool blaze_gold_recipe = b(true, "blaze_gold_recipe",
            DDRecipes.Comments.blaze_gold_recipe);

    public final ConfigBool refined_radiance_recipe = b(true, "refined_radiance_recipe",
            DDRecipes.Comments.refined_radiance_recipe);
    public final ConfigInt refined_radiance_max_height = i(319, minHeight, maxHeight, "refined_radiance_max_height",
            DDRecipes.Comments.refined_radiance_max);
    public final ConfigInt refined_radiance_min_height = i(-32, minHeight, maxHeight, "refined_radiance_min_height",
            DDRecipes.Comments.refined_radiance_min);
    public final ConfigInt refined_radiance_light_level = i(15, 0, 15, "refined_radiance_light_level",
            DDRecipes.Comments.refined_radiance_light_level);

    public final ConfigBool shadow_steel_recipe = b(true, "shadow_steel_recipe",
            DDRecipes.Comments.shadow_steel_recipe);
    public final ConfigInt shadow_steel_min_height = i(-10, minHeight, maxHeight, "shadow_steel_min_height",
            DDRecipes.Comments.shadow_steel_min);


    public final ConfigBool stargaze_singularity_recipe = b(true, "stargaze_singularity_recipe",
            DDRecipes.Comments.stargaze_singularity_recipe);
    public final ConfigInt stargaze_singularity_min_time = i(16000, 0, 24000, "stargaze_singularity_min_time",
            DDRecipes.Comments.stargaze_singularity_min_time);
    public final ConfigInt stargaze_singularity_max_time = i(20000, 0, 24000, "stargaze_singularity_max_time",
            DDRecipes.Comments.stargaze_singularity_max_time);
    public final ConfigFloat stargaze_singularity_max_height_division = f(1.25f, -256.0f, 256.0f, "stargaze_singularity_max_height_division",
            DDRecipes.Comments.stargaze_singularity_max_height_division);
    
    
    
    @Override
    public String getName() {
        return "recipes";
    }

    private static class Comments {
        static String bulkPressing = "Allow the Hydraulic Press to process entire stacks at a time.";
        static String bulkCutting = "Allow the Bronze Saw to process entire stacks at a time.";
        static String compound_recipe = "Compound Recipes";
        static String blaze_gold_recipe = "Blaze Brass Recipe";
        static String refined_radiance_recipe = "Refined Radiance Recipe";
        static String refined_radiance_max = "Shadow Steel Recipe Require Max Height";
        static String refined_radiance_min = "Shadow Steel Recipe Require Min Height";
        static String refined_radiance_light_level = "Shadow Steel Recipe Require Light Level";
        static String shadow_steel_recipe = "Shadow Steel Recipe";
        static String shadow_steel_min = "Shadow Steel Recipe Require Min Height";
        static String stargaze_singularity_recipe = "Stargaze Recipe";
        static String stargaze_singularity_min_time = "Stargaze Recipe Require Min Time";
        static String stargaze_singularity_max_time = "Stargaze Recipe Require Max Time";
        static String stargaze_singularity_max_height_division = "Stargaze Recipe Require Min of Max Height Division";
        static String allowStonecuttingOnSaw = "Allow any stonecutting recipes to be processed by a Bronze Saw.";
        static String allowWoodcuttingOnSaw = "Allow any Druidcraft woodcutter recipes to be processed by a Bronze Saw.";
    }
}
