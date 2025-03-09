package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllSoundEvents;
import dev.lopyluna.create_d2d.DesiresCreate;

public class DesiresSoundEvents {


    private static AllSoundEvents.SoundEntryBuilder create(String name) {
        return AllSoundEvents.create(DesiresCreate.loc(name));
    }
}
