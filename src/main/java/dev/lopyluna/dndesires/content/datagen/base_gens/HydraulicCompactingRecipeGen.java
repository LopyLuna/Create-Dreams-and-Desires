package dev.lopyluna.dndesires.content.datagen.base_gens;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class HydraulicCompactingRecipeGen extends StandardProcessingRecipeGen<CompactingRecipe> {

    public HydraulicCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected DesiresRecipeTypes getRecipeType() {
        return DesiresRecipeTypes.HYDRAULIC_COMPACTING;
    }

}
