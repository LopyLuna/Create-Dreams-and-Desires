package dev.lopyluna.dndesires.compat.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.compat.jei.category.HydraulicCategory;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static mezz.jei.api.recipe.RecipeType.createRecipeHolderType;

@JeiPlugin
@SuppressWarnings({"SameParameterValue", "unused"})
@ParametersAreNonnullByDefault
public class DesiresJEI implements IModPlugin {
    private static final ResourceLocation ID = DnDesires.loc("jei_plugin");

    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    public static IJeiRuntime runtime;

    private void loadCategories() {
        allCategories.clear();

        CreateRecipeCategory<?> hydraulic_compacting = builder(BasinRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.HYDRAULIC_COMPACTING)
                .catalyst(DesiresBlocks.HYDRAULIC_PRESS::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(DesiresBlocks.HYDRAULIC_PRESS.get(), AllBlocks.BASIN.get())
                .emptyBackground(177, 103)
                .build(DnDesires.loc("hydraulic_compacting"), HydraulicCategory::new);
    }

    private <T extends Recipe<? extends RecipeInput>> CategoryBuilder<T> builder(Class<T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
        registration.addRecipeCatalyst(DesiresBlocks.HYDRAULIC_PRESS, createRecipeHolderType(Create.asResource("pressing")));
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    private class CategoryBuilder<T extends Recipe<?>> extends CreateRecipeCategory.Builder<T> {
        public CategoryBuilder(Class<? extends T> recipeClass) {
            super(recipeClass);
        }

        @Override
        public @NotNull CreateRecipeCategory<T> build(ResourceLocation id, CreateRecipeCategory.Factory<T> factory) {
            CreateRecipeCategory<T> category = super.build(id, factory);
            allCategories.add(category);
            return category;
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        DesiresJEI.runtime = runtime;
    }
}
