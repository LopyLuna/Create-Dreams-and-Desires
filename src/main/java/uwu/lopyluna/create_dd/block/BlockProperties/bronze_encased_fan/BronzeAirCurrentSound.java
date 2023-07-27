package uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan;

import com.simibubi.create.content.kinetics.fan.AirCurrentSound;
import net.minecraft.sounds.SoundEvent;

public class BronzeAirCurrentSound extends AirCurrentSound {

    private float pitch;

    public BronzeAirCurrentSound(SoundEvent p_i46532_1_, float pitch) {
        super(p_i46532_1_, pitch);
        volume = 0.01f;
        looping = true;
        delay = 0;
        relative = true;
    }


    @Override
    public void tick() {}
    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    @Override
    public void fadeIn(float maxVolume) {
        volume = Math.min(maxVolume, volume + .05f);
    }
    @Override
    public void fadeOut() {
        volume = Math.max(0, volume - .05f);
    }
    @Override
    public boolean isFaded() {
        return volume == 0;
    }

    @Override
    public float getPitch() {
        return pitch;
    }
    @Override
    public void stopSound() {
        stop();
    }

}
