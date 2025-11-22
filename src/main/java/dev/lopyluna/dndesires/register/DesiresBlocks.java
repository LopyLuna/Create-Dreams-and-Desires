package dev.lopyluna.dndesires.register;

import com.simibubi.create.*;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.block.ItemUseOverrides;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.blocks.BoreBlockMovementBehavior;
import dev.lopyluna.dndesires.content.blocks.FanSailBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank.CogCrankBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.cog_crank.CogCrankItem;
import dev.lopyluna.dndesires.content.blocks.kinetics.creative_gear_motor.CreativeGearMotorBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.golden_mixer.GoldenMixerBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.hydraulic_press.HydraulicPressBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialFanBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.inverse_gearshift.InverseGearshiftBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.multimeter.MultiMeterBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.multimeter.MultiMeterGen;
import dev.lopyluna.dndesires.content.blocks.kinetics.omni_gearbox.OmniGearboxBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.omni_speed_controller.OmniSpeedControllerBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.dndesires.content.blocks.kinetics.stirling_engine.flywheel.PoweredFlywheelBlock;
import dev.lopyluna.dndesires.content.blocks.logistics.fluid_gauge.FluidGaugeBlock;
import dev.lopyluna.dndesires.content.blocks.logistics.fluid_hatch.FluidHatchBlock;
import dev.lopyluna.dndesires.content.blocks.logistics.roll_table.RollTableBlock;
import dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBlock;
import dev.lopyluna.dndesires.content.configs.server.kinetics.DStress;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.util.DeferredSoundType;

import java.util.function.Function;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.simpleBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndesires.DnDesires.REG;
import static dev.lopyluna.dndesires.register.client.DesiresSpriteShifts.omni;

@SuppressWarnings({"removal", "unused"})
public class DesiresBlocks {

