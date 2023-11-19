package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DDGeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) DDConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, DDPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DDBiomeModifiers::bootstrap);

    public DDGeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DDCreate.MOD_ID));
    }

    @Override
    public String getName() {
        return DDCreate.NAME + " Generated Registry Entries";
    }
}
