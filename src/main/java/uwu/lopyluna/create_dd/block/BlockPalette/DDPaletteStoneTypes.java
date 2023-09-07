package uwu.lopyluna.create_dd.block.BlockPalette;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.ForgeRegistries;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteBlockPattern;
import uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPalettesVariant;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;

import java.util.function.Function;

import static uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteBlockPattern.STANDARD_RANGE;

public enum DDPaletteStoneTypes {

    potassic(STANDARD_RANGE, r -> r.paletteStoneBlock("potassic", () -> Blocks.DEEPSLATE, true, false)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BLUE))
            .properties(p -> p.sound(new ForgeSoundType(0.8f, 0.85f, () -> DDSoundEvents.ore_stone_break.get(),
                    () -> DDSoundEvents.ore_stone_step.get(), () -> DDSoundEvents.ore_stone_place.get(),
                    () -> DDSoundEvents.ore_stone_hit.get(), () -> DDSoundEvents.ore_stone_fall.get())))
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
    private DDPalettesVariant variants;

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

    public DDPalettesVariant getVariants() {
        return variants;
    }

    public static void register(CreateRegistrate registrate) {
        for (DDPaletteStoneTypes paletteStoneVariants : values()) {
            NonNullSupplier<Block> baseBlock = paletteStoneVariants.factory.apply(registrate);
            paletteStoneVariants.baseBlock = baseBlock;
            String id = Lang.asId(paletteStoneVariants.name());
            paletteStoneVariants.materialTag =
                    AllTags.optionalTag(ForgeRegistries.ITEMS, DDCreate.asResource("stone_types/" + id));
            paletteStoneVariants.variants = new DDPalettesVariant(id, paletteStoneVariants);
        }
    }
}
