package dev.lopyluna.dndesires.content.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FanRecipe extends StandardProcessingRecipe<SingleRecipeInput> {

    public FanRecipe(DesiresRecipeTypes recipeType, ProcessingRecipeParams params) {
        super(recipeType, params);
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level level) {
        if (inv.isEmpty()) return false;
        return ingredients.getFirst().test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }
}