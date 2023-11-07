package uwu.lopyluna.create_dd.block.BlockResources;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import uwu.lopyluna.create_dd.DDCreate;


public class DDBlockSpriteShifts {




    public static final CTSpriteShiftEntry
    MITHRIL_CASING = omni("mithril_casing"),
    BRONZE_CASING = omni("bronze_casing"),
    ZINC_CASING = omni("zinc_casing"),
    TIN_CASING = omni("tin_casing"),
    NETHERITE_CASING = omni("netherite_casing"),
    MOSSY_ANDESITE_CASING = omni("mossy_andesite_casing"),
    BRICK_CASING = omni("brick_casing"),
    NETHER_BRICK_CASING = omni("nether_brick_casing"),
    HYDRAULIC_CASING = omni("hydraulic_casing"),
    INDUSTRIAL_CASING = omni("industrial_casing"),
    STEEL_CASING = omni("steel_casing"),
    OVERBURDEN_CASING = omni("overburden_casing"),
    SHADOW_STEEL_CASING = omni("shadow_steel_casing"),
    REFINED_RADIANCE_CASING = omni("refined_radiance_casing"),
    STARGAZE_SINGULARITY_CASING = omni("stargaze_singularity_casing"),
    BLAZE_GOLD_CASING = omni("blaze_gold_casing"),
    OVERCHARGED_CASING = omni("overcharged_casing"),

    reinforcement_plating = omni("reinforcement_plating"),

    blueprint_block = omni("blueprint_block"),

    vertical_framed_split_glass = omni("palettes/vertical_framed_split_glass"),
    horizontal_framed_split_glass = omni("palettes/horizontal_framed_split_glass"),
    framed_split_glass = omni("palettes/framed_split_glass"),
    ornate_iron_glass = omni("palettes/ornate_iron_glass"),
    ornate_iron_glass_top = omni("palettes/ornate_iron_glass_top"),

    HAZARD = omni("hazard_block"),
    HORIZONTAL_HAZARD = omni("horizontal_hazard_block"),
    HAZARD_R = omni("hazard_block_r"),
    HORIZONTAL_HAZARD_R = omni("horizontal_hazard_block_r");

    public static final CTSpriteShiftEntry
    TRAIN_SCAFFOLD = horizontal("train_scaffold"),
    MITHRIL_SCAFFOLD = horizontal("mithril_scaffold"),
    BRONZE_SCAFFOLD = horizontal("bronze_scaffold"),
    STEEL_SCAFFOLD = horizontal("steel_scaffold"),
    ZINC_SCAFFOLD = horizontal("zinc_scaffold"),
    TIN_SCAFFOLD = horizontal("tin_scaffold"),
    NETHERITE_SCAFFOLD = horizontal("netherite_scaffold"),
    MOSSY_ANDESITE_SCAFFOLD = horizontal("mossy_andesite_scaffold"),
    HYDRAULIC_SCAFFOLD = horizontal("hydraulic_scaffold"),
    INDUSTRIAL_SCAFFOLD = horizontal("industrial_scaffold"),
    STARGAZE_SINGULARITY_SCAFFOLD = horizontal("stargaze_singularity_scaffold"),
    BLAZE_GOLD_SCAFFOLD = horizontal("blaze_gold_scaffold"),
    OVERCHARGED_SCAFFOLD = horizontal("overcharged_scaffold"),
    OVERBURDEN_SCAFFOLD = horizontal("overburden_scaffold"),
    SHADOW_STEEL_SCAFFOLD = horizontal("shadow_steel_scaffold"),
    REFINED_RADIANCE_SCAFFOLD = horizontal("refined_radiance_scaffold");


    public static final CTSpriteShiftEntry
    TRAIN_SCAFFOLD_INSIDE = horizontal("train_scaffold_inside"),
    MITHRIL_SCAFFOLD_INSIDE = horizontal("mithril_scaffold_inside"),
    BRONZE_SCAFFOLD_INSIDE = horizontal("bronze_scaffold_inside"),
    STEEL_SCAFFOLD_INSIDE = horizontal("steel_scaffold_inside"),
    ZINC_SCAFFOLD_INSIDE = horizontal("zinc_scaffold_inside"),
    TIN_SCAFFOLD_INSIDE = horizontal("tin_scaffold_inside"),
    NETHERITE_SCAFFOLD_INSIDE = horizontal("netherite_scaffold_inside"),
    MOSSY_ANDESITE_SCAFFOLD_INSIDE = horizontal("mossy_andesite_scaffold_inside"),
    HYDRAULIC_SCAFFOLD_INSIDE = horizontal("hydraulic_scaffold_inside"),
    INDUSTRIAL_SCAFFOLD_INSIDE = horizontal("industrial_scaffold_inside"),
    OVERBURDEN_SCAFFOLD_INSIDE = horizontal("overburden_scaffold_inside"),
    STARGAZE_SINGULARITY_SCAFFOLD_INSIDE = horizontal("stargaze_singularity_scaffold_inside"),
    BLAZE_GOLD_SCAFFOLD_INSIDE = horizontal("blaze_gold_scaffold_inside"),
    OVERCHARGED_SCAFFOLD_INSIDE = horizontal("overcharged_scaffold_inside"),
    SHADOW_STEEL_SCAFFOLD_INSIDE = horizontal("shadow_steel_scaffold_inside"),
    REFINED_RADIANCE_SCAFFOLD_INSIDE = horizontal("refined_radiance_scaffold_inside");

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }
    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DDCreate.asResource("block/" + blockTextureName), DDCreate.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
