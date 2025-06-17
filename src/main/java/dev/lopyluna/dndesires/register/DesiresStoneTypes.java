package dev.lopyluna.dndesires.register;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.register.helpers.wood_types.BlockPattern;
import dev.lopyluna.dndesires.register.helpers.wood_types.VariantEntry;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

import static dev.lopyluna.dndesires.register.DesiresSoundTypes.CRACKLE_STONE;
import static dev.lopyluna.dndesires.register.helpers.wood_types.BlockPattern.STANDARD_RANGE;

public enum DesiresStoneTypes {
    BRECCIA(STANDARD_RANGE, r -> r.paletteStoneBlock("breccia", () -> Blocks.TUFF, true, true)
            .properties(p -> p.destroyTime(1.25f)
                    .sound(CRACKLE_STONE)
                    .mapColor(MapColor.COLOR_ORANGE))
            .register())
    ;

    private final Function<CreateRegistrate, NonNullSupplier<Block>> factory;
    private VariantEntry variants;

    public NonNullSupplier<Block> baseBlock;
    public final BlockPattern[] variantTypes;
    public TagKey<Item> materialTag;

    DesiresStoneTypes(BlockPattern[] variantTypes, Function<CreateRegistrate, NonNullSupplier<Block>> factory) {
        this.factory = factory;
        this.variantTypes = variantTypes;
    }
    public NonNullSupplier<Block> getBaseBlock() {
        return baseBlock;
    }
    public VariantEntry getVariants() {
        return variants;
    }
    public static void register(CreateRegistrate registrate) {
        for (DesiresStoneTypes paletteStoneVariants : values()) {
            paletteStoneVariants.baseBlock = paletteStoneVariants.factory.apply(registrate);
            String id = Lang.asId(paletteStoneVariants.name());
            paletteStoneVariants.materialTag = DesiresTags.optionalTag(BuiltInRegistries.ITEM, DnDesires.loc("stone_types/" + id));
            paletteStoneVariants.variants = new VariantEntry(id, paletteStoneVariants);
        }
    }
}
