package uwu.lopyluna.create_dd.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.ConversionRecipe;
import com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.fml.ModList;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.item.DDItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class MysteriousConversion extends MysteriousItemConversionCategory {

    public static final List<ConversionRecipe> RECIPES = new ArrayList<>();

    static {
        if (DDConfigs.server().recipes.shadow_steel_recipe.get()) {
		RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.SHADOW_STEEL.asStack()));}

        if (DDConfigs.server().recipes.refined_radiance_recipe.get()) {
		RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.REFINED_RADIANCE.asStack()));}

        if (ModList.get().isLoaded("createaddition")) {
            RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.OVERCHARGE_ALLOY.asStack()));}

        if (DDConfigs.server().recipes.blaze_gold_recipe.get()) {
        RECIPES.add(ConversionRecipe.create(DDItems.CHROMATIC_COMPOUND.asStack(), DDItems.BLAZE_GOLD.asStack()));}

        if (DDConfigs.server().recipes.stargaze_singularity_recipe.get()) {
        RECIPES.add(ConversionRecipe.create(DDItems.FALLEN_STARGAZE_SINGULARITY.asStack(), DDItems.STARGAZE_SINGULARITY.asStack()));}


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
    public void draw(ConversionRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_LONG_ARROW.render(matrixStack, 52, 20);
        AllGuiTextures.JEI_QUESTION_MARK.render(matrixStack, 77, 5);
    }
}
