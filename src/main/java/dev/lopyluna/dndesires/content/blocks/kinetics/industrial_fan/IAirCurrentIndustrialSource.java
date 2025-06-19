package dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan;

import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import dev.lopyluna.dndesires.content.configs.server.DKinetics;
import dev.lopyluna.dndesires.register.DesiresConfigs;
import net.minecraft.util.Mth;

public interface IAirCurrentIndustrialSource extends IAirCurrentSource {
    @Override
    default float getMaxDistance() {
        float speed = Math.abs(this.getSpeed());
        DKinetics config = DesiresConfigs.server().kinetics;
        float distanceFactor = Math.min(speed / config.fanRotationArgmax.get(), 1);
        float pushDistance = Mth.lerp(distanceFactor, 3, config.fanPushDistance.get());
        float pullDistance = Mth.lerp(distanceFactor, 3f, config.fanPullDistance.get());
        return this.getSpeed() > 0 ? pushDistance : pullDistance;
    }
}
