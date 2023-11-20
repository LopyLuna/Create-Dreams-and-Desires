package uwu.lopyluna.create_dd;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import uwu.lopyluna.create_dd.worldgen.DDGeneratedEntriesProvider;

import java.util.concurrent.CompletableFuture;

public class DDCreateData {

    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();



        if (event.includeServer()) {
            DDGeneratedEntriesProvider generatedEntriesProvider = new DDGeneratedEntriesProvider(output, lookupProvider);
            lookupProvider = generatedEntriesProvider.getRegistryProvider();
            generator.addProvider(true, generatedEntriesProvider);
        }

    }


    private static void addExtraRegistrateData() {

    }
}