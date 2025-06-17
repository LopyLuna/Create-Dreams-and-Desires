package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllSoundEvents;
import dev.lopyluna.dndesires.DnDesires;

public class DesiresSoundEvents {


    private static AllSoundEvents.SoundEntryBuilder create(String name) {
        return AllSoundEvents.create(DnDesires.loc(name));
    }
}
