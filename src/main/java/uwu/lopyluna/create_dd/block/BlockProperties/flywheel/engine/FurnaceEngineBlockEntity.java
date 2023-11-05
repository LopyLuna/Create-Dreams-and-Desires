package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;


import com.simibubi.create.content.kinetics.BlockStressValues;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class FurnaceEngineBlockEntity extends EngineBlockEntity {

    public FurnaceEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        updateFurnace();
        super.tick();
    }

    public void updateFurnace() {
        assert level != null;
        BlockState state = level.getBlockState(EngineBlock.getBaseBlockPos(getBlockState(), worldPosition));
        FurnaceEngineInteractions.InteractionHandler handler = FurnaceEngineInteractions.getHandler(state);
        FurnaceEngineInteractions.HeatSource heatSource = handler.getHeatSource(state);
        if (heatSource.isEmpty())
            return;

        float modifier = handler.getSpeedModifier(state);
        boolean active = heatSource.isActive();

        appliedCapacity = (float) (active ? BlockStressValues.getCapacity(DDBlocks.FURNACE_ENGINE.get())
                : 0);
        appliedSpeed = active ? 24 * modifier : 0;

        refreshWheelSpeed();
    }

}