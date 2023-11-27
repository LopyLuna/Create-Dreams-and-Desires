package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.world.item.Items;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.fluid.DDFluids;
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
		.addToIndex(),
    STONE_GENERATION = create("stone_generation").item(Items.COBBLESTONE)
            .defaultLang("Stone Generations", "Stone Generators Fluids")
		.addToIndex();
    public static void register() {

        PonderRegistry.TAGS.forTag(FAN_HEATER)
                .add(Items.LAVA_BUCKET)
                .add(AllBlocks.BLAZE_BURNER)
                .add(DDBlocks.superheating_sail)
        ;

        PonderRegistry.TAGS.forTag(STONE_GENERATION)
                .add(Items.LAVA_BUCKET)
                .add(Items.WATER_BUCKET)
                .add(Items.BLUE_ICE)
                .add(Items.SOUL_SAND)
                .add(AllFluids.HONEY.get().getBucket())
                .add(AllFluids.CHOCOLATE.get().getBucket())
                .add(DDFluids.CHROMATIC_WASTE.get().getBucket())
                .add(DDFluids.SAP.get().getBucket())
                .add(DDFluids.SHIMMER.get().getBucket())
                .add(DDFluids.VANILLA_MILKSHAKE.get().getBucket())
                .add(DDFluids.VANILLA.get().getBucket())
                .add(DDFluids.CARAMEL.get().getBucket())
                .add(DDFluids.CARAMEL_MILKSHAKE.get().getBucket())
                .add(DDFluids.CHOCOLATE_MILKSHAKE.get().getBucket())
                .add(DDFluids.HOT_CHOCOLATE.get().getBucket())
                .add(DDFluids.GLOWBERRY.get().getBucket())
                .add(DDFluids.GLOWBERRY_MILKSHAKE.get().getBucket())
                .add(DDFluids.STRAWBERRY.get().getBucket())
                .add(DDFluids.STRAWBERRY_MILKSHAKE.get().getBucket())
                .add(DDFluids.CREAM.get().getBucket())
                .add(DDFluids.CONDENSE_MILK.get().getBucket())
                .add(DDBlocks.ponder_stone_generation)
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
