package uwu.lopyluna.create_dd.recipe.Recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.level.Level;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.recipe.IndustrialFanProcessing;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SuperHeatingRecipe extends ProcessingRecipe<IndustrialFanProcessing.SuperHeatingWrapper> {

    public SuperHeatingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(DDRecipesTypes.SUPERHEATING, params);
    }

    @Override
    public boolean matches(IndustrialFanProcessing.SuperHeatingWrapper inv, Level worldIn) {
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
}