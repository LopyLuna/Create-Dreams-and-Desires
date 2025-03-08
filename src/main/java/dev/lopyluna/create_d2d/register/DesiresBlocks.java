package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.create_d2d.DesiresCreate;
import dev.lopyluna.create_d2d.content.blocks.BoreBlockMovementBehavior;
import dev.lopyluna.create_d2d.content.blocks.hydraulic_press.HydraulicPressBlock;
import dev.lopyluna.create_d2d.content.blocks.propeller.PropellerBlock;
import dev.lopyluna.create_d2d.content.blocks.roll_table.RollTableBlock;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBlock;
import dev.lopyluna.create_d2d.content.configs.server.kinetics.DStress;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.util.DeferredSoundType;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.create_d2d.DesiresCreate.REG;

@SuppressWarnings({"removal", "unused"})
public class DesiresBlocks {


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
                    .save(p, DesiresCreate.loc("crafting/" + c.getName())))
            .blockstate((c, p) -> {
                var model = p.cubeAll(c.get());
                p.simpleBlockWithItem(c.get(), model);
            })
            .simpleItem()
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
                    .save(p, DesiresCreate.loc("crafting/" + c.getName())))
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
                    .save(p, DesiresCreate.loc("crafting/" + c.getName())))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .addLayer(() -> RenderType::cutoutMipped)
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<PropellerBlock> PROPELLER = REG.block("propeller", PropellerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN))
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static final DyedBlockList<PropellerBlock> DYED_PROPELLERS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_propeller", p -> new PropellerBlock(p, color))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .blockstate((c, p) -> {
                    ModelFile model = p.models().withExistingParent(colorName + "_propeller", p.modLoc("block/propeller/block"))
                            .texture("0", p.modLoc("block/propeller/" + colorName));
                    BlockStateGen.axisBlock(c, p, s -> model);
                })
                .item()
                .model((c, p) -> p.withExistingParent(colorName + "_propeller", p.modLoc("block/propeller/item"))
                        .texture("1", p.modLoc("block/propeller/" + colorName)))
                .build()
                .register();
    });

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
                    .define('B', AllItems.MINECART_COUPLING.get())
                    .define('C', AllBlocks.ZINC_BLOCK.get())
                    .unlockedBy("has_" + c.getName(), has(c.get()))
                    .save(p, DesiresCreate.loc("crafting/" + c.getName())))
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

    public static void register() {}
}
