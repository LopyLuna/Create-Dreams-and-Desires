package dev.lopyluna.dndesires.register.client;

import dev.lopyluna.dndesires.DnDesires;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = DnDesires.MOD_ID, value = Dist.CLIENT, bus =EventBusSubscriber.Bus.MOD)
public class DesiresEntityRenderRegistry {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }
}
