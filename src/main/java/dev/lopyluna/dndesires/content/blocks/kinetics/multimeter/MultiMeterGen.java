package dev.lopyluna.dndesires.content.blocks.kinetics.multimeter;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.simibubi.create.foundation.data.DirectionalAxisBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class MultiMeterGen extends DirectionalAxisBlockStateGen {
    @Override
    public <T extends Block> String getModelPrefix(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return "block/gauge/base";
    }
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return prov.models().getExistingFile(Create.asResource(getModelPrefix(ctx, prov, state) + (state.getValue(GaugeBlock.FACING).getAxis().isVertical() ? "" : "_wall")));
    }
}
