package dev.lopyluna.dndesires.content.datagen.recipes;

import com.simibubi.create.AllItems;
import dev.lopyluna.dndesires.content.datagen.base_gens.SandingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class SandingFanRecipeGen extends SandingRecipeGen {

    GeneratedRecipe COPPER_BLOCK = convert(Items.EXPOSED_COPPER, Items.COPPER_BLOCK);
    GeneratedRecipe EXPOSED_COPPER = convert(Items.WEATHERED_COPPER, Items.EXPOSED_COPPER);
    GeneratedRecipe WEATHERED_COPPER = convert(Items.OXIDIZED_COPPER, Items.WEATHERED_COPPER);
    GeneratedRecipe CUT_COPPER = convert(Items.EXPOSED_CUT_COPPER, Items.CUT_COPPER);
    GeneratedRecipe EXPOSED_CUT_COPPER = convert(Items.WEATHERED_CUT_COPPER, Items.EXPOSED_CUT_COPPER);
    GeneratedRecipe WEATHERED_CUT_COPPER = convert(Items.OXIDIZED_CUT_COPPER, Items.WEATHERED_CUT_COPPER);
    GeneratedRecipe CUT_COPPER_SLAB = convert(Items.EXPOSED_CUT_COPPER_SLAB, Items.CUT_COPPER_SLAB);
    GeneratedRecipe EXPOSED_CUT_COPPER_SLAB = convert(Items.WEATHERED_CUT_COPPER_SLAB, Items.EXPOSED_CUT_COPPER_SLAB);
    GeneratedRecipe WEATHERED_CUT_COPPER_SLAB = convert(Items.OXIDIZED_CUT_COPPER_SLAB, Items.WEATHERED_CUT_COPPER_SLAB);
    GeneratedRecipe CUT_COPPER_STAIRS = convert(Items.EXPOSED_CUT_COPPER_STAIRS, Items.CUT_COPPER_STAIRS);
    GeneratedRecipe EXPOSED_CUT_COPPER_STAIRS = convert(Items.WEATHERED_CUT_COPPER_STAIRS, Items.EXPOSED_CUT_COPPER_STAIRS);
    GeneratedRecipe WEATHERED_CUT_COPPER_STAIRS = convert(Items.OXIDIZED_CUT_COPPER_STAIRS, Items.WEATHERED_CUT_COPPER_STAIRS);

    GeneratedRecipe ANDESITE = convert(Items.POLISHED_ANDESITE, Items.ANDESITE);
    GeneratedRecipe ANDESITE_SLAB = convert(Items.POLISHED_ANDESITE_SLAB, Items.ANDESITE_SLAB);
    GeneratedRecipe ANDESITE_STAIRS = convert(Items.POLISHED_ANDESITE_STAIRS, Items.ANDESITE_STAIRS);

    GeneratedRecipe GRANITE = convert(Items.POLISHED_GRANITE, Items.GRANITE);
    GeneratedRecipe GRANITE_SLAB = convert(Items.POLISHED_GRANITE_SLAB, Items.GRANITE_SLAB);
    GeneratedRecipe GRANITE_STAIRS = convert(Items.POLISHED_GRANITE_STAIRS, Items.GRANITE_STAIRS);

    GeneratedRecipe DIORITE = convert(Items.POLISHED_DIORITE, Items.DIORITE);
    GeneratedRecipe DIORITE_SLAB = convert(Items.POLISHED_DIORITE_SLAB, Items.DIORITE_SLAB);
    GeneratedRecipe DIORITE_STAIRS = convert(Items.POLISHED_DIORITE_STAIRS, Items.DIORITE_STAIRS);

    GeneratedRecipe COBBLED_DEEPSLATE = convert(Items.POLISHED_DEEPSLATE, Items.COBBLED_DEEPSLATE);
    GeneratedRecipe COBBLED_DEEPSLATE_SLAB = convert(Items.POLISHED_DEEPSLATE_SLAB, Items.COBBLED_DEEPSLATE_SLAB);
    GeneratedRecipe COBBLED_DEEPSLATE_STAIRS = convert(Items.POLISHED_DEEPSLATE_STAIRS, Items.COBBLED_DEEPSLATE_STAIRS);
    GeneratedRecipe COBBLED_DEEPSLATE_WALL = convert(Items.POLISHED_DEEPSLATE_WALL, Items.COBBLED_DEEPSLATE_WALL);

    GeneratedRecipe BASALT = convert(Items.POLISHED_BASALT, Items.BASALT);
    GeneratedRecipe PACKED_MUD = convert(Items.MUD, Items.PACKED_MUD);
    GeneratedRecipe WARPED_NYLIUM = convert(Items.WARPED_NYLIUM, Items.NETHERRACK);
    GeneratedRecipe CRIMSON_NYLIUM = convert(Items.CRIMSON_NYLIUM, Items.NETHERRACK);
    GeneratedRecipe NETHERRACK = convert(Items.MAGMA_BLOCK, Items.NETHERRACK);

    GeneratedRecipe POLISHED_ROSE_QUARTZ = convert(AllItems.ROSE_QUARTZ, AllItems.POLISHED_ROSE_QUARTZ);

    public SandingFanRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
