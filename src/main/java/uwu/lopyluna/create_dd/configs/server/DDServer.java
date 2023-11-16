package uwu.lopyluna.create_dd.configs.server;

import uwu.lopyluna.create_dd.configs.DDConfigBase;
import uwu.lopyluna.create_dd.DDCreate;

@SuppressWarnings({"all"})
public class DDServer extends DDConfigBase {

    public final ConfigGroup infrastructure = group(0, "infrastructure", DDServer.Comments.infrastructure);

    //kienetic group
    public final DDKinetics kinetics = nested(0, DDKinetics::new, DDServer.Comments.kinetics);

    //recipe group
    public final DDRecipes recipes = nested(0, DDRecipes::new, DDServer.Comments.recipe);



    @Override
    public String getName() {
        return "server";
    }

    private static class Comments {
        static String infrastructure = "The Backbone of " + DDCreate.NAME;
        static String recipe = "Recipes";
        static String kinetics = "Parameters and abilities of " + DDCreate.NAME + " kinetic mechanisms";

    }
}