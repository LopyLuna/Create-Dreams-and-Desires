package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan;

import com.simibubi.create.content.kinetics.fan.AirCurrentSound;
import net.minecraft.sounds.SoundEvent;

public class IndustrialAirCurrentSound extends AirCurrentSound {
    protected IndustrialAirCurrentSound(SoundEvent p_i46532_1_, float pitch) {
        super(p_i46532_1_, pitch);
        this.setPitch(pitch);
        volume = 0.0f;
        looping = false;
        delay = 0;
        relative = true;
    }
}
