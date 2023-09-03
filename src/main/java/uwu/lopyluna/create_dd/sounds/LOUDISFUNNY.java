package uwu.lopyluna.create_dd.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_dd.DDcreate;

public class LOUDISFUNNY {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DDcreate.MOD_ID);

    public static RegistryObject<SoundEvent> ore_stone_break = registerSoundEvent("ore_stone_break");
    public static RegistryObject<SoundEvent> ore_stone_step = registerSoundEvent("ore_stone_step");
    public static RegistryObject<SoundEvent> ore_stone_place = registerSoundEvent("ore_stone_place");
    public static RegistryObject<SoundEvent> ore_stone_hit = registerSoundEvent("ore_stone_hit");
    public static RegistryObject<SoundEvent> ore_stone_fall = registerSoundEvent("ore_stone_fall");

    public static RegistryObject<SoundEvent> magical_metal_break = registerSoundEvent("magical_metal_break");
    public static RegistryObject<SoundEvent> magical_metal_step = registerSoundEvent("magical_metal_step");
    public static RegistryObject<SoundEvent> magical_metal_place = registerSoundEvent("magical_metal_place");
    public static RegistryObject<SoundEvent> magical_metal_hit = registerSoundEvent("magical_metal_hit");
    public static RegistryObject<SoundEvent> magical_metal_fall = registerSoundEvent("magical_metal_fall");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(DDcreate.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
