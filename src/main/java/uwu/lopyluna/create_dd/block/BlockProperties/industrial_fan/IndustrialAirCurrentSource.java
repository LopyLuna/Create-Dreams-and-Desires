package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan;

import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CKinetics;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public interface IndustrialAirCurrentSource extends IAirCurrentSource {
    @Nullable
    IndustrialAirCurrent getBAirCurrent();

    @Nullable
    @Override
    Level getAirCurrentWorld();
    @Override
    BlockPos getAirCurrentPos();
    @Override
    float getSpeed();
    @Override
    Direction getAirflowOriginSide();
    @Override
    @Nullable
    Direction getAirFlowDirection();
    @Override
    default float getMaxDistance() {
        float speed = Math.abs(this.getSpeed());
        CKinetics config = AllConfigs.server().kinetics;
        float distanceFactor = Math.min(speed / config.fanRotationArgmax.get(), 1);
        float pushDistance = Mth.lerp(distanceFactor, 3, config.fanPushDistance.get());
        float pullDistance = Mth.lerp(distanceFactor, 3f, config.fanPullDistance.get());
        return this.getSpeed() > 0 ? pushDistance : pullDistance;
    }
    @Override
    boolean isSourceRemoved();
}
