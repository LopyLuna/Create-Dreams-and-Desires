package uwu.lopyluna.create_dd.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelInstance;
import com.simibubi.create.content.kinetics.flywheel.FlywheelRenderer;
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
import uwu.lopyluna.create_dd.block.BlockProperties.fan.*;

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

    public static final BlockEntityEntry<TwoBladeFanBlockEntity> two_blade_fan = REGISTRATE
            .blockEntity("2_blade_fan", TwoBladeFanBlockEntity::new)
            .instance(() -> TwoBladeFanBlockInstance::new, false)
            .validBlocks(YIPPEE.two_blade_fan)
            .renderer(() -> TwoBladeFanBlockRenderer::new)
            .register();

    public static final BlockEntityEntry<FourBladeFanBlockEntity> four_blade_fan = REGISTRATE
            .blockEntity("4_blade_fan", FourBladeFanBlockEntity::new)
            .instance(() -> FourBladeFanBlockInstance::new, false)
            .validBlocks(YIPPEE.four_blade_fan)
            .renderer(() -> FourBladeFanBlockRenderer::new)
            .register();

    public static final BlockEntityEntry<EightBladeFanBlockEntity> eight_blade_fan = REGISTRATE
            .blockEntity("8_blade_fan", EightBladeFanBlockEntity::new)
            .instance(() -> EightBladeFanBlockInstance::new, false)
            .validBlocks(YIPPEE.eight_blade_fan)
            .renderer(() -> EightBladeFanBlockRenderer::new)
            .register();

    public static void register() {}
}
