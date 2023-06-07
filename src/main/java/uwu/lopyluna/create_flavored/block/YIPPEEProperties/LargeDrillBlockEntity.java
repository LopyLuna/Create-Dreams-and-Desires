package uwu.lopyluna.create_flavored.block.YIPPEEProperties;

import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LargeDrillBlockEntity extends DrillBlockEntity {

    public LargeDrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected int getSize() {
        return 2;
    }
}
