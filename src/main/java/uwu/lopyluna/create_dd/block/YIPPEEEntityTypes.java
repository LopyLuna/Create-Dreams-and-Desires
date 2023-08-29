package uwu.lopyluna.create_dd.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.motor.CreativeMotorBlockEntity;
import com.simibubi.create.content.kinetics.motor.CreativeMotorRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.ReversedGearboxBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_drill.BronzeDrillBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_drill.BronzeDrillInstance;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_drill.BronzeDrillRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeEncasedFanBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeEncasedFanRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan.BronzeFanInstance;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawInstance;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.*;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.*;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HYPressInstance;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.ponder_box.PonderBoxBlockEntity;
import uwu.lopyluna.create_dd.block.BlockProperties.ponder_box.PonderBoxRenderer;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainGearshiftBlock2Entity;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;


public class YIPPEEEntityTypes {

    public static final BlockEntityEntry<BronzeSawBlockEntity> BRONZE_SAW = REGISTRATE
            .blockEntity("bronze_saw", BronzeSawBlockEntity::new)
            .instance(() -> BronzeSawInstance::new)
            .validBlocks(YIPPEE.BRONZE_SAW)
            .renderer(() -> BronzeSawRenderer::new)
            .register();

    public static final BlockEntityEntry<BronzeDrillBlockEntity> BRONZE_DRILL = REGISTRATE
            .blockEntity("bronze_drill", BronzeDrillBlockEntity::new)
            .instance(() -> BronzeDrillInstance::new, false)
            .validBlocks(YIPPEE.BRONZE_DRILL)
            .renderer(() -> BronzeDrillRenderer::new)
            .register();

    public static final BlockEntityEntry<KineticBlockEntity> ENCASED_SHAFT = REGISTRATE
            .blockEntity("encased_shaft", KineticBlockEntity::new)
            .instance(() -> ShaftInstance::new, false)
            .validBlocks(YIPPEE.secondary_encased_chain_drive)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<YIPPEESlidingDoorBlockEntity> SLIDING_DOOR =
            REGISTRATE.blockEntity("sliding_door", YIPPEESlidingDoorBlockEntity::new)
                    .renderer(() -> YIPPEESlidingDoorRenderer::new)
                    .validBlocks(YIPPEE.rose_door, YIPPEE.smoked_door, YIPPEE.spirit_door)
                    .register();

    public static final BlockEntityEntry<PonderBoxBlockEntity> ponder_in_a_box =
            REGISTRATE.blockEntity("ponder_in_a_box", PonderBoxBlockEntity::new)
                    .renderer(() -> PonderBoxRenderer::new)
                    .validBlocks(YIPPEE.ponder_in_a_box)
                    .register();

    public static final BlockEntityEntry<BronzeEncasedFanBlockEntity> BRONZE_ENCASED_FAN =
            REGISTRATE.blockEntity("industrial_fan", BronzeEncasedFanBlockEntity::new)
                    .instance(() -> BronzeFanInstance::new, false)
                    .validBlocks(YIPPEE.industrial_fan)
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

    public static final BlockEntityEntry<HydraulicPressBlockEntity> hydraulic_press = REGISTRATE
            .blockEntity("hydraulic_press", HydraulicPressBlockEntity::new)
            .instance(() -> HYPressInstance::new)
            .validBlocks(YIPPEE.hydraulic_press)
            .renderer(() -> HydraulicPressRenderer::new)
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

    public static final BlockEntityEntry<ChainGearshiftBlock2Entity> secondary_adjustable_chain_gearshift = REGISTRATE
            .blockEntity("secondary_adjustable_chain_gearshift", ChainGearshiftBlock2Entity::new)
            .instance(() -> ShaftInstance::new, false)
            .validBlocks(YIPPEE.secondary_adjustable_chain_gearshift)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<KineticMotorBlockEntity> MOTOR = REGISTRATE
            .blockEntity("motor", KineticMotorBlockEntity::new)
            .instance(() -> HalfShaftInstance::new, false)
            .validBlocks(YIPPEE.KINETIC_MOTOR)
            .renderer(() -> KineticMotorRenderer::new)
            .register();

    public static final BlockEntityEntry<AcceleratorMotorBlockEntity> AC_MOTOR = REGISTRATE
            .blockEntity("accelerator_motor", AcceleratorMotorBlockEntity::new)
            .instance(() -> HalfShaftInstance::new, false)
            .validBlocks(YIPPEE.ACCELERATOR_MOTOR)
            .renderer(() -> AcceleratorMotorRenderer::new)
            .register();
    public static void register() {}
}
