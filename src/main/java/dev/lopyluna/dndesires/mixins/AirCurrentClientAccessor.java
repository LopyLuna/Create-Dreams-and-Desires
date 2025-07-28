package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = AirCurrent.Client.class, remap = false)
public interface AirCurrentClientAccessor {
    @Invoker("enableClientPlayerSound")
    static void enableClientPlayerSound(Entity e, float maxVolume) {
        throw new AssertionError();
    }
}
