package uwu.lopyluna.create_dd.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import uwu.lopyluna.create_dd.DDcreate;
import uwu.lopyluna.create_dd.block.YIPPEE;

public class DDPonderTags extends AllPonderTags {

    public static final PonderTag

            KINETIC_APPLIANCES = create("kinetic_appliances").item(YIPPEE.hydraulic_press.get())
            .defaultLang("Kinetic Appliances", "Components which make use of Rotational Force")
            .addToIndex()
    ;

    private static PonderTag create(String id) {
        return new PonderTag(DDcreate.asResource(id));
    }

    public static void register() {

        PonderRegistry.TAGS.forTag(KINETIC_APPLIANCES)
                .add(YIPPEE.hydraulic_press)
                ;
    }
}
