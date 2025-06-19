package dev.lopyluna.dndesires.register.helpers;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.lopyluna.dndesires.content.items.packages.BurstPackageItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Locale;

import static dev.lopyluna.dndesires.DnDesires.REG;

@SuppressWarnings({"unused"})
public class ItemTransgender {

    public static ItemBuilder<BurstPackageItem, CreateRegistrate> burstPackageItem(PackageStyles.PackageStyle style) {
        String size = "_" + style.width() + "x" + style.height();
        return REG.item(style.getItemId().getPath(), p -> new BurstPackageItem(p, style))
                .properties(p -> p.stacksTo(1))
                .tag(AllTags.AllItemTags.PACKAGES.tag)
                .model((c, p) -> {
                    if (style.rare()) p.withExistingParent(c.getName(), Create.asResource("item/package/custom" + size)).texture("2", Create.asResource("item/package/" + style.type()));
                    else p.withExistingParent(c.getName(), Create.asResource("item/package/" + style.type() + size));
                }).lang((style.rare() ? "Rare" : style.type().substring(0, 1).toUpperCase(Locale.ROOT) + style.type().substring(1)) + " Package");
    }

    public static ItemEntry<Item> itemEntry(String name) {
        return REG.item(name, Item::new).register();
    }

    public static ItemEntry<Item> itemEntry(String name, String lang) {
        return REG.item(name, Item::new).lang(lang).register();
    }

    @SafeVarargs
    public static ItemEntry<Item> itemEntryTagged(String name, TagKey<Item>... tags) {
        return REG.item(name, Item::new).tag(tags).register();
    }

    @SafeVarargs
    public static ItemEntry<Item> itemEntryTagged(String name, String lang, TagKey<Item>... tags) {
        return REG.item(name, Item::new).tag(tags).lang(lang).register();
    }
}
