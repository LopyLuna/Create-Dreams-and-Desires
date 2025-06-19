package dev.lopyluna.dndesires.content.datagen.base_gens;

import dev.lopyluna.dndesires.content.datagen.recipes.helper.FanProcessingRecipeGen;
import dev.lopyluna.dndesires.content.recipes.SeethingRecipe;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class SeethingRecipeGen extends FanProcessingRecipeGen<SeethingRecipe> {

    public SeethingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected DesiresRecipeTypes getRecipeType() {
        return DesiresRecipeTypes.SEETHING;
    }

}
