package dev.lopyluna.create_d2d.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.lopyluna.create_d2d.content.blocks.propeller.PropellerBE;
import dev.lopyluna.create_d2d.content.blocks.propeller.PropellerRenderer;
import dev.lopyluna.create_d2d.content.blocks.propeller.PropellerVisual;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineBE;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineRenderer;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineVisual;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBE;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelRenderer;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelVisual;

import static dev.lopyluna.create_d2d.DesiresCreate.REG;

public class DesiresBETypes {

    public static final BlockEntityEntry<PropellerBE> PROPELLER = REG
            .blockEntity("propeller", PropellerBE::new)
            .visual(() -> PropellerVisual::new, false)
            .validBlocks(DesiresBlocks.PROPELLER)
            .validBlocks(DesiresBlocks.DYED_PROPELLERS.toArray())
            .renderer(() -> PropellerRenderer::new)
            .register();

    public static final BlockEntityEntry<StirlingEngineBE> STIRLING_ENGINE = REG
            .blockEntity("stirling_engine", StirlingEngineBE::new)
            .visual(() -> StirlingEngineVisual::new, false)
            .validBlocks(DesiresBlocks.STIRLING_ENGINE)
            .renderer(() -> StirlingEngineRenderer::new)
            .register();

    public static final BlockEntityEntry<PoweredFlywheelBE> POWERED_FLYWHEEL = REG
            .blockEntity("powered_flywheel", PoweredFlywheelBE::new)
            .visual(() -> PoweredFlywheelVisual::new, false)
            .validBlock(DesiresBlocks.POWERED_FLYWHEEL)
            .renderer(() -> PoweredFlywheelRenderer::new)
            .register();

    public static void register() {}
}
