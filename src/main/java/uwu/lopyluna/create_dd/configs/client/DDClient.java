package uwu.lopyluna.create_dd.configs.client;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.CClient;
import uwu.lopyluna.create_dd.configs.DDConfigBase;

public class DDClient extends DDConfigBase {

    public final ConfigGroup client = group(0, "client",
            DDClient.Comments.client);


    //no group
    public final ConfigFloat fanParticleDensity = f(.5f, 0, 1, "fanParticleDensity",
            DDClient.Comments.fanParticleDensity);

    @Override
    public String getName() {
        return "client";
    }


    private static class Comments {
        static String client = "Client-only settings - If you're looking for general settings, look inside your worlds serverconfig folder!";
        static String fanParticleDensity = "Higher density means more spawned particles.";
    }
}
