package dev.lopyluna.dndesires.content.blocks.logistics.bore_block;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import net.minecraft.util.Mth;

public class BoreBlockMovementBehavior extends BlockBreakingMovementBehaviour {

    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption) lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption) lowerLimit = 2f;
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 350f, lowerLimit, 16f);
    }
}
