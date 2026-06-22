package dev.lopyluna.dndesires;

import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class DesiresClient {

    public DesiresClient(IEventBus modEventBus) {
        DesiresPartialModels.init();
        IEventBus neoEventBus = NeoForge.EVENT_BUS;
        //modEventBus.addListener(DesiresClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DesiresPartialModels.init();
    }
}
