package dev.lopyluna.dndesires.content.datagen.recipes;

import dev.lopyluna.dndesires.content.datagen.base_gens.DragonBreathingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class DragonBreathingFanRecipeGen extends DragonBreathingRecipeGen {

    GeneratedRecipe CHORUS_FRUIT = convert(Items.APPLE, Items.CHORUS_FRUIT, .5f);
    GeneratedRecipe END_STONE = convert(Items.DEEPSLATE, Items.END_STONE);
    GeneratedRecipe DRAGON_BREATH = convert(Items.GLASS_BOTTLE, Items.DRAGON_BREATH);
    GeneratedRecipe ENDER_PEARL = convert(Items.MAGMA_CREAM, Items.ENDER_PEARL, .15f);
    GeneratedRecipe CRYING_OBSIDIAN = convert(Items.OBSIDIAN, Items.CRYING_OBSIDIAN);

    public DragonBreathingFanRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
