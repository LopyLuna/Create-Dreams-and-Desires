package dev.lopyluna.dndesires.register;

import dev.lopyluna.dndesires.DnDesires;

import static dev.lopyluna.dndesires.DnDesires.REG;

public class DesiresLangPartial {
    public static void addTranslations() {
        REG.addRawLang(recipe("hydraulic_compacting"), "Hydraulic Compacting");
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
        var lang = DnDesires.MOD_ID;
        if (!prefix.isEmpty()) lang = prefix + "." + lang;
        if (!prefixType.isEmpty()) lang = prefixType + "." + lang;
        if (!suffix.isEmpty()) lang = lang + "." + suffix;
        if (!suffixType.isEmpty()) lang = lang + "." + suffixType;
        return lang;
    }
}