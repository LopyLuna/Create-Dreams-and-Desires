package dev.lopyluna.create_d2d;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import dev.lopyluna.create_d2d.content.datagen.DatagenTags;
import dev.lopyluna.create_d2d.content.datagen.recipes.MechanicalCraftingGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static dev.lopyluna.create_d2d.DesiresCreate.MOD_ID;
import static dev.lopyluna.create_d2d.DesiresCreate.REG;

public class DesiresDatagen {
    @SuppressWarnings("all")
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new MechanicalCraftingGen(output, lookupProvider));

        event.getGenerator().addProvider(true, REG.setDataProvider(new RegistrateDataProvider(REG, MOD_ID, event)));
    }

    private static void addExtraRegistrateData() {
        DatagenTags.addGenerators();

        //REG.addDataGenerator(ProviderType.LANG, provider -> {
        //    BiConsumer<String, String> langConsumer = provider::add;
//
        //    provideDefaultLang("interface", langConsumer);
        //    provideDefaultLang("tooltips", langConsumer);
        //});
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/create_d2d/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
