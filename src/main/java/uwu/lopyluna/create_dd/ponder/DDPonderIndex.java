package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.simibubi.create.infrastructure.ponder.DebugScenes;
import com.simibubi.create.infrastructure.ponder.scenes.*;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class DDPonderIndex {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(DDCreate.MOD_ID);
    static final PonderRegistrationHelper CREATE_HELPER = new PonderRegistrationHelper(Create.ID);

    public static final boolean REGISTER_DEBUG_SCENES = false;

    public static void register() {
        HELPER.forComponents(DDBlocks.hydraulic_press).addStoryBoard("hydraulic_press", DDProcessingScenes::bulk_pressing, AllPonderTags.KINETIC_APPLIANCES);
        HELPER.forComponents(DDBlocks.BRONZE_SAW).addStoryBoard("bronze_saw", DDProcessingScenes::processing, AllPonderTags.KINETIC_APPLIANCES);
        HELPER.forComponents(DDBlocks.FLYWHEEL).addStoryBoard("furnace_engine", DDProcessingScenes::flywheel);
        HELPER.forComponents(DDBlocks.FURNACE_ENGINE).addStoryBoard("furnace_engine", DDProcessingScenes::furnaceEngine);
        HELPER.forComponents(DDBlocks.blasting_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.smoking_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.haunting_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.splashing_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.superheating_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.freezing_sail).addStoryBoard("fan_sails", DDProcessingScenes::fan_sails);
        HELPER.forComponents(DDBlocks.ACCELERATOR_MOTOR).addStoryBoard("accelerator_motor", DDProcessingScenes::Motors, AllPonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.KINETIC_MOTOR).addStoryBoard("kinetic_motor", DDProcessingScenes::Motors, AllPonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.cogCrank).addStoryBoard("cog_crank", DDProcessingScenes::cogCrank, AllPonderTags.KINETIC_SOURCES);
        HELPER.forComponents(DDBlocks.industrial_fan).addStoryBoard("industrial_fan_source", DDProcessingScenes::industrial_fan_source, AllPonderTags.KINETIC_SOURCES);

        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/blue_ice", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/caramel", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/caramel_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chocolate", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chocolate_milk", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/chromatic_waste", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/condence_milk", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/cream", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/glowberry", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/glowberry_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/honey", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/hot_chocolate", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/sap", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/shimmer", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/strawberry", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/strawberry_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/vanilla", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/vanilla_milkshake", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/water", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);
        HELPER.forComponents(DDBlocks.ponder_stone_generation).addStoryBoard("gen/water_chroma", DDProcessingScenes::stone_generation, DDPonderTags.STONE_GENERATION);

        ////////////////////// Create = below |||| Create DD = Above

        CREATE_HELPER.forComponents(DDBlocks.BRONZE_SAW)
                .addStoryBoard("mechanical_saw/breaker", MechanicalSawScenes::treeCutting)
                .addStoryBoard("mechanical_saw/contraption", MechanicalSawScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.BRONZE_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.RADIANT_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);
        CREATE_HELPER.forComponents(DDBlocks.SHADOW_DRILL)
                .addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption, AllPonderTags.CONTRAPTION_ACTOR);

        CREATE_HELPER.forComponents(DDBlocks.industrial_fan)
                .addStoryBoard("fan/direction", FanScenes::direction, AllPonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("fan/processing", FanScenes::processing);

        CREATE_HELPER.addStoryBoard(DDBlocks.REVERSED_GEARSHIFT, "gearshift", KineticsScenes::gearshift, AllPonderTags.KINETIC_RELAYS);

        CREATE_HELPER.forComponents(DDBlocks.blasting_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.smoking_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.haunting_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.splashing_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.superheating_sail)
                .addStoryBoard("sail", BearingScenes::sail);
        CREATE_HELPER.forComponents(DDBlocks.freezing_sail)
                .addStoryBoard("sail", BearingScenes::sail);

        if (REGISTER_DEBUG_SCENES)
            DebugScenes.registerAll();
    }
}
