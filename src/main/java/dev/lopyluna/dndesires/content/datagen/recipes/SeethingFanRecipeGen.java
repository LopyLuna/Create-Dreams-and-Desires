package dev.lopyluna.dndesires.content.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.CompatMetals;
import dev.lopyluna.dndesires.content.datagen.base_gens.SeethingRecipeGen;
import dev.lopyluna.dndesires.register.DesiresItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class SeethingFanRecipeGen extends SeethingRecipeGen {

    GeneratedRecipe ENDER_EYE = convert(Items.ENDER_PEARL, Items.ENDER_EYE);
    GeneratedRecipe MAGMA_BLOCK = convert(Items.NETHERRACK, Items.MAGMA_BLOCK);
    GeneratedRecipe MAGMA_CREAM = convert(Items.SLIME_BALL, Items.MAGMA_CREAM);
    GeneratedRecipe LAPIS_LAZULI_SHARD = convert(Items.CALCITE, DesiresItems.LAPIS_LAZULI_SHARD, .75f);
    GeneratedRecipe DIAMOND_SHARD_FROM_BLOCK = convert(Items.COAL_BLOCK, DesiresItems.DIAMOND_SHARD, .25f);
    GeneratedRecipe DIAMOND_SHARD_FROM_ORE = convert(Items.DEEPSLATE_COAL_ORE, DesiresItems.DIAMOND_SHARD, .75f);
    GeneratedRecipe NETHERITE_SCRAP  = convert(Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, .15f);

    GeneratedRecipe CRUSHED_COPPER = crushedOre(AllItems.CRUSHED_COPPER, Items.COPPER_INGOT, Items.COPPER_INGOT, .5f);
    GeneratedRecipe CRUSHED_ZINC = crushedOre(AllItems.CRUSHED_ZINC, AllItems.ZINC_INGOT, AllItems.ZINC_INGOT, .25f);
    GeneratedRecipe CRUSHED_GOLD = crushedOre(AllItems.CRUSHED_GOLD, Items.GOLD_INGOT, Items.GOLD_INGOT, .5f);
    GeneratedRecipe CRUSHED_IRON = crushedOre(AllItems.CRUSHED_IRON, Items.IRON_INGOT, Items.IRON_INGOT, .75f);

    GeneratedRecipe CRUSHED_OSMIUM = moddedCrushedOre(CompatMetals.OSMIUM, AllItems.CRUSHED_OSMIUM::get, "ingot", .5f);
    GeneratedRecipe CRUSHED_PLATINUM = moddedCrushedOre(CompatMetals.PLATINUM, AllItems.CRUSHED_PLATINUM::get, "ingot", .25f);
    GeneratedRecipe CRUSHED_SILVER = moddedCrushedOre(CompatMetals.SILVER, AllItems.CRUSHED_SILVER::get, "ingot", .25f);
    GeneratedRecipe CRUSHED_TIN = moddedCrushedOre(CompatMetals.TIN, AllItems.CRUSHED_TIN::get, "ingot", .75f);
    GeneratedRecipe CRUSHED_LEAD = moddedCrushedOre(CompatMetals.LEAD, AllItems.CRUSHED_LEAD::get, "ingot", .5f);
    GeneratedRecipe CRUSHED_QUICKSILVER = moddedCrushedOre(CompatMetals.QUICKSILVER, AllItems.CRUSHED_QUICKSILVER::get, "ingot", .5f);
    GeneratedRecipe CRUSHED_BAUXITE = moddedCrushedOre(CompatMetals.ALUMINUM, AllItems.CRUSHED_BAUXITE::get, "ingot", .75f);
    GeneratedRecipe CRUSHED_URANIUM = moddedCrushedOre(CompatMetals.URANIUM, AllItems.CRUSHED_URANIUM::get, "ingot", .25f);
    GeneratedRecipe CRUSHED_NICKEL = moddedCrushedOre(CompatMetals.NICKEL, AllItems.CRUSHED_NICKEL::get, "ingot", .5f);

    public SeethingFanRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
