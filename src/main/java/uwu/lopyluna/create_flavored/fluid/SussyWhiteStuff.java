package uwu.lopyluna.create_flavored.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import uwu.lopyluna.create_flavored.block.YIPPEEPaletteStoneTypes;

import javax.annotation.Nullable;

import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;

public class SussyWhiteStuff {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSE_MILK =
            REGISTRATE.standardFluid("condense_milk", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("cream", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("vanilla", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("vanilla_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("strawberry", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("strawberry_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("glowberry", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("glowberry_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("caramel", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("caramel_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("hot_chocolate", SussyWhiteStuff.NoColorFluidAttributes::new)
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
            REGISTRATE.standardFluid("chocolate_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
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

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        if (fluid.isSame(SussyWhiteStuff.VANILLA.get()))
            return YIPPEEPaletteStoneTypes.potassic.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.VANILLA_MILKSHAKE.get()))
            return YIPPEEPaletteStoneTypes.potassic.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.STRAWBERRY.get()))
            return AllPaletteStoneTypes.CRIMSITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.STRAWBERRY_MILKSHAKE.get()))
            return AllPaletteStoneTypes.CRIMSITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.GLOWBERRY.get()))
            return AllPaletteStoneTypes.OCHRUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.GLOWBERRY_MILKSHAKE.get()))
            return AllPaletteStoneTypes.OCHRUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.CARAMEL.get()))
            return AllPaletteStoneTypes.VERIDIUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.CARAMEL_MILKSHAKE.get()))
            return AllPaletteStoneTypes.VERIDIUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.CREAM.get()))
            return AllPaletteStoneTypes.ASURINE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.HOT_CHOCOLATE.get()))
            return AllPaletteStoneTypes.SCORCHIA.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.CHOCOLATE_MILKSHAKE.get()))
            return AllPaletteStoneTypes.SCORIA.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(SussyWhiteStuff.CONDENSE_MILK.get()))
            return AllPaletteStoneTypes.CALCITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
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
