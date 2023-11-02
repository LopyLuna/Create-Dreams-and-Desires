package uwu.lopyluna.create_dd.recipe.Recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SuperheatingRecipe extends ProcessingRecipe<SuperheatingRecipe.SuperheatingWrapper> {

    public SuperheatingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(DDRecipesTypes.SUPERHEATING, params);
    }

    @Override
    public boolean matches(SuperheatingWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static class SuperheatingWrapper extends RecipeWrapper {
        public SuperheatingWrapper() {
            super(new ItemStackHandler(1));
        }
    }

}