package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.function.Supplier;

import static dev.lopyluna.create_d2d.DesiresCreate.REG;
import static dev.lopyluna.create_d2d.DesiresUtils.randomChance;
import static dev.lopyluna.create_d2d.register.DesiresConfigs.client;
import static dev.lopyluna.create_d2d.register.DesiresCreativeTabs.BASE_CREATIVE_TAB;

public class DesiresFluids {
    static {
        REG.setCreativeTab(BASE_CREATIVE_TAB);
    }

    public static final FluidEntry<BaseFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE = newFluid("Chocolate Milkshake",
            0xB2614D, "c")
            .register();
    public static final FluidEntry<BaseFlowingFluid.Flowing> VANILLA_MILKSHAKE = newFluid("Vanilla Milkshake",
            0xEDDABA, "v")
            .register();
    public static final FluidEntry<BaseFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE = newFluid("Strawberry Milkshake",
            0xD57A8B, "s")
            .register();
    public static final FluidEntry<BaseFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE = newFluid("Glowberry Milkshake",
            0xD8A155, "g")
            .register();
    public static final FluidEntry<BaseFlowingFluid.Flowing> PUMPKIN_MILKSHAKE = newFluid("Pumpkin Milkshake",
            0xCB7B38, "p")
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> SAP = newFluid("Sap",
            0xC87E50, "")
            .register();

