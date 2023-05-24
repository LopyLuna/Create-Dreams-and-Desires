package uwu.lopyluna.create_flavored.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_flavored.Flavoredcreate;

public class Pipebomb {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flavoredcreate.MOD_ID);


    //YIPPEE ITEMS PIPEBOMB YUMMY
    public static final RegistryObject<Item> mithril_ingot = ITEMS.register("mithril_ingot", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> mithril_nugget = ITEMS.register("mithril_nugget", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> mithril_sheet = ITEMS.register("mithril_sheet", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));

    public static final RegistryObject<Item> bronze_ingot = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> bronze_nugget = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> bronze_sheet = ITEMS.register("bronze_sheet", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));

    public static final RegistryObject<Item> steel_ingot = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> steel_nugget = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> steel_sheet = ITEMS.register("steel_sheet", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));

    public static final RegistryObject<Item> tin_ingot = ITEMS.register( "tin_ingot", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> tin_nugget = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));
    public static final RegistryObject<Item> tin_raw = ITEMS.register( "raw_tin", () -> new Item(new Item.Properties().tab(PipebombTab.FlavoredCreate_TAB)));






    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
