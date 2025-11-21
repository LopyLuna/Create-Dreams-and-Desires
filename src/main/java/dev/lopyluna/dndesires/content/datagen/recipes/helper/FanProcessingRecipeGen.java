package dev.lopyluna.dndesires.content.datagen.recipes.helper;

import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class FanProcessingRecipeGen<R extends StandardProcessingRecipe<?>> extends StandardProcessingRecipeGen<R> {

    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> result, float chance) {
        return convert(() -> Ingredient.of(item), result::get, chance);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> result) {
        return convert(() -> Ingredient.of(item), result::get);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> result, float chance, int amount) {
        return convert(() -> Ingredient.of(item), result::get, chance, amount);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> result, int amount) {
        return convert(() -> Ingredient.of(item), result::get, amount);
    }

    public GeneratedRecipe convert(ItemLike input, ItemLike result, float chance) {
        return convert(() -> Ingredient.of(input), () -> result, chance);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(ItemLike input, ItemLike result, float chance, int amount) {
        return convert(() -> Ingredient.of(input), () -> result, chance, amount);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike result, int amount) {
        return convert(() -> Ingredient.of(input), () -> result, amount);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result, float chance) {
        return convert(input, result, chance, 1);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return convert(input, result, 1);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result, float chance, int amount) {
        return create(asResource(getPath(result)+"_from_"+getPath(() -> input.get().getItems()[0].getItem())), p -> p.withItemIngredients(input.get()).output(chance, result.get(), amount));
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result, int amount) {
        return create(asResource(getPath(result)+"_from_"+getPath(() -> input.get().getItems()[0].getItem())), p -> p.withItemIngredients(input.get()).output(result.get(), amount));
    }


    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, float mChance, ItemEntry<Item> secondary, float sChance) {
        return convert(() -> Ingredient.of(item), main::get, mChance, secondary::get, sChance);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, ItemEntry<Item> secondary, float sChance) {
        return convert(() -> Ingredient.of(item), main::get, secondary::get, sChance);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, ItemEntry<Item> secondary) {
        return convert(() -> Ingredient.of(item), main::get, secondary::get);
    }

    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, float mChance, int mAmount, ItemEntry<Item> secondary, float sChance, int sAmount) {
        return convert(() -> Ingredient.of(item), main::get, mChance, mAmount, secondary::get, sChance, sAmount);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, int mAmount, ItemEntry<Item> secondary, float sChance, int sAmount) {
        return convert(() -> Ingredient.of(item), main::get, mAmount, secondary::get, sChance, sAmount);
    }
    public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> main, int mAmount, ItemEntry<Item> secondary, int sAmount) {
        return convert(() -> Ingredient.of(item), main::get, mAmount, secondary::get, sAmount);
    }

    public GeneratedRecipe convert(ItemLike input, ItemLike main, float mChance, ItemLike secondary, float sChance) {
        return convert(() -> Ingredient.of(input), () -> main, mChance, () -> secondary, sChance);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike main, ItemLike secondary, float sChance) {
        return convert(() -> Ingredient.of(input), () -> main, () -> secondary, sChance);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike main, ItemLike secondary) {
        return convert(() -> Ingredient.of(input), () -> main, () -> secondary);
    }

    public GeneratedRecipe convert(ItemLike input, ItemLike main, float mChance, int mAmount, ItemLike secondary, float sChance, int sAmount) {
        return convert(() -> Ingredient.of(input), () -> main, mChance, mAmount, () -> secondary, sChance, sAmount);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike main, int mAmount, ItemLike secondary, float sChance, int sAmount) {
        return convert(() -> Ingredient.of(input), () -> main, mAmount, () -> secondary, sChance, sAmount);
    }
    public GeneratedRecipe convert(ItemLike input, ItemLike main, int mAmount, ItemLike secondary, int sAmount) {
        return convert(() -> Ingredient.of(input), () -> main, mAmount, () -> secondary, sAmount);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, float mChance, Supplier<ItemLike> secondary, float sChance) {
        return convert(input, main, mChance, 1, secondary, sChance, 1);
    }
    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, Supplier<ItemLike> secondary, float sChance) {
        return convert(input, main, 1, secondary, sChance, 1);
    }
    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, Supplier<ItemLike> secondary) {
        return convert(input, main, 1, secondary, 1);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, float mChance, int mAmount, Supplier<ItemLike> secondary, float sChance, int sAmount) {
        return create(asResource(getPath(main)+"_"+getPath(secondary)), p -> p.withItemIngredients(input.get()).output(mChance, main.get(), mAmount).output(sChance, secondary.get(), sAmount));
    }
    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, int mAmount, Supplier<ItemLike> secondary, float sChance, int sAmount) {
        return create(asResource(getPath(main)+"_"+getPath(secondary)), p -> p.withItemIngredients(input.get()).output(main.get(), mAmount).output(sChance, secondary.get(), sAmount));
    }
    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> main, int mAmount, Supplier<ItemLike> secondary, int sAmount) {
        return create(asResource(getPath(main)+"_"+getPath(secondary)), p -> p.withItemIngredients(input.get()).output(main.get(), mAmount).output(secondary.get(), sAmount));
    }


    public GeneratedRecipe crushedOre(ItemLike crushed, ItemLike main, ItemLike secondary, float secondaryChance) {
        return crushedOre(() -> crushed, () -> main, 1, () -> secondary, 1, secondaryChance);
    }

    public GeneratedRecipe crushedOre(ItemLike crushed, ItemLike main, ItemLike secondary, int secondaryAmount, float secondaryChance) {
        return crushedOre(() -> crushed, () -> main, 1, () -> secondary, secondaryAmount, secondaryChance);
    }

    public GeneratedRecipe crushedOre(ItemLike crushed, ItemLike main, int amount, ItemLike secondary, float secondaryChance) {
        return crushedOre(() -> crushed, () -> main, amount, () -> secondary, 1, secondaryChance);
    }

    public GeneratedRecipe crushedOre(Supplier<ItemLike> crushed, Supplier<ItemLike> main, int amount, Supplier<ItemLike> secondary, int secondaryAmount, float secondaryChance) {
        return create(getPath(crushed), b -> b.withItemIngredients(Ingredient.of(crushed.get())).output(main.get(), amount).output(secondaryChance, secondary.get(), secondaryAmount));
    }

    public GeneratedRecipe moddedCrushedOre(CommonMetal metal, Supplier<ItemLike> crushed, String type, float secondaryChance) {
        return moddedCrushedOre(metal, crushed, type, 1, type, 1, secondaryChance);
    }

    public GeneratedRecipe moddedCrushedOre(CommonMetal metal, Supplier<ItemLike> crushed, String mainType, String secondaryType, float secondaryChance) {
        return moddedCrushedOre(metal, crushed, mainType, 1, secondaryType, 1, secondaryChance);
    }

    public GeneratedRecipe moddedCrushedOre(CommonMetal metal, Supplier<ItemLike> crushed, String mainType, int amount, String secondaryType, int secondaryAmount, float secondaryChance) {
        for (var mod : metal.mods) {
            var metalName = metal.getName(mod);
            var main = getType(mod, metalName, mainType);
            var secondary = getType(mod, metalName, secondaryType);
            create(mod.getId() + "/" + getPath(crushed), b -> b.withItemIngredients(Ingredient.of(crushed.get()))
                    .output(1, main, amount).output(secondaryChance, secondary, secondaryAmount).whenModLoaded(mod.getId()));
        }
        return null;
    }

    public static ResourceLocation getType(Mods mod, String name, String type) {
        return switch (type) {
            case "ingot" -> mod.ingotOf(name);
            case "nugget" -> mod.nuggetOf(name);
            case "ore" -> mod.oreOf(name);
            case "deepslate_ore" -> mod.deepslateOreOf(name);
            default -> mod.asResource(name);
        };
    }

    public GeneratedRecipe moddedCrushedOre(ItemEntry<? extends Item> crushed, CommonMetal metal, String type, float chance, int amount) {
        for (var mod : metal.mods) {
            var metalName = metal.getName(mod);
            var result = getType(mod, metalName, type);
            create(mod.getId() + "/" + crushed.getId().getPath(), b -> b.withItemIngredients(Ingredient.of(crushed::get))
                    .output(chance, result, amount).whenModLoaded(mod.getId()));
        }
        return null;
    }

    public GeneratedRecipe simpleModded(Mods mod, String input, String output) {
        return create(mod.getId() + "/" + output, b -> b.require(mod, input).output(mod, output).whenModLoaded(mod.getId()));
    }

    public static String getPath(Supplier<ItemLike> item) {
        return RegisteredObjectsHelper.getKeyOrThrow(item.get().asItem()).getPath();
    }

    public FanProcessingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }
}
