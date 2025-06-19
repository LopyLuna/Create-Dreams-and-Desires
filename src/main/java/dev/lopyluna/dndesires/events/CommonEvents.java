package dev.lopyluna.dndesires.events;

import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressBE;
import dev.lopyluna.dndesires.content.blocks.logistics.roll_table.RollTableBE;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = DnDesires.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        RollTableBE.registerCapabilities(event);
        HydraulicPressBE.registerCapabilities(event);
    }
}
