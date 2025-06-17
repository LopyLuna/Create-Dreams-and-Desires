package dev.lopyluna.dndesires.content.datagen;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import dev.lopyluna.dndesires.content.datagen.recipes.EmptyingRecipeGen;
import dev.lopyluna.dndesires.content.datagen.recipes.FillingRecipeGen;
import dev.lopyluna.dndesires.content.datagen.recipes.HydraulicRecipeGen;
import dev.lopyluna.dndesires.content.datagen.recipes.MixingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DesiresRecipeProvider extends RecipeProvider {

    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();
    protected final List<GeneratedRecipe> all = new ArrayList<>();

    public DesiresRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }


    public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new EmptyingRecipeGen(output, registries));
        GENERATORS.add(new FillingRecipeGen(output, registries));
        GENERATORS.add(new MixingRecipeGen(output, registries));
        GENERATORS.add(new HydraulicRecipeGen(output, registries));

        gen.addProvider(true, new DataProvider() {
            @Override
            public @NotNull String getName() {
                return "DnDesires's Processing Recipes";
            }
            @Override
            public @NotNull CompletableFuture<?> run(@NotNull CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream().map(gen -> gen.run(dc)).toArray(CompletableFuture[]::new));
            }
        });
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        all.forEach(c -> c.register(pRecipeOutput));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    @FunctionalInterface
    public interface GeneratedRecipe {
        void register(RecipeOutput output);
    }
}