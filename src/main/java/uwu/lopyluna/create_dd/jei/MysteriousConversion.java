package uwu.lopyluna.create_dd.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.ConversionRecipe;
import com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import uwu.lopyluna.create_dd.item.DDItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class MysteriousConversion extends MysteriousItemConversionCategory {

    public static final List<ConversionRecipe> RECIPES = new ArrayList<>();

    static {
		RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.SHADOW_STEEL.asStack()));
		RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.REFINED_RADIANCE.asStack()));
        RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.OVERCHARGE_ALLOY.asStack()));
    }

    public MysteriousConversion(Info<ConversionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ConversionRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 27, 17)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 132, 17)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(recipe.getRollableResults().get(0).getStack());
    }

    @Override
    public void draw(ConversionRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_LONG_ARROW.render(matrixStack, 52, 20);
        AllGuiTextures.JEI_QUESTION_MARK.render(matrixStack, 77, 5);
    }
}
