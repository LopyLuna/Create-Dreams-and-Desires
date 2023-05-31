package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import uwu.lopyluna.create_flavored.block.MechanicalSmasher.SmashingBlockEntity;
import uwu.lopyluna.create_flavored.block.MechanicalSmasher.SmashingControllerBlockEntity;

import static com.simibubi.create.Create.REGISTRATE;

public class YIPPEEentitytypes {

    public static final BlockEntityEntry<SmashingBlockEntity> MECHANICAL_SMASHER = REGISTRATE
            .blockEntity("mechanical_smasher", SmashingBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new, false)
            .validBlocks(YIPPEE.MECHANICAL_SMASHER)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<SmashingControllerBlockEntity> MECHANICAL_SMASHER_CONTROLLER = REGISTRATE
            .blockEntity("mechanical_smasher_controller", SmashingControllerBlockEntity::new)
            .validBlocks(YIPPEE.MECHANICAL_SMASHER_CONTROLLER)
            // .renderer(() -> renderer)
            .register();


    public static void register() {}
}
