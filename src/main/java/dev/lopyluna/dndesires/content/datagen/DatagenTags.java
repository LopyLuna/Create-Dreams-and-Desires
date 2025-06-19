package dev.lopyluna.dndesires.content.datagen;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import static dev.lopyluna.dndesires.DnDesires.REG;

@SuppressWarnings("deprecation")
public class DatagenTags {
    public static void addGenerators() {
        REG.addDataGenerator(ProviderType.BLOCK_TAGS, DatagenTags::genBlockTags);
        REG.addDataGenerator(ProviderType.ITEM_TAGS, DatagenTags::genItemTags);
    }
    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        var prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                .add(Items.DRAGON_BREATH)
                .add(Items.POWDER_SNOW_BUCKET)
                .add(Items.OMINOUS_BOTTLE)
                .add(Items.EXPERIENCE_BOTTLE)
                .add(Items.LIGHT)
                .addTag(Tags.Items.BUCKETS)
                .addTag(Tags.Items.BUDS)
                .addTag(Tags.Items.CLUSTERS)
                .addTag(ItemTags.CANDLES)
        ;


        for (var tag : DesiresTags.ItemTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
        prov.tag(DesiresTags.BlockTags.WEAK_FURNACE.tag).add(Blocks.FURNACE);
        prov.tag(DesiresTags.BlockTags.STRONG_FURNACE.tag).add(Blocks.BLAST_FURNACE);
        prov.tag(DesiresTags.BlockTags.ORE_GENERATOR.tag).add(Blocks.BEDROCK);
        prov.tag(DesiresTags.BlockTags.ARTIFICIAL_ORE_GENERATOR.tag).add(Blocks.NETHERITE_BLOCK, Blocks.REINFORCED_DEEPSLATE, Blocks.DRAGON_EGG, Blocks.END_PORTAL, Blocks.BEACON);

        prov.tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.tag).add(Blocks.DRAGON_WALL_HEAD).add(Blocks.DRAGON_EGG);
        prov.tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag).addTag(BlockTags.SAND);
        prov.tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag).add(Blocks.POWDER_SNOW);
        prov.tag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag).add(AllBlocks.BLAZE_BURNER.get());

        prov.tag(DesiresTags.BlockTags.FAN_CATALYSTS_DRAGON_SUPPORT.tag).add(DesiresBlocks.INDUSTRIAL_FAN.get());

        prov.tag(DesiresTags.BlockTags.INDUSTRIAL_FAN_HEATER.tag)
                .add(Blocks.LAVA)
                .add(DesiresBlocks.SEETHING_SAIL.get())
                .add(AllBlocks.BLAZE_BURNER.get());

        prov.tag(DesiresTags.BlockTags.INDUSTRIAL_FAN_TRANSPARENT.tag).addTag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag);

        prov.tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                .addTag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.tag)
                .addTag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag)
                .addTag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag)
                .addTag(DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag);

        for (var tag : DesiresTags.BlockTags.values()) if (tag.alwaysDatagen) prov.getOrCreateRawBuilder(tag.tag);
    }
}
