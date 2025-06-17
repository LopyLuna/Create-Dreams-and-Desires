package dev.lopyluna.dndesires.register.helpers.wood_types;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.*;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndesires.DnDesires;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.lopyluna.dndesires.register.helpers.wood_types.BlockPartial.ALL_PARTIALS;
import static dev.lopyluna.dndesires.register.helpers.wood_types.BlockPartial.FOR_POLISHED;

@SuppressWarnings("unused")
public class BlockPattern {
    public static final BlockPattern
            CUT = create("cut", PatternNameType.PREFIX, ALL_PARTIALS),
            BRICKS = create("cut_bricks", PatternNameType.WRAP, ALL_PARTIALS).textures("brick"),
            SMALL_BRICKS = create("small_bricks", PatternNameType.WRAP, ALL_PARTIALS).textures("small_brick"),
            POLISHED = create("polished_cut", PatternNameType.PREFIX, FOR_POLISHED).textures("polished", "slab"),
            LAYERED = create("layered", PatternNameType.PREFIX).blockStateFactory(p -> p::cubeColumn)
                    .textures("layered", "cap")
                    .connectedTextures(v -> new HorizontalCTBehaviour(ct(v, BlockPattern.CTs.LAYERED), ct(v, BlockPattern.CTs.CAP))),
            PILLAR = create("pillar", PatternNameType.SUFFIX).blockStateFactory(p -> p::pillar)
                    .block(ConnectedPillarBlock::new)
                    .textures("pillar", "cap")
                    .connectedTextures(v -> new RotatedPillarCTBehaviour(ct(v, BlockPattern.CTs.PILLAR), ct(v, BlockPattern.CTs.CAP)))
            ;

    public static final BlockPattern[] VANILLA_RANGE = { CUT, POLISHED, BRICKS, SMALL_BRICKS, LAYERED, PILLAR };
    public static final BlockPattern[] STANDARD_RANGE = { CUT, POLISHED, BRICKS, SMALL_BRICKS, LAYERED, PILLAR };

    static final String TEXTURE_LOCATION = "block/palettes/stone_types/%s/%s";

    private BlockPattern.PatternNameType nameType;
    private String[] textures;
    private String id;
    private boolean isTranslucent;
    private TagKey<Block>[] blockTags;
    private TagKey<Item>[] itemTags;
    @SuppressWarnings("all")
    private Optional<Function<String, ConnectedTextureBehaviour>> ctFactory;

    private BlockPattern.IPatternBlockStateGenerator blockStateGenerator;
    private NonNullFunction<BlockBehaviour.Properties, ? extends Block> blockFactory;
    private NonNullFunction<NonNullSupplier<Block>, NonNullBiConsumer<DataGenContext<Block, ? extends Block>, RegistrateRecipeProvider>> additionalRecipes;
    private BlockPartial<? extends Block>[] partials;

    @OnlyIn(Dist.CLIENT)
    private RenderType renderType;

    private static BlockPattern create(String name, BlockPattern.PatternNameType nameType,
                                              BlockPartial<?>... partials) {
        BlockPattern pattern = new BlockPattern();
        pattern.id = name;
        pattern.ctFactory = Optional.empty();
        pattern.nameType = nameType;
        pattern.partials = partials;
        pattern.additionalRecipes = $ -> NonNullBiConsumer.noop();
        pattern.isTranslucent = false;
        pattern.blockFactory = Block::new;
        pattern.textures = new String[] { name };
        pattern.blockStateGenerator = p -> p::cubeAll;
        return pattern;
    }

    @SuppressWarnings("all")
    public BlockPattern.IPatternBlockStateGenerator getBlockStateGenerator() {
        return blockStateGenerator;
    }

    public boolean isTranslucent() {
        return isTranslucent;
    }

    public TagKey<Block>[] getBlockTags() {
        return blockTags;
    }

    public TagKey<Item>[] getItemTags() {
        return itemTags;
    }

    public NonNullFunction<BlockBehaviour.Properties, ? extends Block> getBlockFactory() {
        return blockFactory;
    }

    public BlockPartial<? extends Block>[] getPartials() {
        return partials;
    }

    public String getTexture(int index) {
        return textures[index];
    }

    public void addRecipes(NonNullSupplier<Block> baseBlock, DataGenContext<Block, ? extends Block> c, RegistrateRecipeProvider p) {
        additionalRecipes.apply(baseBlock).accept(c, p);
    }

    public Optional<Supplier<ConnectedTextureBehaviour>> createCTBehaviour(String variant) {
        return ctFactory.map(d -> () -> d.apply(variant));
    }

    private BlockPattern blockStateFactory(BlockPattern.IPatternBlockStateGenerator factory) {
        blockStateGenerator = factory;
        return this;
    }

