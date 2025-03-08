package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.lopyluna.create_d2d.DesiresCreate;
import dev.lopyluna.create_d2d.content.items.gatling_breaker.GatlingBreakerItem;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.Tags;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.create_d2d.DesiresCreate.REG;

@SuppressWarnings("unused")
public class DesiresItems {

    public static final ItemEntry<Item> BURNER = REG.item("burner", Item::new)
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("AB")
                    .pattern("BA")
                    .define('A', Items.NETHERITE_SCRAP)
                    .define('B', AllItems.ZINC_INGOT.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DesiresCreate.loc("crafting/" + c.getName())))
            .lang("Burner Stock")
            .register();

    public static final ItemEntry<GatlingBreakerItem> GATLING_BREAKER = REG.item("gatling_breaker", GatlingBreakerItem::new)
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .tag(ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BREAKS_DECORATED_POTS, Tags.Items.TOOLS)
            .lang("Gatling Breaker")
            .model(AssetLookup.itemModelWithPartials())
            .register();


    public static void register() {}
}
