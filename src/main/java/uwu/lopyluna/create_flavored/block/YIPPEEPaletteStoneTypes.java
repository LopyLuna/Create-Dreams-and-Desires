package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_flavored.Flavoredcreate;

import java.util.function.Function;

import static uwu.lopyluna.create_flavored.block.YIPPEEPaletteBlockPattern.STANDARD_RANGE;

public enum YIPPEEPaletteStoneTypes {

    potassic(STANDARD_RANGE, r -> r.paletteStoneBlock("potassic", () -> Blocks.DEEPSLATE, true, false)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BLUE))
            .register()),

    weather_limestone(STANDARD_RANGE, r -> r.paletteStoneBlock("scorchia", () -> Blocks.SANDSTONE, true, false)
            .properties(p -> p.color(MaterialColor.COLOR_LIGHT_GRAY))
            .register()),

    VERIDIUM(STANDARD_RANGE, r -> r.paletteStoneBlock("veridium", () -> Blocks.TUFF, true, true)
            .properties(p -> p.destroyTime(1.25f)
                    .color(MaterialColor.WARPED_NYLIUM))
            .register())

    ;
    private final Function<CreateRegistrate, NonNullSupplier<Block>> factory;
    private YIPPEEPalettesVariant variants;

    public NonNullSupplier<Block> baseBlock;
    public final YIPPEEPaletteBlockPattern[] variantTypes;
    public TagKey<Item> materialTag;

    YIPPEEPaletteStoneTypes(YIPPEEPaletteBlockPattern[] variantTypes,
                            Function<CreateRegistrate, NonNullSupplier<Block>> factory) {
        this.factory = factory;
        this.variantTypes = variantTypes;
    }

    public NonNullSupplier<Block> getBaseBlock() {
        return baseBlock;
    }

    public YIPPEEPalettesVariant getVariants() {
        return variants;
    }

    public static void register(CreateRegistrate registrate) {
        for (YIPPEEPaletteStoneTypes paletteStoneVariants : values()) {
            NonNullSupplier<Block> baseBlock = paletteStoneVariants.factory.apply(registrate);
            paletteStoneVariants.baseBlock = baseBlock;
            String id = Lang.asId(paletteStoneVariants.name());
            paletteStoneVariants.materialTag =
                    AllTags.optionalTag(ForgeRegistries.ITEMS, Flavoredcreate.asResource("stone_types/" + id));
            paletteStoneVariants.variants = new YIPPEEPalettesVariant(id, paletteStoneVariants);
        }
    }
}
