package uwu.lopyluna.create_dd.init;

import static uwu.lopyluna.create_dd.DDcreate.REGISTRATE;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import uwu.lopyluna.create_dd.content.block.bronze_drill.*;
import uwu.lopyluna.create_dd.content.block.bronze_encased_fan.*;
import uwu.lopyluna.create_dd.content.block.bronze_saw.*;
import uwu.lopyluna.create_dd.content.block.door.*;
import uwu.lopyluna.create_dd.content.block.drill.*;
import uwu.lopyluna.create_dd.content.block.fan.*;
import uwu.lopyluna.create_dd.content.block.hydraulic_press.*;
import uwu.lopyluna.create_dd.content.block.reversed_gearbox.*;
import uwu.lopyluna.create_dd.content.block.secondary_encased_chain_drive.*;

public class DDBlockEntityTypes {
	public static final BlockEntityEntry<BronzeSawBlockEntity> BRONZE_SAW = REGISTRATE
			.blockEntity("bronze_saw", BronzeSawBlockEntity::new)
			.instance(() -> BronzeSawInstance::new)
			.validBlocks(DDBlocks.BRONZE_SAW)
			.renderer(() -> BronzeSawRenderer::new)
			.register();

	public static final BlockEntityEntry<BronzeDrillBlockEntity> BRONZE_DRILL = REGISTRATE
			.blockEntity("bronze_drill", BronzeDrillBlockEntity::new)
			.instance(() -> BronzeDrillInstance::new, false)
			.validBlocks(DDBlocks.BRONZE_DRILL)
			.renderer(() -> BronzeDrillRenderer::new)
			.register();

	public static final BlockEntityEntry<KineticBlockEntity> ENCASED_SHAFT = REGISTRATE
			.blockEntity("encased_shaft", KineticBlockEntity::new)
			.instance(() -> ShaftInstance::new, false)
			.validBlocks(DDBlocks.secondary_encased_chain_drive)
			.renderer(() -> ShaftRenderer::new)
			.register();

	public static final BlockEntityEntry<DDSlidingDoorBlockEntity> SLIDING_DOOR =
			REGISTRATE.blockEntity("sliding_door", DDSlidingDoorBlockEntity::new)
					.renderer(() -> DDSlidingDoorRenderer::new)
					.validBlocks(DDBlocks.rose_door, DDBlocks.smoked_door, DDBlocks.spirit_door)
					.register();

	public static final BlockEntityEntry<BronzeEncasedFanBlockEntity> BRONZE_ENCASED_FAN =
			REGISTRATE.blockEntity("bronze_encased_fan", BronzeEncasedFanBlockEntity::new)
					.instance(() -> BronzeFanInstance::new, false)
					.validBlocks(DDBlocks.BRONZE_ENCASED_FAN)
					.renderer(() -> BronzeEncasedFanRenderer::new)
					.register();

	public static final BlockEntityEntry<RadiantDrillBlockEntity> RADIANT_DRILL =
			REGISTRATE.blockEntity("radiant_drill", RadiantDrillBlockEntity::new)
					.instance(() -> RadiantDrillInstance::new, false)
					.validBlocks(DDBlocks.RADIANT_DRILL)
					.renderer(() -> RadiantDrillRenderer::new)
					.register();

	public static final BlockEntityEntry<ShadowDrillBlockEntity> SHADOW_DRILL =
			REGISTRATE.blockEntity("shadow_drill", ShadowDrillBlockEntity::new)
					.instance(() -> ShadowDrillInstance::new, false)
					.validBlocks(DDBlocks.SHADOW_DRILL)
					.renderer(() -> ShadowDrillRenderer::new)
					.register();

	public static final BlockEntityEntry<ReversedGearboxBlockEntity> REVERSED_GEARSHIFT = REGISTRATE
			.blockEntity("gearshift", ReversedGearboxBlockEntity::new)
			.instance(() -> SplitShaftInstance::new, false)
			.validBlocks(DDBlocks.REVERSED_GEARSHIFT)
			.renderer(() -> SplitShaftRenderer::new)
			.register();

	public static final BlockEntityEntry<HydraulicPressBlockEntity> hydraulic_press = REGISTRATE
			.blockEntity("hydraulic_press", HydraulicPressBlockEntity::new)
			.instance(() -> HydraulicPressInstance::new)
			.validBlocks(DDBlocks.hydraulic_press)
			.renderer(() -> HydraulicPressRenderer::new)
			.register();

	public static final BlockEntityEntry<TwoBladeFanBlockEntity> two_blade_fan = REGISTRATE
			.blockEntity("2_blade_fan", TwoBladeFanBlockEntity::new)
			.instance(() -> TwoBladeFanBlockInstance::new, false)
			.validBlocks(DDBlocks.two_blade_fan)
			.renderer(() -> TwoBladeFanBlockRenderer::new)
			.register();

	public static final BlockEntityEntry<FourBladeFanBlockEntity> four_blade_fan = REGISTRATE
			.blockEntity("4_blade_fan", FourBladeFanBlockEntity::new)
			.instance(() -> FourBladeFanBlockInstance::new, false)
			.validBlocks(DDBlocks.four_blade_fan)
			.renderer(() -> FourBladeFanBlockRenderer::new)
			.register();

	public static final BlockEntityEntry<EightBladeFanBlockEntity> eight_blade_fan = REGISTRATE
			.blockEntity("8_blade_fan", EightBladeFanBlockEntity::new)
			.instance(() -> EightBladeFanBlockInstance::new, false)
			.validBlocks(DDBlocks.eight_blade_fan)
			.renderer(() -> EightBladeFanBlockRenderer::new)
			.register();

	public static final BlockEntityEntry<ChainGearshiftBlock2Entity> secondary_adjustable_chain_gearshift = REGISTRATE
			.blockEntity("secondary_adjustable_chain_gearshift", ChainGearshiftBlock2Entity::new)
			.instance(() -> ShaftInstance::new, false)
			.validBlocks(DDBlocks.secondary_adjustable_chain_gearshift)
			.renderer(() -> ShaftRenderer::new)
			.register();

	public static void register() {}
}
