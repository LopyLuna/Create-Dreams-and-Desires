package uwu.lopyluna.create_dd.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperHeatingRecipe;

import java.util.Optional;
import java.util.function.Supplier;

public enum DDRecipesTypes implements IRecipeTypeInfo {

    SUPERHEATING(SuperHeatingRecipe::new),
    FREEZING(FreezingRecipe::new);

    
    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    private final Supplier<RecipeType<?>> type;


    DDRecipesTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = DDCreate.asResource(name);
        serializerObject = DDRecipesTypes.Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        @Nullable RegistryObject<RecipeType<?>> typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
    }

    DDRecipesTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }

    public static void register(IEventBus modEventBus) {
        ShapedRecipe.setCraftingSize(9, 9);
        DDRecipesTypes.Registers.SERIALIZER_REGISTER.register(modEventBus);
        DDRecipesTypes.Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DDCreate.MOD_ID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, DDCreate.MOD_ID);
    }
    
}
