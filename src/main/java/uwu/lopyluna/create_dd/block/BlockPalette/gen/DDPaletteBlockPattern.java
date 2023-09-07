package uwu.lopyluna.create_dd.block.BlockPalette.gen;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import com.simibubi.create.foundation.block.connected.*;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteBlockPartial.ALL_PARTIALS;
import static uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteBlockPartial.FOR_POLISHED;


public class DDPaletteBlockPattern {

    public static final DDPaletteBlockPattern

            CUT =
            create("cut", PatternNameType.PREFIX, ALL_PARTIALS),

            BRICKS = create("cut_bricks", PatternNameType.WRAP, ALL_PARTIALS).textures("brick"),

            SMALL_BRICKS = create("small_bricks", PatternNameType.WRAP, ALL_PARTIALS).textures("small_brick"),
        
            POLISHED = create("polished_cut", PatternNameType.PREFIX, FOR_POLISHED).textures("polished", "slab"),
        
            LAYERED = create("layered", PatternNameType.PREFIX).blockStateFactory(p -> p::cubeColumn)
                    .textures("layered", "cap")
                    .connectedTextures(v -> new HorizontalCTBehaviour(ct(v, DDPaletteBlockPattern.CTs.LAYERED), ct(v, DDPaletteBlockPattern.CTs.CAP))),
        
            PILLAR = create("pillar", PatternNameType.SUFFIX).blockStateFactory(p -> p::pillar)
            .block(ConnectedPillarBlock::new)
            .textures("pillar", "cap")
            .connectedTextures(v -> new RotatedPillarCTBehaviour(ct(v, DDPaletteBlockPattern.CTs.PILLAR), ct(v, DDPaletteBlockPattern.CTs.CAP)))

            ;

    public static final DDPaletteBlockPattern[] STANDARD_RANGE = { CUT, POLISHED, BRICKS, SMALL_BRICKS, LAYERED, PILLAR };

    static final String TEXTURE_LOCATION = "block/palettes/stone_types/%s/%s";

    private DDPaletteBlockPattern.PatternNameType nameType;
    private String[] textures;
    private String id;
    private boolean isTranslucent;
    private Optional<Function<String, ConnectedTextureBehaviour>> ctFactory;

    private DDPaletteBlockPattern.IPatternBlockStateGenerator blockStateGenerator;
    private NonNullFunction<BlockBehaviour.Properties, ? extends Block> blockFactory;
    private NonNullFunction<NonNullSupplier<Block>, NonNullBiConsumer<DataGenContext<Block, ? extends Block>, RegistrateRecipeProvider>> additionalRecipes;
    private DDPaletteBlockPartial<? extends Block>[] partials;

    @OnlyIn(Dist.CLIENT)
    private RenderType renderType;

    private static DDPaletteBlockPattern create(String name, DDPaletteBlockPattern.PatternNameType nameType,
                                                DDPaletteBlockPartial<?>... partials) {
        DDPaletteBlockPattern pattern = new DDPaletteBlockPattern();
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

    public DDPaletteBlockPattern.IPatternBlockStateGenerator getBlockStateGenerator() {
        return blockStateGenerator;
    }

    public NonNullFunction<BlockBehaviour.Properties, ? extends Block> getBlockFactory() {
        return blockFactory;
    }

    public DDPaletteBlockPartial<? extends Block>[] getPartials() {
        return partials;
    }

    public String getTexture(int index) {
        return textures[index];
    }


    public Optional<Supplier<ConnectedTextureBehaviour>> createCTBehaviour(String variant) {
        return ctFactory.map(d -> () -> d.apply(variant));
    }

    // Builder

    private DDPaletteBlockPattern blockStateFactory(DDPaletteBlockPattern.IPatternBlockStateGenerator factory) {
        blockStateGenerator = factory;
        return this;
    }

    private DDPaletteBlockPattern textures(String... textures) {
        this.textures = textures;
        return this;
    }

    private DDPaletteBlockPattern block(NonNullFunction<BlockBehaviour.Properties, ? extends Block> blockFactory) {
        this.blockFactory = blockFactory;
        return this;
    }

    private DDPaletteBlockPattern connectedTextures(Function<String, ConnectedTextureBehaviour> factory) {
        this.ctFactory = Optional.of(factory);
        return this;
    }

    // Model generators

    public DDPaletteBlockPattern.IBlockStateProvider cubeAll(String variant) {
        ResourceLocation all = toLocation(variant, textures[0]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeAll(createName(variant), all));
    }

    public DDPaletteBlockPattern.IBlockStateProvider cubeBottomTop(String variant) {
        ResourceLocation side = toLocation(variant, textures[0]);
        ResourceLocation bottom = toLocation(variant, textures[1]);
        ResourceLocation top = toLocation(variant, textures[2]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeBottomTop(createName(variant), side, bottom, top));
    }

    public DDPaletteBlockPattern.IBlockStateProvider pillar(String variant) {
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
                        }, BlockStateProperties.WATERLOGGED, ConnectedPillarBlock.NORTH, ConnectedPillarBlock.SOUTH,
                        ConnectedPillarBlock.EAST, ConnectedPillarBlock.WEST);
    }

    public DDPaletteBlockPattern.IBlockStateProvider cubeColumn(String variant) {
        ResourceLocation side = toLocation(variant, textures[0]);
        ResourceLocation end = toLocation(variant, textures[1]);
        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                .cubeColumn(createName(variant), side, end));
    }

    // Utility

    public String createName(String variant) {
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

    public static ResourceLocation toLocation(String variant, String texture) {
        return DDCreate.asResource(
                String.format(TEXTURE_LOCATION, texture, variant + (texture.equals("cut") ? "_" : "_cut_") + texture));
    }

    protected static CTSpriteShiftEntry ct(String variant, DDPaletteBlockPattern.CTs texture) {
        ResourceLocation resLoc = texture.srcFactory.apply(variant);
        ResourceLocation resLocTarget = texture.targetFactory.apply(variant);
        return CTSpriteShifter.getCT(texture.type, resLoc,
                new ResourceLocation(resLocTarget.getNamespace(), resLocTarget.getPath() + "_connected"));
    }

    @FunctionalInterface
    static interface IPatternBlockStateGenerator
            extends Function<DDPaletteBlockPattern, Function<String, DDPaletteBlockPattern.IBlockStateProvider>> {
    }

    @FunctionalInterface
    static interface IBlockStateProvider
            extends NonNullBiConsumer<DataGenContext<Block, ? extends Block>, RegistrateBlockstateProvider> {
    }

    enum PatternNameType {
        PREFIX, SUFFIX, WRAP
    }

    // Textures with connectability, used by Spriteshifter

    public enum CTs {

        PILLAR(AllCTTypes.RECTANGLE, s -> toLocation(s, "pillar")),
        CAP(AllCTTypes.OMNIDIRECTIONAL, s -> toLocation(s, "cap")),
        LAYERED(AllCTTypes.HORIZONTAL_KRYPPERS, s -> toLocation(s, "layered"))

        ;

        public CTType type;
        private Function<String, ResourceLocation> srcFactory;
        private Function<String, ResourceLocation> targetFactory;

        private CTs(CTType type, Function<String, ResourceLocation> factory) {
            this(type, factory, factory);
        }

        private CTs(CTType type, Function<String, ResourceLocation> srcFactory,
                    Function<String, ResourceLocation> targetFactory) {
            this.type = type;
            this.srcFactory = srcFactory;
            this.targetFactory = targetFactory;
        }

    }

}
