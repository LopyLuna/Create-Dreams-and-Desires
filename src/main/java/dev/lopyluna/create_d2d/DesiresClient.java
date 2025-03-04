package dev.lopyluna.create_d2d;

import dev.lopyluna.create_d2d.register.client.DesiresPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

import static dev.lopyluna.create_d2d.DesiresCreate.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class DesiresClient {

    public DesiresClient(IEventBus modEventBus) {
        IEventBus neoEventBus = NeoForge.EVENT_BUS;
        modEventBus.addListener(DesiresClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DesiresPartialModels.init();
    }
}
