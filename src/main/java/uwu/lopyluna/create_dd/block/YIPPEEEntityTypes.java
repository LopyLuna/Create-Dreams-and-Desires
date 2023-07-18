package uwu.lopyluna.create_dd.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.transmission.GearshiftBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import uwu.lopyluna.create_dd.block.BlockProperties.ReversedGearboxBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeEncasedFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeEncasedFanRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeFanInstance;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.*;

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

    public static final BlockEntityEntry<RadiantDrillBlockEntity> RADIANT_DRILL =
            REGISTRATE.blockEntity("radiant_drill", RadiantDrillBlockEntity::new)
            .instance(() -> RadiantDrillInstance::new, false)
            .validBlocks(YIPPEE.RADIANT_DRILL)
            .renderer(() -> RadiantDrillRenderer::new)
            .register();

    public static final BlockEntityEntry<ShadowDrillBlockEntity> SHADOW_DRILL =
            REGISTRATE.blockEntity("shadow_drill", ShadowDrillBlockEntity::new)
            .instance(() -> ShadowDrillInstance::new, false)
            .validBlocks(YIPPEE.SHADOW_DRILL)
            .renderer(() -> ShadowDrillRenderer::new)
            .register();

    public static final BlockEntityEntry<ReversedGearboxBlockEntity> REVERSED_GEARSHIFT = REGISTRATE
            .blockEntity("gearshift", ReversedGearboxBlockEntity::new)
            .instance(() -> SplitShaftInstance::new, false)
            .validBlocks(YIPPEE.REVERSED_GEARSHIFT)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static void register() {}
}
