package dev.lopyluna.dndesires.content.datagen.base_gens;

import dev.lopyluna.dndesires.content.datagen.recipes.helper.FanProcessingRecipeGen;
import dev.lopyluna.dndesires.content.recipes.DragonBreathingRecipe;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DragonBreathingRecipeGen extends FanProcessingRecipeGen<DragonBreathingRecipe> {

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(asResource(RegisteredObjectsHelper.getKeyOrThrow(result.get().asItem()).getPath()),
                p -> p.withItemIngredients(input.get()).output(result.get()));
    }

    public DragonBreathingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected DesiresRecipeTypes getRecipeType() {
        return DesiresRecipeTypes.DRAGON_BREATHING;
    }

}
