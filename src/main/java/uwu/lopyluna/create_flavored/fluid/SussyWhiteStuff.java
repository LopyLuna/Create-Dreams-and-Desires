package uwu.lopyluna.create_flavored.fluid;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import uwu.lopyluna.create_flavored.block.YIPPEEPaletteStoneTypes;

import javax.annotation.Nullable;

import static com.simibubi.create.AllTags.optionalTag;
import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;

public class SussyWhiteStuff {

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CONDENSE_MILK =
            REGISTRATE.standardFluid("condense_milk", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Condense Milk")
                    .tag(AllTags.forgeFluidTag("condense_milk"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA =
            REGISTRATE.standardFluid("vanilla", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Vanilla")
                    .tag(AllTags.forgeFluidTag("vanilla"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILKSHAKE =
            REGISTRATE.standardFluid("vanilla_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Vanilla Milkshake")
                    .tag(AllTags.forgeFluidTag("vanilla_milkshake"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY =
            REGISTRATE.standardFluid("strawberry", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Strawberry")
                    .tag(AllTags.forgeFluidTag("strawberry"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE =
            REGISTRATE.standardFluid("strawberry_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Strawberry Milkshake")
                    .tag(AllTags.forgeFluidTag("strawberry_milkshake"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY =
            REGISTRATE.standardFluid("glowberry", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Glowberry")
                    .tag(AllTags.forgeFluidTag("glowberry"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE =
            REGISTRATE.standardFluid("glowberry_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Glowberry Milkshake")
                    .tag(AllTags.forgeFluidTag("glowberry_milkshake"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL =
            REGISTRATE.standardFluid("caramel", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Caramel")
                    .tag(AllTags.forgeFluidTag("caramel"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CARAMEL_MILKSHAKE =
            REGISTRATE.standardFluid("caramel_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Caramel Milkshake")
                    .tag(AllTags.forgeFluidTag("caramel_milkshake"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM =
            REGISTRATE.standardFluid("cream", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Cream")
                    .tag(AllTags.forgeFluidTag("cream"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> HOT_CHOCOLATE =
            REGISTRATE.standardFluid("hot_chocolate", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Hot Chocolate")
                    .tag(AllTags.forgeFluidTag("hot_chocolate"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE =
            REGISTRATE.standardFluid("chocolate_milkshake", SussyWhiteStuff.NoColorFluidAttributes::new)
                    .lang("Hot Chocolate Milkshake")
                    .tag(AllTags.forgeFluidTag("chocolate_milkshake"))
                    .tag(SussyWhiteStuff.MinecraftFluidTag("water"))
                    .attributes(b -> b.viscosity(1500)
                            .density(1400))
                    .properties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
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
    private static TagKey<Fluid> MinecraftFluidTag(String water) {
        return MinecraftTag(ForgeRegistries.FLUIDS, water);
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> MinecraftTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("minecraft", path));
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
}
