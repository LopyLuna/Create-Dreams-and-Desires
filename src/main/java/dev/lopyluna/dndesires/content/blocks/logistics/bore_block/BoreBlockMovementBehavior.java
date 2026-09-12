package dev.lopyluna.dndesires.content.blocks.logistics.bore_block;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import dev.ryanhcode.sable.ActiveSableCompanion;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class BoreBlockMovementBehavior extends BlockBreakingMovementBehaviour {

    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = lowerLimit(context);
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 350f, lowerLimit, 16f);
    }

    private float lowerLimit(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption) lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption) lowerLimit = 2f;
        return lowerLimit;
    }

    @Override
    public boolean isActive(MovementContext context) {
        return super.isActive(context) && Math.abs(context.getAnimationSpeed())>lowerLimit(context)/4;
    }
}
