package dev.lopyluna.dndesires.register;

import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.lopyluna.dndesires.content.blocks.hydraulic_press.HydraulicPressBE;
import dev.lopyluna.dndesires.content.blocks.hydraulic_press.HydraulicPressRenderer;
import dev.lopyluna.dndesires.content.blocks.hydraulic_press.HydraulicPressVisual;
import dev.lopyluna.dndesires.content.blocks.inverse_gearshift.InverseGearshiftBE;
import dev.lopyluna.dndesires.content.blocks.omni_speed_controller.OmniSpeedControllerBE;
import dev.lopyluna.dndesires.content.blocks.roll_table.RollTableBE;
import dev.lopyluna.dndesires.content.blocks.roll_table.RollTableRenderer;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.StirlingEngineBE;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.StirlingEngineRenderer;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.StirlingEngineVisual;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.flywheel.PoweredFlywheelRenderer;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.flywheel.PoweredFlywheelVisual;

import static dev.lopyluna.dndesires.DnDesires.REG;

public class DesiresBETypes {

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
