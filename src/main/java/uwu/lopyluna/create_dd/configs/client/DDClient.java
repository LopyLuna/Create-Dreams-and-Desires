package uwu.lopyluna.create_dd.configs.client;

import uwu.lopyluna.create_dd.configs.DDConfigBase;

public class DDClient extends DDConfigBase {

    public final ConfigGroup client = group(0, "client",
            DDClient.Comments.client);



    @Override
    public String getName() {
        return "client";
    }


    private static class Comments {
        static String client = "Client-only settings - If you're looking for general settings, look inside your worlds serverconfig folder!";
    }
}
