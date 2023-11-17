package uwu.lopyluna.create_dd.compat;

import uwu.lopyluna.create_dd.compat.registries.TinkersCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompatibleManager {
    public static final List<CompatibleMod> MODS;

    public static final TinkersCompat TIC;


    static {
        List<CompatibleMod> mods = new ArrayList<>();
        mods.add(TIC = new TinkersCompat());

        for (CompatibleMod mod : mods) {
            mod.tryLoad();
        }

        MODS = Collections.unmodifiableList(mods);
    }

    public static void visit() {

    }
}