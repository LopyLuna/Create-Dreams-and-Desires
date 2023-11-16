package uwu.lopyluna.create_dd.worldgen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.infrastructure.worldgen.LayerPattern;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Blocks;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class DDLayerPatterns {

    public static final NonNullSupplier<LayerPattern> CASSITERITE = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .block(DDPaletteStoneTypes.potassic.getBaseBlock())
                    .size(2, 5))
            .layer(l -> l.weight(1)
                    .block(Blocks.DIORITE)
                    .block(Blocks.DEEPSLATE)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DEEPSLATE, Blocks.DIORITE)
                    .size(2, 2))
            .layer(l -> l.weight(1)
                    .block(DDPaletteStoneTypes.weathered_limestone.getBaseBlock())
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> GABBRO = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .block(DDPaletteStoneTypes.gabbro.getBaseBlock())
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .block(Blocks.GRANITE)
                    .block(Blocks.DRIPSTONE_BLOCK)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DRIPSTONE_BLOCK, Blocks.GRANITE)
                    .size(1, 2))
            .layer(l -> l.weight(1)
                    .block(DDPaletteStoneTypes.gabbro.getBaseBlock())
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DRIPSTONE_BLOCK, Blocks.GRANITE)
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> RAW_CASSITERITE = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .blocks(DDBlocks.tin_ore.get(), DDBlocks.deepslate_tin_ore.get())
                    .size(1, 3))
            .layer(l -> l.weight(1)
                    .block(Blocks.DIORITE)
                    .block(Blocks.DEEPSLATE)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DEEPSLATE, Blocks.DIORITE)
                    .size(2, 2))
            .layer(l -> l.weight(1)
                    .block(DDPaletteStoneTypes.weathered_limestone.getBaseBlock())
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> RAW_CINNABAR = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .blocks(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE)
                    .size(1, 1))
            .layer(l -> l.weight(1)
                    .block(Blocks.TUFF)
                    .block(Blocks.DEEPSLATE)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DEEPSLATE, Blocks.TUFF)
                    .size(2, 2))
            .layer(l -> l.weight(1)
                    .block(AllPaletteStoneTypes.LIMESTONE.getBaseBlock())
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> RAW_MAGNETITE = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .blocks(AllBlocks.ZINC_ORE.get(), AllBlocks.DEEPSLATE_ZINC_ORE.get())
                    .size(1, 2))
            .layer(l -> l.weight(1)
                    .block(Blocks.TUFF)
                    .block(Blocks.DEEPSLATE)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.DEEPSLATE, Blocks.TUFF)
                    .size(2, 2))
            .layer(l -> l.weight(1)
                    .block(Blocks.CALCITE)
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> RAW_OCHRESTONE = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .blocks(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE)
                    .size(1, 1))
            .layer(l -> l.weight(2)
                    .block(Blocks.TUFF)
                    .block(Blocks.DEEPSLATE)
                    .size(2, 3))
            .layer(l -> l.weight(2)
                    .block(Blocks.DRIPSTONE_BLOCK)
                    .size(1, 2))
            .build();

    public static final NonNullSupplier<LayerPattern> RAW_MALACHITE = () -> LayerPattern.builder()
            .layer(l -> l.weight(4)
                    .blocks(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE)
                    .size(2, 4))
            .layer(l -> l.weight(2)
                    .block(Blocks.TUFF)
                    .block(Blocks.ANDESITE)
                    .size(2, 3))
            .layer(l -> l.weight(2)
                    .blocks(Blocks.TUFF, Blocks.ANDESITE)
                    .size(2, 2))
            .layer(l -> l.weight(3)
                    .block(Blocks.SMOOTH_BASALT)
                    .size(1, 2))
            .build();


    public static final NonNullSupplier<LayerPattern> RAW_SCORIA = () -> LayerPattern.builder()
            .layer(l -> l.weight(2)
                    .blocks(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE)
                    .size(2, 5))
            .layer(l -> l.weight(2)
                    .block(Blocks.TUFF)
                    .block(Blocks.ANDESITE)
                    .size(1, 4))
            .layer(l -> l.weight(1)
                    .blocks(Blocks.TUFF, Blocks.ANDESITE)
                    .size(2, 3))
            .layer(l -> l.weight(1)
                    .block(Blocks.DIORITE)
                    .size(3, 5))
            .build();


    public static final NonNullSupplier<LayerPattern> WEATHERED_LIMESTONE = () -> LayerPattern.builder()
            .layer(l -> l.weight(1)
                    .block(DDPaletteStoneTypes.weathered_limestone.getBaseBlock())
                    .size(1, 3))
            .layer(l -> l.weight(2)
                    .block(Blocks.CALCITE))
            .layer(l -> l.weight(1)
                    .block(AllPaletteStoneTypes.LIMESTONE.getBaseBlock())
                    .size(1, 2))
            .layer(l -> l.weight(1)
                    .block(Blocks.DIORITE))
            .layer(l -> l.weight(3)
                    .block(DDPaletteStoneTypes.weathered_limestone.getBaseBlock())
                    .size(2, 4))
            .build();
}
