package dev.lopyluna.create_d2d.register.helpers.wood_types;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonnullType;
import dev.lopyluna.create_d2d.DesiresCreate;
import dev.lopyluna.create_d2d.register.DesiresStoneTypes;
import net.createmod.catnip.lang.Lang;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import java.util.List;
import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

@SuppressWarnings("all")
public abstract class BlockPartial<B extends Block> {

    public static final BlockPartial<StairBlock> STAIR = new Stairs();
    public static final BlockPartial<SlabBlock> SLAB = new Slab(false);
    public static final BlockPartial<SlabBlock> UNIQUE_SLAB = new Slab(true);
    public static final BlockPartial<WallBlock> WALL = new Wall();

    public static final BlockPartial<?>[] ALL_PARTIALS = { STAIR, SLAB, WALL };
    public static final BlockPartial<?>[] FOR_POLISHED = { STAIR, UNIQUE_SLAB, WALL };

    private final String name;

    private BlockPartial(String name) {
        this.name = name;
    }

    public @NonnullType BlockBuilder<B, CreateRegistrate> create(String variantName, BlockPattern pattern, BlockEntry<? extends Block> block, DesiresStoneTypes variant) {
        String patternName = Lang.nonPluralId(pattern.createName(variantName));
        String blockName = patternName + "_" + this.name;
        BlockBuilder<B, CreateRegistrate> blockBuilder = DesiresCreate.REG
                .block(blockName, p -> createBlock(block))
                .blockstate((c, p) -> generateBlockState(c, p, variantName, pattern, block))
                .recipe((c, p) -> createRecipes(variant, block, c, p))
                .transform(b -> transformBlock(b, variantName, pattern));
        ItemBuilder<BlockItem, BlockBuilder<B, CreateRegistrate>> itemBuilder = blockBuilder.item().transform(b -> transformItem(b, variantName, pattern));
        if (canRecycle()) itemBuilder.tag(variant.materialTag);
        return itemBuilder.build();
    }
    protected ResourceLocation getTexture(String variantName, BlockPattern pattern, int index) {
        return BlockPattern.toLocation(variantName, pattern.getTexture(index));
    }
    protected BlockBuilder<B, CreateRegistrate> transformBlock(BlockBuilder<B, CreateRegistrate> builder, String variantName, BlockPattern pattern) {
        getBlockTags().forEach(builder::tag);
        return builder.transform(pickaxeOnly());
    }
    protected ItemBuilder<BlockItem, BlockBuilder<B, CreateRegistrate>> transformItem(ItemBuilder<BlockItem, BlockBuilder<B, CreateRegistrate>> builder, String variantName, BlockPattern pattern) {
        getItemTags().forEach(builder::tag);
        return builder;
    }
    protected boolean canRecycle() {
        return true;
    }
    protected abstract Iterable<TagKey<Block>> getBlockTags();
    protected abstract Iterable<TagKey<Item>> getItemTags();
    protected abstract B createBlock(Supplier<? extends Block> block);
    protected abstract void createRecipes(DesiresStoneTypes type, BlockEntry<? extends Block> patternBlock, DataGenContext<Block, ? extends Block> c, RegistrateRecipeProvider p);
    protected abstract void generateBlockState(DataGenContext<Block, B> ctx, RegistrateBlockstateProvider prov, String variantName, BlockPattern pattern, Supplier<? extends Block> block);

