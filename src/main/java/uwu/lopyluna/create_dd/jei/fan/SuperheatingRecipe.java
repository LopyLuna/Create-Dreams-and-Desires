package uwu.lopyluna.create_dd.jei.fan;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.level.Level;
import uwu.lopyluna.create_dd.recipes.BakingFanProcessing;
import uwu.lopyluna.create_dd.recipes.BakingRecipesTypes;

public class SuperheatingRecipe extends ProcessingRecipe<BakingFanProcessing.SuperHeatingWrapper> {

    public SuperheatingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
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