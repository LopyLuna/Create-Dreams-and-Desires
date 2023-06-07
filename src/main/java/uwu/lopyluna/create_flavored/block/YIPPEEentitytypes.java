package uwu.lopyluna.create_flavored.block;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import uwu.lopyluna.create_flavored.block.YIPPEEProperties.LargeDrillBlockEntity;
import uwu.lopyluna.create_flavored.block.YIPPEEProperties.LargeDrillInstance;
import uwu.lopyluna.create_flavored.block.YIPPEEProperties.LargeDrillRenderer;

import static com.simibubi.create.Create.REGISTRATE;

public class YIPPEEentityTypes {

    public static final BlockEntityEntry<LargeDrillBlockEntity> LARGE_DRILL = REGISTRATE
            .blockEntity("large_drill", LargeDrillBlockEntity::new)
            .instance(() -> LargeDrillInstance::new, false)
            .validBlocks(YIPPEE.LARGE_MECHANICAL_DRILL)
            .renderer(() -> LargeDrillRenderer::new)
            .register();

}
