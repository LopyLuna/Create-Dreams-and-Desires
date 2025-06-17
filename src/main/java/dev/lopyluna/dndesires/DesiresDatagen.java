package dev.lopyluna.dndesires;

import com.google.gson.JsonElement;
import com.simibubi.create.foundation.utility.FilesHelper;
import dev.lopyluna.dndesires.content.datagen.DatagenTags;
import dev.lopyluna.dndesires.content.datagen.DesiresRecipeProvider;
import dev.lopyluna.dndesires.content.datagen.recipes.MechanicalCraftingGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

public class DesiresDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(MOD_ID)) addExtraRegistrateData();
    }

    @SuppressWarnings("all")
    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(MOD_ID)) return;
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new MechanicalCraftingGen(output, lookupProvider));


        if (event.includeServer()) DesiresRecipeProvider.registerAllProcessing(generator, output, lookupProvider);
    }


    private static void addExtraRegistrateData() {
        DatagenTags.addGenerators();
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        var path = "assets/"+ MOD_ID +"/lang/default/" + fileName + ".json";
        var jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) consumer.accept(entry.getKey(), entry.getValue().getAsString());
    }
}
