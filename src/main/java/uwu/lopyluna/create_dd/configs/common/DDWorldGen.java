package uwu.lopyluna.create_dd.configs.common;

import uwu.lopyluna.create_dd.configs.DDConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.worldgen.DDOreFeatureConfigEntries;

public class DDWorldGen extends DDConfigBase {

    public static final int FORCED_UPDATE_VERSION = 2;

    public final ConfigBool disable = b(false, "disableWorldGen", DDWorldGen.Comments.disable);

    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
        super.registerAll(builder);
        DDOreFeatureConfigEntries.fillConfig(builder, DDCreate.MOD_ID);
    }

    @Override
    public String getName() {
        return "worldgen.v" + FORCED_UPDATE_VERSION;
    }

    private static class Comments {
        static String disable = "Prevents all worldgen added by " + DDCreate.NAME + " from taking effect";
    }

}
