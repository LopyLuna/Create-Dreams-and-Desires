package dev.lopyluna.dndesires.content.items;

import dev.lopyluna.dndesires.content.items.handheld_drill.HandheldDrillKeyPacket;
import dev.lopyluna.dndesires.content.items.handheld_saw.HandheldSawKeyPacket;
import dev.lopyluna.dndesires.register.client.DesiresKeys;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.GameType;

public class HandheldKeyHandlerClient {

    public static void onKeyInput(int key, boolean pressed) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null || mc.gameMode == null || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        if (DesiresKeys.HANDHELD_SAW_ACTIVATION.doesModifierAndCodeMatch(key)) {
            CatnipServices.NETWORK.sendToServer(new HandheldSawKeyPacket(pressed));
            player.getPersistentData().putBoolean("HandheldSawKey", pressed);
        }
        if (DesiresKeys.HANDHELD_DRILL_ACTIVATION.doesModifierAndCodeMatch(key)) {
            CatnipServices.NETWORK.sendToServer(new HandheldDrillKeyPacket(pressed));
            player.getPersistentData().putBoolean("HandheldDrillKey", pressed);
        }
    }
}
