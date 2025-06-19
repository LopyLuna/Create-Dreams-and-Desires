package dev.lopyluna.dndesires.register;

import com.mojang.serialization.Codec;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.recipes.*;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public enum DesiresRecipeTypes implements IRecipeTypeInfo, StringRepresentable {
    HYDRAULIC_COMPACTING(HydraulicRecipe::new),
    DRAGON_BREATHING(DragonBreathingRecipe::new),
    SANDING(SandingRecipe::new),
    FREEZING(FreezingRecipe::new),
    SEETHING(SeethingRecipe::new)
    ;

    public final ResourceLocation id;
    public final Supplier<RecipeSerializer<?>> serializerSupplier;
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializerObject;
    @Nullable
    private final DeferredHolder<RecipeType<?>, RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    public static final Codec<DesiresRecipeTypes> CODEC = StringRepresentable.fromEnum(DesiresRecipeTypes::values);

    DesiresRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        var name = Lang.asId(name());
        id = DnDesires.loc(name);
        this.serializerSupplier = serializerSupplier;
        serializerObject = DesiresRecipeTypes.Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        if (registerType) {
            typeObject = DesiresRecipeTypes.Registers.TYPE_REGISTER.register(name, typeSupplier);
            type = typeObject;
        } else {
            typeObject = null;
            type = typeSupplier;
        }
    }

    DesiresRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        var name = Lang.asId(name());
        id = DnDesires.loc(name);
        this.serializerSupplier = serializerSupplier;
        serializerObject = DesiresRecipeTypes.Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = DesiresRecipeTypes.Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
    }

    DesiresRecipeTypes(StandardProcessingRecipe.Factory<?> processingFactory) {
        this(() -> new StandardProcessingRecipe.Serializer<>(processingFactory));
    }

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        ShapedRecipePattern.setCraftingSize(9, 9);
        DesiresRecipeTypes.Registers.SERIALIZER_REGISTER.register(modEventBus);
        DesiresRecipeTypes.Registers.TYPE_REGISTER.register(modEventBus);
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
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {
        return (RecipeType<R>) type.get();
    }

    public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> find(I inv, Level level) {
        return level.getRecipeManager().getRecipeFor(getType(), inv, level);
    }

    @Override
    public @NotNull String getSerializedName() {
        return id.toString();
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, DnDesires.MOD_ID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, DnDesires.MOD_ID);
    }
}
