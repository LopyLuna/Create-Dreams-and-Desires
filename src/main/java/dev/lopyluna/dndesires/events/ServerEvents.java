package dev.lopyluna.dndesires.events;

import dev.lopyluna.dndesires.DnDesires;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = DnDesires.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEvents {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        DnDesires.LOGGER.info(DnDesires.NAME + " SERVER SETUP");
    }
}
