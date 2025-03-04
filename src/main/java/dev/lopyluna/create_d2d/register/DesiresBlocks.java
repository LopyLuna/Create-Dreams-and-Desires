package dev.lopyluna.create_d2d.register;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.create_d2d.content.blocks.propeller.PropellerBlock;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.StirlingEngineBlock;
import dev.lopyluna.create_d2d.content.blocks.stirling_engine.flywheel.PoweredFlywheelBlock;
import dev.lopyluna.create_d2d.content.configs.server.kinetics.DStress;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static dev.lopyluna.create_d2d.DesiresCreate.REG;

public class DesiresBlocks {

    public static final BlockEntry<PropellerBlock> PROPELLER = REG.block("propeller", PropellerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN))
            .transform(axeOrPickaxe())
            .transform(DStress.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static final DyedBlockList<PropellerBlock> DYED_PROPELLERS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_propeller", p -> new PropellerBlock(p, color))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .blockstate((c, p) -> {
                    ModelFile model = p.models().withExistingParent(colorName + "_propeller", p.modLoc("block/propeller/block"))
                            .texture("0", p.modLoc("block/propeller/" + colorName));
                    BlockStateGen.axisBlock(c, p, s -> model);
                })
                .item()
                .model((c, p) -> p.withExistingParent(colorName + "_propeller", p.modLoc("block/propeller/item"))
                        .texture("1", p.modLoc("block/propeller/" + colorName)))
                .build()
                .register();
    });

    public static final BlockEntry<StirlingEngineBlock> STIRLING_ENGINE = REG.block("stirling_engine", StirlingEngineBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_CYAN).forceSolidOn())
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.BRITTLE.tag)
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(DStress.setCapacity(1024.0))
            .onRegister(BlockStressValues.setGeneratorSpeed(32, true))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<PoweredFlywheelBlock> POWERED_FLYWHEEL = REG.block("powered_flywheel", PoweredFlywheelBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_YELLOW).forceSolidOn())
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(false))
            .loot((lt, block) -> lt.dropOther(block, AllBlocks.FLYWHEEL.get()))
            .register();

    public static void register() {}
}
