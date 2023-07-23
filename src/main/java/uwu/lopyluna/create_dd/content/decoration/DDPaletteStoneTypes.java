package uwu.lopyluna.create_dd.content.decoration;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import uwu.lopyluna.create_dd.DDcreate;

import java.util.function.Function;

import static uwu.lopyluna.create_dd.content.decoration.DDPaletteBlockPattern.STANDARD_RANGE;

public enum DDPaletteStoneTypes {
	potassic(STANDARD_RANGE, r -> r.paletteStoneBlock("potassic", () -> Blocks.DEEPSLATE, true, false)
			.properties(p -> p.color(MaterialColor.TERRACOTTA_BLUE))
			.register()),

	weathered_limestone(STANDARD_RANGE, r -> r.paletteStoneBlock("weathered_limestone", () -> Blocks.SANDSTONE, true, false)
			.properties(p -> p.color(MaterialColor.COLOR_LIGHT_GRAY))
			.register()),

	gabbro(STANDARD_RANGE, r -> r.paletteStoneBlock("gabbro", () -> Blocks.TUFF, true, true)
			.properties(p -> p.destroyTime(1.25f)
			.color(MaterialColor.TERRACOTTA_LIGHT_GRAY))
			.register())

	;
	private final Function<CreateRegistrate, NonNullSupplier<Block>> factory;
	private DDPaletteVariant variants;

	public NonNullSupplier<Block> baseBlock;
	public final DDPaletteBlockPattern[] variantTypes;
	public TagKey<Item> materialTag;

	DDPaletteStoneTypes(DDPaletteBlockPattern[] variantTypes,
							Function<CreateRegistrate, NonNullSupplier<Block>> factory) {
		this.factory = factory;
		this.variantTypes = variantTypes;
	}

	public NonNullSupplier<Block> getBaseBlock() {
		return baseBlock;
	}

	public DDPaletteVariant getVariants() {
		return variants;
	}

	public static void register(CreateRegistrate registrate) {
		for (DDPaletteStoneTypes paletteStoneVariants : values()) {
			NonNullSupplier<Block> baseBlock = paletteStoneVariants.factory.apply(registrate);
			paletteStoneVariants.baseBlock = baseBlock;
			String id = Lang.asId(paletteStoneVariants.name());
			paletteStoneVariants.materialTag =
					AllTags.optionalTag(Registry.ITEM, DDcreate.asResource("stone_types/" + id));
			paletteStoneVariants.variants = new DDPaletteVariant(id, paletteStoneVariants);
		}
	}
}
