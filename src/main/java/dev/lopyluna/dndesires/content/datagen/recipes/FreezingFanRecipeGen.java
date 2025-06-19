package dev.lopyluna.dndesires.content.datagen.recipes;

import dev.lopyluna.dndesires.content.datagen.base_gens.FreezingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class FreezingFanRecipeGen extends FreezingRecipeGen {

    GeneratedRecipe PACKED_ICE = convert(Items.ICE, Items.PACKED_ICE);
    GeneratedRecipe BLUE_ICE = convert(Items.PACKED_ICE, Items.BLUE_ICE);
    GeneratedRecipe POWDER_SNOW_BUCKET = convert(Items.WATER_BUCKET, Items.POWDER_SNOW_BUCKET);
    GeneratedRecipe SLIME_BALL = convert(Items.MAGMA_CREAM, Items.SLIME_BALL);
    GeneratedRecipe SNOW_BLOCK = convert(Items.SNOWBALL, Items.SNOW_BLOCK);
    GeneratedRecipe OBSIDIAN = convert(Items.CRYING_OBSIDIAN, Items.OBSIDIAN);


    public FreezingFanRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