    private BlockPattern textures(String... textures) {
        this.textures = textures;
        return this;
    }

    private BlockPattern block(NonNullFunction<BlockBehaviour.Properties, ? extends Block> blockFactory) {
        this.blockFactory = blockFactory;
        return this;
    }

    private BlockPattern connectedTextures(Function<String, ConnectedTextureBehaviour> factory) {
        this.ctFactory = Optional.of(factory);
        return this;
    }

    @SuppressWarnings("all")
    public BlockPattern.IBlockStateProvider cubeAll(String variant) {
        ResourceLocation all = toLocation(variant, textures[0]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeAll(createName(variant), all));
    }

    @SuppressWarnings("all")
    public BlockPattern.IBlockStateProvider cubeBottomTop(String variant) {
        ResourceLocation side = toLocation(variant, textures[0]);
        ResourceLocation bottom = toLocation(variant, textures[1]);
        ResourceLocation top = toLocation(variant, textures[2]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeBottomTop(createName(variant), side, bottom, top));
    }

    @SuppressWarnings("all")
    public BlockPattern.IBlockStateProvider pillar(String variant) {
        ResourceLocation side = toLocation(variant, textures[0]);
        ResourceLocation end = toLocation(variant, textures[1]);
        return (ctx, prov) -> prov.getVariantBuilder(ctx.getEntry())
                .forAllStatesExcept(state -> {
                    Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
                    if (axis == Direction.Axis.Y)
                        return ConfiguredModel.builder()
                                .modelFile(prov.models()
                                        .cubeColumn(createName(variant), side, end))
                                .uvLock(false)
                                .build();
                    return ConfiguredModel.builder()
                            .modelFile(prov.models()
                                    .cubeColumnHorizontal(createName(variant) + "_horizontal", side, end))
                            .uvLock(false)
                            .rotationX(90)
                            .rotationY(axis == Direction.Axis.X ? 90 : 0)
                            .build();
                    }, BlockStateProperties.WATERLOGGED, ConnectedPillarBlock.NORTH, ConnectedPillarBlock.SOUTH, ConnectedPillarBlock.EAST, ConnectedPillarBlock.WEST);
    }

    @SuppressWarnings("all")
    public BlockPattern.IBlockStateProvider cubeColumn(String variant) {
        ResourceLocation side = toLocation(variant, textures[0]);
        ResourceLocation end = toLocation(variant, textures[1]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeColumn(createName(variant), side, end));
    }
    protected String createName(String variant) {
        if (nameType == PatternNameType.WRAP) {
            String[] split = id.split("_");
            if (split.length == 2) {
                String formatString = "%s_%s_%s";
                return String.format(formatString, split[0], variant, split[1]);
            }
        }
        String formatString = "%s_%s";
        return nameType == PatternNameType.SUFFIX ? String.format(formatString, variant, id) : String.format(formatString, id, variant);
    }
    protected static ResourceLocation toLocation(String variant, String texture) {
        return DnDesires.loc(String.format(TEXTURE_LOCATION, texture, variant + (texture.equals("cut") ? "_" : "_cut_") + texture));
    }
    protected static CTSpriteShiftEntry ct(String variant, BlockPattern.CTs texture) {
        ResourceLocation resLoc = texture.srcFactory.apply(variant);
        ResourceLocation resLocTarget = texture.targetFactory.apply(variant);
        return CTSpriteShifter.getCT(texture.type, resLoc, ResourceLocation.fromNamespaceAndPath(resLocTarget.getNamespace(), resLocTarget.getPath() + "_connected"));
    }
    @FunctionalInterface
    interface IPatternBlockStateGenerator extends Function<BlockPattern, Function<String, BlockPattern.IBlockStateProvider>> { }
    @FunctionalInterface
    interface IBlockStateProvider extends NonNullBiConsumer<DataGenContext<Block, ? extends Block>, RegistrateBlockstateProvider> { }

    enum PatternNameType {
        PREFIX, SUFFIX, WRAP
    }
    public enum CTs {
        PILLAR(AllCTTypes.RECTANGLE, s -> toLocation(s, "pillar")),
        CAP(AllCTTypes.OMNIDIRECTIONAL, s -> toLocation(s, "cap")),
        LAYERED(AllCTTypes.HORIZONTAL_KRYPPERS, s -> toLocation(s, "layered"));
        public final CTType type;
        private final Function<String, ResourceLocation> srcFactory;
        private final Function<String, ResourceLocation> targetFactory;
        CTs(CTType type, Function<String, ResourceLocation> factory) {
            this(type, factory, factory);
        }
        CTs(CTType type, Function<String, ResourceLocation> srcFactory,
            Function<String, ResourceLocation> targetFactory) {
            this.type = type;
            this.srcFactory = srcFactory;
            this.targetFactory = targetFactory;
        }
    }
}
