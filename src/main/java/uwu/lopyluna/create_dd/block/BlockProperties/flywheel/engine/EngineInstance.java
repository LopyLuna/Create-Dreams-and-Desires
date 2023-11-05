package uwu.lopyluna.create_dd.block.BlockProperties.flywheel;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.foundation.utility.AngleHelper;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlock;
import uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine.EngineBlockEntity;

public class EngineInstance extends BlockEntityInstance<EngineBlockEntity> {

    protected ModelData frame;

    public EngineInstance(MaterialManager modelManager, EngineBlockEntity tile) {
        super(modelManager, tile);

        Block block = blockState
                .getBlock();
        if (!(block instanceof EngineBlock engineBlock))
            return;

        PartialModel frame = engineBlock.getFrameModel();

        Direction facing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);

        this.frame = getTransformMaterial().getModel(frame, blockState).createInstance();

        float angle = AngleHelper.rad(AngleHelper.horizontalAngle(facing));

        this.frame.loadIdentity()
                .translate(getInstancePosition())
                .nudge(pos.hashCode())
                .centre()
                .rotate(Direction.UP, angle)
                .unCentre()
                .translate(0, 0, -1);
    }

    @Override
    public void remove() {
        frame.delete();
    }

    @Override
    public void updateLight() {
        relight(pos, frame);
    }
}