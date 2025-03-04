package dev.lopyluna.create_d2d.content.configs.server;

import dev.lopyluna.create_d2d.content.configs.server.kinetics.DStress;
import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DKinetics extends ConfigBase {

    public final DStress stressValues = nested(1, DStress::new, "Fine tune the kinetic stats of individual components");

    @Override
    public @NotNull String getName() {
        return "kinetics";
    }
}
