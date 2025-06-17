package dev.lopyluna.dndesires.content.propagators;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public interface RotationPropagation {
    default Float getRotationSpeedModifier(KineticBlockEntity from, KineticBlockEntity to) { return null; }
    default Float getConveyedSpeed(KineticBlockEntity from, KineticBlockEntity to) { return null; }
    default boolean propagateNewSource(KineticBlockEntity currentTE) { return false; }
    default boolean handleRemoved(Level level, BlockPos pos, KineticBlockEntity be) { return false; }
    default List<BlockPos> getPotentialNeighbourLocations(KineticBlockEntity be, List<BlockPos> original) { return null; }
}
