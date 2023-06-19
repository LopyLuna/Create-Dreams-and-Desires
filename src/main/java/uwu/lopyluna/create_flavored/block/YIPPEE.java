package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.copycat.CopycatPanelBlock;
import com.simibubi.create.content.decoration.copycat.CopycatPanelModel;
import com.simibubi.create.content.decoration.copycat.CopycatStepBlock;
import com.simibubi.create.content.decoration.copycat.CopycatStepModel;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.ForgeSoundType;
import uwu.lopyluna.create_flavored.block.BlockProperties.*;
import uwu.lopyluna.create_flavored.item.PipebombTab;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;

public class YIPPEE {

    static {
        REGISTRATE.creativeModeTab(() -> PipebombTab.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<Block> mithril_block = REGISTRATE.block("mithril_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.WARPED_NYLIUM))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(10f,24f))
            .lang("Block of Mithril")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> bronze_block = REGISTRATE.block("bronze_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(10f,10f))
            .lang("Block of Bronze")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> steel_block = REGISTRATE.block("steel_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(6f,16f))
            .lang("Block of Steel")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> tin_ore = REGISTRATE.block("tin_ore", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .properties(p -> p.strength(3f,3f))
            .lang("Tin Ore")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> deepslate_tin_ore = REGISTRATE.block("deepslate_tin_ore", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE_GOLD_ORE)
            .properties(p -> p.color(MaterialColor.DEEPSLATE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .properties(p -> p.strength(4.5f,3f))
            .lang("Deepslate Tin Ore")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> tin_block = REGISTRATE.block("tin_block", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(p -> p.color(MaterialColor.QUARTZ))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.METAL))
            .properties(p -> p.strength(3f,6f))
            .lang("Block of Tin")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> raw_tin_block = REGISTRATE.block("raw_tin_block", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .properties(p -> p.strength(1f,1.6f))
            .lang("Block of Raw Tin")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> leather_block = REGISTRATE.block("leather_block", Block::new)
            .initialProperties(() -> Blocks.HAY_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.sound(SoundType.WOOL))
            .properties(p -> p.strength(1f,1.6f))
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/leather"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Leather")
            .register();

    public static final BlockEntry<Block> lapis_alloy_block = REGISTRATE.block("lapis_alloy_block", Block::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/lapis_alloy"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Lapis Alloy")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> chromatic_block = REGISTRATE.block("chromatic_block", Block::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.2f, () -> SoundEvents.POLISHED_DEEPSLATE_BREAK,
                    () -> SoundEvents.POLISHED_DEEPSLATE_STEP, () -> SoundEvents.POLISHED_DEEPSLATE_PLACE,
                    () -> SoundEvents.POLISHED_DEEPSLATE_HIT, () -> SoundEvents.POLISHED_DEEPSLATE_FALL)))
            .properties(p -> p.requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .lang("Block of Chromatic Compound")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<MagicBlock> refined_radiance_block = REGISTRATE.block("refined_radiance_block", MagicBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.SNOW))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.25f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.AMETHYST_BLOCK_STEP, () -> SoundEvents.AMETHYST_BLOCK_PLACE,
                    () -> SoundEvents.AMETHYST_BLOCK_HIT, () -> SoundEvents.AMETHYST_BLOCK_FALL)))
            .properties(p -> p.lightLevel($ -> 12))
            .properties(p -> p.requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .lang("Block of Refined Radiance")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<MagicBlock> shadow_steel_block = REGISTRATE.block("shadow_steel_block", MagicBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.requiresCorrectToolForDrops())
            .properties(p -> p.sound(new ForgeSoundType(1, .25f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .transform(pickaxeOnly())
            .lang("Block of Shadow Steel")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<CasingBlock> mithril_casing = REGISTRATE.block("mithril_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.MITHRIL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Mithril Casing")
            .register();

    public static final BlockEntry<CasingBlock> bronze_casing = REGISTRATE.block("bronze_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.BRONZE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Bronze Casing")
            .register();

    public static final BlockEntry<CasingBlock> zinc_casing = REGISTRATE.block("zinc_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.ZINC_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Zinc Casing")
            .register();

    public static final BlockEntry<Block> mossy_andesite_alloy_block = REGISTRATE.block("mossy_andesite_alloy_block", Block::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .lang("Mossy Block of Andesite Alloy")
            .simpleItem()
            .register();

    public static final BlockEntry<CasingBlock> mossy_andesite_casing = REGISTRATE.block("mossy_andesite_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.MOSSY_ANDESITE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Mossy Andesite Casing")
            .register();

    public static final BlockEntry<CasingBlock> hydraulic_casing = REGISTRATE.block("hydraulic_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.HYDRAULIC_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.COPPER))
            .lang("Hydraulic Casing")
            .register();

    public static final BlockEntry<CasingBlock> industrial_casing = REGISTRATE.block("industrial_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.INDUSTRIAL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Industrial Casing")
            .register();

    public static final BlockEntry<CasingBlock> overburden_casing = REGISTRATE.block("overburden_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.OVERBURDEN_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_LIGHT_BLUE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Overburden Casing")
            .register();

    public static final BlockEntry<MagicBlockcasing> shadow_steel_casing = REGISTRATE.block("shadow_steel_casing", MagicBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.SHADOW_STEEL_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1, .25f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .lang("Shadow Casing")
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<MagicBlockcasing> refined_radiance_casing = REGISTRATE.block("refined_radiance_casing", MagicBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.REFINED_RADIANCE_CASING))
            .properties(p -> p.color(MaterialColor.SNOW))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.25f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.AMETHYST_BLOCK_STEP, () -> SoundEvents.AMETHYST_BLOCK_PLACE,
                    () -> SoundEvents.AMETHYST_BLOCK_HIT, () -> SoundEvents.AMETHYST_BLOCK_FALL)))
            .properties(p -> p.lightLevel($ -> 12))
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .lang("Radiant Casing")
            .register();

    //MECHANICAL BLOCKS

    //WOODSET BLOCKS

    public static final BlockEntry<HotAssRotatedBlockPillar> rose_log = REGISTRATE.block("rose_log", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Rose Log")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> smoked_log = REGISTRATE.block("smoked_log", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Log")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> stripped_rose_log = REGISTRATE.block("stripped_rose_log", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Rose Log")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> stripped_smoked_log = REGISTRATE.block("stripped_smoked_log", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Smoked Log")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> rose_wood = REGISTRATE.block("rose_wood", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Rose Wood")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> smoked_wood = REGISTRATE.block("smoked_wood", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Wood")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> stripped_rose_wood = REGISTRATE.block("stripped_rose_wood", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Rose Wood")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssRotatedBlockPillar> stripped_smoked_wood = REGISTRATE.block("stripped_smoked_wood", HotAssRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Smoked Wood")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssBlock> rose_planks = REGISTRATE.block("rose_planks", HotAssBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Rose Planks")
            .simpleItem()
            .register();

    public static final BlockEntry<HotAssBlock> smoked_planks = REGISTRATE.block("smoked_planks", HotAssBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Planks")
            .simpleItem()
            .register();


    public static final BlockEntry<MetalScaffoldingBlock> TRAIN_SCAFFOLD =
            REGISTRATE.block("train_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("train",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("plates/obsidian")), MaterialColor.COLOR_BLACK,
                            YIPPEESpriteShifts.TRAIN_SCAFFOLD, YIPPEESpriteShifts.TRAIN_SCAFFOLD_INSIDE, AllSpriteShifts.RAILWAY_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> HYDRAULIC_SCAFFOLD =
            REGISTRATE.block("hydraulic_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("hydraulic",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/copper")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.HYDRAULIC_SCAFFOLD, YIPPEESpriteShifts.HYDRAULIC_SCAFFOLD_INSIDE, YIPPEESpriteShifts.HYDRAULIC_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> OVERBURDEN_SCAFFOLD =
            REGISTRATE.block("overburden_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("overburden",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/lapis_alloy")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.OVERBURDEN_SCAFFOLD, YIPPEESpriteShifts.OVERBURDEN_SCAFFOLD_INSIDE, YIPPEESpriteShifts.OVERBURDEN_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> INDUSTRIAL_SCAFFOLD =
            REGISTRATE.block("industrial_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("industrial",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/industrial_iron")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.INDUSTRIAL_SCAFFOLD, YIPPEESpriteShifts.INDUSTRIAL_SCAFFOLD_INSIDE, YIPPEESpriteShifts.INDUSTRIAL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> MITHRIL_SCAFFOLD =
            REGISTRATE.block("mithril_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("mithril",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/mithril")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.MITHRIL_SCAFFOLD, YIPPEESpriteShifts.MITHRIL_SCAFFOLD_INSIDE, YIPPEESpriteShifts.MITHRIL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> MOSSY_ANDESITE_SCAFFOLD =
            REGISTRATE.block("mossy_andesite_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("mossy_andesite", () -> DataIngredient.items(AllItems.ANDESITE_ALLOY.get()),
                            MaterialColor.STONE, YIPPEESpriteShifts.MOSSY_ANDESITE_SCAFFOLD, YIPPEESpriteShifts.MOSSY_ANDESITE_SCAFFOLD_INSIDE,
                            YIPPEESpriteShifts.MOSSY_ANDESITE_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> ZINC_SCAFFOLD =
            REGISTRATE.block("zinc_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("zinc",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/zinc")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.ZINC_SCAFFOLD, YIPPEESpriteShifts.ZINC_SCAFFOLD_INSIDE, YIPPEESpriteShifts.ZINC_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> BRONZE_SCAFFOLD =
            REGISTRATE.block("bronze_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("bronze",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/bronze")), MaterialColor.TERRACOTTA_YELLOW,
                            YIPPEESpriteShifts.BRONZE_SCAFFOLD, YIPPEESpriteShifts.BRONZE_SCAFFOLD_INSIDE, YIPPEESpriteShifts.BRONZE_CASING))
                    .register();



    public static final BlockEntry<BlockcopycatBlock> COPYCAT_BlOCK =
            REGISTRATE.block("copycat_block", BlockcopycatBlock::new)
                    .transform(BuilderTransformers.copycat())
                    .onRegister(CreateRegistrate.blockModel(() -> BlockcopycatBlockModel::new))
                    .item()
                    .recipe((c, p) -> p.stonecutting(DataIngredient.tag(AllTags.forgeItemTag("ingots/zinc")), c::get, 1))
                    .transform(customItemModel("copycat_base", "block"))
                    .register();

    public static final BlockEntry<BlockcopycatSlab> COPYCAT_SLAB =
            REGISTRATE.block("copycat_slab", BlockcopycatSlab::new)
                    .transform(BuilderTransformers.copycat())
                    .onRegister(CreateRegistrate.blockModel(() -> BlockcopycatSlabModel::new))
                    .item()
                    .recipe((c, p) -> p.stonecutting(DataIngredient.tag(AllTags.forgeItemTag("ingots/zinc")), c::get, 2))
                    .transform(customItemModel("copycat_base", "slab"))
                    .register();




    public static void register() {}
}
