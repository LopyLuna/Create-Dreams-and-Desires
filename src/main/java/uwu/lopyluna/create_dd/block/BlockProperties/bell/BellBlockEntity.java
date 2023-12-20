package uwu.lopyluna.create_dd.block.BlockProperties.bell;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static uwu.lopyluna.create_dd.block.BlockProperties.bell.BellBlock.bellCooldown;


public class BellBlockEntity extends SmartBlockEntity {

    public BellBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        if (bellCooldown > 0)
            bellCooldown = bellCooldown - 1;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}
}
