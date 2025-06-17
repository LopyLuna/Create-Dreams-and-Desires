package dev.lopyluna.dndesires.compat.jei.category;

import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class HydraulicCategory extends BasinCategory {
    private final AnimatedHydraulic press = new AnimatedHydraulic(true);
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

    public HydraulicCategory(CreateRecipeCategory.Info<BasinRecipe> info) {
        super(info, true);
    }

    @Override
    public void draw(@NotNull BasinRecipe recipe, @NotNull IRecipeSlotsView iRecipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);
        var requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE) heater.withHeat(requiredHeat.visualizeAsBlazeBurner()).draw(graphics, getBackground().getWidth() / 2 + 3, 55);
        press.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    }
}
