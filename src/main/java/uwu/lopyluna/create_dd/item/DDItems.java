package uwu.lopyluna.create_dd.item;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import uwu.lopyluna.create_dd.creative.DDItemTab;
import uwu.lopyluna.create_dd.item.ItemProperties.ItemDisabled;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem1;
import uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem.SequencedCraftingItem2;
import uwu.lopyluna.create_dd.item.ItemProperties.StargazeInfiniteBlock;
import uwu.lopyluna.create_dd.item.ItemProperties.compound.UnchargedStargazeSingularity;
import uwu.lopyluna.create_dd.item.ItemProperties.compound.*;
import uwu.lopyluna.create_dd.item.ItemProperties.exp.ExperienceNuggetItemOne;
import uwu.lopyluna.create_dd.item.ItemProperties.exp.ExperienceNuggetItemTwo;
import uwu.lopyluna.create_dd.item.ItemProperties.milkshakes.*;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.DeforesterItem;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.ForestRavagerItem;

import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static com.simibubi.create.AllTags.forgeItemTag;
import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;

@SuppressWarnings({"all"})
public class DDItems {

    static {
        REGISTRATE.setCreativeTab(DDItemTab.BASE_CREATIVE_TAB);
    }

    //YIPPEE ITEMS PIPEBOMB YUMMY ac
    public static final ItemEntry<Item>
            mithril_ingot = taggedIngredient("mithril_ingot", forgeItemTag("ingots/mithril"), CREATE_INGOTS.tag),
            bronze_ingot = taggedIngredient("bronze_ingot", forgeItemTag("ingots/bronze"), CREATE_INGOTS.tag),
            steel_ingot = taggedIngredient("steel_ingot", forgeItemTag("ingots/steel"), CREATE_INGOTS.tag),
            tin_ingot = taggedIngredient("tin_ingot", forgeItemTag("ingots/tin"), CREATE_INGOTS.tag),
            industrial_iron_ingot = taggedIngredient("industrial_iron_ingot", forgeItemTag("ingots/industrial_iron"), CREATE_INGOTS.tag),
            mithril_nugget = taggedIngredient("mithril_nugget", forgeItemTag("nuggets/mithril")),
            bronze_nugget = taggedIngredient("bronze_nugget", forgeItemTag("nuggets/bronze")),
            steel_nugget = taggedIngredient("steel_nugget", forgeItemTag("nuggets/steel")),
            tin_nugget = taggedIngredient("tin_nugget", forgeItemTag("nuggets/tin")),
            industrial_iron_nugget = taggedIngredient("industrial_iron_nugget", forgeItemTag("nuggets/industrial_iron")),
            mithril_sheet = taggedIngredient("mithril_sheet", forgeItemTag("plates/mithril")),
            bronze_sheet = taggedIngredient("bronze_sheet", forgeItemTag("plates/bronze")),
            steel_sheet = taggedIngredient("steel_sheet", forgeItemTag("plates/steel")),
            industrial_iron_sheet = taggedIngredient("industrial_iron_sheet", forgeItemTag("plates/industrial_iron")),
            tin_raw = ingredient("raw_tin"),
            ember_alloy = taggedIngredient("ember_alloy", forgeItemTag("ingots/ember_alloy")),
            lapis_alloy = taggedIngredient("lapis_alloy", forgeItemTag("ingots/lapis_alloy")),
            lapis_sheet = taggedIngredient("lapis_sheet", forgeItemTag("plates/lapis_alloy")),
            andesite_sheet = taggedIngredient("andesite_sheet", forgeItemTag("plates/andesite_alloy")),
            zinc_sheet = taggedIngredient("zinc_sheet", forgeItemTag("plates/zinc")),
            tin_sheet = taggedIngredient("tin_sheet", forgeItemTag("plates/tin")),
            integrated_circuit = ingredient("integrated_circuit"),
            integrated_mechanism = ingredient("integrated_mechanism"),
            calculation_mechanism = ingredient("calculation_mechanism"),
            inductive_mechanism = ingredient("inductive_mechanism"),
            infernal_mechanism = ingredient("infernal_mechanism"),
            sealed_mechanism = ingredient("sealed_mechanism"),
            spectral_ruby = ingredient("spectral_ruby"),
            polished_spectral_ruby = ingredient("polished_spectral_ruby"),
            diamond_shard = taggedIngredient("diamond_shard", forgeItemTag("nuggets/diamond")),
            compound_base = ingredient("compound_base"),
            crystallized_sap = ingredient("crystallized_sap"),
            raw_rubber = ingredient("raw_rubber"),
            rubber = ingredient("rubber");

    public static final ItemEntry<SimpleFoiledItem>
            frozen_nugget = foilIngredient("frozen_nugget")
    ;
    //ac
    public static final ItemEntry<ItemDisabled>
            abstruse_mechanism = i("abstruse_mechanism")
            ;

