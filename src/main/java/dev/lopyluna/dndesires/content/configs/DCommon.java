package dev.lopyluna.dndesires.content.configs;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class DCommon extends ConfigBase {
    public final ConfigGroup common = group(0,
            "common", "Configs for the General Game");


    @Override
    public @NotNull String getName() {
        return "common";
    }
}
