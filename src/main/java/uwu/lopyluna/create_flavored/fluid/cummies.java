package uwu.lopyluna.create_flavored.fluid;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;

import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;

public class cummies {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENCE_MILK  =
            REGISTRATE.standardFluid("condence_milk", cummies.NoColorFluidAttributes::new)
                    .lang("Condence Milk")
                    .tag(AllTags.forgeFluidTag("condence_milk"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA =
            REGISTRATE.standardFluid("vanilla", cummies.NoColorFluidAttributes::new)
                    .lang("Vanilla")
                    .tag(AllTags.forgeFluidTag("vanilla"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILKSHAKE =
            REGISTRATE.standardFluid("vanilla_milkshake", cummies.NoColorFluidAttributes::new)
                    .lang("Vanilla Milkshake")
                    .tag(AllTags.forgeFluidTag("vanilla_milkshake"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY =
            REGISTRATE.standardFluid("strawberry", cummies.NoColorFluidAttributes::new)
                    .lang("Strawberry")
                    .tag(AllTags.forgeFluidTag("strawberry"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE =
            REGISTRATE.standardFluid("strawberry_milkshake", cummies.NoColorFluidAttributes::new)
                    .lang("Strawberry Milkshake")
                    .tag(AllTags.forgeFluidTag("strawberry_milkshake"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL =
            REGISTRATE.standardFluid("caramel", cummies.NoColorFluidAttributes::new)
                    .lang("Caramel")
                    .tag(AllTags.forgeFluidTag("caramel"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL_MILKSHAKE =
            REGISTRATE.standardFluid("caramel_milkshake", cummies.NoColorFluidAttributes::new)
                    .lang("Caramel Milkshake")
                    .tag(AllTags.forgeFluidTag("caramel_milkshake"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM =
            REGISTRATE.standardFluid("cream", cummies.NoColorFluidAttributes::new)
                    .lang("Cream")
                    .tag(AllTags.forgeFluidTag("cream"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_CHOCOLATE =
            REGISTRATE.standardFluid("hot_chocolate", cummies.NoColorFluidAttributes::new)
                    .lang("Hot Chocolate")
                    .tag(AllTags.forgeFluidTag("hot_chocolate"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE =
            REGISTRATE.standardFluid("chocolate_milkshake", cummies.NoColorFluidAttributes::new)
                    .lang("Hot Chocolate Milkshake")
                    .tag(AllTags.forgeFluidTag("chocolate_milkshake"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<PotionFluid> SODA =
            REGISTRATE.virtualFluid("soda", PotionFluid.PotionFluidAttributes::new, PotionFluid::new)
                    .lang("Soda")
                    .register();

    public static final FluidEntry<PotionFluid> CREAM_SODA =
            REGISTRATE.virtualFluid("cream_soda", PotionFluid.PotionFluidAttributes::new, PotionFluid::new)
                    .lang("Cream Soda")
                    .register();

    public static void register() {}

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        if (fluid.isSame(VANILLA.get()))
            return AllPaletteStoneTypes.OCHRUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(VANILLA_MILKSHAKE.get()))
            return AllPaletteStoneTypes.OCHRUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(STRAWBERRY.get()))
            return AllPaletteStoneTypes.CRIMSITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(STRAWBERRY_MILKSHAKE.get()))
            return AllPaletteStoneTypes.CRIMSITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(CARAMEL.get()))
            return AllPaletteStoneTypes.VERIDIUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(CARAMEL_MILKSHAKE.get()))
            return AllPaletteStoneTypes.VERIDIUM.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(CREAM.get()))
            return AllPaletteStoneTypes.ASURINE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(HOT_CHOCOLATE.get()))
            return AllPaletteStoneTypes.SCORCHIA.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(CHOCOLATE_MILKSHAKE.get()))
            return AllPaletteStoneTypes.SCORIA.getBaseBlock()
                    .get()
                    .defaultBlockState();
        if (fluid.isSame(CONDENCE_MILK.get()))
            return AllPaletteStoneTypes.CALCITE.getBaseBlock()
                    .get()
                    .defaultBlockState();
        return null;
    }
    private static class NoColorFluidAttributes extends FluidAttributes {

        protected NoColorFluidAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }

        @Override
        public int getColor(BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

    }
}
