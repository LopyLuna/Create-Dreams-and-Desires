package uwu.lopyluna.create_flavored.Event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uwu.lopyluna.create_flavored.entity.Bobert.SmartBuilderRenderer;
import uwu.lopyluna.create_flavored.entity.bobert;

public class FlavoredClient {

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(bobert.SMART_BUILDER.get(), SmartBuilderRenderer::new);
    }
}
