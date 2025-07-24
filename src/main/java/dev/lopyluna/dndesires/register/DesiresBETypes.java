package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank.CogCrankBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank.CogCrankRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank.CogCrankVisual;
import dev.lopyluna.dndesires.content.blocks.kinetics.creative_gear_motor.CreativeGearMotorBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.creative_gear_motor.GearMotorRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressVisual;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialFanBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialFanRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.inverse_gearshift.InverseGearshiftBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.multimeter.MultiMeterBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.multimeter.MultiMeterRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.omni_gearbox.OmniGearboxBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.omni_gearbox.OmniGearboxRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.omni_speed_controller.OmniSpeedControllerBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.StirlingEngineBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.StirlingEngineRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.StirlingEngineVisual;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel.PoweredFlywheelRenderer;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel.PoweredFlywheelVisual;
import dev.lopyluna.dndesires.content.blocks.logistics.fluid_gauge.FluidGaugeBE;
import dev.lopyluna.dndesires.content.blocks.logistics.fluid_gauge.FluidGaugeRenderer;
import dev.lopyluna.dndesires.content.blocks.logistics.fluid_hatch.FluidHatchBE;
import dev.lopyluna.dndesires.content.blocks.logistics.roll_table.RollTableBE;
import dev.lopyluna.dndesires.content.blocks.logistics.roll_table.RollTableRenderer;
import dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBE;

import static dev.lopyluna.dndesires.DnDesires.REG;

public class DesiresBETypes {

    public static final BlockEntityEntry<CogCrankBE> COG_CRANK = REG
            .blockEntity("cog_crank", CogCrankBE::new)
            .visual(() -> CogCrankVisual::new)
            .validBlocks(DesiresBlocks.COG_CRANK, DesiresBlocks.LARGE_COG_CRANK)
            .renderer(() -> CogCrankRenderer::new)
            .register();

    public static final BlockEntityEntry<MultiMeterBE> MULTIMETER = REG
            .blockEntity("multimeter", MultiMeterBE::new)
            .visual(() -> ShaftVisual::new, true)
            .validBlocks(DesiresBlocks.MULTIMETER)
            .renderer(() -> MultiMeterRenderer::new)
            .register();

    public static final BlockEntityEntry<SmartHopperBE> SMART_HOPPER = REG
            .blockEntity("smart_hopper", SmartHopperBE::new)
            .validBlocks(DesiresBlocks.SMART_HOPPER)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<OmniGearboxBE> GEARBOX = REG
            .blockEntity("gearbox", OmniGearboxBE::new)
            .validBlocks(DesiresBlocks.OMNI_GEARBOX)
            .renderer(() -> OmniGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<CreativeGearMotorBE> MOTOR = REG
            .blockEntity("motor", CreativeGearMotorBE::new)
            .visual(() -> SingleAxisRotatingVisual.ofZ(AllPartialModels.MECHANICAL_PUMP_COG))
            .validBlocks(DesiresBlocks.CREATIVE_GEAR_MOTOR)
            .renderer(() -> GearMotorRenderer::new)
            .register();

    public static final BlockEntityEntry<FluidGaugeBE> FLUID_GAUGE = REG
            .blockEntity("fluid_gauge", FluidGaugeBE::new)
            .validBlocks(DesiresBlocks.FLUID_GAUGE)
            .renderer(() -> FluidGaugeRenderer::new)
            .register();

    public static final BlockEntityEntry<FluidHatchBE> FLUID_HATCH = REG
            .blockEntity("fluid_hatch", FluidHatchBE::new)
            .validBlocks(DesiresBlocks.FLUID_HATCH)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<IndustrialFanBE> INDUSTRIAL_FAN = REG
            .blockEntity("industrial_fan", IndustrialFanBE::new)
            //.visual(() -> IndustrialFanVisual::new, true)
            .validBlocks(DesiresBlocks.INDUSTRIAL_FAN)
            .renderer(() -> IndustrialFanRenderer::new)
            .register();

    public static final BlockEntityEntry<OmniSpeedControllerBE> OMNI_SPEED_CONTROLLER = REG
            .blockEntity("omni_speed_controller", OmniSpeedControllerBE::new)
            .visual(() -> SplitShaftVisual::new, false)
            .validBlocks(DesiresBlocks.OMNI_SPEED_CONTROLLER)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<InverseGearshiftBE> INVERSE_GEARSHIFT = REG
            .blockEntity("inverse_gearshift", InverseGearshiftBE::new)
            .visual(() -> SplitShaftVisual::new, false)
            .validBlocks(DesiresBlocks.INVERSE_GEARSHIFT)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<RollTableBE> ROLL_TABLE = REG
            .blockEntity("roll_table", RollTableBE::new)
            .validBlocks(DesiresBlocks.ROLL_TABLE)
            .renderer(() -> RollTableRenderer::new)
            .register();

    public static final BlockEntityEntry<HydraulicPressBE> HYDRAULIC_PRESS = REG
            .blockEntity("hydraulic_press", HydraulicPressBE::new)
            .visual(() -> HydraulicPressVisual::new, true)
            .validBlocks(DesiresBlocks.HYDRAULIC_PRESS)
            .renderer(() -> HydraulicPressRenderer::new)
            .register();

    public static final BlockEntityEntry<StirlingEngineBE> STIRLING_ENGINE = REG
            .blockEntity("stirling_engine", StirlingEngineBE::new)
            .visual(() -> StirlingEngineVisual::new, false)
            .validBlocks(DesiresBlocks.STIRLING_ENGINE)
            .renderer(() -> StirlingEngineRenderer::new)
            .register();

    public static final BlockEntityEntry<PoweredFlywheelBE> POWERED_FLYWHEEL = REG
            .blockEntity("powered_flywheel", PoweredFlywheelBE::new)
            .visual(() -> PoweredFlywheelVisual::new, true)
            .validBlock(DesiresBlocks.POWERED_FLYWHEEL)
            .renderer(() -> PoweredFlywheelRenderer::new)
            .register();

    public static void register() {}
}
