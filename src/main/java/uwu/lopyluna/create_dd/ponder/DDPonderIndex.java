package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.simibubi.create.infrastructure.ponder.DebugScenes;
import com.simibubi.create.infrastructure.ponder.PonderIndex;
import com.simibubi.create.infrastructure.ponder.scenes.KineticsScenes;
import com.simibubi.create.infrastructure.ponder.scenes.MechanicalDrillScenes;
import uwu.lopyluna.create_dd.DDcreate;
import uwu.lopyluna.create_dd.block.YIPPEE;

public class DDPonderIndex extends PonderIndex {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(DDcreate.MOD_ID);
    static final PonderRegistrationHelper CREATE_HELPER = new PonderRegistrationHelper(Create.ID);

    public static final boolean REGISTER_DEBUG_SCENES = false;

    public static void register() {
        HELPER.forComponents(YIPPEE.hydraulic_press)
                .addStoryBoard("hydraulic_press/pressing", DDProcessingScenes::bulk_pressing)
                .addStoryBoard("hydraulic_press/compacting", DDProcessingScenes::bronze_compacting);
        HELPER.forComponents(AllBlocks.BASIN)
                .addStoryBoard("hydraulic_press/compacting", DDProcessingScenes::bronze_compacting);

        CREATE_HELPER.forComponents(YIPPEE.BRONZE_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(YIPPEE.RADIANT_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(YIPPEE.SHADOW_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);

        CREATE_HELPER.addStoryBoard(YIPPEE.REVERSED_GEARSHIFT, "gearshift", KineticsScenes::gearshift, AllPonderTags.KINETIC_RELAYS);

        if (REGISTER_DEBUG_SCENES)
            DebugScenes.registerAll();
    }
}
