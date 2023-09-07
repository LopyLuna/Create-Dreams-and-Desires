package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class DDPonderTags extends AllPonderTags {

    public static final PonderTag

            KINETIC_APPLIANCES = create("kinetic_appliances").item(DDBlocks.hydraulic_press.get())
            .defaultLang("Kinetic Appliances", "Components which make use of Rotational Force")
            .addToIndex()
    ;

    private static PonderTag create(String id) {
        return new PonderTag(DDCreate.asResource(id));
    }

    public static void register() {

        PonderRegistry.TAGS.forTag(KINETIC_APPLIANCES)
                .add(DDBlocks.hydraulic_press)
                ;
    }
}
