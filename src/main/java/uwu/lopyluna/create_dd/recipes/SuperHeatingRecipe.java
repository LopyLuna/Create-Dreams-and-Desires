package uwu.lopyluna.create_dd.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SuperHeatingRecipe extends ProcessingRecipe<BakingFanProcessing.SuperHeatingWrapper> {

    public SuperHeatingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(BakingRecipesTypes.SUPERHEATING, params);
    }

    @Override
    public boolean matches(BakingFanProcessing.SuperHeatingWrapper inv, Level worldIn) {
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