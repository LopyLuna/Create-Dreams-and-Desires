package dev.lopyluna.dndesires.content.recipes;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;

public class HydraulicRecipe extends BasinRecipe {
    public HydraulicRecipe(ProcessingRecipeParams params) {
        super(DesiresRecipeTypes.HYDRAULIC_COMPACTING, params);
    }
}
