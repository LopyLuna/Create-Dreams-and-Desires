package dev.lopyluna.dndesires.register;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry.SpudSentryBE;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

import static dev.lopyluna.dndesires.DnDesires.MOD_ID;
import static dev.lopyluna.dndesires.DnDesires.REG;

@SuppressWarnings("unused")
public class DesiresLangPartial {
    public static void addTranslations() {
        REG.addRawLang(recipe("hydraulic_compacting"), "Hydraulic Compacting");
        REG.addRawLang(recipeFan("dragon_breathing"), "Bulk Dragon Breathing");
        REG.addRawLang(recipeFan("freezing"), "Bulk Freezing");
        REG.addRawLang(recipeFan("sanding"), "Bulk Sanding");
        REG.addRawLang(recipeFan("seething"), "Bulk Seething");

        REG.addRawLang(MOD_ID+".spud_sentry.target_mode", "Target Mode");
        REG.addRawLang(MOD_ID+".spud_sentry.filter_mode", "Filter Mode");

        for (var value : SpudSentryBE.TargetMode.values()) REG.addRawLang(value.getTranslationKey(), RegistrateLangProvider.toEnglishName(value.name().toLowerCase()));
        for (var value : SpudSentryBE.FilterMode.values()) REG.addRawLang(value.getTranslationKey(), RegistrateLangProvider.toEnglishName(value.name().toLowerCase()));
    }

    public static String recipeFan(String type) {
        return "create.recipe.fan_" + type;
    }
    public static String recipe(String type) {
        return newLang("", "", "recipe", type);
    }
    public static String item(String type) {
        return newLang("item", "", "", type);
    }
    public static String block(String type) {
        return newLang("block", "", "", type);
    }
    public static String biome(String type) {
        return newLang("biome", "", "", type);
    }
    public static String newLang(String prefix, String prefixType, String suffix, String suffixType) {
        var lang = MOD_ID;
        if (!prefix.isEmpty()) lang = prefix + "." + lang;
        if (!prefixType.isEmpty()) lang = prefixType + "." + lang;
        if (!suffix.isEmpty()) lang = lang + "." + suffix;
        if (!suffixType.isEmpty()) lang = lang + "." + suffixType;
        return lang;
    }

    public static LangBuilder builder() {
        return new LangBuilder(MOD_ID);
    }

    public static MutableComponent translateDirect(String key, Object... args) {
        Object[] args1 = LangBuilder.resolveBuilders(args);
        return Component.translatable(MOD_ID + "." + key, args1);
    }

    public static List<Component> translatedOptions(String prefix, String... keys) {
        List<Component> result = new ArrayList<>(keys.length);
        for (String key : keys)
            result.add(translate((prefix != null ? prefix + "." : "") + key).component());
        return result;
    }

    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}