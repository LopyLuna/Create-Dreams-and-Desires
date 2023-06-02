package uwu.lopyluna.create_flavored.block.MechanicalSmasher;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import uwu.lopyluna.create_flavored.RecookingBOB;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SmashingRecipe extends AbstractSmashingRecipe {

	public SmashingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		super(RecookingBOB.SMASHING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
				.test(inv.getItem(0));
	}

	@Override
	protected int getMaxOutputCount() {
		return 7;
	}

}
