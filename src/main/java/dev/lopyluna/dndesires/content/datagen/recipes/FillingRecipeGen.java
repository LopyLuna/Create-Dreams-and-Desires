package dev.lopyluna.dndesires.content.datagen.recipes;

import dev.lopyluna.dndesires.register.DesiresFluids;
import dev.lopyluna.dndesires.register.DesiresItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class FillingRecipeGen extends com.simibubi.create.api.data.recipe.FillingRecipeGen {

    GeneratedRecipe CHOCOLATE = create("chocolate", b -> b.require(DesiresFluids.CHOCOLATE_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(DesiresItems.CHOCOLATE_MILKSHAKE)
    );
    GeneratedRecipe VANILLA = create("vanilla", b -> b.require(DesiresFluids.VANILLA_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(DesiresItems.VANILLA_MILKSHAKE)
    );
    GeneratedRecipe STRAWBERRY = create("strawberry", b -> b.require(DesiresFluids.STRAWBERRY_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(DesiresItems.STRAWBERRY_MILKSHAKE)
    );
    GeneratedRecipe GLOWBERRY = create("glowberry", b -> b.require(DesiresFluids.GLOWBERRY_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(DesiresItems.GLOWBERRY_MILKSHAKE)
    );
    GeneratedRecipe PUMPKIN = create("pumpkin", b -> b.require(DesiresFluids.PUMPKIN_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(DesiresItems.PUMPKIN_MILKSHAKE)
    );

    public FillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