    public static final ItemEntry<SequencedAssemblyItem>
            incomplete_stargaze_singularity = sequencedIngredient("incomplete_stargaze_singularity"),
            incomplete_integrated_circuit = sequencedIngredient("incomplete_integrated_circuit"),
            incomplete_integrated_mechanism = sequencedIngredient("incomplete_integrated_mechanism"),
            incomplete_abstruse_mechanism = sequencedIngredient("incomplete_abstruse_mechanism"),
            incomplete_calculation_mechanism = sequencedIngredient("incomplete_calculation_mechanism"),
            incomplete_inductive_mechanism = sequencedIngredient("incomplete_inductive_mechanism"),
            incomplete_infernal_mechanism = sequencedIngredient("incomplete_infernal_mechanism"),
            incomplete_sealed_mechanism = sequencedIngredient("incomplete_sealed_mechanism");

    public static final ItemEntry<StargazeInfiniteBlock>
            INFA_STONE = infablock("stone", Blocks.STONE),
            INFA_GRANITE = infablock("granite", Blocks.GRANITE),
            INFA_DIORITE = infablock("diorite", Blocks.DIORITE),
            INFA_ANDESITE = infablock("andesite", Blocks.ANDESITE),
            INFA_COBBLED_DEEPSLATE = infablock("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE),
            INFA_CALCITE = infablock("calcite", Blocks.CALCITE),
            INFA_TUFF = infablock("tuff", Blocks.TUFF),
            INFA_DRIPSTONE_BLOCK = infablock("dripstone_block", Blocks.DRIPSTONE_BLOCK),
            INFA_DIRT = infablock("dirt", Blocks.DIRT),
            INFA_COARSE_DIRT = infablock("coarse_dirt", Blocks.COARSE_DIRT),
            INFA_MUD = infablock("mud", Blocks.MUD),
            INFA_COBBLESTONE = infablock("cobblestone", Blocks.COBBLESTONE),
            INFA_SAND = infablock("sand", Blocks.SAND),
            INFA_RED_SAND = infablock("red_sand", Blocks.RED_SAND),
            INFA_GRAVEL = infablock("gravel", Blocks.GRAVEL),
            INFA_OBSIDIAN = infablock("obsidian", Blocks.OBSIDIAN),
            INFA_ICE = infablock("ice", Blocks.ICE),
            INFA_SNOW_BLOCK = infablock("snow_block", Blocks.SNOW_BLOCK),
            INFA_SOUL_SAND = infablock("soul_sand", Blocks.SOUL_SAND),
            INFA_END_STONE = infablock("end_stone", Blocks.END_STONE),
            INFA_NETHERRACK = infablock("netherrack", Blocks.NETHERRACK),
            INFA_MAGMA_BLOCK = infablock("magma_block", Blocks.MAGMA_BLOCK),
            INFA_MOSS_BLOCK = infablock("moss_block", Blocks.MOSS_BLOCK);


    public static final ItemEntry<CombustibleItem> COAL_PIECE = REGISTRATE.item("coal_piece", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(200))
            .register();

    public static final ItemEntry<ChromaticCompound> CHROMATIC_COMPOUND =
            REGISTRATE.item("chromatic_compound", ChromaticCompound::new)
            .properties(p -> p.stacksTo(16)
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant())
            .register();

    public static final ItemEntry<ShadowSteel> SHADOW_STEEL =
            REGISTRATE.item("shadow_steel", ShadowSteel::new)
            .properties(p -> p.stacksTo(16)
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant())
            .register();

    public static final ItemEntry<ShadowSteel> SHADOW_STEEL_SHEET =
            REGISTRATE.item("shadow_steel_sheet", ShadowSteel::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<RefinedRadiance> REFINED_RADIANCE =
            REGISTRATE.item("refined_radiance", RefinedRadiance::new)
            .properties(p -> p.stacksTo(16)
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant())
            .register();

    public static final ItemEntry<RefinedRadiance> REFINED_RADIANCE_SHEET =
            REGISTRATE.item("refined_radiance_sheet", RefinedRadiance::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<UnchargedStargazeSingularity> FALLEN_STARGAZE_SINGULARITY =
            REGISTRATE.item("fallen_stargaze_singularity", UnchargedStargazeSingularity::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<StargazeSingularity> STARGAZE_SINGULARITY =
            REGISTRATE.item("stargaze_singularity", StargazeSingularity::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.EPIC)
                            .fireResistant())
                    .register();

    public static final ItemEntry<StargazeSingularity> STARGAZE_SINGULARITY_SHEET =
            REGISTRATE.item("stargaze_singularity_sheet", StargazeSingularity::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.EPIC)
                            .fireResistant())
                    .register();

    public static final ItemEntry<BlazeGold> BLAZE_GOLD =
            REGISTRATE.item("blaze_gold", BlazeGold::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<BlazeGold> BLAZE_GOLD_SHEET =
            REGISTRATE.item("blaze_gold_sheet", BlazeGold::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<OverchargeAlloy> OVERCHARGE_ALLOY =
            REGISTRATE.item("overcharge_alloy", OverchargeAlloy::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();

    public static final ItemEntry<OverchargeAlloy> OVERCHARGE_ALLOY_SHEET =
            REGISTRATE.item("overcharge_alloy_sheet", OverchargeAlloy::new)
                    .properties(p -> p.stacksTo(16)
                            .rarity(Rarity.UNCOMMON)
                            .fireResistant())
                    .register();


    public static final ItemEntry<ForestRavagerItem> forest_ravager =
            REGISTRATE.item("forest_ravager", ForestRavagerItem::new)
            .properties(p -> p.stacksTo(1)
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant())
            .model(AssetLookup.itemModelWithPartials())
            .register();

    public static final ItemEntry<DeforesterItem> deforester_saw =
            REGISTRATE.item("deforester_saw", DeforesterItem::new)
                    .properties(p -> p.stacksTo(1))
                    .model(AssetLookup.itemModelWithPartials())
                    .register();

    public static final ItemEntry<ExperienceNuggetItemOne> ONE_EXP_NUGGET =
            REGISTRATE.item("experience_ingot", ExperienceNuggetItemOne::new)
                    .tag(Tags.Items.INGOTS)
                    .properties(p -> p.stacksTo(64)
                            .rarity(Rarity.UNCOMMON))
                    .lang("Ingot of Experience")
                    .register();

    public static final ItemEntry<ExperienceNuggetItemTwo> TWO_EXP_NUGGET =
            REGISTRATE.item("experience_mass", ExperienceNuggetItemTwo::new)
                    .properties(p -> p.stacksTo(64)
                            .rarity(Rarity.EPIC)
                            .fireResistant())
                    .lang("Mass of Experience")
                    .register();

    public static final ItemEntry<CaramelMilkshake> caramel_milkshake = REGISTRATE.item("caramel_milkshake", CaramelMilkshake::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();

    public static final ItemEntry<ChocolateMilkshake> chocolate_milkshake = REGISTRATE.item("chocolate_milkshake", ChocolateMilkshake::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();

    public static final ItemEntry<GlowberryMilkshake> glowberry_milkshake = REGISTRATE.item("glowberry_milkshake", GlowberryMilkshake::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();

    public static final ItemEntry<StrawberryMilkshake> strawberry_milkshake = REGISTRATE.item("strawberry_milkshake", StrawberryMilkshake::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();

    public static final ItemEntry<VanillaMilkshake> vanilla_milkshake = REGISTRATE.item("vanilla_milkshake", VanillaMilkshake::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();

    public static final ItemEntry<HotChocolate> hot_chocolate = REGISTRATE.item("hot_chocolate", HotChocolate::new)
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .properties(p -> p.stacksTo(12))
            .register();


    public static final ItemEntry<SequencedCraftingItem1>
            incomplete_crafted_inductive_mechanism1 = craftingIngredient1("crafting_inductive_mechanism1"),
            incomplete_crafted_kinetic_mechanism1 = craftingIngredient1("crafting_kinetic_mechanism1")

            ;

    public static final ItemEntry<SequencedCraftingItem2>
            incomplete_crafted_inductive_mechanism2 = craftingIngredient2("crafting_inductive_mechanism2"),
            incomplete_crafted_kinetic_mechanism2 = craftingIngredient2("crafting_kinetic_mechanism2");

    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    private static ItemEntry<StargazeInfiniteBlock> infablock(String name, Block block) {
        return REGISTRATE.item("infa" + name.toLowerCase(), p -> new StargazeInfiniteBlock(block, p))
                .properties(p -> p.stacksTo(1)
                        .rarity(Rarity.EPIC)
                        .fireResistant())
                .register();
    }

    private static ItemEntry<SimpleFoiledItem> foilIngredient(String name) {
        return REGISTRATE.item(name, SimpleFoiledItem::new)
                .register();
    }

    private static ItemEntry<ItemDisabled> i(String name) {
        return REGISTRATE.item(name, ItemDisabled::new)
                .register();
    }

    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
        return REGISTRATE.item(name, SequencedAssemblyItem::new)
                .register();
    }

    private static ItemEntry<SequencedCraftingItem1> craftingIngredient1(String name) {
        return REGISTRATE.item(name, SequencedCraftingItem1::new)
                .register();
    }
    private static ItemEntry<SequencedCraftingItem2> craftingIngredient2(String name) {
        return REGISTRATE.item(name, SequencedCraftingItem2::new)
                .register();
    }

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }

    public static void register() {}
}
