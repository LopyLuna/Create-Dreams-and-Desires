package dev.lopyluna.dndesires.events;

import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressBE;
import dev.lopyluna.dndesires.content.blocks.logistics.roll_table.RollTableBE;
import dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBE;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = DnDesires.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        RollTableBE.registerCapabilities(event);
        HydraulicPressBE.registerCapabilities(event);
        SmartHopperBE.registerCapabilities(event);
    }
}
