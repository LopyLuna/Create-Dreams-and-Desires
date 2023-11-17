package uwu.lopyluna.create_dd.fluid;

import com.simibubi.create.api.event.PipeCollisionEvent;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.block.BlockPalette.DDPaletteStoneTypes;

import javax.annotation.Nullable;

import static uwu.lopyluna.create_dd.DDCreate.REGISTRATE;


@Mod.EventBusSubscriber
public class DDFluids {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSE_MILK =
            REGISTRATE.standardFluid("condense_milk", DDFluids.NoColorFluidAttributes::new)
                    .lang("Condense Milk")
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400)
                            .luminosity(12))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400)
                            .luminosity(12))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
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
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHROMATIC_WASTE =
            REGISTRATE.standardFluid("chromatic_waste", DDFluids.NoColorFluidAttributes::new)
                    .lang("Chromatic Waste")
                    .attributes(b -> b.viscosity(6000)
                            .density(3000)
                            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                            .sound(SoundEvents.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                            .luminosity(8))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP =
            REGISTRATE.standardFluid("sap", DDFluids.NoColorFluidAttributes::new)
                    .lang("Tree Sap")
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SHIMMER =
            REGISTRATE.standardFluid("shimmer", DDFluids.NoColorFluidAttributes::new)
                    .lang("Shimmer")
                    .attributes(b -> b.viscosity(6000)
                            .density(50)
                            .rarity(Rarity.EPIC)
                            .luminosity(15)
                            .temperature(-1000)
                            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_FILL_POWDER_SNOW)
                            .sound(SoundEvents.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_POWDER_SNOW))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(20)
                            .slopeFindDistance(0)
                            .explosionResistance(10000f))
                    .source(ForgeFlowingFluid.Source::new)
                    .bucket()
                    .properties(p -> p.rarity(Rarity.EPIC))
                    .build()
                    .register();

    public static void register() {}

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        if (fluid.isSame(DDFluids.SHIMMER.get()))
            return DDPaletteStoneTypes.aethersite.getBaseBlock().get().defaultBlockState();

        if (fluid.isSame(DDFluids.CHROMATIC_WASTE.get()))
            return Blocks.CRYING_OBSIDIAN.defaultBlockState();

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

    private static class NoColorFluidAttributes extends FluidAttributes {

        public BlockState getBlock(BlockAndTintGetter reader, BlockPos pos, FluidState state)
        {
            return state.createLegacyBlock();
        }


        protected NoColorFluidAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }

        @Override
        public int getColor(BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }


    @SubscribeEvent
    public static void whenFluidsMeet(BlockEvent.FluidPlaceBlockEvent event) {
        BlockState blockState = event.getOriginalState();
        FluidState fluidState = blockState.getFluidState();
        BlockPos pos = event.getPos();
        LevelAccessor world = event.getWorld();

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
        } else if (FluidHelper.isTag(pf, FluidTags.WATER) && wf == CHROMATIC_WASTE.get().getSource()) {
            event.setState(Blocks.CRYING_OBSIDIAN.defaultBlockState());
        } else if (pf == Fluids.WATER && wf == CHROMATIC_WASTE.get().getFlowing()) {
            event.setState(Blocks.OBSIDIAN.defaultBlockState());
        } else if (pf == CHROMATIC_WASTE.get().getSource() && wf == Fluids.WATER) {
            event.setState(Blocks.OBSIDIAN.defaultBlockState());
        } else if (pf == CHROMATIC_WASTE.get().getSource() && wf == Fluids.FLOWING_WATER) {
            event.setState(Blocks.OBSIDIAN.defaultBlockState());
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
