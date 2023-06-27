package uwu.lopyluna.create_dd.block;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import uwu.lopyluna.create_dd.block.BlockProperties.YIPPEESlidingDoorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.YIPPEESlidingDoorRenderer;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;


public class YIPPEEEntityTypes {

    public static final BlockEntityEntry<YIPPEESlidingDoorBlockEntity> SLIDING_DOOR =
            REGISTRATE.blockEntity("sliding_door", YIPPEESlidingDoorBlockEntity::new)
                    .renderer(() -> YIPPEESlidingDoorRenderer::new)
                    .validBlocks(YIPPEE.rose_door, YIPPEE.smoked_door)
                    .register();


    public static void register() {}
}
