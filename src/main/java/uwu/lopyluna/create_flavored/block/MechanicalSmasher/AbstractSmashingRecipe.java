package uwu.lopyluna.create_flavored.block.MechanicalSmasher;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractSmashingRecipe extends ProcessingRecipe<RecipeWrapper> {

	public AbstractSmashingRecipe(IRecipeTypeInfo recipeType, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		super(recipeType, params);
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

}
