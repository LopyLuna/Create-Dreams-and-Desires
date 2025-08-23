package dev.lopyluna.dndesires.register.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;
import static dev.lopyluna.dndesires.DnDesires.NAME;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public enum DesiresKeys {
    HANDHELD_SAW_ACTIVATION("saw_active", GLFW.GLFW_KEY_LEFT_ALT, "Active Handheld Saw Action"),
    HANDHELD_DRILL_ACTIVATION("drill_active", GLFW.GLFW_KEY_LEFT_ALT, "Active Handheld Drill Action")
    ;

    private KeyMapping keybind;
    private final String description;
    private final String translation;
    private final int key;
    private final boolean modifiable;

    DesiresKeys(int defaultKey) {
        this("", defaultKey, "");
    }
    DesiresKeys(String description, int defaultKey, String translation) {
        this.description = MOD_ID + ".keyinfo." + description;
        this.key = defaultKey;
        this.modifiable = !description.isEmpty();
        this.translation = translation;
    }

    public static void provideLang(BiConsumer<String, String> consumer) {
        for (var key : values()) if (key.modifiable) consumer.accept(key.description, key.translation);
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        for (var key : values()) {
            key.keybind = new KeyMapping(key.description, key.key, NAME);
            if (!key.modifiable) continue;
            event.register(key.keybind);
        }
    }

    public KeyMapping getKeybind() {
        return keybind;
    }

    public boolean isPressed() {
        if (!modifiable) return isKeyDown(key);
        return keybind.isDown();
    }

    public String getBoundKey() {
        return keybind.getTranslatedKeyMessage().getString().toUpperCase();
    }

    public boolean doesModifierAndCodeMatch(int code) {
        boolean codeMatches = code == keybind.getKey().getValue();

        boolean modifierMatches;
        var modifier = keybind.getKeyModifier();
        if (modifier == KeyModifier.NONE) modifierMatches = true;
        else modifierMatches = KeyModifier.getActiveModifiers().contains(modifier);

        return codeMatches && modifierMatches;
    }

    public static boolean isKeyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
    }

    public static boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), button) == 1;
    }

    public static boolean ctrlDown() {
        return Screen.hasControlDown();
    }
    public static boolean shiftDown() {
        return Screen.hasShiftDown();
    }
    public static boolean altDown() {
        return Screen.hasAltDown();
    }

}
