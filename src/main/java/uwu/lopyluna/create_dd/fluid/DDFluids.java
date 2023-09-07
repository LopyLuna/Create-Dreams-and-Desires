package uwu.lopyluna.create_dd.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;

import javax.annotation.Nullable;

import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;


public class DDFluids {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSE_MILK =
            REGISTRATE.standardFluid("condense_milk", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("cream", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("vanilla", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("vanilla_milkshake", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("strawberry", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("strawberry_milkshake", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("glowberry", DDFluids.NoColorFluidAttributes::new)
                    .lang("Glowberry")
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

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE =
            REGISTRATE.standardFluid("glowberry_milkshake", DDFluids.NoColorFluidAttributes::new)
                    .lang("Glowberry Milkshake")
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

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL =
            REGISTRATE.standardFluid("caramel", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("caramel_milkshake", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("hot_chocolate", DDFluids.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("chocolate_milkshake", DDFluids.NoColorFluidAttributes::new)
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

    public static void register() {}

    public static void registerFluidInteractions() {
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
                        return DDBlocks.potassic_cobble.getDefaultState();}}));

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
                        return DDBlocks.crimsite_cobble.getDefaultState();}}));

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
                        return DDBlocks.ochrum_cobble.getDefaultState();}}));

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
                        return DDBlocks.veridium_cobble.getDefaultState();}}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CONDENSE_MILK.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.CALCITE.getBaseBlock().get().defaultBlockState();}}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CREAM.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return DDBlocks.asurine_cobble.getDefaultState();}}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                HOT_CHOCOLATE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();}}));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                CHOCOLATE_MILKSHAKE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return AllPaletteStoneTypes.SCORIA.getBaseBlock().get().defaultBlockState();}}));
    }

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
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
        return null;

    }

    private static class NoColorFluidAttributes extends AllFluids.TintedFluidType {

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
}
