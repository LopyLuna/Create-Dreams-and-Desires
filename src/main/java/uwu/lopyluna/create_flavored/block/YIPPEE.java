package uwu.lopyluna.create_flavored.block;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;
import uwu.lopyluna.create_flavored.block.MechanicalSmasher.SmashingBlock;
import uwu.lopyluna.create_flavored.block.MechanicalSmasher.SmashingControllerBlock;
import uwu.lopyluna.create_flavored.item.PipebombTab;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
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
            .initialProperties(() -> Blocks.DEEPSLATE_GOLD_ORE)
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

    //MECHANICAL BLOCKS

    public static final BlockEntry<SmashingBlock> MECHANICAL_SMASHER = REGISTRATE.block("mechanical_smasher", SmashingBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(pickaxeOnly())
                    .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p)))
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(BlockStressDefaults.setImpact(8.0))
                    .item()
                    .transform(customItemModel())
                    .lang("Mechanical Smasher")
                    .register();

    public static final BlockEntry<SmashingControllerBlock> MECHANICAL_SMASHER_CONTROLLER = REGISTRATE.block("mechanical_smasher_controller", SmashingControllerBlock::new)
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noDrops()
                            .air())
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(BlockStateGen.mapToAir(p), SmashingControllerBlock.FACING))
                    .register();

    public static void register() {}
}
