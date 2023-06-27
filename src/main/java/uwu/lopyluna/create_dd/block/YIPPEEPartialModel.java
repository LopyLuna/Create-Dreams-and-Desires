package uwu.lopyluna.create_dd.block;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.DDcreate;

import java.util.HashMap;
import java.util.Map;

public class YIPPEEPartialModel {

    public static final Map<ResourceLocation, Couple<PartialModel>> FOLDING_DOORS = new HashMap<>();
    static {
        putFoldingDoor("rose_door");
        putFoldingDoor("smoked_door");
    }
    private static void putFoldingDoor(String path) {
        FOLDING_DOORS.put(DDcreate.asResource(path),
                Couple.create(block(path + "/fold_left"), block(path + "/fold_right")));
    }

    private static PartialModel block(String path) {
        return new PartialModel(DDcreate.asResource("block/" + path));
    }

    public static void init() {
    }
}