    private static class Stairs extends BlockPartial<StairBlock> {
        public Stairs() {
            super("stairs");
        }
        @Override
        protected StairBlock createBlock(Supplier<? extends Block> block) {
            return new StairBlock(block.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(block.get()));
        }
        @Override
        protected void generateBlockState(DataGenContext<Block, StairBlock> ctx, RegistrateBlockstateProvider prov, String variantName, BlockPattern pattern, Supplier<? extends Block> block) {
            prov.stairsBlock(ctx.get(), getTexture(variantName, pattern, 0));
        }
        @Override
        protected Iterable<TagKey<Block>> getBlockTags() {
            return List.of(BlockTags.STAIRS);
        }
        @Override
        protected Iterable<TagKey<Item>> getItemTags() {
            return List.of(ItemTags.STAIRS);
        }
        @Override
        protected void createRecipes(DesiresStoneTypes type, BlockEntry<? extends Block> patternBlock, DataGenContext<Block, ? extends Block> c, RegistrateRecipeProvider p) {
            RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
            p.stairs(DataIngredient.items(patternBlock.get()), category, c, c.getName(), false);
            p.stonecutting(DataIngredient.tag(type.materialTag), category, c, 1);
        }
    }
    private static class Slab extends BlockPartial<SlabBlock> {
        private final boolean customSide;
        public Slab(boolean customSide) {
            super("slab");
            this.customSide = customSide;
        }
        @Override
        protected SlabBlock createBlock(Supplier<? extends Block> block) {
            return new SlabBlock(BlockBehaviour.Properties.ofFullCopy(block.get()));
        }
        @Override
        protected boolean canRecycle() {
            return false;
        }
        @Override
        protected void generateBlockState(DataGenContext<Block, SlabBlock> ctx, RegistrateBlockstateProvider prov, String variantName, BlockPattern pattern, Supplier<? extends Block> block) {
            String name = ctx.getName();
            ResourceLocation mainTexture = getTexture(variantName, pattern, 0);
            ResourceLocation sideTexture = customSide ? getTexture(variantName, pattern, 1) : mainTexture;
            ModelFile bottom = prov.models().slab(name, sideTexture, mainTexture, mainTexture);
            ModelFile top = prov.models().slabTop(name + "_top", sideTexture, mainTexture, mainTexture);
            ModelFile doubleSlab;
            if (customSide) doubleSlab = prov.models().cubeColumn(name + "_double", sideTexture, mainTexture);
            else doubleSlab = prov.models().getExistingFile(prov.modLoc(pattern.createName(variantName)));
            prov.slabBlock(ctx.get(), bottom, top, doubleSlab);
        }
        @Override
        protected Iterable<TagKey<Block>> getBlockTags() {
            return List.of(BlockTags.SLABS);
        }
        @Override
        protected Iterable<TagKey<Item>> getItemTags() {
            return List.of(ItemTags.SLABS);
        }
        @Override
        protected void createRecipes(DesiresStoneTypes type, BlockEntry<? extends Block> patternBlock, DataGenContext<Block, ? extends Block> c, RegistrateRecipeProvider p) {
            RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
            p.slab(DataIngredient.items(patternBlock.get()), category, c, c.getName(), false);
            p.stonecutting(DataIngredient.tag(type.materialTag), category, c, 2);
            DataIngredient ingredient = DataIngredient.items(c.get());
            ShapelessRecipeBuilder.shapeless(category, patternBlock.get())
                    .requires(ingredient.toVanilla())
                    .requires(ingredient.toVanilla())
                    .unlockedBy("has_" + c.getName(), ingredient.getCriterion(p))
                    .save(p, DesiresCreate.MOD_ID + ":" + c.getName() + "_recycling");
        }
        @Override
        protected BlockBuilder<SlabBlock, CreateRegistrate> transformBlock(BlockBuilder<SlabBlock, CreateRegistrate> builder, String variantName, BlockPattern pattern) {
            builder.loot((lt, block) -> lt.add(block, lt.createSlabItemTable(block)));
            return super.transformBlock(builder, variantName, pattern);
        }
    }

    private static class Wall extends BlockPartial<WallBlock> {
        public Wall() {
            super("wall");
        }
        @Override
        protected WallBlock createBlock(Supplier<? extends Block> block) {
            return new WallBlock(BlockBehaviour.Properties.ofFullCopy(block.get()).forceSolidOn());
        }
        @Override
        protected ItemBuilder<BlockItem, BlockBuilder<WallBlock, CreateRegistrate>> transformItem(
                ItemBuilder<BlockItem, BlockBuilder<WallBlock, CreateRegistrate>> builder, String variantName,
                BlockPattern pattern) {
            builder.model((c, p) -> p.wallInventory(c.getName(), getTexture(variantName, pattern, 0)));
            return super.transformItem(builder, variantName, pattern);
        }
        @Override
        protected void generateBlockState(DataGenContext<Block, WallBlock> ctx, RegistrateBlockstateProvider prov, String variantName, BlockPattern pattern, Supplier<? extends Block> block) {
            prov.wallBlock(ctx.get(), pattern.createName(variantName), getTexture(variantName, pattern, 0));
        }
        @Override
        protected Iterable<TagKey<Block>> getBlockTags() {
            return List.of(BlockTags.WALLS);
        }
        @Override
        protected Iterable<TagKey<Item>> getItemTags() {
            return List.of(ItemTags.WALLS);
        }
        @Override
        protected void createRecipes(DesiresStoneTypes type, BlockEntry<? extends Block> patternBlock, DataGenContext<Block, ? extends Block> c, RegistrateRecipeProvider p) {
            RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
            p.stonecutting(DataIngredient.tag(type.materialTag), category, c, 1);
            DataIngredient ingredient = DataIngredient.items(patternBlock.get());
            ShapedRecipeBuilder.shaped(category, c.get(), 6)
                    .pattern("XXX")
                    .pattern("XXX")
                    .define('X', ingredient.toVanilla())
                    .unlockedBy("has_" + p.safeName(ingredient), ingredient.getCriterion(p))
                    .save(p, p.safeId(c.get()));
        }
    }
}
