package dev.lopyluna.dndesires.content.datagen.recipes;

import com.google.common.base.Supplier;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeBuilder;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.datagen.DesiresRecipeProvider;
import dev.lopyluna.dndesires.register.DesiresItems;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

import static dev.lopyluna.dndesires.register.DesiresTags.commonItemTag;

@SuppressWarnings("unused")
public class MechanicalCraftingGen extends DesiresRecipeProvider {

    GeneratedRecipe GATLING_BREAKER = create(DesiresItems.GATLING_BREAKER::get).recipe(b -> b
            .key('D', Ingredient.of(Tags.Items.GEMS_DIAMOND))
            .key('N', Ingredient.of(Tags.Items.INGOTS_NETHERITE))
            .key('O', Ingredient.of(Tags.Items.OBSIDIANS))
            .key('P', Ingredient.of(Tags.Items.GEMS_PRISMARINE))
            .key('I', Ingredient.of(commonItemTag("plates/iron")))
            .key('M', AllItems.PRECISION_MECHANISM)
            .key('A', AllItems.ANDESITE_ALLOY)
            .patternLine("  AIII")
            .patternLine("ONMDPP")
            .patternLine("OOOA  ")
    );
    
    public MechanicalCraftingGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(result);
    }

    class GeneratedRecipeBuilder {

        private String suffix;
        private final Supplier<ItemLike> result;
        private int amount;

        public GeneratedRecipeBuilder(Supplier<ItemLike> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
            return register(output -> {
                var b = builder.apply(MechanicalCraftingRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = DnDesires.loc("mechanical_crafting/" + RegisteredObjectsHelper.getKeyOrThrow(result.get().asItem()).getPath() + suffix);
                b.build(output, location);
            });
        }
    }
}
