package uwu.lopyluna.create_dd.block.BlockPalette.gen;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;

import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

@SuppressWarnings({"unused", "pure"})
public class DDPalettesVariant {

    public final ImmutableList<BlockEntry<? extends Block>> registeredBlocks;
    public final ImmutableList<BlockEntry<? extends Block>> registeredPartials;

    public DDPalettesVariant(String name, DDPaletteStoneTypes paletteStoneVariants) {
        ImmutableList.Builder<BlockEntry<? extends Block>> registeredBlocks = ImmutableList.builder();
        ImmutableList.Builder<BlockEntry<? extends Block>> registeredPartials = ImmutableList.builder();
        NonNullSupplier<Block> baseBlock = paletteStoneVariants.baseBlock;

        for (DDPaletteBlockPattern pattern : paletteStoneVariants.variantTypes) {
            BlockBuilder<? extends Block, CreateRegistrate> builder =
                    REGISTRATE.block(pattern.createName(name), pattern.getBlockFactory())
                            .initialProperties(baseBlock)
                            .transform(pickaxeOnly())
                            .blockstate(pattern.getBlockStateGenerator()
                                    .apply(pattern)
                                    .apply(name)::accept);

            ItemBuilder<BlockItem, ? extends BlockBuilder<? extends Block, CreateRegistrate>> itemBuilder =
                    builder.item();

            itemBuilder.tag(paletteStoneVariants.materialTag);

            pattern.createCTBehaviour(name)
                    .ifPresent(b -> builder.onRegister(connectedTextures(b)));

            itemBuilder.register();
            BlockEntry<? extends Block> block = builder.register();
            registeredBlocks.add(block);

            for (DDPaletteBlockPartial<? extends Block> partialBlock : pattern.getPartials())
                registeredPartials.add(partialBlock.create(name, pattern, block, paletteStoneVariants)
                        .register());
        }

        this.registeredBlocks = registeredBlocks.build();
        this.registeredPartials = registeredPartials.build();
    }
}
