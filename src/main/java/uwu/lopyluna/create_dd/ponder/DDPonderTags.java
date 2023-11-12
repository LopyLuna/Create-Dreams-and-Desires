package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.world.item.Items;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.item.DDItems;

import static com.simibubi.create.infrastructure.ponder.AllPonderTags.*;

public class DDPonderTags {

    private static PonderTag create(String id) {
        return new PonderTag(DDCreate.asResource(id));
    }

    public static final PonderTag
    CREATEDD = create("create_dd").item(DDItems.spectral_ruby.get())
            .defaultLang("Create: Dreams n' Desires", "Where Dreams & also Desires come true!")
		.addToIndex(),
    FAN_HEATER = create("fan_heater").item(AllBlocks.BLAZE_BURNER.get())
            .defaultLang("Industrial Fan Heaters", "Heater that are valid for the Industrial Fan")
		.addToIndex();
    public static void register() {


        PonderRegistry.TAGS.forTag(FAN_HEATER)
                .add(Items.LAVA_BUCKET)
                .add(AllBlocks.BLAZE_BURNER)
                .add(DDBlocks.superheating_sail)
        ;


        PonderRegistry.TAGS.forTag(CREATEDD)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.hydraulic_press)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW)
                .add(DDBlocks.cogCrank)
                .add(DDBlocks.ACCELERATOR_MOTOR)
                .add(DDBlocks.KINETIC_MOTOR)
                .add(DDBlocks.FLYWHEEL)
                .add(DDBlocks.FURNACE_ENGINE)
                .add(DDBlocks.REVERSED_GEARSHIFT)
                .add(DDBlocks.blasting_sail)
                .add(DDBlocks.freezing_sail)
                .add(DDBlocks.smoking_sail)
                .add(DDBlocks.splashing_sail)
                .add(DDBlocks.superheating_sail)
                .add(DDBlocks.haunting_sail)
                .add(DDBlocks.SPECTRAL_RUBY_LAMP)
        ;

        PonderRegistry.TAGS.forTag(KINETIC_APPLIANCES)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.hydraulic_press)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW)
                ;

        PonderRegistry.TAGS.forTag(CONTRAPTION_ACTOR)
                .add(DDBlocks.BRONZE_DRILL)
                .add(DDBlocks.SHADOW_DRILL)
                .add(DDBlocks.RADIANT_DRILL)
                .add(DDBlocks.BRONZE_SAW)
        ;

        PonderRegistry.TAGS.forTag(ARM_TARGETS)
                .add(DDBlocks.BRONZE_SAW)
        ;

        PonderRegistry.TAGS.forTag(KINETIC_SOURCES)
                .add(DDBlocks.industrial_fan)
                .add(DDBlocks.cogCrank)
                .add(DDBlocks.ACCELERATOR_MOTOR)
                .add(DDBlocks.KINETIC_MOTOR)
                .add(DDBlocks.FLYWHEEL)
                .add(DDBlocks.FURNACE_ENGINE)
        ;

        PonderRegistry.TAGS.forTag(KINETIC_RELAYS)
                .add(DDBlocks.REVERSED_GEARSHIFT)
        ;

        PonderRegistry.TAGS.forTag(SAILS)
                .add(DDBlocks.blasting_sail)
                .add(DDBlocks.freezing_sail)
                .add(DDBlocks.smoking_sail)
                .add(DDBlocks.splashing_sail)
                .add(DDBlocks.superheating_sail)
                .add(DDBlocks.haunting_sail)
        ;

        PonderRegistry.TAGS.forTag(REDSTONE)
                .add(DDBlocks.SPECTRAL_RUBY_LAMP)
        ;

    }
}
