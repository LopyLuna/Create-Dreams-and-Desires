package uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.BiFunction;

public class ChainDriveBlockGen extends SpecialBlockStateGen {

    private BiFunction<BlockState, String, ModelFile> modelFunc;

    public ChainDriveBlockGen(BiFunction<BlockState, String, ModelFile> modelFunc) {
        this.modelFunc = modelFunc;
    }

    @Override
    protected int getXRotation(BlockState state) {
        ChainDriveBlock2.Part part = state.getValue(ChainDriveBlock2.PART);
        boolean connectedAlongFirst = state.getValue(ChainDriveBlock2.CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis axis = state.getValue(ChainDriveBlock2.AXIS);

        if (part == ChainDriveBlock2.Part.NONE)
            return axis == Direction.Axis.Y ? 90 : 0;
        if (axis == Direction.Axis.X)
            return (connectedAlongFirst ? 90 : 0) + (part == ChainDriveBlock2.Part.START ? 180 : 0);
        if (axis == Direction.Axis.Z)
            return (connectedAlongFirst ? 0 : (part == ChainDriveBlock2.Part.START ? 270 : 90));
        return 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        ChainDriveBlock2.Part part = state.getValue(ChainDriveBlock2.PART);
        boolean connectedAlongFirst = state.getValue(ChainDriveBlock2.CONNECTED_ALONG_FIRST_COORDINATE);
        Direction.Axis axis = state.getValue(ChainDriveBlock2.AXIS);

        if (part == ChainDriveBlock2.Part.NONE)
            return axis == Direction.Axis.X ? 90 : 0;
        if (axis == Direction.Axis.Z)
            return (connectedAlongFirst && part == ChainDriveBlock2.Part.END ? 270 : 90);
        boolean flip = part == ChainDriveBlock2.Part.END && !connectedAlongFirst || part == ChainDriveBlock2.Part.START && connectedAlongFirst;
        if (axis == Direction.Axis.Y)
            return (connectedAlongFirst ? 90 : 0) + (flip ? 180 : 0);
        return 0;
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        return modelFunc.apply(state, getModelSuffix(state));
    }

    protected String getModelSuffix(BlockState state) {
        ChainDriveBlock2.Part part = state.getValue(ChainDriveBlock2.PART);
        Direction.Axis axis = state.getValue(ChainDriveBlock2.AXIS);

        if (part == ChainDriveBlock2.Part.NONE)
            return "single";

        String orientation = axis == Direction.Axis.Y ? "vertical" : "horizontal";
        String section = part == ChainDriveBlock2.Part.MIDDLE ? "middle" : "end";
        return section + "_" + orientation;
    }

}
