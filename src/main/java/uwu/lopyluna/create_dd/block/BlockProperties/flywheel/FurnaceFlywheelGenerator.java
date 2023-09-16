package uwu.lopyluna.create_dd.block.BlockProperties.flywheel;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.client.model.generators.ModelFile;

public class FurnaceFlywheelGenerator extends SpecialBlockStateGen {

    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        return horizontalAngle(state.getValue(FurnaceFlywheelBlock.HORIZONTAL_AXIS)) + 90;
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        return prov.models()
                .getExistingFile(prov.modLoc("block/" + ctx.getName() + "/casing_" + state.getValue(FurnaceFlywheelBlock.CONNECTION)
                        .getSerializedName()));
    }
}
