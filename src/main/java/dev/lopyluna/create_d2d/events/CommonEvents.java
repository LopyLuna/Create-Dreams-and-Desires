package dev.lopyluna.create_d2d.events;

import dev.lopyluna.create_d2d.content.blocks.hydraulic_press.HydraulicPressBE;
import dev.lopyluna.create_d2d.content.blocks.roll_table.RollTableBE;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

//@EventBusSubscriber
public class CommonEvents {

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            RollTableBE.registerCapabilities(event);
            HydraulicPressBE.registerCapabilities(event);
        }
    }
}