    public static final BlockEntry<CasingBlock> OVERBURDEN_CASING = REG.block("overburden_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omni("overburden_casing")))
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AllBlocks.STOCK_LINK.get(), 1)
                        .pattern("A").pattern("B")
                        .define('A', AllItems.TRANSMITTER)
                        .define('B', c.get())
                        .unlockedBy("has_transmitter", has(AllItems.TRANSMITTER))
                        .save(p, DnDesires.loc("crafting/transmitter"));
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AllBlocks.PACKAGE_FROGPORT.get(), 1)
                        .pattern("A").pattern("B").pattern("C")
                        .define('A', Items.SLIME_BALL)
                        .define('B', c.get())
                        .define('C', AllItems.ANDESITE_ALLOY)
                        .unlockedBy("has_vault", has(AllBlocks.ITEM_VAULT))
                        .save(p, DnDesires.loc("crafting/frogport"));
            })
            .transform(pickaxeOnly())
            .register();

    public static final BlockEntry<CasingBlock> INDUSTRIAL_CASING = REG.block("industrial_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> omni("industrial_casing")))
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .transform(pickaxeOnly())
            .register();

    public static final BlockEntry<IndustrialFanBlock> INDUSTRIAL_FAN = REG.block("industrial_fan", IndustrialFanBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .transform(pickaxeOnly())
            .transform(DStress.setImpact(4))
            .transform(DStress.setCapacity(16))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("C").pattern("I").pattern("P").define('P', AllItems.PROPELLER.get()).define('C', AllBlocks.COGWHEEL.get()).define('I', INDUSTRIAL_CASING.get())
                    .unlockedBy("has_casing", has(INDUSTRIAL_CASING.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName()))).item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CogCrankBlock> COG_CRANK = REG.block("cog_crank", CogCrankBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.DIRT))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .transform(DStress.setCapacity(8.0))
            .onRegister(BlockStressValues.setGeneratorSpeed(32))
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.HAND_CRANK.get())
                    .requires(AllBlocks.COGWHEEL.get())
                    .unlockedBy("has_cog", has(AllBlocks.COGWHEEL.get()))
                    .save(p, DnDesires.loc("crafting/cog_crank")))
            .onRegister(ItemUseOverrides::addBlock)
            .item(CogCrankItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CogCrankBlock> LARGE_COG_CRANK  = REG.block("large_cog_crank", CogCrankBlock::large)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .transform(DStress.setCapacity(8.0))
            .onRegister(BlockStressValues.setGeneratorSpeed(16))
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                        .requires(AllBlocks.HAND_CRANK.get())
                        .requires(AllBlocks.LARGE_COGWHEEL.get())
                        .unlockedBy("has_cog", has(AllBlocks.COGWHEEL.get()))
                        .save(p, DnDesires.loc("crafting/large_cog_crank"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                        .requires(COG_CRANK.get())
                        .requires(ItemTags.PLANKS)
                        .unlockedBy("has_cog", has(AllBlocks.COGWHEEL.get()))
                        .save(p, DnDesires.loc("crafting/cog_crank_to_large"));
            })
            .onRegister(ItemUseOverrides::addBlock)
            .item(CogCrankItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<MultiMeterBlock> MULTIMETER = REG.block("multimeter", MultiMeterBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .blockstate(new MultiMeterGen()::generate)
            .transform(displaySource(AllDisplaySources.KINETIC_SPEED))
            .transform(displaySource(AllDisplaySources.KINETIC_STRESS))
            .recipe((c, p) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 2)
                        .requires(AllBlocks.STRESSOMETER).requires(AllBlocks.SPEEDOMETER)
                        .unlockedBy("has_compass", has(Items.COMPASS))
                        .save(p, DnDesires.loc("crafting/" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AllBlocks.STRESSOMETER.get(), 2)
                        .requires(AllBlocks.STRESSOMETER).requires(c.get())
                        .unlockedBy("has_compass", has(Items.COMPASS))
                        .save(p, DnDesires.loc("crafting/" + c.getName() + "_stress"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AllBlocks.SPEEDOMETER.get(), 2)
                        .requires(AllBlocks.SPEEDOMETER).requires(c.get())
                        .unlockedBy("has_compass", has(Items.COMPASS))
                        .save(p, DnDesires.loc("crafting/" + c.getName() + "_speed"));
            }).item()
            .transform(ModelGen.customItemModel("gauge", "_", "item"))
            .register();

    public static final BlockEntry<SmartHopperBlock> SMART_HOPPER = REG.block("smart_hopper", SmartHopperBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> {
                for (var dir : Iterate.directions) {
                    if (dir == Direction.UP) continue;
                    var suffix = getHopperSuffix(dir);
                    suffix = !suffix.isEmpty() ? "_" + suffix : suffix;
                    p.models().withExistingParent( "block/smart_hopper/block_powered" + suffix, p.modLoc("block/smart_hopper/block" + suffix)).texture("1", p.modLoc("block/smart_hopper_powered"));
                }
                p.getVariantBuilder(c.get()).forAllStates((state) -> {
                    Function<BlockState, ModelFile> modelFunc = s -> AssetLookup.partialBaseModel(c, p, s.getValue(SmartHopperBlock.POWERED) ? "powered" : "", getHopperSuffix(s.getValue(SmartHopperBlock.FACING)));
                    return ConfiguredModel.builder().modelFile(modelFunc.apply(state)).build();
                });
            }).recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("A").pattern("B").pattern("C")
                    .define('A', DesiresTags.commonItemTag("plates/brass"))
                    .define('B', Items.HOPPER)
                    .define('C', AllItems.ELECTRON_TUBE.get())
                    .unlockedBy("has_hopper", has(Items.HOPPER))
                    .save(p, DnDesires.loc("crafting/" + c.getName()))).item()
            .transform(customItemModel("_", "block"))
            .register();

    public static String getHopperSuffix(Direction dir) {
        return switch (dir) {
            case NORTH -> "north";
            case SOUTH -> "south";
            case WEST -> "west";
            case EAST -> "east";
            default -> "";
        };
    }

    public static final BlockEntry<CreativeGearMotorBlock> CREATIVE_GEAR_MOTOR = REG.block("creative_gear_motor", CreativeGearMotorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.COLOR_PURPLE).forceSolidOn())
            .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.directionalBlockProviderIgnoresWaterlogged(true))
            .transform(DStress.setCapacity(16384.0))
            .onRegister(BlockStressValues.setGeneratorSpeed(256, true))
            .item()
            .properties(p -> p.rarity(Rarity.EPIC))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<FluidGaugeBlock> FLUID_GAUGE = REG.block("fluid_gauge", FluidGaugeBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).sound(SoundType.COPPER))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.horizontalBlock(c.get(),
                    s -> AssetLookup.partialBaseModel(c, p)))
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(Items.COPPER_INGOT).requires(Items.COMPASS)
                    .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .transform(customItemModel("_", "block"))
            .register();

    public static final BlockEntry<FluidHatchBlock> FLUID_HATCH = REG.block("fluid_hatch", FluidHatchBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_ORANGE).sound(SoundType.COPPER))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.horizontalBlock(c.get(),
                    s -> AssetLookup.partialBaseModel(c, p, s.getValue(FluidHatchBlock.OPEN) ? "open" : "closed")))
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllItems.ANDESITE_ALLOY.get()).requires(Items.COPPER_TRAPDOOR)
                    .unlockedBy("has_andesite_alloy", has(AllItems.ANDESITE_ALLOY.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .transform(customItemModel("_", "block_closed"))
            .register();

    public static final BlockEntry<OmniGearboxBlock> OMNI_GEARBOX = REG.block("omni_gearbox", OmniGearboxBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.lightLevel($ -> 2).noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN))
            .transform(DStress.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> simpleBlock(c, p, $ -> AssetLookup.partialBaseModel(c, p)))
            .item()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 2)
                    .pattern(" V ").pattern("HCH").pattern(" V ")
                    .define('V', AllItems.VERTICAL_GEARBOX.get())
                    .define('H', AllBlocks.GEARBOX.get())
                    .define('C', AllBlocks.BRASS_CASING.get())
                    .unlockedBy("has_casing", has(AllBlocks.BRASS_CASING.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<OmniSpeedControllerBlock> OMNI_SPEED_CONTROLLER = REG.block("omni_speed_controller", OmniSpeedControllerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_BROWN))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(DStress.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("CBC")
                    .define('C', AllBlocks.LARGE_COGWHEEL.get())
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
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("A").pattern("B")
                    .define('A', DesiresTags.commonItemTag("plates/iron"))
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
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("A").pattern("B").pattern("C")
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


    public static final BlockEntry<GoldenMixerBlock> GOLDEN_MIXER = REG.block("gold_mixer", GoldenMixerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(DStress.setImpact(8.0))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("A").pattern("B").pattern("C")
                    .define('A', AllItems.PRECISION_MECHANISM.get())
                    .define('B', AllBlocks.BRASS_CASING.get())
                    .define('C', DesiresItems.GOLDEN_WHISK.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DnDesires.loc("crafting/" + c.getName())))
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .lang("Golden Mixer")
            .register();

    public static final BlockEntry<StirlingEngineBlock> STIRLING_ENGINE = REG.block("stirling_engine", StirlingEngineBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN).forceSolidOn())
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(DStress.setCapacity(1024.0))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("A").pattern("B").pattern("C")
                    .define('A', DesiresTags.commonItemTag("plates/brass"))
                    .define('B', AllItems.ANDESITE_ALLOY.get())
                    .define('C', DesiresTags.commonItemTag("storage_blocks/zinc"))
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
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
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
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 4)
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
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                        .pattern("AAA").pattern("ABA").pattern("AAA")
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
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/splashing"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
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
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/haunting"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
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
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/smoking"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
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
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/blasting"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.LAVA_BUCKET, p, c))
            .lang("Blasting Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> SEETHING_SAIL = REG.block("seething_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .properties(p -> p.lightLevel(s -> 15))
            .transform(axeOnly())
            .blockstate((c, p) -> {
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/seething"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag)
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 4)
                    .pattern("SCS").pattern("CRC").pattern("SCS")
                    .define('S', BLASTING_SAIL.get()).define('R', DesiresItems.BURNER_STOCK).define('C', AllItems.BLAZE_CAKE.get())
                    .unlockedBy("has_" + getItemName(AllItems.BLAZE_CAKE.get()), has(AllItems.BLAZE_CAKE.get()))
                    .save(p, DnDesires.loc("crafting/fan_catalyst/" + c.getName()))
            ).tag(DesiresTags.BlockTags.INDUSTRIAL_FAN_HEATER.tag)
            .lang("Seething Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> FREEZING_SAIL = REG.block("freezing_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .transform(axeOnly())
            .blockstate((c, p) -> {
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/freezing"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.POWDER_SNOW_BUCKET, p, c))
            .lang("Freezing Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> SANDING_SAIL = REG.block("sanding_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .transform(axeOnly())
            .blockstate((c, p) -> {
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/sanding"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.SAND, p, c))
            .lang("Sanding Catalyst Sail")
            .item()
            .build()
            .register();

    public static final BlockEntry<FanSailBlock> DRAGON_BREATHING_SAIL = REG.block("dragon_breathing_sail", FanSailBlock::sail)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING).noOcclusion())
            .transform(axeOnly())
            .blockstate((c, p) -> {
                var model = p.models().withExistingParent(c.getName(), Create.asResource("block/white_sail"))
                        .texture("0", p.modLoc("block/sail/dragon_breathing"));
                p.directionalBlock(c.get(), model);
                p.simpleBlockItem(c.get(), model);
            }).tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.tag)
            .recipe((c, p) -> fanSailCrafting(c.get(), Items.DRAGON_HEAD, p, c))
            .lang("Dragon Breathing Catalyst Sail")
            .item()
            .build()
            .register();

    public static void fanSailCrafting(ItemLike itemLike, ItemLike catalyst, RegistrateRecipeProvider pFinishedRecipeConsumer, DataGenContext<Block, FanSailBlock> c) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemLike, 4)
                .pattern("SCS").pattern("CRC").pattern("SCS")
                .define('S', AllBlocks.SAIL_FRAME.get())
                .define('R', DesiresTags.commonItemTag("storage_blocks/cardboard"))
                .define('C', catalyst)
                .unlockedBy("has_" + getItemName(catalyst), has(catalyst))
                .save(pFinishedRecipeConsumer, DnDesires.loc("crafting/fan_catalyst/" + c.getName()));
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    public static void register() {}
}
