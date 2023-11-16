package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.configs.server.DDKinetics;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public interface IndustrialAirCurrentSource {
    @Nullable
    IndustrialAirCurrent getAirCurrent();

    @Nullable
    Level getAirCurrentWorld();

    BlockPos getAirCurrentPos();

    float getSpeed();

    Direction getAirflowOriginSide();

    @Nullable
    Direction getAirFlowDirection();

    default float getMaxDistance() {
        float speed = Math.abs(this.getSpeed());
        DDKinetics config = DDConfigs.server().kinetics;
        float distanceFactor = Math.min(speed / config.fanRotationArgmax.get(), 1);
        float pushDistance = Mth.lerp(distanceFactor, 3, config.fanPushDistance.get());
        float pullDistance = Mth.lerp(distanceFactor, 3f, config.fanPullDistance.get());
        return this.getSpeed() > 0 ? pushDistance : pullDistance;
    }

    boolean isSourceRemoved();
}
