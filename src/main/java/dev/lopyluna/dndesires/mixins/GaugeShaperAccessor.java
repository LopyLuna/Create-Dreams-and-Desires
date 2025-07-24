package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.kinetics.gauge.GaugeShaper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GaugeShaper.class)
public interface GaugeShaperAccessor {
    @Invoker("make")
    static GaugeShaper make() {
        throw new AssertionError();
    }
}
