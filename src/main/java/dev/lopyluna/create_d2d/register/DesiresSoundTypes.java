package dev.lopyluna.create_d2d.register;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class DesiresSoundTypes {

    public static SoundType CRACKLE_STONE = new DeferredSoundType(0.9f, 0.7f,
            () -> SoundEvents.GILDED_BLACKSTONE_BREAK,
            () -> SoundEvents.GILDED_BLACKSTONE_STEP,
            () -> SoundEvents.GILDED_BLACKSTONE_PLACE,
            () -> SoundEvents.GILDED_BLACKSTONE_HIT,
            () -> SoundEvents.GILDED_BLACKSTONE_FALL
    );
}