    public static FluidBuilder<BaseFlowingFluid.Flowing, CreateRegistrate> newFluid(String name, int hexColor, String type) {
        String id = name.toLowerCase().replace(" ", "_");
        return REG.standardFluid(id, SolidRenderedPlaceableFluidType.create(hexColor, () -> 1f / 4f * (type.equals("c") ?
                        client().chocolateTransparencyMultiplier : type.equals("v") ?
                        client().vanillaTransparencyMultiplier : type.equals("s") ?
                        client().strawberryTransparencyMultiplier : type.equals("g") ?
                        client().glowberryTransparencyMultiplier : type.equals("p") ?
                        client().pumpkinTransparencyMultiplier :
                        client().sapTransparencyMultiplier).getF()
                )).lang(name)
                .properties(b -> b.viscosity(1000)
                        .density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2)
                        .tickRate(10)
                        .slopeFindDistance(3)
                        .explosionResistance(100f))
                .tag(DesiresTags.commonFluidTag(id))
                .source(BaseFlowingFluid.Source::new)
                .bucket()
                .tag(DesiresTags.commonItemTag("buckets/" + id))
                .build();
    }

    public static void register() {}


    public static void registerFluidInteractions() {
        addMilkshakeInteraction(CHOCOLATE_MILKSHAKE.get(), AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get());
        //addMilkshakeInteraction(VANILLA_MILKSHAKE.get(), AllPaletteStoneTypes.ASURINE.getBaseBlock().get(), DesiresPaletteStoneTypes.DOLOMITE.getBaseBlock().get());
        addMilkshakeInteraction(STRAWBERRY_MILKSHAKE.get(), AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE);
        //addMilkshakeInteraction(GLOWBERRY_MILKSHAKE.get(), AllPaletteStoneTypes.OCHRUM.getBaseBlock().get(), DesiresPaletteStoneTypes.GABBRO.getBaseBlock().get());
        //addMilkshakeInteraction(PUMPKIN_MILKSHAKE.get(), DesiresPaletteStoneTypes.BRECCIA.getBaseBlock().get(), Blocks.DRIPSTONE_BLOCK);
    }

    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        if (fluid.isSame(CHOCOLATE_MILKSHAKE.get())) return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();
        //if (fluid.isSame(VANILLA_MILKSHAKE.get())) return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();
        if (fluid.isSame(STRAWBERRY_MILKSHAKE.get())) return Blocks.COBBLED_DEEPSLATE.defaultBlockState();
        //if (fluid.isSame(GLOWBERRY_MILKSHAKE.get())) return AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get().defaultBlockState();
        if (fluid.isSame(PUMPKIN_MILKSHAKE.get())) return Blocks.DRIPSTONE_BLOCK.defaultBlockState();
        return null;
    }

    public static BlockState getInteractions(FluidState fluidState, Level level, BlockPos pos) {
        if (addMilkshakeFlag(fluidState, CHOCOLATE_MILKSHAKE.get(), level, pos))
            return addMilkshakeStones(AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get(), level, pos);
        //if (addMilkshakeFlag(fluidState, VANILLA_MILKSHAKE.get(), level, pos))
        //    return addMilkshakeStones(AllPaletteStoneTypes.ASURINE.getBaseBlock().get(), DesiresPaletteStoneTypes.DOLOMITE.getBaseBlock().get(), level, pos);
        if (addMilkshakeFlag(fluidState, STRAWBERRY_MILKSHAKE.get(), level, pos))
            return addMilkshakeStones(AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE, level, pos);
        //if (addMilkshakeFlag(fluidState, GLOWBERRY_MILKSHAKE.get(), level, pos))
        //    return addMilkshakeStones(AllPaletteStoneTypes.OCHRUM.getBaseBlock().get(), DesiresPaletteStoneTypes.GABBRO.getBaseBlock().get(), level, pos);
        //if (addMilkshakeFlag(fluidState, PUMPKIN_MILKSHAKE.get(), level, pos))
        //    return addMilkshakeStones(DesiresPaletteStoneTypes.BRECCIA.getBaseBlock().get(), Blocks.DRIPSTONE_BLOCK, level, pos);

        return null;
    }

    public static void addMilkshakeInteraction(Fluid fluid, Block stoneBedrock, Block stoneDefault) {
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) -> level.getFluidState(relativePos).is(fluid) &&
                        fluidState.isSource(),
                Blocks.OBSIDIAN.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) -> randomChance(DesiresConfigs.server().chanceForOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(DesiresTags.BlockTags.ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) &&
                        !fluidState.isSource(),
                stoneBedrock.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) -> randomChance(DesiresConfigs.server().chanceForArtificialOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(DesiresTags.BlockTags.ARTIFICIAL_ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) &&
                        !fluidState.isSource(),
                stoneBedrock.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) -> level.getFluidState(relativePos).is(fluid) &&
                        !fluidState.isSource(),
                stoneDefault.defaultBlockState()
        ));
    }
    @SuppressWarnings("deprecation")
    public static boolean addMilkshakeFlag(FluidState fluidState, Fluid milkshake, Level level, BlockPos pos) {
        boolean validMilkshakePoints =
                level.getFluidState(pos.relative(Direction.Axis.X, 1)).is(milkshake) ||
                level.getFluidState(pos.relative(Direction.Axis.Y, 1)).is(milkshake) ||
                level.getFluidState(pos.relative(Direction.Axis.Z, 1)).is(milkshake);
        boolean pointIsEmpty =
                level.getBlockState(pos).isAir() ||
                level.getBlockState(pos).canBeReplaced();
        boolean isValidMilkshake = validMilkshakePoints && pointIsEmpty && !(level.getBlockState(pos).liquid());
        return fluidState.getType().isSame(milkshake) || isValidMilkshake;
    }

    public static BlockState addMilkshakeStones(Block stoneBedrock, Block stoneDefault, Level level, BlockPos pos) {
        if (level.getBlockState(pos.below()).is(DesiresTags.BlockTags.ORE_GENERATOR.tag) && randomChance(DesiresConfigs.server().chanceForOreStone.get(), level))
            return stoneBedrock.defaultBlockState();
        else if (level.getBlockState(pos.below()).is(DesiresTags.BlockTags.ARTIFICIAL_ORE_GENERATOR.tag) && randomChance(DesiresConfigs.server().chanceForArtificialOreStone.get(), level))
            return stoneBedrock.defaultBlockState();
        return stoneDefault.defaultBlockState();
    }

    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }
        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) { super(properties, stillTexture, flowingTexture); }
        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }
        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }
        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
