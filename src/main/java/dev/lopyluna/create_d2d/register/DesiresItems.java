package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.lopyluna.create_d2d.DesiresCreate;
import dev.lopyluna.create_d2d.content.items.gatling_breaker.GatlingBreakerItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.create_d2d.DesiresCreate.REG;
import static dev.lopyluna.create_d2d.register.DesiresTags.commonItemTag;

@SuppressWarnings("unused")
public class DesiresItems {

    public static final ItemEntry<Item> BURNER = REG.item("burner", Item::new)
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
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

    //MATERIALS

    public static final ItemEntry<Item> LAPIS_LAZULI_SHARD = REG.item("lapis_lazuli_shard", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DesiresCreate.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/lapis"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.LAPIS_LAZULI;
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DesiresCreate.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 8)
                        .requires(output)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DesiresCreate.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .register();

    public static final ItemEntry<Item> DIAMOND_SHARD = REG.item("diamond_shard", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DesiresCreate.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/diamond"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.DIAMOND;
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DesiresCreate.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 4)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DesiresCreate.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .register();

    public static final ItemEntry<CombustibleItem> COAL_PIECE = REG.item("coal_piece", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(200))
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DesiresCreate.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/coal"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.COAL;
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 1)
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DesiresCreate.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 8)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DesiresCreate.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .register();

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static void register() {}
}
