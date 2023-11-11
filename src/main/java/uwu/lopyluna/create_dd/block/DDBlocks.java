package uwu.lopyluna.create_dd.block;

import com.simibubi.create.*;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.ItemUseOverrides;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.BlockProperties.*;
import uwu.lopyluna.create_dd.block.BlockProperties.accelerator_motor.AcceleratorMotorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.cog_crank.CogCrankBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.FlywheelGenerator;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.FurnaceEngineBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw.BronzeSawMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatBlockModel;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatSlab;
import uwu.lopyluna.create_dd.block.BlockProperties.copycat.BlockcopycatSlabModel;
import uwu.lopyluna.create_dd.block.BlockProperties.door.YIPPEESlidingDoorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant.RadiantDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillMovementBehaviour;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.EightBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.FourBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.fan.TwoBladeFanBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.kinetic_motor.KineticMotorBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.magic.*;
import uwu.lopyluna.create_dd.block.BlockProperties.ponder_box.PonderBoxBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.potato_turret.PotatoTurretBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainDriveBlock2;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainDriveBlockGen;
import uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive.ChainGearshiftBlock2;
import uwu.lopyluna.create_dd.block.BlockProperties.wood.*;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockSpriteShifts;
import uwu.lopyluna.create_dd.creative.DDItemTab;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;

