package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.items.MilkshakeItem;
import dev.lopyluna.dndesires.content.items.gatling_breaker.GatlingBreakerItem;
import dev.lopyluna.dndesires.content.items.handheld_drill.HandheldDrillItem;
import dev.lopyluna.dndesires.content.items.handheld_saw.HandheldSawItem;
import dev.lopyluna.dndesires.register.helpers.ItemTransgender;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Supplier;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndesires.DnDesires.REG;
import static dev.lopyluna.dndesires.register.DesiresTags.commonItemTag;

@SuppressWarnings("unused")
public class DesiresItems {
    public static final ItemEntry<MilkshakeItem> CHOCOLATE_MILKSHAKE = milkshake("Chocolate Milkshake", () -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 4 * 60 * 20, 1, false, false, false)).register();
    public static final ItemEntry<MilkshakeItem> VANILLA_MILKSHAKE = milkshake("Vanilla Milkshake", () -> new MobEffectInstance(MobEffects.SATURATION, 10 * 20, 0, false, false, false)).register();
    public static final ItemEntry<MilkshakeItem> STRAWBERRY_MILKSHAKE = milkshake("Strawberry Milkshake", () -> new MobEffectInstance(MobEffects.REGENERATION, 30 * 20, 1, false, false, false)).register();
    public static final ItemEntry<MilkshakeItem> GLOWBERRY_MILKSHAKE = milkshake("Glowberry Milkshake", () -> new MobEffectInstance(MobEffects.NIGHT_VISION, 10 * 60 * 20, 0, false, false, false)).register();
    public static final ItemEntry<MilkshakeItem> PUMPKIN_MILKSHAKE = milkshake("Pumpkin Milkshake", () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * 20, 1, false, false, false)).register();

    public static ItemBuilder<MilkshakeItem, CreateRegistrate> milkshake(String name, Supplier<MobEffectInstance> effectIn) {
        String id = name.toLowerCase().replace(" ", "_");
        return REG.item(id, MilkshakeItem::new)
                .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                .properties(p -> p.stacksTo(16).food(new FoodProperties.Builder().nutrition(12).saturationModifier(1.2F).alwaysEdible().effect(effectIn, 1F).build())
                ).lang(name);
    }

    public static final ItemEntry<HandheldSawItem> HANDHELD_SAW = REG.item("handheld_saw", p -> new HandheldSawItem(Tiers.DIAMOND, p))
            .properties(p -> p.attributes(AxeItem.createAttributes(Tiers.DIAMOND, 1.0F, 1.0F)))
            .model(AssetLookup.itemModelWithPartials())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("AGS")
                    .pattern("CSS")
                    .define('A', AllItems.ANDESITE_ALLOY)
                    .define('G', AllBlocks.COGWHEEL.get())
                    .define('C', AllBlocks.ANDESITE_CASING.get())
                    .define('S', AllTags.commonItemTag("plates/iron"))
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .tag(ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BREAKS_DECORATED_POTS, Tags.Items.TOOLS)
            .register();

    public static final ItemEntry<HandheldDrillItem> HANDHELD_DRILL = REG.item("handheld_drill", p -> new HandheldDrillItem(Tiers.DIAMOND, p))
            .properties(p -> p.attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, 1.0F)))
            .model(AssetLookup.itemModelWithPartials())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("BGA")
                    .pattern("CA ")
                    .define('A', AllItems.ANDESITE_ALLOY)
                    .define('B', AllTags.commonItemTag("ingots/brass"))
                    .define('G', AllBlocks.COGWHEEL.get())
                    .define('C', AllBlocks.BRASS_CASING.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .tag(ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BREAKS_DECORATED_POTS, Tags.Items.TOOLS)
            .register();

    public static final ItemEntry<Item> BURNER_STOCK = REG.item("burner", Item::new)
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("AB")
                    .pattern("BA")
                    .define('A', Items.NETHERITE_SCRAP)
                    .define('B', AllTags.commonItemTag("ingots/zinc"))
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .lang("Burner Stock")
            .register();

    public static final ItemEntry<GatlingBreakerItem> GATLING_BREAKER = REG.item("gatling_breaker", GatlingBreakerItem::new)
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .tag(ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BREAKS_DECORATED_POTS, Tags.Items.TOOLS)
            .lang("Gatling Breaker")
            .model(AssetLookup.itemModelWithPartials())
            .register();

    //MATERIALS

    public static final ItemEntry<Item> GOLDEN_WHISK = REG.item("gold_whisk", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DnDesires.loc("item/" + c.getId().getPath())))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern(" A ").pattern("GAG").pattern("GGG")
                    .define('A', AllItems.ANDESITE_ALLOY)
                    .define('G', AllTags.commonItemTag("plates/gold"))
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName()))).lang("Golden Whisk")
            .register();

    public static final ItemEntry<Item> LAPIS_LAZULI_SHARD = REG.item("lapis_lazuli_shard", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DnDesires.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/lapis"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.LAPIS_LAZULI;
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DnDesires.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 8)
                        .requires(output)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDesires.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .register();

    public static final ItemEntry<Item> DIAMOND_SHARD = REG.item("diamond_shard", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DnDesires.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/diamond"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.DIAMOND;
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DnDesires.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 4)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDesires.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .register();

    public static final ItemEntry<Item> COAL_PIECE = REG.item("coal_piece", Item::new)
            .model((c, p) -> p.withExistingParent(c.getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", DnDesires.loc("item/" + c.getId().getPath())))
            .tag(commonItemTag("nuggets/coal"), commonItemTag("nuggets"))
            .recipe((c, p) -> {
                Item output = Items.COAL;
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 1)
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .requires(c.get()).requires(c.get())
                        .unlockedBy("has_" + getItemName(output), has(output))
                        .save(p, DnDesires.loc("crafting/" + getItemName(output) + "_from_" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 8)
                        .requires(output)
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDesires.loc("crafting/" + c.getName() + "_from_" + getItemName(output)));
            })
            .burnTime(200)
            .register();

    static {
        @SuppressWarnings("all")
        var styles = PackageStyles.STYLES;
        boolean rareCreated = false;
        boolean normalCreated = false;
        for (PackageStyles.PackageStyle style : styles) {
            var packageItem = ItemTransgender.burstPackageItem(style);
            if (rareCreated && style.rare() || normalCreated && !style.rare()) packageItem.setData(ProviderType.LANG, NonNullBiConsumer.noop());
            rareCreated |= style.rare();
            normalCreated |= !style.rare();
            packageItem.register();
        }
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static void register() {}
}
