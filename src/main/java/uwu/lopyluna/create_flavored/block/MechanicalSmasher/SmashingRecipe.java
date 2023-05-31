package uwu.lopyluna.create_flavored.block.MechanicalSmasher;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SmashingRecipe extends AbstractSmashingRecipe {

	public SmashingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.CRUSHING, params);
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
