package dev.lopyluna.dndesires.events;

import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.items.HandheldKeyHandlerClient;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = DnDesires.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().screen != null) return;
        int key = event.getKey();
        boolean pressed = !(event.getAction() == 0);
        HandheldKeyHandlerClient.onKeyInput(key, pressed);
    }
}
