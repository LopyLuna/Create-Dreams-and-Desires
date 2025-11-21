package dev.lopyluna.dndesires.content.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class ItemApplicationRecipeGen extends com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen {
    GeneratedRecipe OVERBURDEN = create("overburden_casing", b -> b.require(AllBlocks.ANDESITE_CASING)
            .require(AllItems.IRON_SHEET)
            .output(DesiresBlocks.OVERBURDEN_CASING)
    );
    GeneratedRecipe INDUSTRIAL = create("industrial_casing", b -> b.require(AllBlocks.COPPER_CASING)
            .require(AllItems.ZINC_INGOT)
            .output(DesiresBlocks.INDUSTRIAL_CASING)
    );

    public ItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }
    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
