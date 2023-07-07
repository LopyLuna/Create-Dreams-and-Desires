package uwu.lopyluna.create_dd.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.content.kinetics.fan.EncasedFanRenderer;
import com.simibubi.create.content.kinetics.fan.FanInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import uwu.lopyluna.create_dd.block.BlockProperties.*;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;


public class YIPPEEEntityTypes {

    public static final BlockEntityEntry<YIPPEESlidingDoorBlockEntity> SLIDING_DOOR =
            REGISTRATE.blockEntity("sliding_door", YIPPEESlidingDoorBlockEntity::new)
                    .renderer(() -> YIPPEESlidingDoorRenderer::new)
                    .validBlocks(YIPPEE.rose_door, YIPPEE.smoked_door, YIPPEE.spirit_door)
                    .register();

    public static final BlockEntityEntry<BronzeEncasedFanBlockEntity> BRONZE_ENCASED_FAN =
            REGISTRATE.blockEntity("bronze_encased_fan", BronzeEncasedFanBlockEntity::new)
                    .instance(() -> BronzeFanInstance::new, false)
                    .validBlocks(YIPPEE.BRONZE_ENCASED_FAN)
                    .renderer(() -> BronzeEncasedFanRenderer::new)
                    .register();

    public static void register() {}
}
