package uwu.lopyluna.create_dd.configs.server;

import uwu.lopyluna.create_dd.configs.DDConfigBase;

public class DDKinetics extends DDConfigBase {



    public final DDStress stressValues = nested(1, DDStress::new, DDKinetics.Comments.stress);

    @Override
    public String getName() {
        return "kinetics";
    }


    private static class Comments {
    static String stress = "Fine tune the kinetic stats of individual components";
    }
}
