package uwu.lopyluna.create_dd.jei;

import com.simibubi.create.*;
import com.simibubi.create.compat.jei.*;
import com.simibubi.create.compat.jei.category.*;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import uwu.lopyluna.create_dd.DDcreate;
import uwu.lopyluna.create_dd.block.YIPPEE;
import uwu.lopyluna.create_dd.jei.fan.*;
import uwu.lopyluna.create_dd.recipes.BakingRecipesTypes;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DDcreateJEI implements IModPlugin {

    private static final ResourceLocation MOD_ID = new ResourceLocation(DDcreate.MOD_ID, "jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return MOD_ID;
    }


    public IIngredientManager ingredientManager;
    final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ALL.clear();

        ALL.add(builder(FreezingRecipe.class)
            .addTypedRecipes(BakingRecipesTypes.FREEZING::getType)
            .catalyst(YIPPEE.industrial_fan::get)
            .itemIcon(AllItems.PROPELLER.get(), Items.POWDER_SNOW_BUCKET)
            .emptyBackground(178, 72)
            .build("industrial_fan_freezing", FanFreezingCategory::new));

        ALL.add(builder(SuperheatingRecipe.class)
            .addTypedRecipes(BakingRecipesTypes.SUPERHEATING::getType)
            .catalyst(YIPPEE.industrial_fan::get)
            .itemIcon(AllItems.PROPELLER.get(), AllItems.BLAZE_CAKE.get())
            .emptyBackground(178, 72)
            .build("industrial_fan_superheating", FanSuperheatingCategory::new));

		ALL.forEach(registration::addRecipeCategories);
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        ALL.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ALL.forEach(c -> c.registerCatalysts(registration));

        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "pressing")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.hydraulic_press.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "automatic_packing")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.hydraulic_press.get()), type));

        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "sawing")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.BRONZE_SAW.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "block_cutting")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.BRONZE_SAW.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "wood_cutting")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.BRONZE_SAW.get()), type));

        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "fan_washing")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.industrial_fan.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "fan_smoking")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.industrial_fan.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "fan_blasting")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.industrial_fan.get()), type));
        registration.getJeiHelpers().getRecipeType(new ResourceLocation("create", "fan_haunting")).ifPresent(type ->
                registration.addRecipeCatalyst(new ItemStack(YIPPEE.industrial_fan.get()), type));
    }

    private <T extends Recipe<?>> DDcreateJEI.CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    private static class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = cRecipes -> true;

        private IDrawable background;
        private IDrawable icon;

        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get()
                    .asItem()));
        }

        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder<T> itemIcon(ItemLike item1, ItemLike item2) {
            icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }

        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int width, int height) {
            background(new EmptyBackground(width, height));
            return this;
        }

        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier;
            if (predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<T> recipes = new ArrayList<>();
                    for (Consumer<List<T>> consumer : recipeListConsumers)
                        consumer.accept(recipes);
                    return recipes;
                };
            } else {
                recipesSupplier = Collections::emptyList;
            }

            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                    new mezz.jei.api.recipe.RecipeType<>(DDcreate.asResource(name), recipeClass),
                    Component.translatable(DDcreate.MOD_ID + ".recipe." + name), background, icon, recipesSupplier, catalysts);
            return factory.create(info);
        }
    }
}