package dev.lopyluna.dndesires.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.blocks.BoreBlockMovementBehavior;
import dev.lopyluna.dndesires.content.blocks.FanSailBlock;
import dev.lopyluna.dndesires.content.blocks.hydraulic_press.HydraulicPressBlock;
import dev.lopyluna.dndesires.content.blocks.inverse_gearshift.InverseGearshiftBlock;
import dev.lopyluna.dndesires.content.blocks.omni_speed_controller.OmniSpeedControllerBlock;
import dev.lopyluna.dndesires.content.blocks.roll_table.RollTableBlock;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.dndesires.content.blocks.stirling_engine.flywheel.PoweredFlywheelBlock;
import dev.lopyluna.dndesires.content.configs.server.kinetics.DStress;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.util.DeferredSoundType;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndesires.DnDesires.REG;

@SuppressWarnings({"removal", "unused"})
public class DesiresBlocks {

    public static final BlockEntry<OmniSpeedControllerBlock> OMNI_SPEED_CONTROLLER = REG.block("omni_speed_controller", OmniSpeedControllerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_BROWN))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(DStress.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern(" C ")
                    .pattern("CBC")
                    .pattern(" C ")
                    .define('C', AllBlocks.COGWHEEL.get())
                    .define('B', AllBlocks.ROTATION_SPEED_CONTROLLER.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<InverseGearshiftBlock> INVERSE_GEARSHIFT = REG.block("inverse_gearshift", InverseGearshiftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(DStress.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.COGWHEEL.get()).requires(AllBlocks.ANDESITE_CASING.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<RollTableBlock> ROLL_TABLE = REG.block("roll_table", RollTableBlock::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .transform(pickaxeOnly())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("A")
                    .pattern("B")
                    .define('A', AllItems.IRON_SHEET.get())
                    .define('B', AllBlocks.DEPOT.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.standardModel(c, p)))
            .simpleItem()
            .register();

    public static final BlockEntry<HydraulicPressBlock> HYDRAULIC_PRESS = REG.block("hydraulic_press", HydraulicPressBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.COLOR_ORANGE))
            .transform(pickaxeOnly())
            .transform(DStress.setImpact(16.0))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("A")
                    .pattern("B")
                    .pattern("C")
                    .define('A', AllBlocks.FLUID_TANK.get())
                    .define('B', AllBlocks.MECHANICAL_PRESS.get())
                    .define('C', Items.COPPER_BLOCK)
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .addLayer(() -> RenderType::cutoutMipped)
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<StirlingEngineBlock> STIRLING_ENGINE = REG.block("stirling_engine", StirlingEngineBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN).forceSolidOn())
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(DStress.setCapacity(1024.0))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("A")
                    .pattern("B")
                    .pattern("C")
                    .define('A', AllItems.BRASS_SHEET.get())
                    .define('B', AllItems.ANDESITE_ALLOY.get())
                    .define('C', AllBlocks.ZINC_BLOCK.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .onRegister(BlockStressValues.setGeneratorSpeed(32, true))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<PoweredFlywheelBlock> POWERED_FLYWHEEL = REG.block("powered_flywheel", PoweredFlywheelBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_YELLOW).forceSolidOn())
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(false))
            .loot((lt, block) -> lt.dropOther(block, AllBlocks.FLYWHEEL.get()))
            .register();

    public static final TagKey<Item> ASPHALT_BLOCKS = DesiresTags.modItemTag("asphalts");

    public static final BlockEntry<Block> ASPHALT_BLOCK = REG.block("asphalt", Block::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.destroyTime(1.25f).speedFactor(0.995F).jumpFactor(1.25F).friction(0.5F).mapColor(MapColor.COLOR_BLACK).sound(SoundType.POLISHED_DEEPSLATE))
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag, DesiresTags.modBlockTag("asphalts"))
            .blockstate((c, p) -> {
                var model = p.cubeAll(c.get());
                p.simpleBlockWithItem(c.get(), model);
            }).item()
            .tag(ASPHALT_BLOCKS)
            .build()
            .register();

    public static final DyedBlockList<Block> DYED_ASPHALT_BLOCK = new DyedBlockList<>(color -> {
        var colorName = color.getSerializedName();
        return REG.block(colorName + "_asphalt", Block::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.destroyTime(1.25f).speedFactor(0.995F).jumpFactor(1.25F).friction(0.5F).mapColor(color.getMapColor()).sound(SoundType.POLISHED_DEEPSLATE))
                .transform(pickaxeOnly())
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                        .pattern("AAA").pattern("ABA").pattern("AAA").define('A', ASPHALT_BLOCKS).define('B', color.getTag())
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDesires.loc("crafting/" + c.getName())))
                .tag(DesiresTags.BlockTags.DYED_BLOCKS.tag, AllTags.AllBlockTags.WRENCH_PICKUP.tag, DesiresTags.modBlockTag("asphalts"))
                .blockstate((c, p) -> p.simpleBlockWithItem(c.get(), p.models().withExistingParent(colorName + "_asphalt", p.modLoc("block/asphalt")).texture("all", p.modLoc("block/asphalt/" + colorName))))
                .item()
                .tag(DesiresTags.ItemTags.DYED_BLOCKS.tag, ASPHALT_BLOCKS)
                .build()
                .register();
    });

    public static final TagKey<Item> BORE_BLOCKS = DesiresTags.modItemTag("bore_blocks");

    public static final BlockEntry<Block> BORE_BLOCK = REG.block("bore_block", Block::new)
            .initialProperties(SharedProperties::netheriteMetal)
            .properties(p -> p.mapColor(MapColor.STONE).sound(new DeferredSoundType(0.9f, 1.25f, () -> SoundEvents.NETHERITE_BLOCK_BREAK,
                    () -> SoundEvents.NETHERITE_BLOCK_STEP, () -> SoundEvents.NETHERITE_BLOCK_PLACE,
                    () -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL)))
            .onRegister(movementBehaviour(new BoreBlockMovementBehavior()))
            .transform(pickaxeOnly())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                    .pattern("AIA")
                    .pattern("ICI")
                    .pattern("AIA")
                    .define('A', AllItems.ANDESITE_ALLOY.get())
                    .define('C', AllBlocks.ANDESITE_ALLOY_BLOCK.get())
                    .define('I', Items.IRON_INGOT)
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .blockstate((c, p) -> {
                var model = p.cubeAll(c.get());
                p.simpleBlockWithItem(c.get(), model);
            })
            .item()
            .tag(BORE_BLOCKS)
            .build()
            .register();

    public static final DyedBlockList<Block> DYED_BORE_BLOCK = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_bore_block", Block::new)
                .initialProperties(SharedProperties::netheriteMetal)
                .properties(p -> p.mapColor(color.getMapColor()).sound(new DeferredSoundType(0.9f, 1.25f, () -> SoundEvents.NETHERITE_BLOCK_BREAK,
                        () -> SoundEvents.NETHERITE_BLOCK_STEP, () -> SoundEvents.NETHERITE_BLOCK_PLACE,
                        () -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL)))
                .onRegister(movementBehaviour(new BoreBlockMovementBehavior()))
                .transform(pickaxeOnly())
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .define('A', BORE_BLOCKS)
                        .define('B', color.getTag())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDesires.loc("crafting/" + c.getName())))
                .tag(DesiresTags.BlockTags.DYED_BLOCKS.tag)
                .blockstate((c, p) -> {
                    var model = p.models().withExistingParent(colorName + "_bore_block", p.modLoc("block/bore_block"))
                            .texture("all", p.modLoc("block/bore_block/" + colorName));
                    p.simpleBlockWithItem(c.get(), model);
                })
                .item()
                .tag(DesiresTags.ItemTags.DYED_BLOCKS.tag, BORE_BLOCKS)
                .build()
                .register();
    });

    public static final BlockEntry<FanSailBlock> SPLASHING_SAIL = REG.block("splashing_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .transform(axeOnly())
            .blockstate((c, p) -> {
                ModelFile model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/splashing"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            })
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SPLASHING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.WATER_BUCKET, p, c))
            .lang("Splashing Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> HAUNTING_SAIL = REG.block("haunting_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .properties(p -> p.lightLevel(s -> 8))
            .transform(axeOnly())
            .blockstate((c, p) -> {
                ModelFile model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/haunting"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            })
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_HAUNTING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.SOUL_CAMPFIRE, p, c))
            .lang("Haunting Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> SMOKING_SAIL = REG.block("smoking_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .properties(p -> p.lightLevel(s -> 8))
            .transform(axeOnly())
            .blockstate((c, p) -> {
                ModelFile model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/smoking"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            })
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SMOKING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.CAMPFIRE, p, c))
            .lang("Smoking Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> BLASTING_SAIL = REG.block("blasting_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .properties(p -> p.lightLevel(s -> 12))
            .transform(axeOnly())
            .blockstate((c, p) -> {
                ModelFile model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/blasting"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            })
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.LAVA_BUCKET, p, c))
            .lang("Blasting Catalyst Sail")
            .item()
            .build()
            .register();

    //public static final BlockEntry<FanSailBlock> SEETHING_SAIL = REG.block("seething_sail", FanSailBlock::sail)*
    //        .initialProperties(SharedProperties::wooden)
    //        .properties(p -> p.mapColor(MapColor.DIRT))
    //        .properties(p -> p.sound(SoundType.SCAFFOLDING)
    //                .noOcclusion())
    //        .properties(p -> p.lightLevel(s -> 15))
    //        .transform(axeOnly())
    //        .blockstate(BlockStateGen.directionalBlockProvider(false))
    //        .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
    //        .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
    //        .tag(DesiresTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag)
    //        .recipe((c, p) -> ShapedRecipeBuilder.shaped(c.get(), 4)
    //                        .pattern("SCS")
    //                        .pattern("CRC")
    //                        .pattern("SCS")
    //                        .define('S', BLASTING_SAIL.get())
    //                        .define('R', Items.NETHERITE_SCRAP)
    //                        .define('C', DesiresItems.SEETHING_ABLAZE_ROD.get())
    //                        .unlockedBy("has_" + getItemName(DesiresItems.SEETHING_ABLAZE_ROD.get()), has(DesiresItems.SEETHING_ABLAZE_ROD.get()))
    //                        .save(p, DesireUtil.asResource("crafting/fan_catalyst/" + c.getName()))
    //        )
    //        .tag(DesiresTags.AllBlockTags.INDUSTRIAL_FAN_HEATER.tag)
    //        .lang("Seething Catalyst Sail")
    //        .item()
    //        .build()
    //        .register();

    //public static final BlockEntry<FanSailBlock> FREEZING_SAIL = REG.block("freezing_sail", FanSailBlock::sail)*
    //        .initialProperties(SharedProperties::wooden)
    //        .properties(p -> p.mapColor(MapColor.DIRT))
    //        .properties(p -> p.sound(SoundType.SCAFFOLDING)
    //                .noOcclusion())
    //        .transform(axeOnly())
    //        .blockstate(BlockStateGen.directionalBlockProvider(false))
    //        .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
    //        .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
    //        .tag(DesiresTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag)
    //        .recipe((c, p) -> fanSailCrafting(c.get(), Items.POWDER_SNOW_BUCKET, p, c))
    //        .lang("Freezing Catalyst Sail")
    //        .item()
    //        .build()
    //        .register();

    //public static final BlockEntry<FanSailBlock> SANDING_SAIL = REG.block("sanding_sail", FanSailBlock::sail)*
    //        .initialProperties(SharedProperties::wooden)
    //        .properties(p -> p.mapColor(MapColor.DIRT))
    //        .properties(p -> p.sound(SoundType.SCAFFOLDING)
    //                .noOcclusion())
    //        .transform(axeOnly())
    //        .blockstate(BlockStateGen.directionalBlockProvider(false))
    //        .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
    //        .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
    //        .tag(DesiresTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag)
    //        .recipe((c, p) -> fanSailCrafting(c.get(), Items.SAND, p, c))
    //        .lang("Sanding Catalyst Sail")
    //        .item()
    //        .build()
    //        .register();

    public static void fanSailCrafting(ItemLike itemLike, ItemLike catalyst, RegistrateRecipeProvider pFinishedRecipeConsumer, DataGenContext<Block, FanSailBlock> c) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemLike, 4)
                .pattern("SCS")
                .pattern("CRC")
                .pattern("SCS")
                .define('S', AllBlocks.SAIL_FRAME.get())
                .define('R', Items.BLACK_WOOL)
                //.define('R', RUBBER_BLOCK.get())
                .define('C', catalyst)
                .unlockedBy("has_" + getItemName(catalyst), has(catalyst))
                .save(pFinishedRecipeConsumer, DnDesires.loc("crafting/fan_catalyst/" + c.getName()));
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static void register() {}
}
