package dev.lopyluna.dndesires.compat.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.compat.jei.category.*;
import dev.lopyluna.dndesires.content.recipes.DragonBreathingRecipe;
import dev.lopyluna.dndesires.content.recipes.FreezingRecipe;
import dev.lopyluna.dndesires.content.recipes.SandingRecipe;
import dev.lopyluna.dndesires.content.recipes.SeethingRecipe;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

        CreateRecipeCategory<?> dragonBreathing = builder(DragonBreathingRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.DRAGON_BREATHING)
                .catalystStack(getInFan("fan_dragon_breathing", "Fan behind Dragon bound blocks"))
                .doubleItemIcon(AllItems.PROPELLER.get(), Items.DRAGON_HEAD)
                .emptyBackground(178, 72)
                .build("fan_dragon_breathing", DragonBreathingCategory::new);
        CreateRecipeCategory<?> freezing = builder(FreezingRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.FREEZING)
                .catalystStack(getFan("fan_freezing", "Fan behind Freezing Snow"))
                .catalystStack(DesiresBlocks.INDUSTRIAL_FAN::asStack)
                .doubleItemIcon(AllItems.PROPELLER.get(), Items.POWDER_SNOW_BUCKET)
                .emptyBackground(178, 72)
                .build("fan_freezing", FreezingCategory::new);
        CreateRecipeCategory<?> sanding = builder(SandingRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.SANDING)
                .catalystStack(getFan("fan_sanding", "Fan behind Sand"))
                .catalystStack(DesiresBlocks.INDUSTRIAL_FAN::asStack)
                .doubleItemIcon(AllItems.PROPELLER.get(), Items.SAND)
                .emptyBackground(178, 72)
                .build("fan_sanding", SandingCategory::new);
        CreateRecipeCategory<?> seething = builder(SeethingRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.SEETHING)
                .catalystStack(getFan("fan_seething", "Fan behind Seething Heat"))
                .catalystStack(DesiresBlocks.INDUSTRIAL_FAN::asStack)
                .doubleItemIcon(AllItems.PROPELLER.get(), AllItems.BLAZE_CAKE)
                .emptyBackground(178, 72)
                .build("fan_seething", SeethingCategory::new);

        CreateRecipeCategory<?> hydraulic_compacting = builder(BasinRecipe.class)
                .addTypedRecipes(DesiresRecipeTypes.HYDRAULIC_COMPACTING)
                .catalyst(DesiresBlocks.HYDRAULIC_PRESS::get)
                .catalyst(AllBlocks.BASIN::get)
                .doubleItemIcon(DesiresBlocks.HYDRAULIC_PRESS.get(), AllBlocks.BASIN.get())
                .emptyBackground(177, 103)
                .build(DnDesires.loc("hydraulic_compacting"), HydraulicCategory::new);
    }

    public static Supplier<ItemStack> getInFan(String name, String fullName) {
        var stack = DesiresBlocks.INDUSTRIAL_FAN.asStack();
        stack.set(DataComponents.CUSTOM_NAME, Component.translatableWithFallback(DnDesires.MOD_ID + ".recipe." + name + ".fan", fullName).withStyle(style -> style.withItalic(false)));
        return () -> stack;
    }

    public static Supplier<ItemStack> getFan(String name, String fullName) {
        var stack = AllBlocks.ENCASED_FAN.asStack();
        stack.set(DataComponents.CUSTOM_NAME, Component.translatableWithFallback(DnDesires.MOD_ID + ".recipe." + name + ".fan", fullName).withStyle(style -> style.withItalic(false)));
        return () -> stack;
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
