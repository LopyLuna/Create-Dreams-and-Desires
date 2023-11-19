package uwu.lopyluna.create_dd.configs.common;

import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.DDCreate;

public class DDWorldGen extends DDConfigBase {

    public final ConfigBool disable = b(false, "disableWorldGen", DDWorldGen.Comments.disable);

    @Override
    public String getName() {
        return "worldgen";
    }

    private static class Comments {
        static String disable = "Prevents all worldgen added by " + DDCreate.NAME + " from taking effect";
    }

}
