package dev.lopyluna.dndesires.register;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.fan_types.DragonBreathingType;
import dev.lopyluna.dndesires.content.fan_types.FreezingType;
import dev.lopyluna.dndesires.content.fan_types.SandingType;
import dev.lopyluna.dndesires.content.fan_types.SeethingType;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.ApiStatus;

public class DesiresFanProcessingTypes {
    public static final DragonBreathingType DRAGON_BREATHING_TYPE = register("dragon_breathing", new DragonBreathingType());
    public static final SandingType SANDING_TYPE = register("sanding", new SandingType());
    public static final FreezingType FREEZING_TYPE = register("freezing", new FreezingType());
    public static final SeethingType SEETHING_TYPE = register("seething", new SeethingType());

    static {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("DRAGON_BREATHING", DRAGON_BREATHING_TYPE);
        map.put("SANDING", SANDING_TYPE);
        map.put("FREEZING", FREEZING_TYPE);
        map.put("SEETHING", SEETHING_TYPE);
        map.trim();
    }

    private static <T extends FanProcessingType> T register(String name, T type) {
        return Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, DnDesires.loc(name), type);
    }

    @ApiStatus.Internal
    public static void init() {
    }
}