@SuppressWarnings({"unused", "removal"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DDBlocks {
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DDCreate.MOD_ID);
    
    static {
        REGISTRATE.creativeModeTab(() -> DDItemTab.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<Block> mithril_block = REGISTRATE.block("mithril_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.WARPED_NYLIUM))
            .properties(p -> p.sound(new ForgeSoundType(0.75f, .7f, () -> DDSoundEvents.magical_metal_break.get(),
                    () -> DDSoundEvents.magical_metal_step.get(), () -> DDSoundEvents.magical_metal_place.get(),
                    () -> DDSoundEvents.magical_metal_hit.get(), () -> DDSoundEvents.magical_metal_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(16f,48f))
            .lang("Block of Mithril")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> bronze_block = REGISTRATE.block("bronze_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(12f,10f))
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

    public static final BlockEntry<Block> industrial_iron_block = REGISTRATE.block("industrial_iron_block", Block::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .lang("Solid Block of Industrial Iron")
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
            .properties(p -> p.strength(0.5f,1f))
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/leather"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Leather")
            .register();

    public static final BlockEntry<Block> lapis_alloy_block = REGISTRATE.block("lapis_alloy_block", Block::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/lapis_alloy"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Lapis Alloy")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ember_alloy_block = REGISTRATE.block("ember_alloy_block", Block::new)
            .initialProperties(() -> Blocks.BROWN_TERRACOTTA)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .tag(Tags.Blocks.STORAGE_BLOCKS)
            .transform(tagBlockAndItem("storage_blocks/ember_alloy"))
            .tag(Tags.Items.STORAGE_BLOCKS)
            .build()
            .lang("Block of Ember Alloy")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> chromatic_block = REGISTRATE.block("chromatic_block", Block::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.2f, () -> SoundEvents.POLISHED_DEEPSLATE_BREAK,
                    () -> SoundEvents.POLISHED_DEEPSLATE_STEP, () -> SoundEvents.POLISHED_DEEPSLATE_PLACE,
                    () -> SoundEvents.POLISHED_DEEPSLATE_HIT, () -> SoundEvents.POLISHED_DEEPSLATE_FALL)))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(12f,25f))
            .transform(pickaxeOnly())
            .lang("Block of Chromatic Compound")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<RadiantBlock> refined_radiance_block = REGISTRATE.block("refined_radiance_block", RadiantBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.SNOW))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.25f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.AMETHYST_BLOCK_STEP, () -> SoundEvents.AMETHYST_BLOCK_PLACE,
                    () -> SoundEvents.AMETHYST_BLOCK_HIT, () -> SoundEvents.AMETHYST_BLOCK_FALL)))
            .properties(p -> p.lightLevel($ -> 12))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(16f,48f))
            .transform(pickaxeOnly())
            .lang("Block of Refined Radiance")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<ShadowBlock> shadow_steel_block = REGISTRATE.block("shadow_steel_block", ShadowBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1, .25f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(16f,48f))
            .transform(pickaxeOnly())
            .lang("Block of Shadow Steel")
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<OverchargedAlloyBlock> overcharged_alloy_block = REGISTRATE.block("overcharged_alloy_block", OverchargedAlloyBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(1, 1.25f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(12f,32f))
            .transform(pickaxeOnly())
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<BlazeGoldBlock> blaze_gold_block = REGISTRATE.block("blaze_gold_block", BlazeGoldBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_YELLOW))
            .properties(p -> p.sound(new ForgeSoundType(1, 1f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(8f,24f))
            .transform(pickaxeOnly())
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<StargazeBlock> stargaze_singularity_block = REGISTRATE.block("stargaze_singularity_block", StargazeBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1, .75f, () -> SoundEvents.AMETHYST_CLUSTER_BREAK,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_CLUSTER_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL)))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(32f,512f))
            .transform(pickaxeOnly())
            .simpleItem()
            .item()
            .properties(p -> p.rarity(Rarity.EPIC))
            .build()
            .register();

    public static final BlockEntry<Block> mossy_andesite_alloy_block = REGISTRATE.block("mossy_andesite_alloy_block", Block::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.color(MaterialColor.STONE))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .lang("Mossy Block of Andesite Alloy")
            .simpleItem()
            .register();

    public static final BlockEntry<CasingBlock> mithril_casing = REGISTRATE.block("mithril_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.MITHRIL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.sound(new ForgeSoundType(0.9f, .7f, () -> DDSoundEvents.magical_metal_break.get(),
                    () -> DDSoundEvents.magical_metal_step.get(), () -> DDSoundEvents.magical_metal_place.get(),
                    () -> DDSoundEvents.magical_metal_hit.get(), () -> DDSoundEvents.magical_metal_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .lang("Mithril Casing")
            .register();

    public static final BlockEntry<CasingBlock> bronze_casing = REGISTRATE.block("bronze_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.BRONZE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Bronze Casing")
            .register();

    public static final BlockEntry<CasingBlock> zinc_casing = REGISTRATE.block("zinc_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.ZINC_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Zinc Casing")
            .register();

    public static final BlockEntry<CasingBlock> tin_casing = REGISTRATE.block("tin_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.TIN_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Tin Casing")
            .register();

    public static final BlockEntry<CasingBlock> netherite_casing = REGISTRATE.block("netherite_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.NETHERITE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Netherite Casing")
            .register();

    public static final BlockEntry<CasingBlock> brick_casing = REGISTRATE.block("brick_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.BRICK_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.MUD_BRICKS))
            .register();

    public static final BlockEntry<CasingBlock> nether_brick_casing = REGISTRATE.block("nether_brick_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.NETHER_BRICK_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.NETHER_BRICKS))
            .register();

    public static final BlockEntry<CasingBlock> mossy_andesite_casing = REGISTRATE.block("mossy_andesite_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.MOSSY_ANDESITE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Mossy Andesite Casing")
            .register();

    public static final BlockEntry<CasingBlock> hydraulic_casing = REGISTRATE.block("hydraulic_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.HYDRAULIC_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.COPPER))
            .lang("Hydraulic Casing")
            .register();

    public static final BlockEntry<CasingBlock> industrial_casing = REGISTRATE.block("industrial_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.INDUSTRIAL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Industrial Casing")
            .register();

    public static final BlockEntry<CasingBlock> overburden_casing = REGISTRATE.block("overburden_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.OVERBURDEN_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_LIGHT_BLUE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Overburden Casing")
            .register();

    public static final BlockEntry<CasingBlock> steel_casing = REGISTRATE.block("steel_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.STEEL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .lang("Steel Casing")
            .register();

    public static final BlockEntry<ShadowBlockcasing> shadow_steel_casing = REGISTRATE.block("shadow_steel_casing", ShadowBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.SHADOW_STEEL_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1f, .4f, () -> DDSoundEvents.magic_casing_break.get(),
                    () -> DDSoundEvents.magic_casing_step.get(), () -> DDSoundEvents.magic_casing_place.get(),
                    () -> DDSoundEvents.magic_casing_hit.get(), () -> DDSoundEvents.magic_casing_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(8f,24f))
            .lang("Shadow Casing")
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<RadiantBlockcasing> refined_radiance_casing = REGISTRATE.block("refined_radiance_casing", RadiantBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.REFINED_RADIANCE_CASING))
            .properties(p -> p.color(MaterialColor.SNOW))
            .properties(p -> p.sound(new ForgeSoundType(1f, 1.25f, () -> DDSoundEvents.magic_casing_break.get(),
                    () -> DDSoundEvents.magic_casing_step.get(), () -> DDSoundEvents.magic_casing_place.get(),
                    () -> DDSoundEvents.magic_casing_hit.get(), () -> DDSoundEvents.magic_casing_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(8f,24f))
            .properties(p -> p.lightLevel($ -> 12))
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .lang("Radiant Casing")
            .register();

    public static final BlockEntry<OverchargedBlockcasing> overcharged_casing = REGISTRATE.block("overcharged_casing", OverchargedBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.OVERCHARGED_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1f, 1f, () -> DDSoundEvents.magic_casing_break.get(),
                    () -> DDSoundEvents.magic_casing_step.get(), () -> DDSoundEvents.magic_casing_place.get(),
                    () -> DDSoundEvents.magic_casing_hit.get(), () -> DDSoundEvents.magic_casing_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(6f,32f))
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<BlazeGoldBlockcasing> blaze_gold_casing = REGISTRATE.block("blaze_gold_casing", BlazeGoldBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.BLAZE_GOLD_CASING))
            .properties(p -> p.color(MaterialColor.COLOR_BLACK))
            .properties(p -> p.sound(new ForgeSoundType(1f, 1.1f, () -> DDSoundEvents.magical_metal_break.get(),
                    () -> DDSoundEvents.magical_metal_step.get(), () -> DDSoundEvents.magical_metal_place.get(),
                    () -> DDSoundEvents.magical_metal_hit.get(), () -> DDSoundEvents.magical_metal_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(5f,24f))
            .item()
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .build()
            .register();

    public static final BlockEntry<StargazeBlockcasing> stargaze_singularity_casing = REGISTRATE.block("stargaze_singularity_casing", StargazeBlockcasing::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.STARGAZE_SINGULARITY_CASING))
            .properties(p -> p.color(MaterialColor.SNOW))
            .properties(p -> p.sound(new ForgeSoundType(1f, 0.8f, () -> DDSoundEvents.magic_casing_break.get(),
                    () -> DDSoundEvents.magic_casing_step.get(), () -> DDSoundEvents.magic_casing_place.get(),
                    () -> DDSoundEvents.magic_casing_hit.get(), () -> DDSoundEvents.magic_casing_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .properties(p -> p.strength(12f,250f))
            .properties(p -> p.lightLevel($ -> 8))
            .item()
            .properties(p -> p.rarity(Rarity.EPIC))
            .build()
            .register();

    public static final BlockEntry<CasingBlock> reinforcement_plating = REGISTRATE.block("reinforcement_plating", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> DDBlockSpriteShifts.reinforcement_plating))
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(24f,32f))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> pondering_block_light = REGISTRATE.block("pondering_block_light", Block::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.sound(new ForgeSoundType(1, 2f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.SNOW_STEP, () -> SoundEvents.DEEPSLATE_BRICKS_PLACE,
                    () -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL)))
            .properties(p -> p.strength(1f,5000f))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> pondering_block_dark = REGISTRATE.block("pondering_block_dark", Block::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.sound(new ForgeSoundType(1, 2f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.SNOW_STEP, () -> SoundEvents.DEEPSLATE_BRICKS_PLACE,
                    () -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL)))
            .properties(p -> p.strength(1f,5000f))
            .simpleItem()
            .register();
//ac
    //MECHANICAL BLOCKS

    public static final BlockEntry<PonderBoxBlock> ponder_in_a_box = REGISTRATE.block("ponder_in_a_box", PonderBoxBlock::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.noOcclusion()
            .noCollission())
            .properties(p -> p.sound(new ForgeSoundType(1, 1.5f, () -> SoundEvents.AMETHYST_BLOCK_BREAK,
                    () -> SoundEvents.AMETHYST_BLOCK_CHIME, () -> SoundEvents.SMALL_AMETHYST_BUD_PLACE,
                    () -> SoundEvents.AMETHYST_CLUSTER_STEP, () -> SoundEvents.AMETHYST_BLOCK_CHIME)))
            .properties(p -> p.strength(50f,5000f))
            .simpleItem()
            .register();

    public static final BlockEntry<ReversedGearboxBlock> REVERSED_GEARSHIFT =
            REGISTRATE.block("reversed_gearshift", ReversedGearboxBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<BronzeSawBlock> BRONZE_SAW =
            REGISTRATE.block("bronze_saw", BronzeSawBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setImpact(12.0))
            .onRegister(movementBehaviour(new BronzeSawMovementBehaviour()))
            .addLayer(() -> RenderType::cutoutMipped)
            .simpleItem()
            .register();

    public static final BlockEntry<BronzeDrillBlock> BRONZE_DRILL =
            REGISTRATE.block("bronze_drill", BronzeDrillBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(12.0))
            .onRegister(movementBehaviour(new BronzeDrillMovementBehaviour()))
            .simpleItem()
            .register();

    public static final BlockEntry<IndustrialFanBlock> industrial_fan =
            REGISTRATE.block("industrial_fan", IndustrialFanBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL)
                    .sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setImpact(4.0))
            .transform(BlockStressDefaults.setCapacity(16))
            .simpleItem()
            .register();

    public static final BlockEntry<FurnaceEngineBlock> FURNACE_ENGINE =
            REGISTRATE.block("furnace_engine", FurnaceEngineBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setCapacity(341.5))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FlywheelBlock> FLYWHEEL =
            REGISTRATE.block("flywheel", FlywheelBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(new FlywheelGenerator()::generate)
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<PotatoTurretBlock> POTATO_TURRET =
            REGISTRATE.block("potato_turret", PotatoTurretBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(p -> p.color(MaterialColor.COLOR_BLUE)
                            .sound(SoundType.NETHERITE_BLOCK))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStates(s -> ConfiguredModel.builder()
                                    .modelFile(AssetLookup.partialBaseModel(c, p))
                                    .build()))
                    .transform(pickaxeOnly())
                    .transform(BlockStressDefaults.setImpact(2.0))
                    .simpleItem()
                    .register();

    public static final BlockEntry<RadiantDrillBlock> RADIANT_DRILL =
            REGISTRATE.block("radiant_drill", RadiantDrillBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
                    .addLayer(() -> RenderType::translucent)
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(16.0))
            .onRegister(movementBehaviour(new RadiantDrillMovementBehaviour()))
            .simpleItem()
            .register();

    public static final BlockEntry<ShadowDrillBlock> SHADOW_DRILL =
            REGISTRATE.block("shadow_drill", ShadowDrillBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(16.0))
            .onRegister(movementBehaviour(new ShadowDrillMovementBehaviour()))
            .simpleItem()
            .register();

    public static final BlockEntry<HydraulicPressBlock> hydraulic_press =
            REGISTRATE.block("hydraulic_press", HydraulicPressBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL)
                    .sound(SoundType.COPPER))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setImpact(24.0))
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<KineticMotorBlock> KINETIC_MOTOR =
            REGISTRATE.block("kinetic_motor", KineticMotorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(pickaxeOnly())
            .transform(BlockStressDefaults.setCapacity(1.5))
            .transform(BlockStressDefaults.setGeneratorSpeed(() -> Couple.create(0, 32)))
            .simpleItem()
            .register();

    public static final BlockEntry<AcceleratorMotorBlock> ACCELERATOR_MOTOR =
            REGISTRATE.block("accelerator_motor", AcceleratorMotorBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .transform(pickaxeOnly())
                    .transform(BlockStressDefaults.setCapacity(0))
                    .transform(BlockStressDefaults.setGeneratorSpeed(() -> Couple.create(0, 256)))
                    .simpleItem()
                    .register();

    public static final BlockEntry<CogCrankBlock> cogCrank = REGISTRATE.block("cog_crank", CogCrankBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .transform(BlockStressDefaults.setCapacity(8.0))
            .transform(BlockStressDefaults.setGeneratorSpeed(CogCrankBlock::getSpeedRange))
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.getEntry(), 1)
                    .requires(AllBlocks.HAND_CRANK.get())
                    .requires(AllBlocks.COGWHEEL.get())
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                    .save(prov))
            .onRegister(ItemUseOverrides::addBlock)
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FanSailBlock> splashing_sail =
            REGISTRATE.block("splashing_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.splashing_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FanSailBlock> haunting_sail =
            REGISTRATE.block("haunting_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .properties(p -> p.lightLevel(s -> 8))
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.haunting_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FanSailBlock> smoking_sail =
            REGISTRATE.block("smoking_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .properties(p -> p.lightLevel(s -> 8))
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.smoking_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FanSailBlock> blasting_sail =
            REGISTRATE.block("blasting_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .properties(p -> p.lightLevel(s -> 12))
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.blasting_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FanSailBlock> superheating_sail =
            REGISTRATE.block("superheating_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .properties(p -> p.lightLevel(s -> 15))
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.superheating_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FanSailBlock> freezing_sail =
            REGISTRATE.block("freezing_sail", FanSailBlock::sail)
                    .initialProperties(SharedProperties::wooden)
                    .properties(p -> p.color(MaterialColor.DIRT))
                    .properties(p -> p.sound(SoundType.SCAFFOLDING)
                            .noOcclusion())
                    .transform(axeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(false))
                    .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                    .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                    .tag(DDTags.AllBlockTags.freezing_type.tag)
                    .simpleItem()
                    .register();

    public static final BlockEntry<ChainDriveBlock2> secondary_encased_chain_drive =
            REGISTRATE.block("secondary_encased_chain_drive", ChainDriveBlock2::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.color(MaterialColor.PODZOL))
                    .transform(BlockStressDefaults.setNoImpact())
                    .transform(axeOrPickaxe())
                    .blockstate((c, p) -> new ChainDriveBlockGen((state, suffix) -> p.models()
                            .getExistingFile(p.modLoc("block/" + c.getName() + "/" + suffix))).generate(c, p))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<ChainGearshiftBlock2> secondary_adjustable_chain_gearshift =
            REGISTRATE.block("secondary_adjustable_chain_gearshift", ChainGearshiftBlock2::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.color(MaterialColor.NETHER))
                    .transform(BlockStressDefaults.setNoImpact())
                    .transform(axeOrPickaxe())
                    .blockstate((c, p) -> new ChainDriveBlockGen((state, suffix) -> {
                        String powered = state.getValue(ChainGearshiftBlock2.POWERED) ? "_powered" : "";
                        return p.models()
                                .withExistingParent(c.getName() + "_" + suffix + powered,
                                        p.modLoc("block/secondary_encased_chain_drive/" + suffix))
                                .texture("side", p.modLoc("block/" + c.getName() + powered));
                    }).generate(c, p))
                    .item()
                    .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/secondary_encased_chain_drive/item"))
                            .texture("side", p.modLoc("block/" + c.getName())))
                    .build()
                    .register();

    //Decoratives

    public static final BlockEntry<MetalScaffoldingBlock> TRAIN_SCAFFOLD =
            REGISTRATE.block("train_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("train",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("plates/obsidian")), MaterialColor.COLOR_BLACK,
                            DDBlockSpriteShifts.TRAIN_SCAFFOLD, DDBlockSpriteShifts.TRAIN_SCAFFOLD_INSIDE, AllSpriteShifts.RAILWAY_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> HYDRAULIC_SCAFFOLD =
            REGISTRATE.block("hydraulic_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("hydraulic",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/copper")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.HYDRAULIC_SCAFFOLD, DDBlockSpriteShifts.HYDRAULIC_SCAFFOLD_INSIDE, DDBlockSpriteShifts.HYDRAULIC_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> OVERBURDEN_SCAFFOLD =
            REGISTRATE.block("overburden_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("overburden",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/lapis_alloy")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.OVERBURDEN_SCAFFOLD, DDBlockSpriteShifts.OVERBURDEN_SCAFFOLD_INSIDE, DDBlockSpriteShifts.OVERBURDEN_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> INDUSTRIAL_SCAFFOLD =
            REGISTRATE.block("industrial_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("industrial",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/industrial_iron")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.INDUSTRIAL_SCAFFOLD, DDBlockSpriteShifts.INDUSTRIAL_SCAFFOLD_INSIDE, DDBlockSpriteShifts.INDUSTRIAL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> MITHRIL_SCAFFOLD =
            REGISTRATE.block("mithril_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("mithril",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/mithril")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.MITHRIL_SCAFFOLD, DDBlockSpriteShifts.MITHRIL_SCAFFOLD_INSIDE, DDBlockSpriteShifts.MITHRIL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> MOSSY_ANDESITE_SCAFFOLD =
            REGISTRATE.block("mossy_andesite_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("mossy_andesite", () -> DataIngredient.items(AllItems.ANDESITE_ALLOY.get()),
                            MaterialColor.STONE, DDBlockSpriteShifts.MOSSY_ANDESITE_SCAFFOLD, DDBlockSpriteShifts.MOSSY_ANDESITE_SCAFFOLD_INSIDE,
                            DDBlockSpriteShifts.MOSSY_ANDESITE_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> ZINC_SCAFFOLD =
            REGISTRATE.block("zinc_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("zinc",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/zinc")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.ZINC_SCAFFOLD, DDBlockSpriteShifts.ZINC_SCAFFOLD_INSIDE, DDBlockSpriteShifts.ZINC_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> TIN_SCAFFOLD =
            REGISTRATE.block("tin_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("tin",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/tin")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.TIN_SCAFFOLD, DDBlockSpriteShifts.TIN_SCAFFOLD_INSIDE, DDBlockSpriteShifts.TIN_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> BRONZE_SCAFFOLD =
            REGISTRATE.block("bronze_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("bronze",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/bronze")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.BRONZE_SCAFFOLD, DDBlockSpriteShifts.BRONZE_SCAFFOLD_INSIDE, DDBlockSpriteShifts.BRONZE_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> STEEL_SCAFFOLD =
            REGISTRATE.block("steel_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("steel",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/steel")), MaterialColor.TERRACOTTA_YELLOW,
                            DDBlockSpriteShifts.STEEL_SCAFFOLD, DDBlockSpriteShifts.STEEL_SCAFFOLD_INSIDE, DDBlockSpriteShifts.STEEL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> SHADOW_SCAFFOLD =
            REGISTRATE.block("shadow_steel_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("shadow_steel",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/shadow_steel")), MaterialColor.COLOR_BLACK,
                            DDBlockSpriteShifts.SHADOW_STEEL_SCAFFOLD, DDBlockSpriteShifts.SHADOW_STEEL_SCAFFOLD_INSIDE, DDBlockSpriteShifts.SHADOW_STEEL_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> RADIANT_SCAFFOLD =
            REGISTRATE.block("refined_radiance_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("refined_radiance",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/refined_radiance")), MaterialColor.SNOW,
                            DDBlockSpriteShifts.REFINED_RADIANCE_SCAFFOLD, DDBlockSpriteShifts.REFINED_RADIANCE_SCAFFOLD_INSIDE, DDBlockSpriteShifts.REFINED_RADIANCE_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> STARGAZE_SINGULARITY_SCAFFOLD =
            REGISTRATE.block("stargaze_singularity_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("stargaze_singularity",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/stargaze_singularity")), MaterialColor.TERRACOTTA_BLACK,
                            DDBlockSpriteShifts.STARGAZE_SINGULARITY_SCAFFOLD, DDBlockSpriteShifts.STARGAZE_SINGULARITY_SCAFFOLD_INSIDE, DDBlockSpriteShifts.STARGAZE_SINGULARITY_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> BLAZE_GOLD_SCAFFOLD =
            REGISTRATE.block("blaze_gold_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("blaze_gold",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/blaze_gold")), MaterialColor.COLOR_RED,
                            DDBlockSpriteShifts.BLAZE_GOLD_SCAFFOLD, DDBlockSpriteShifts.BLAZE_GOLD_SCAFFOLD_INSIDE, DDBlockSpriteShifts.BLAZE_GOLD_CASING))
                    .register();
    public static final BlockEntry<OverchargedScaffoldingBlock> OVERCHARGED_SCAFFOLD =
            REGISTRATE.block("overcharged_scaffolding", OverchargedScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("overcharge",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/overcharge")), MaterialColor.COLOR_LIGHT_BLUE,
                            DDBlockSpriteShifts.OVERCHARGED_SCAFFOLD, DDBlockSpriteShifts.OVERCHARGED_SCAFFOLD_INSIDE, DDBlockSpriteShifts.OVERCHARGED_CASING))
                    .register();
    public static final BlockEntry<MetalScaffoldingBlock> NETHERITE_SCAFFOLD =
            REGISTRATE.block("netherite_scaffolding", MetalScaffoldingBlock::new)
                    .transform(BuilderTransformers.scaffold("netherite",
                            () -> DataIngredient.tag(AllTags.forgeItemTag("ingots/netherite")), MaterialColor.COLOR_LIGHT_BLUE,
                            DDBlockSpriteShifts.NETHERITE_SCAFFOLD, DDBlockSpriteShifts.NETHERITE_SCAFFOLD_INSIDE, DDBlockSpriteShifts.NETHERITE_CASING))
                    .register();

    public static final BlockEntry<BlockcopycatBlock> COPYCAT_BlOCK =
            REGISTRATE.block("copycat_block", BlockcopycatBlock::new)
                    .transform(BuilderTransformers.copycat())
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .onRegister(CreateRegistrate.blockModel(() -> BlockcopycatBlockModel::new))
                    .item()
                    .transform(customItemModel("copycat_base", "block"))
                    .register();

    public static final BlockEntry<BlockcopycatSlab> COPYCAT_SLAB =
            REGISTRATE.block("copycat_slab", BlockcopycatSlab::new)
                    .transform(BuilderTransformers.copycat())
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .onRegister(CreateRegistrate.blockModel(() -> BlockcopycatSlabModel::new))
                    .item()
                    .transform(customItemModel("copycat_base", "slab"))
                    .register();

    public static final BlockEntry<SpectralRubyLampBlock> SPECTRAL_RUBY_LAMP =
            REGISTRATE.block("spectral_ruby_lamp", SpectralRubyLampBlock::new)
                    .initialProperties(() -> Blocks.REDSTONE_LAMP)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_PINK)
                            .noOcclusion()
                            .lightLevel(s -> s.getValue(SpectralRubyLampBlock.POWER)))
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<RotatedPillarBlock> SPECTRAL_RUBY_BLOCK =
            REGISTRATE.block("spectral_ruby_block", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.AMETHYST_BLOCK)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_MAGENTA)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
            .transform(pickaxeOnly())
            .simpleItem()
            .register();

    public static final BlockEntry<Block> SPECTRAL_RUBY_TILES =
            REGISTRATE.block("spectral_ruby_tiles", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_MAGENTA))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .simpleItem()
            .register();

    public static final BlockEntry<Block> SMALL_SPECTRAL_RUBY_TILES =
            REGISTRATE.block("small_spectral_ruby_tiles", Block::new)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_MAGENTA))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .transform(pickaxeOnly())
            .simpleItem()
            .register();

    public static final BlockEntry<GlassBlock> vertical_framed_split_glass = REGISTRATE.block("vertical_framed_split_glass", GlassBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.vertical_framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();
    public static final BlockEntry<ConnectedGlassPaneBlock> vertical_framed_split_glass_pane = REGISTRATE.block("vertical_framed_split_glass_pane", ConnectedGlassPaneBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.vertical_framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();
    public static final BlockEntry<GlassBlock> horizontal_framed_split_glass = REGISTRATE.block("horizontal_framed_split_glass", GlassBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.horizontal_framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();
    public static final BlockEntry<ConnectedGlassPaneBlock> horizontal_framed_split_glass_pane = REGISTRATE.block("horizontal_framed_split_glass_pane", ConnectedGlassPaneBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.horizontal_framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();
    public static final BlockEntry<GlassBlock> framed_split_glass = REGISTRATE.block("framed_split_glass", GlassBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();
    public static final BlockEntry<ConnectedGlassPaneBlock> framed_split_glass_pane = REGISTRATE.block("framed_split_glass_pane", ConnectedGlassPaneBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.framed_split_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .register();

    public static final BlockEntry<GlassBlock> ornate_iron_glass = REGISTRATE.block("ornate_iron_glass", GlassBlock::new)
            .transform(BuilderTransgender.blockv2(() -> DDBlockSpriteShifts.ornate_iron_glass, () -> DDBlockSpriteShifts.ornate_iron_glass_top))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .register();
    public static final BlockEntry<ConnectedGlassPaneBlock> ornate_iron_glass_pane = REGISTRATE.block("ornate_iron_glass_pane", ConnectedGlassPaneBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.ornate_iron_glass))
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::cutoutMipped)
            .register();

    public static final BlockEntry<Block> blueprint_block = REGISTRATE.block("blueprint_block", Block::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.blueprint_block))
            .initialProperties(() -> Blocks.HAY_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(1, 0.85f, () -> SoundEvents.PAINTING_BREAK,
                    () -> SoundEvents.MOSS_STEP, () -> SoundEvents.PAINTING_PLACE,
                    () -> SoundEvents.BAMBOO_HIT, () -> SoundEvents.MOSS_STEP)))
            .properties(p -> p.strength(0.05f,0.5f))
            .lang("Block of Blueprint")
            .register();

    public static final BlockEntry<CarpetBlock> blueprint_carpet = REGISTRATE.block("blueprint_carpet", CarpetBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.blueprint_block))
            .initialProperties(() -> Blocks.HAY_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_BLUE)
                    .noOcclusion())
            .properties(p -> p.sound(new ForgeSoundType(1, 0.85f, () -> SoundEvents.PAINTING_BREAK,
                    () -> SoundEvents.MOSS_STEP, () -> SoundEvents.PAINTING_PLACE,
                    () -> SoundEvents.BAMBOO_HIT, () -> SoundEvents.MOSS_STEP)))
            .properties(p -> p.strength(0.05f,0.5f))
            .lang("Block of Blueprint")
            .register();

    public static final BlockEntry<MysteriousCarpetBlock> mysterious_blueprint_carpet = REGISTRATE.block("mysterious_blueprint_carpet", MysteriousCarpetBlock::new)
            .transform(BuilderTransgender.block(() -> DDBlockSpriteShifts.blueprint_block))
            .initialProperties(() -> Blocks.HAY_BLOCK)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_BLUE)
                    .noOcclusion()
                    .noCollission())
            .properties(p -> p.sound(new ForgeSoundType(1, 0.85f, () -> SoundEvents.PAINTING_BREAK,
                    () -> SoundEvents.MOSS_STEP, () -> SoundEvents.PAINTING_PLACE,
                    () -> SoundEvents.BAMBOO_HIT, () -> SoundEvents.MOSS_STEP)))
            .properties(p -> p.strength(0.05f,0.5f))
            .lang("Block of Blueprint")
            .register();

    //WOODSET BLOCKS


    //ROSE WOODSET

    public static final BlockEntry<CanBurnRotatedBlockPillar> rose_log = REGISTRATE.block("rose_log", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Log")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> stripped_rose_log = REGISTRATE.block("stripped_rose_log", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Stripped Rose Log")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> rose_wood = REGISTRATE.block("rose_wood", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> stripped_rose_wood = REGISTRATE.block("stripped_rose_wood", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Stripped Rose Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnBlock> rose_planks = REGISTRATE.block("rose_planks", CanBurnBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Planks")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnSlabBlock> rose_slab = REGISTRATE.block("rose_slab", CanBurnSlabBlock::new)
            .initialProperties(() -> Blocks.OAK_SLAB)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Slab")
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> rose_stairs = REGISTRATE.block("rose_stairs", p -> new StairBlock(DDBlocks.rose_planks::getDefaultState, p))
            .initialProperties(() -> Blocks.OAK_STAIRS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Stairs")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnFenceBlock> rose_fence = REGISTRATE.block("rose_fence", CanBurnFenceBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Fence")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnFenceGateBlock> rose_fence_gate = REGISTRATE.block("rose_fence_gate", CanBurnFenceGateBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE_GATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Fence Gate")
            .simpleItem()
            .register();
    public static final BlockEntry<YIPPEESlidingDoorBlock> rose_door =
            REGISTRATE.block("rose_door", p -> new YIPPEESlidingDoorBlock(p, true))
                    .initialProperties(() -> Blocks.OAK_DOOR)
                    .transform(BuilderTransgender.slidingDoor("rose"))
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_RED)
                            .sound(SoundType.WOOD)
                            .noOcclusion())
                    .register();
    public static final BlockEntry<CanBurnTrapDoorBlock> rose_trapdoor = REGISTRATE.block("rose_trapdoor", CanBurnTrapDoorBlock::new)
            .initialProperties(() -> Blocks.OAK_TRAPDOOR)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED)
                    .noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .lang("Rose Trapdoor")
            .simpleItem()
            .register();
    public static final BlockEntry<WoodButtonBlock> rose_button = REGISTRATE.block("rose_button", WoodButtonBlock::new)
            .initialProperties(() -> Blocks.OAK_BUTTON)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Button")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnPressurePlateBlock> rose_pressure_plate = REGISTRATE.block("rose_pressure_plate", p -> new CanBurnPressurePlateBlock(CanBurnPressurePlateBlock.Sensitivity.EVERYTHING, p))
            .initialProperties(() -> Blocks.OAK_PRESSURE_PLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .lang("Rose Pressure Plate")
            .simpleItem()
            .register();


    //SMOKED WOODSET

    public static final BlockEntry<CanBurnRotatedBlockPillar> smoked_log = REGISTRATE.block("smoked_log", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Log")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> stripped_smoked_log = REGISTRATE.block("stripped_smoked_log", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Smoked Log")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> smoked_wood = REGISTRATE.block("smoked_wood", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnRotatedBlockPillar> stripped_smoked_wood = REGISTRATE.block("stripped_smoked_wood", CanBurnRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Stripped Smoked Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnBlock> smoked_planks = REGISTRATE.block("smoked_planks", CanBurnBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Planks")
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> smoked_stairs = REGISTRATE.block("smoked_stairs", p -> new StairBlock(DDBlocks.smoked_planks::getDefaultState, p))
            .initialProperties(() -> Blocks.OAK_STAIRS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Stairs")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnSlabBlock> smoked_slab = REGISTRATE.block("smoked_slab", CanBurnSlabBlock::new)
            .initialProperties(() -> Blocks.OAK_SLAB)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Slab")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnFenceBlock> smoked_fence = REGISTRATE.block("smoked_fence", CanBurnFenceBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Fence")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnFenceGateBlock> smoked_fence_gate = REGISTRATE.block("smoked_fence_gate", CanBurnFenceGateBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE_GATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Fence Gate")
            .simpleItem()
            .register();
    public static final BlockEntry<YIPPEESlidingDoorBlock> smoked_door =
            REGISTRATE.block("smoked_door", p -> new YIPPEESlidingDoorBlock(p, true))
                    .initialProperties(() -> Blocks.OAK_DOOR)
                    .transform(BuilderTransgender.slidingDoor("smoked"))
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN)
                            .sound(SoundType.WOOD)
                            .noOcclusion())
                    .register();
    public static final BlockEntry<CanBurnTrapDoorBlock> smoked_trapdoor = REGISTRATE.block("smoked_trapdoor", CanBurnTrapDoorBlock::new)
            .initialProperties(() -> Blocks.OAK_TRAPDOOR)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN)
                    .noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .lang("Smoked Trapdoor")
            .simpleItem()
            .register();
    public static final BlockEntry<WoodButtonBlock> smoked_button = REGISTRATE.block("smoked_button", WoodButtonBlock::new)
            .initialProperties(() -> Blocks.OAK_BUTTON)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Button")
            .simpleItem()
            .register();
    public static final BlockEntry<CanBurnPressurePlateBlock> smoked_pressure_plate = REGISTRATE.block("smoked_pressure_plate", p -> new CanBurnPressurePlateBlock(CanBurnPressurePlateBlock.Sensitivity.EVERYTHING, p))
            .initialProperties(() -> Blocks.OAK_PRESSURE_PLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .lang("Smoked Pressure Plate")
            .simpleItem()
            .register();

    //SPIRIT WOODSET

    public static final BlockEntry<NormalLogRotatedBlockPillar> spirit_log = REGISTRATE.block("spirit_log", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.WARPED_STEM)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Log")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> stripped_spirit_log = REGISTRATE.block("stripped_spirit_log", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_WARPED_STEM)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Stripped Spirit Log")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> spirit_wood = REGISTRATE.block("spirit_wood", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.WARPED_HYPHAE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> stripped_spirit_wood = REGISTRATE.block("stripped_spirit_wood", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_WARPED_HYPHAE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Stripped Spirit Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<Block> spirit_planks = REGISTRATE.block("spirit_planks", Block::new)
            .initialProperties(() -> Blocks.WARPED_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Planks")
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> spirit_stairs = REGISTRATE.block("spirit_stairs", p -> new StairBlock(DDBlocks.spirit_planks::getDefaultState, p))
            .initialProperties(() -> Blocks.WARPED_STAIRS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Stairs")
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> spirit_slab = REGISTRATE.block("spirit_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.WARPED_SLAB)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Slab")
            .simpleItem()
            .register();
    public static final BlockEntry<FenceBlock> spirit_fence = REGISTRATE.block("spirit_fence", FenceBlock::new)
            .initialProperties(() -> Blocks.WARPED_FENCE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Fence")
            .simpleItem()
            .register();
    public static final BlockEntry<FenceGateBlock> spirit_fence_gate = REGISTRATE.block("spirit_fence_gate", FenceGateBlock::new)
            .initialProperties(() -> Blocks.WARPED_FENCE_GATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Fence Gate")
            .simpleItem()
            .register();
    public static final BlockEntry<YIPPEESlidingDoorBlock> spirit_door =
            REGISTRATE.block("spirit_door", p -> new YIPPEESlidingDoorBlock(p, true))
                    .initialProperties(() -> Blocks.WARPED_DOOR)
                    .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                            () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                            () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
                    .transform(BuilderTransgender.slidingDoor("spirit"))
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE)
                            .noOcclusion())
                    .register();
    public static final BlockEntry<TrapDoorBlock> spirit_trapdoor = REGISTRATE.block("spirit_trapdoor", TrapDoorBlock::new)
            .initialProperties(() -> Blocks.WARPED_TRAPDOOR)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE)
                    .noOcclusion())
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .addLayer(() -> RenderType::cutoutMipped)
            .lang("Spirit Trapdoor")
            .simpleItem()
            .register();
    public static final BlockEntry<WoodButtonBlock> spirit_button = REGISTRATE.block("spirit_button", WoodButtonBlock::new)
            .initialProperties(() -> Blocks.WARPED_BUTTON)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Button")
            .simpleItem()
            .register();
    public static final BlockEntry<PressurePlateBlock> spirit_pressure_plate = REGISTRATE.block("spirit_pressure_plate", p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p))
            .initialProperties(() -> Blocks.WARPED_PRESSURE_PLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_PURPLE))
            .properties(p -> p.sound(new ForgeSoundType(1, .7f, () -> SoundEvents.WOOD_BREAK,
                    () -> SoundEvents.STEM_STEP, () -> SoundEvents.WOOD_PLACE,
                    () -> SoundEvents.STEM_HIT, () -> SoundEvents.STEM_FALL)))
            .lang("Spirit Pressure Plate")
            .simpleItem()
            .register();

    //RUBBER WOODSET

    public static final BlockEntry<SaplingBlock> rubber_sapling = REGISTRATE.block("rubber_sapling", p -> new SaplingBlock(new OakTreeGrower(), p))
            .initialProperties(() -> Blocks.OAK_SAPLING)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Sapling")
            .addLayer(() -> RenderType::cutoutMipped)
            .simpleItem()
            .register();

    public static final BlockEntry<LeavesBlock> rubber_leaves = REGISTRATE.block("rubber_leaves", LeavesBlock::new)
            .initialProperties(() -> Blocks.AZALEA_LEAVES)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Leaves")
            .simpleItem()
            .register();

    public static final BlockEntry<NormalLogRotatedBlockPillar> rubber_log = REGISTRATE.block("rubber_log", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Log")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> stripped_rubber_log = REGISTRATE.block("stripped_rubber_log", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_LOG)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Stripped Rubber Log")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> rubber_wood = REGISTRATE.block("rubber_wood", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<NormalLogRotatedBlockPillar> stripped_rubber_wood = REGISTRATE.block("stripped_rubber_wood", NormalLogRotatedBlockPillar::new)
            .initialProperties(() -> Blocks.STRIPPED_OAK_WOOD)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Stripped Rubber Wood")
            .simpleItem()
            .register();
    public static final BlockEntry<Block> rubber_planks = REGISTRATE.block("rubber_planks", Block::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Planks")
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> rubber_slab = REGISTRATE.block("rubber_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.OAK_SLAB)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Slab")
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> rubber_stairs = REGISTRATE.block("rubber_stairs", p -> new StairBlock(DDBlocks.rubber_planks::getDefaultState, p))
            .initialProperties(() -> Blocks.OAK_STAIRS)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Stairs")
            .simpleItem()
            .register();
    public static final BlockEntry<FenceBlock> rubber_fence = REGISTRATE.block("rubber_fence", FenceBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Fence")
            .simpleItem()
            .register();
    public static final BlockEntry<FenceGateBlock> rubber_fence_gate = REGISTRATE.block("rubber_fence_gate", FenceGateBlock::new)
            .initialProperties(() -> Blocks.OAK_FENCE_GATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Fence Gate")
            .simpleItem()
            .register();
    public static final BlockEntry<DoorBlock> rubber_door =
            REGISTRATE.block("rubber_door", DoorBlock::new)
                    .initialProperties(() -> Blocks.OAK_DOOR)
                    .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN)
                            .sound(SoundType.WOOD)
                            .noOcclusion())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .simpleItem()
                    .register();
    public static final BlockEntry<TrapDoorBlock> rubber_trapdoor = REGISTRATE.block("rubber_trapdoor", TrapDoorBlock::new)
            .initialProperties(() -> Blocks.OAK_TRAPDOOR)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN)
                    .noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .lang("Rubber Trapdoor")
            .simpleItem()
            .register();
    public static final BlockEntry<WoodButtonBlock> rubber_button = REGISTRATE.block("rubber_button", WoodButtonBlock::new)
            .initialProperties(() -> Blocks.OAK_BUTTON)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Button")
            .simpleItem()
            .register();
    public static final BlockEntry<PressurePlateBlock> rubber_pressure_plate = REGISTRATE.block("rubber_pressure_plate", p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p))
            .initialProperties(() -> Blocks.OAK_PRESSURE_PLATE)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .lang("Rubber Pressure Plate")
            .simpleItem()
            .register();

    
    
    
    
    public static final BlockEntry<Block> asphalt_block = REGISTRATE.block("asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .lang("Asphalt Block")
            .simpleItem()
            .register();

    public static final BlockEntry<HazardBlock> hazard_block = REGISTRATE.block("hazard_block", HazardBlock::new)
            .transform(BuilderTransgender.hazard(() -> DDBlockSpriteShifts.HAZARD))
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(0.8F)
                    .jumpFactor(0.8F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .lang("Hazard Block")
            .register();

    public static final BlockEntry<HazardBlock> horizontal_hazard_block = REGISTRATE.block("horizontal_hazard_block", HazardBlock::new)
            .transform(BuilderTransgender.hazard(() -> DDBlockSpriteShifts.HORIZONTAL_HAZARD))
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(0.8F)
                    .jumpFactor(0.8F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .lang("Horizontal Hazard Block")
            .register();

    public static final BlockEntry<HazardBlock> hazard_block_r = REGISTRATE.block("hazard_block_r", HazardBlock::new)
            .transform(BuilderTransgender.hazard(() -> DDBlockSpriteShifts.HAZARD_R))
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(0.8F)
                    .jumpFactor(0.8F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .lang("Hazard Block")
            .register();

    public static final BlockEntry<HazardBlock> horizontal_hazard_block_r = REGISTRATE.block("horizontal_hazard_block_r", HazardBlock::new)
            .transform(BuilderTransgender.hazard(() -> DDBlockSpriteShifts.HORIZONTAL_HAZARD_R))
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(0.8F)
                    .jumpFactor(0.8F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .lang("Horizontal Hazard Block")
            .register();

    public static final BlockEntry<Block> andesite_asphalt_block = REGISTRATE.block("andesite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> asurine_asphalt_block = REGISTRATE.block("asurine_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.COLOR_BLUE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> calcite_asphalt_block = REGISTRATE.block("calcite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_WHITE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> crimsite_asphalt_block = REGISTRATE.block("crimsite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.COLOR_RED))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> deepslate_asphalt_block = REGISTRATE.block("deepslate_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.DEEPSLATE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> diorite_asphalt_block = REGISTRATE.block("diorite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.QUARTZ))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> dripstone_asphalt_block = REGISTRATE.block("dripstone_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DRIPSTONE_BLOCK))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> gabbro_asphalt_block = REGISTRATE.block("gabbro_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_LIGHT_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> granite_asphalt_block = REGISTRATE.block("granite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> limestone_asphalt_block = REGISTRATE.block("limestone_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.SAND))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ochrum_asphalt_block = REGISTRATE.block("ochrum_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_YELLOW))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
            .simpleItem()
            .register();

        public static final BlockEntry<Block> potassic_asphalt_block = REGISTRATE.block("potassic_asphalt_block", Block::new)
                .properties(p -> p.destroyTime(1.25f)
                        .speedFactor(1.2F)
                        .jumpFactor(1.2F)
                        .friction(0.6F)
                        .color(MaterialColor.TERRACOTTA_BLUE))
                .properties(p -> p.sound(new ForgeSoundType(0.8f, 0.85f, () -> DDSoundEvents.ore_stone_break.get(),
                        () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                        () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
                .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                .simpleItem()
                .register();

    public static final BlockEntry<Block> aethersite_asphalt_block = REGISTRATE.block("aethersite_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(0.8f, 1.5f, () -> DDSoundEvents.ore_stone_break.get(),
                    () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                    () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> scorchia_asphalt_block = REGISTRATE.block("scorchia_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> scoria_asphalt_block = REGISTRATE.block("scoria_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.COLOR_BROWN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> tuff_asphalt_block = REGISTRATE.block("tuff_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> veridium_asphalt_block = REGISTRATE.block("veridium_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.WARPED_NYLIUM))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> weathered_limestone_asphalt_block = REGISTRATE.block("weathered_limestone_asphalt_block", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .speedFactor(1.2F)
                    .jumpFactor(1.2F)
                    .friction(0.6F)
                    .color(MaterialColor.COLOR_LIGHT_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> andesite_mossy_bricks = REGISTRATE.block("andesite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.STONE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> asurine_mossy_bricks = REGISTRATE.block("asurine_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.COLOR_BLUE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> calcite_mossy_bricks = REGISTRATE.block("calcite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_WHITE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> crimsite_mossy_bricks = REGISTRATE.block("crimsite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.COLOR_RED))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> deepslate_mossy_bricks = REGISTRATE.block("deepslate_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.DEEPSLATE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> diorite_mossy_bricks = REGISTRATE.block("diorite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.QUARTZ))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> dripstone_mossy_bricks = REGISTRATE.block("dripstone_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_BROWN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DRIPSTONE_BLOCK))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> gabbro_mossy_bricks = REGISTRATE.block("gabbro_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_LIGHT_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> granite_mossy_bricks = REGISTRATE.block("granite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> limestone_mossy_bricks = REGISTRATE.block("limestone_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.SAND))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ochrum_mossy_bricks = REGISTRATE.block("ochrum_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_YELLOW))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> potassic_mossy_bricks = REGISTRATE.block("potassic_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(0.8f, 0.85f, () -> DDSoundEvents.ore_stone_break.get(),
                    () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                    () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> aethersite_mossy_bricks = REGISTRATE.block("aethersite_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(0.8f, 1.5f, () -> DDSoundEvents.ore_stone_break.get(),
                    () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                    () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> scorchia_mossy_bricks = REGISTRATE.block("scorchia_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> scoria_mossy_bricks = REGISTRATE.block("scoria_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.COLOR_BROWN))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> tuff_mossy_bricks = REGISTRATE.block("tuff_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.TERRACOTTA_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> veridium_mossy_bricks = REGISTRATE.block("veridium_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.WARPED_NYLIUM))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> weathered_limestone_mossy_bricks = REGISTRATE.block("weathered_limestone_mossy_bricks", Block::new)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.COLOR_LIGHT_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<Block> polished_bronze_block =
            REGISTRATE.block("bronze_polished_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(6f,5f))
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> polished_bronze_stairs =
            REGISTRATE.block("bronze_polished_stairs", p -> new StairBlock(DDBlocks.polished_bronze_block::getDefaultState, p))
            .initialProperties(DDBlocks.polished_bronze_block)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> polished_bronze_slab =
            REGISTRATE.block("bronze_polished_slab", SlabBlock::new)
            .initialProperties(DDBlocks.polished_bronze_block)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> tiled_bronze_block =
            REGISTRATE.block("bronze_tiled_block", Block::new)
            .initialProperties(DDBlocks.polished_bronze_block)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> tiled_bronze_stairs =
            REGISTRATE.block("bronze_tiled_stairs", p -> new StairBlock(DDBlocks.tiled_bronze_block::getDefaultState, p))
            .initialProperties(DDBlocks.polished_bronze_block)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> tiled_bronze_slab =
            REGISTRATE.block("bronze_tiled_slab", SlabBlock::new)
            .initialProperties(DDBlocks.polished_bronze_block)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> polished_steel_block =
            REGISTRATE.block("steel_polished_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .properties(p -> p.strength(3f,8f))
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> polished_steel_stairs =
            REGISTRATE.block("steel_polished_stairs", p -> new StairBlock(DDBlocks.polished_steel_block::getDefaultState, p))
            .initialProperties(DDBlocks.polished_steel_block)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> polished_steel_slab =
            REGISTRATE.block("steel_polished_slab", SlabBlock::new)
            .initialProperties(DDBlocks.polished_steel_block)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> tiled_steel_block =
            REGISTRATE.block("steel_tiled_block", Block::new)
            .initialProperties(DDBlocks.polished_steel_block)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> tiled_steel_stairs =
            REGISTRATE.block("steel_tiled_stairs", p -> new StairBlock(DDBlocks.tiled_steel_block::getDefaultState, p))
            .initialProperties(DDBlocks.polished_steel_block)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> tiled_steel_slab =
            REGISTRATE.block("steel_tiled_slab", SlabBlock::new)
            .initialProperties(DDBlocks.polished_steel_block)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> polished_zinc_block =
            REGISTRATE.block("zinc_polished_block", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> polished_zinc_stairs =
            REGISTRATE.block("zinc_polished_stairs", p -> new StairBlock(DDBlocks.polished_zinc_block::getDefaultState, p))
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> polished_zinc_slab =
            REGISTRATE.block("zinc_polished_slab", SlabBlock::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> tiled_zinc_block =
            REGISTRATE.block("zinc_tiled_block", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> tiled_zinc_stairs =
            REGISTRATE.block("zinc_tiled_stairs", p -> new StairBlock(DDBlocks.tiled_zinc_block::getDefaultState, p))
                    .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> tiled_zinc_slab =
            REGISTRATE.block("zinc_tiled_slab", SlabBlock::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> polished_andesite_alloy_block =
            REGISTRATE.block("andesite_alloy_polished_block", Block::new)
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> polished_andesite_alloy_stairs =
            REGISTRATE.block("andesite_alloy_polished_stairs", p -> new StairBlock(DDBlocks.polished_andesite_alloy_block::getDefaultState, p))
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> polished_andesite_alloy_slab =
            REGISTRATE.block("andesite_alloy_polished_slab", SlabBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> tiled_andesite_alloy_block =
            REGISTRATE.block("andesite_alloy_tiled_block", Block::new)
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();
    public static final BlockEntry<StairBlock> tiled_andesite_alloy_stairs =
            REGISTRATE.block("andesite_alloy_tiled_stairs", p -> new StairBlock(DDBlocks.tiled_andesite_alloy_block::getDefaultState, p))
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();
    public static final BlockEntry<SlabBlock> tiled_andesite_alloy_slab =
            REGISTRATE.block("andesite_alloy_tiled_slab", SlabBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> potassic_cobble =
            REGISTRATE.block("potassic_cobble", Block::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(2.25f).color(MaterialColor.TERRACOTTA_BLUE))
                    .properties(p -> p.sound(new ForgeSoundType(0.8f, 0.85f, () -> DDSoundEvents.ore_stone_break.get(),
                            () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                            () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> asurine_cobble =
            REGISTRATE.block("asurine_cobble", Block::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(2.25f).color(MaterialColor.COLOR_BLUE))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> crimsite_cobble =
            REGISTRATE.block("crimsite_cobble", Block::new)
                    .initialProperties(() -> Blocks.DEEPSLATE)
                    .properties(p -> p.destroyTime(2.25f).color(MaterialColor.COLOR_RED))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> ochrum_cobble =
            REGISTRATE.block("ochrum_cobble", Block::new)
                    .initialProperties(() -> Blocks.CALCITE)
                    .properties(p -> p.destroyTime(2.25f).color(MaterialColor.TERRACOTTA_YELLOW))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<Block> veridium_cobble =
            REGISTRATE.block("veridium_cobble", Block::new)
                    .initialProperties(() -> Blocks.TUFF)
                    .properties(p -> p.destroyTime(2.25f).color(MaterialColor.WARPED_NYLIUM))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .simpleItem()
                    .register();

    public static final BlockEntry<TwoBladeFanBlock> two_blade_fan =
            REGISTRATE.block("2_blade_fan", TwoBladeFanBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FourBladeFanBlock> four_blade_fan =
            REGISTRATE.block("4_blade_fan", FourBladeFanBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<EightBladeFanBlock> eight_blade_fan =
            REGISTRATE.block("8_blade_fan", EightBladeFanBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {
    }
}
