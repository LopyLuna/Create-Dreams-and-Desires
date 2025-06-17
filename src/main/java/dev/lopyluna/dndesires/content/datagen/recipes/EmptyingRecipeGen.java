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
public final class EmptyingRecipeGen extends com.simibubi.create.api.data.recipe.EmptyingRecipeGen {

    GeneratedRecipe CHOCOLATE = create("chocolate", b -> b.output(DesiresFluids.CHOCOLATE_MILKSHAKE.get(), 250)
            .output(Items.GLASS_BOTTLE)
            .require(DesiresItems.CHOCOLATE_MILKSHAKE)
    );
    GeneratedRecipe VANILLA = create("vanilla", b -> b.output(DesiresFluids.VANILLA_MILKSHAKE.get(), 250)
            .output(Items.GLASS_BOTTLE)
            .require(DesiresItems.VANILLA_MILKSHAKE)
    );
    GeneratedRecipe STRAWBERRY = create("strawberry", b -> b.output(DesiresFluids.STRAWBERRY_MILKSHAKE.get(), 250)
            .output(Items.GLASS_BOTTLE)
            .require(DesiresItems.STRAWBERRY_MILKSHAKE)
    );
    GeneratedRecipe GLOWBERRY = create("glowberry", b -> b.output(DesiresFluids.GLOWBERRY_MILKSHAKE.get(), 250)
            .output(Items.GLASS_BOTTLE)
            .require(DesiresItems.GLOWBERRY_MILKSHAKE)
    );
    GeneratedRecipe PUMPKIN = create("pumpkin", b -> b.output(DesiresFluids.PUMPKIN_MILKSHAKE.get(), 250)
            .output(Items.GLASS_BOTTLE)
            .require(DesiresItems.PUMPKIN_MILKSHAKE)
    );

    public EmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
