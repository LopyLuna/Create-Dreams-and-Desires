package dev.lopyluna.dndesires.content.datagen;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static dev.lopyluna.dndesires.DnDesires.REG;

@SuppressWarnings("deprecation")
public class DatagenTags {
    public static void addGenerators() {
        REG.addDataGenerator(ProviderType.BLOCK_TAGS, DatagenTags::genBlockTags);

    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
        prov.tag(DesiresTags.BlockTags.WEAK_FURNACE.tag).add(
                Blocks.FURNACE
        );
        prov.tag(DesiresTags.BlockTags.STRONG_FURNACE.tag).add(
                Blocks.BLAST_FURNACE
        );
        prov.tag(DesiresTags.BlockTags.ORE_GENERATOR.tag).add(
                Blocks.BEDROCK
        );
        prov.tag(DesiresTags.BlockTags.ARTIFICIAL_ORE_GENERATOR.tag).add(
                Blocks.NETHERITE_BLOCK,
                Blocks.REINFORCED_DEEPSLATE,
                Blocks.DRAGON_EGG,
                Blocks.END_PORTAL,
                Blocks.BEACON
        );

        for (DesiresTags.BlockTags tag : DesiresTags.BlockTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }
}
