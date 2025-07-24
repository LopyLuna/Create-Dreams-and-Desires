package dev.lopyluna.dndesires.register;

import com.google.gson.JsonObject;
import com.simibubi.create.AllSoundEvents;
import dev.lopyluna.dndesires.DnDesires;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class DesiresSoundEvents {
    public static final Map<ResourceLocation, AllSoundEvents.SoundEntry> ALL = new HashMap<>();

    public static final AllSoundEvents.SoundEntry FLUID_HATCH = create("fluid_hatch").subtitle("Fluid Hatch opens")
			.playExisting(SoundEvents.COPPER_DOOR_OPEN, .25f, .7f)
			.playExisting(SoundEvents.COPPER_PLACE, .75f, 1.15f)
			.category(SoundSource.BLOCKS)
			.build();

    private static DnDesiresSoundEntryBuilder create(String name) {
        return create(DnDesires.loc(name));
    }
    public static DnDesiresSoundEntryBuilder create(ResourceLocation id) {
        return new DnDesiresSoundEntryBuilder(id);
    }

    public static void prepare() {
        for (var entry : ALL.values()) entry.prepare();
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, helper -> ALL.values().forEach(entry -> entry.register(helper)));
    }

    public static void provideLang(BiConsumer<String, String> consumer) {
        for (var entry : ALL.values()) if (entry.hasSubtitle()) consumer.accept(entry.getSubtitleKey(), entry.getSubtitle());
    }

    public static SoundEntryProvider provider(DataGenerator generator) {
        return new SoundEntryProvider(generator);
    }

    public static class SoundEntryProvider implements DataProvider {
        private final PackOutput output;
        public SoundEntryProvider(DataGenerator generator) {
            output = generator.getPackOutput();
        }
        @Override
        public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
            return generate(output.getOutputFolder(), cache);
        }
        @Override
        public @NotNull String getName() {
            return "DnDesires's Custom Sounds";
        }
        public CompletableFuture<?> generate(Path path, CachedOutput cache) {
            path = path.resolve("assets/dndesires");
            var json = new JsonObject();
            ALL.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> entry.getValue().write(json));
            return DataProvider.saveStable(cache, json, path.resolve("sounds.json"));
        }
    }

    public static class DnDesiresSoundEntryBuilder extends AllSoundEvents.SoundEntryBuilder {
        public DnDesiresSoundEntryBuilder(ResourceLocation id) {
            super(id);
        }

        @Override
        public DnDesiresSoundEntryBuilder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder attenuationDistance(int distance) {
            this.attenuationDistance = distance;
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder noSubtitle() {
            this.subtitle = null;
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder category(SoundSource category) {
            this.category = category;
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder addVariant(String name) {
            return addVariant(DnDesires.loc(name));
        }
        @Override
        public DnDesiresSoundEntryBuilder addVariant(ResourceLocation id) {
            variants.add(id);
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder playExisting(Supplier<SoundEvent> event, float volume, float pitch) {
            wrappedEvents.add(new AllSoundEvents.ConfiguredSoundEvent(event, volume, pitch));
            return this;
        }
        @Override
        public DnDesiresSoundEntryBuilder playExisting(SoundEvent event, float volume, float pitch) {
            return playExisting(() -> event, volume, pitch);
        }
        @Override
        public DnDesiresSoundEntryBuilder playExisting(SoundEvent event) {
            return playExisting(event, 1, 1);
        }
        @Override
        public DnDesiresSoundEntryBuilder playExisting(Holder<SoundEvent> event) {
            return playExisting(event::value, 1, 1);
        }

        @Override
        public AllSoundEvents.SoundEntry build() {
            AllSoundEvents.SoundEntry entry = super.build();
            ALL.put(entry.getId(), entry);
            AllSoundEvents.ALL.remove(entry.getId());
            return entry;
        }

    }
}
