package uwu.lopyluna.create_dd.configs.common;

import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.DDCreate;

public class DDCommon extends DDConfigBase {

    public final DDWorldGen worldGen = nested(0, DDWorldGen::new, DDCommon.Comments.worldGen);

    @Override
    public String getName() {
        return "common";
    }

    private static class Comments {
        static String worldGen = "Modify " + DDCreate.NAME + " impact on your terrain";
    }

}
