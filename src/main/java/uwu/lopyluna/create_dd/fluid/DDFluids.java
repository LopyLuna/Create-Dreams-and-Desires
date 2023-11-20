package uwu.lopyluna.create_dd.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.api.event.PipeCollisionEvent;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;

import javax.annotation.Nullable;

import java.util.function.Consumer;

import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;


@Mod.EventBusSubscriber
public class DDFluids {
    static String id = DDCreate.MOD_ID;
    static String fd = "fluid/";
    static String st = "_still";
    static String fl = "_flow";
    

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSE_MILK =
            REGISTRATE.fluid("condense_milk",
                            new ResourceLocation(id,fd + "condense_milk" + st),
                            new ResourceLocation(id,fd + "condense_milk" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Condense Milk")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM =
            REGISTRATE.fluid("cream",
                            new ResourceLocation(id,fd + "cream" + st),
                            new ResourceLocation(id,fd + "cream" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Cream")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA =
            REGISTRATE.fluid("vanilla",
                            new ResourceLocation(id,fd + "vanilla" + st),
                            new ResourceLocation(id,fd + "vanilla" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Vanilla")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILKSHAKE =
            REGISTRATE.fluid("vanilla_milkshake",
                            new ResourceLocation(id,fd + "vanilla_milkshake" + st),
                            new ResourceLocation(id,fd + "vanilla_milkshake" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Vanilla Milkshake")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY =
            REGISTRATE.fluid("strawberry",
                            new ResourceLocation(id,fd + "strawberry" + st),
                            new ResourceLocation(id,fd + "strawberry" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Strawberry")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE =
            REGISTRATE.fluid("strawberry_milkshake",
                            new ResourceLocation(id,fd + "strawberry_milkshake" + st),
                            new ResourceLocation(id,fd + "strawberry_milkshake" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Strawberry Milkshake")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY =
            REGISTRATE.fluid("glowberry",
                            new ResourceLocation(id,fd + "glowberry" + st),
                            new ResourceLocation(id,fd + "glowberry" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Glowberry")
                    .properties(b -> b.viscosity(1500)
                            .density(1400)
                            .lightLevel(12))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE =
            REGISTRATE.fluid("glowberry_milkshake",
                            new ResourceLocation(id,fd + "glowberry_milkshake" + st),
                            new ResourceLocation(id,fd + "glowberry_milkshake" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Glowberry Milkshake")
                    .properties(b -> b.viscosity(1500)
                            .density(1400)
                            .lightLevel(12))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL =
            REGISTRATE.fluid("caramel",
                            new ResourceLocation(id,fd + "caramel" + st),
                            new ResourceLocation(id,fd + "caramel" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Caramel")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL_MILKSHAKE =
            REGISTRATE.fluid("caramel_milkshake",
                            new ResourceLocation(id,fd + "caramel_milkshake" + st),
                            new ResourceLocation(id,fd + "caramel_milkshake" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Caramel Milkshake")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_CHOCOLATE =
            REGISTRATE.fluid("hot_chocolate",
                            new ResourceLocation(id,fd + "hot_chocolate" + st),
                            new ResourceLocation(id,fd + "hot_chocolate" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Hot Chocolate")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE =
            REGISTRATE.fluid("chocolate_milkshake",
                            new ResourceLocation(id,fd + "chocolate_milkshake" + st),
                            new ResourceLocation(id,fd + "chocolate_milkshake" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Chocolate Milkshake")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHROMATIC_WASTE =
            REGISTRATE.fluid("chromatic_waste",
                            new ResourceLocation(id,fd + "chromatic_waste" + st),
                            new ResourceLocation(id,fd + "chromatic_waste" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Chromatic Waste")
                    .properties(b -> b.viscosity(6000)
                            .density(3000)
                            .lightLevel(8)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                            .lightLevel(8))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP =
            REGISTRATE.fluid("sap",
                            new ResourceLocation(id,fd + "sap" + st),
                            new ResourceLocation(id,fd + "sap" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Tree Sap")
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SHIMMER =
            REGISTRATE.fluid("shimmer",
                            new ResourceLocation(id,fd + "shimmer" + st),
                            new ResourceLocation(id,fd + "shimmer" + fl),
                            DDFluids.NoColorFluidAttributes::new)
                    .lang("Shimmer")
                    .properties(b -> b.viscosity(6000)
                            .density(50)
                            .canSwim(false)
                            .canDrown(true)
                            .canHydrate(false)
                            .rarity(Rarity.EPIC)
                            .lightLevel(15)
                            .temperature(-1000)
                            .motionScale(-1.25)
                            .supportsBoating(false)
                            .canExtinguish(false)
                            .canConvertToSource(true)
                            .pathType(BlockPathTypes.LAVA)
                            .sound(SoundActions.BUCKET_FILL, DDSoundEvents.shimmer_fill.get())
                            .sound(SoundActions.BUCKET_EMPTY, DDSoundEvents.shimmer_empty.get())
                            .adjacentPathType(null))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(20)
                            .slopeFindDistance(0)
                            .explosionResistance(10000f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .properties(p -> p.rarity(Rarity.EPIC))
                    .build()
                    .register();

    public static void register() {}

    public static void registerFluidInteractions() {

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                SHIMMER.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDPaletteStoneTypes.aethersite.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                VANILLA.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.DEEPSLATE.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                VANILLA_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.potassic_cobble.getDefaultState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                STRAWBERRY.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                STRAWBERRY_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.crimsite_cobble.getDefaultState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                GLOWBERRY.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.DRIPSTONE.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                GLOWBERRY_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.ochrum_cobble.getDefaultState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CARAMEL.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return Blocks.BLACKSTONE.defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CARAMEL_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.veridium_cobble.getDefaultState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CONDENSE_MILK.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.CALCITE.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CREAM.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.asurine_cobble.getDefaultState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                HOT_CHOCOLATE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CHOCOLATE_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.SCORIA.getBaseBlock().get().defaultBlockState();
                    }}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                SAP.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return Blocks.BASALT.defaultBlockState();
                    }}));
    }

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        if (fluid.isSame(DDFluids.SHIMMER.get()))
            return DDPaletteStoneTypes.aethersite.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.VANILLA.get()))
            return AllPaletteStoneTypes.DEEPSLATE.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.VANILLA_MILKSHAKE.get()))
            return DDBlocks.potassic_cobble.getDefaultState();

        if (fluid.isSame(DDFluids.STRAWBERRY.get()))
            return DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.STRAWBERRY_MILKSHAKE.get()))
            return DDBlocks.crimsite_cobble.getDefaultState();

        if (fluid.isSame(DDFluids.GLOWBERRY.get()))
            return AllPaletteStoneTypes.DRIPSTONE.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.GLOWBERRY_MILKSHAKE.get()))
            return DDBlocks.ochrum_cobble.getDefaultState();

        if (fluid.isSame(DDFluids.CARAMEL.get()))
            return Blocks.BLACKSTONE.defaultBlockState();

        if (fluid.isSame(DDFluids.CARAMEL_MILKSHAKE.get()))
            return DDBlocks.veridium_cobble.getDefaultState();

        if (fluid.isSame(DDFluids.CREAM.get()))
            return DDBlocks.asurine_cobble.getDefaultState();

        if (fluid.isSame(DDFluids.HOT_CHOCOLATE.get()))
            return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.CHOCOLATE_MILKSHAKE.get()))
            return AllPaletteStoneTypes.SCORIA.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.CONDENSE_MILK.get()))
            return AllPaletteStoneTypes.CALCITE.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.SAP.get()))
            return Blocks.BASALT.defaultBlockState();

        return null;

    }

    public static abstract class TintedFluidType extends FluidType {

        protected static final int NO_TINT = 0xffffffff;
        private ResourceLocation stillTexture;
        private ResourceLocation flowingTexture;

        public TintedFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties);
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {

                @Override
                public ResourceLocation getStillTexture() {
                    return stillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return flowingTexture;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return TintedFluidType.this.getTintColor(stack);
                }

                @Override
                public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return TintedFluidType.this.getTintColor(state, getter, pos);
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                        int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    Vector3f customFogColor = TintedFluidType.this.getCustomFogColor();
                    return customFogColor == null ? fluidFogColor : customFogColor;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                            float nearDistance, float farDistance, FogShape shape) {
                    float modifier = TintedFluidType.this.getFogDistanceModifier();
                    float baseWaterFog = 96.0f;
                    if (modifier != 1f) {
                        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                        RenderSystem.setShaderFogStart(-8);
                        RenderSystem.setShaderFogEnd(baseWaterFog * modifier);
                    }
                }

            });
        }

        protected abstract int getTintColor(FluidStack stack);

        protected abstract int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos);

        protected Vector3f getCustomFogColor() {
            return null;
        }

        protected float getFogDistanceModifier() {
            return 1f;
        }

    }
    

    private static class NoColorFluidAttributes extends TintedFluidType {

        public NoColorFluidAttributes(Properties properties, ResourceLocation stillTexture,
                                      ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }


    @SubscribeEvent
    public static void whenFluidsMeet(BlockEvent.FluidPlaceBlockEvent event) {
        BlockState blockState = event.getOriginalState();
        FluidState fluidState = blockState.getFluidState();
        BlockPos pos = event.getPos();
        LevelAccessor world = event.getLevel();

        if (fluidState.isSource() && FluidHelper.isLava(fluidState.getType()))
            return;

        for (Direction direction : Iterate.directions) {
            FluidState metFluidState =
                    fluidState.isSource() ? fluidState : world.getFluidState(pos.relative(direction));
            if (!metFluidState.is(FluidTags.WATER))
                continue;
            BlockState lavaInteraction = DDFluids.getLavaInteraction(metFluidState);
            if (lavaInteraction == null)
                continue;
            event.setNewState(lavaInteraction);
            break;
        }
    }





    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void handlePipeFlowCollisionFallback(PipeCollisionEvent.Flow event) {
        Fluid f1 = event.getFirstFluid();
        Fluid f2 = event.getSecondFluid();

        if (f1 == Fluids.WATER && f2 == Fluids.LAVA || f2 == Fluids.WATER && f1 == Fluids.LAVA) {
            event.setState(Blocks.COBBLESTONE.defaultBlockState());
        } else if (f1 == Fluids.LAVA && FluidHelper.hasBlockState(f2)) {
            BlockState lavaInteraction = DDFluids.getLavaInteraction(FluidHelper.convertToFlowing(f2).defaultFluidState());
            if (lavaInteraction != null) {
                event.setState(lavaInteraction);
            }
        } else if (f2 == Fluids.LAVA && FluidHelper.hasBlockState(f1)) {
            BlockState lavaInteraction = DDFluids.getLavaInteraction(FluidHelper.convertToFlowing(f1).defaultFluidState());
            if (lavaInteraction != null) {
                event.setState(lavaInteraction);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void handlePipeSpillCollisionFallback(PipeCollisionEvent.Spill event) {
        Fluid pf = event.getPipeFluid();
        Fluid wf = event.getWorldFluid();

        if (FluidHelper.isTag(pf, FluidTags.WATER) && wf == Fluids.LAVA) {
            event.setState(Blocks.OBSIDIAN.defaultBlockState());
        } else if (pf == Fluids.WATER && wf == Fluids.FLOWING_LAVA) {
            event.setState(Blocks.COBBLESTONE.defaultBlockState());
        } else if (pf == Fluids.LAVA && wf == Fluids.WATER) {
            event.setState(Blocks.STONE.defaultBlockState());
        } else if (pf == Fluids.LAVA && wf == Fluids.FLOWING_WATER) {
            event.setState(Blocks.COBBLESTONE.defaultBlockState());
        }

        if (pf == Fluids.LAVA) {
            BlockState lavaInteraction = DDFluids.getLavaInteraction(wf.defaultFluidState());
            if (lavaInteraction != null) {
                event.setState(lavaInteraction);
            }
        } else if (wf == Fluids.FLOWING_LAVA && FluidHelper.hasBlockState(pf)) {
            BlockState lavaInteraction = DDFluids.getLavaInteraction(FluidHelper.convertToFlowing(pf).defaultFluidState());
            if (lavaInteraction != null) {
                event.setState(lavaInteraction);
            }
        }
    }

}