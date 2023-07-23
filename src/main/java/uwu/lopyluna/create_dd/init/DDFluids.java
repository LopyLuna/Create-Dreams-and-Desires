package uwu.lopyluna.create_dd.init;

import static net.minecraft.world.item.Items.BUCKET;
import static uwu.lopyluna.create_dd.DDcreate.registrate;

import javax.annotation.Nullable;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.FluidEntry;

import io.github.fabricators_of_create.porting_lib.event.common.FluidPlaceBlockCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import uwu.lopyluna.create_dd.content.decoration.DDPaletteStoneTypes;
import uwu.lopyluna.create_dd.foundation.DDItemTabs;
@SuppressWarnings("UnstableApiUsage")
public class DDFluids {
	private static final CreateRegistrate REGISTRATE = registrate()
			.creativeModeTab(() -> DDItemTabs.BASE_CREATIVE_TAB);
	public static final FluidEntry<SimpleFlowableFluid.Flowing> CONDENSE_MILK =
			REGISTRATE.standardFluid("condense_milk")
					.lang("Condense Milk")
					.tag(AllTags.forgeFluidTag("condense_milk"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.condense_milk", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, condense_milk -> {
						Fluid source = condense_milk.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> CREAM =
			REGISTRATE.standardFluid("cream")
					.lang("Cream")
					.tag(AllTags.forgeFluidTag("cream"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.cream", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, cream -> {
						Fluid source = cream.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> VANILLA =
			REGISTRATE.standardFluid("vanilla")
					.lang("Vanilla")
					.tag(AllTags.forgeFluidTag("vanilla"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.vanilla", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, vanilla -> {
						Fluid source = vanilla.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> VANILLA_MILKSHAKE =
			REGISTRATE.standardFluid("vanilla_milkshake")
					.lang("Vanilla Milkshake")
					.tag(AllTags.forgeFluidTag("vanilla_milkshake"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.vanilla_milkshake", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, vanilla_milkshake -> {
						Fluid source = vanilla_milkshake.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> STRAWBERRY =
			REGISTRATE.standardFluid("strawberry")
					.lang("Strawberry")
					.tag(AllTags.forgeFluidTag("strawberry"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.strawberry", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, strawberry -> {
						Fluid source = strawberry.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> STRAWBERRY_MILKSHAKE =
			REGISTRATE.standardFluid("strawberry_milkshake")
					.lang("Strawberry Milkshake")
					.tag(AllTags.forgeFluidTag("strawberry_milkshake"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.strawberry_milkshake", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, strawberry_milkshake -> {
						Fluid source = strawberry_milkshake.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> GLOWBERRY =
			REGISTRATE.standardFluid("glowberry")
					.lang("Glowberry")
					.tag(AllTags.forgeFluidTag("glowberry"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.glowberry", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, glowberry -> {
						Fluid source = glowberry.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> GLOWBERRY_MILKSHAKE =
			REGISTRATE.standardFluid("glowberry_milkshake")
					.lang("Glowberry Milkshake")
					.tag(AllTags.forgeFluidTag("glowberry_milkshake"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.glowberry_milkshake", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, glowberry_milkshake -> {
						Fluid source = glowberry_milkshake.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> CARAMEL =
			REGISTRATE.standardFluid("caramel")
					.lang("Caramel")
					.tag(AllTags.forgeFluidTag("caramel"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.caramel", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, caramel -> {
						Fluid source = caramel.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> CARAMEL_MILKSHAKE =
			REGISTRATE.standardFluid("caramel_milkshake")
					.lang("Caramel Milkshake")
					.tag(AllTags.forgeFluidTag("caramel_milkshake"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.caramel_milkshake", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, caramel_milkshake -> {
						Fluid source = caramel_milkshake.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> HOT_CHOCOLATE =
			REGISTRATE.standardFluid("hot_chocolate")
					.lang("Hot Chocolate")
					.tag(AllTags.forgeFluidTag("hot_chocolate"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.hot_chocolate", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, hot_chocolate -> {
						Fluid source = hot_chocolate.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static final FluidEntry<SimpleFlowableFluid.Flowing> CHOCOLATE_MILKSHAKE =
			REGISTRATE.standardFluid("chocolate_milkshake")
					.lang("Chocolate Milkshake")
					.tag(AllTags.forgeFluidTag("chocolate_milkshake"), FluidTags.WATER) // fabric: water tag controls physics
					.fluidProperties(p -> p.levelDecreasePerBlock(2)
							.tickRate(25)
							.flowSpeed(3)
							.blastResistance(100f))
					.fluidAttributes(() -> new CreateAttributeHandler("block.create_dd.chocolate_milkshake", 1500, 1400))
					.onRegisterAfter(Registry.ITEM_REGISTRY, chocolate_milkshake -> {
						Fluid source = chocolate_milkshake.getSource();
						// transfer values
						FluidStorage.combinedItemApiProvider(source.getBucket()).register(context ->
								new FullItemFluidStorage(context, bucket -> ItemVariant.of(BUCKET), FluidVariant.of(source), FluidConstants.BUCKET));
						FluidStorage.combinedItemApiProvider(BUCKET).register(context ->
								new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(source.getBucket()), source, FluidConstants.BUCKET));
					})
					.register();

	public static void register() {}

	public static void registerFluidInteractions() {
		// fabric: no fluid interaction API, use legacy method
		FluidPlaceBlockCallback.EVENT.register(DDFluids::whenFluidsMeet);
	}
	public static BlockState whenFluidsMeet(LevelAccessor world, BlockPos pos, BlockState blockState) {
		FluidState fluidState = blockState.getFluidState();

		if (fluidState.isSource() && FluidHelper.isLava(fluidState.getType()))
			return null;

		for (Direction direction : Iterate.directions) {
			FluidState metFluidState =
					fluidState.isSource() ? fluidState : world.getFluidState(pos.relative(direction));
			if (!metFluidState.is(FluidTags.WATER))
				continue;
			BlockState lavaInteraction = getLavaInteraction(metFluidState);
			if (lavaInteraction == null)
				continue;
			return lavaInteraction;
		}
		return null;
	}
	@Nullable
	public static BlockState getLavaInteraction(FluidState fluidState) {
		Fluid fluid = fluidState.getType();
		if (fluid.isSame(DDFluids.VANILLA.get()))
			return AllPaletteStoneTypes.DEEPSLATE.getBaseBlock().get().defaultBlockState();

		if (fluid.isSame(DDFluids.VANILLA_MILKSHAKE.get()))
			return DDBlocks.potassic_cobble.getDefaultState();

		if (fluid.isSame(DDFluids.STRAWBERRY.get()))
			return Blocks.NETHERRACK.defaultBlockState();

		if (fluid.isSame(DDFluids.STRAWBERRY_MILKSHAKE.get()))
			return DDBlocks.crimsite_cobble.getDefaultState();

		if (fluid.isSame(DDFluids.GLOWBERRY.get()))
			return AllPaletteStoneTypes.DRIPSTONE.getBaseBlock().get().defaultBlockState();

		if (fluid.isSame(DDFluids.GLOWBERRY_MILKSHAKE.get()))
			return DDBlocks.ochrum_cobble.getDefaultState();

		if (fluid.isSame(DDFluids.CARAMEL.get()))
			return DDPaletteStoneTypes.gabbro.getBaseBlock().get().defaultBlockState();

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

	private record CreateAttributeHandler(Component name, int viscosity, boolean lighterThanAir) implements FluidVariantAttributeHandler {
		private CreateAttributeHandler(String key, int viscosity, int density) {
			this(Component.translatable(key), viscosity, density <= 0);
		}

		@Override
		public Component getName(FluidVariant fluidVariant) {
			return name.copy();
		}

		@Override
		public int getViscosity(FluidVariant variant, @Nullable Level world) {
			return viscosity;
		}

		@Override
		public boolean isLighterThanAir(FluidVariant variant) {
			return lighterThanAir;
		}
	}
}
