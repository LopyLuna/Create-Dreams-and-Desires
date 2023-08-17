package uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan;

import com.simibubi.create.content.kinetics.fan.AirCurrentSound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class BronzeAirCurrentSound extends AirCurrentSound {
    protected BronzeAirCurrentSound(SoundEvent p_i46532_1_, float pitch) {
        super(p_i46532_1_, pitch);
        this.setPitch(pitch);
        volume = 0.01f;
        looping = true;
        delay = 0;
        relative = true;
    }
}
