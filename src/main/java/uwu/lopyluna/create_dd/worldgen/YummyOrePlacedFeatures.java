package uwu.lopyluna.create_dd.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class YummyOrePlacedFeatures {

    public static final Holder<PlacedFeature> TIN_ORE_PLACED = PlacementUtils.register("tin_ore_placed",
            YummyOreFeatures.tin_ore, YummyOrePlacement.commonOrePlacement(10, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-45), VerticalAnchor.aboveBottom(128))));
}
