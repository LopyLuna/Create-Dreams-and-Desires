package dev.lopyluna.dndesires.content.datagen.recipes;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@SuppressWarnings("unused")
public final class MixingRecipeGen extends com.simibubi.create.api.data.recipe.MixingRecipeGen {

    GeneratedRecipe CHOCOLATE = create("chocolate", b -> b.output(DesiresFluids.CHOCOLATE_MILKSHAKE.get(), 500)
            .require(Items.SUGAR).require(Items.SUGAR).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL)
            .require(AllItems.BAR_OF_CHOCOLATE).require(AllItems.BAR_OF_CHOCOLATE)
            .require(AllFluids.CHOCOLATE.get(), 250)
    );
    GeneratedRecipe VANILLA = create("vanilla", b -> b.output(DesiresFluids.VANILLA_MILKSHAKE.get(), 500)
            .require(Items.SUGAR).require(Items.SUGAR).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL)
            .require(items(Items.DANDELION, Items.BLUE_ORCHID)).require(items(Items.DANDELION, Items.BLUE_ORCHID))
            .require(Tags.Fluids.MILK, 250)
    );
    GeneratedRecipe STRAWBERRY = create("strawberry", b -> b.output(DesiresFluids.STRAWBERRY_MILKSHAKE.get(), 500)
            .require(Items.SUGAR).require(Items.SUGAR).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL)
            .require(Items.SWEET_BERRIES).require(Items.SWEET_BERRIES)
            .require(Tags.Fluids.MILK, 250)
    );
    GeneratedRecipe GLOWBERRY = create("glowberry", b -> b.output(DesiresFluids.GLOWBERRY_MILKSHAKE.get(), 500)
            .require(Items.SUGAR).require(Items.SUGAR).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL)
            .require(Items.GLOW_BERRIES).require(Items.GLOW_BERRIES)
            .require(Tags.Fluids.MILK, 250)
    );
    GeneratedRecipe PUMPKIN = create("pumpkin", b -> b.output(DesiresFluids.PUMPKIN_MILKSHAKE.get(), 500)
            .require(Items.SUGAR).require(Items.SUGAR).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL).require(Items.SNOWBALL)
            .require(Items.PUMPKIN).require(Items.PUMPKIN)
            .require(Tags.Fluids.MILK, 250)
    );
    GeneratedRecipe ASPHALT = create("asphalt", b -> b.output(DesiresBlocks.ASPHALT_BLOCK, 4)
            .require(AllPaletteStoneTypes.SCORCHIA.baseBlock.get()).require(AllPaletteStoneTypes.SCORCHIA.baseBlock.get())
            .require(Items.BLACKSTONE).require(Items.BLACKSTONE)
            .require(Items.SLIME_BALL).require(AllFluids.TEA.get(), 50)
            .requiresHeat(HeatCondition.HEATED)
    );

    public MixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
