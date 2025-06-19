package dev.lopyluna.dndesires.content.blocks.kinetics.omni_speed_controller;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.AbstractEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OmniSpeedControllerBlock extends AbstractEncasedShaftBlock implements IBE<SplitShaftBlockEntity> {

    public OmniSpeedControllerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var be = level.getBlockEntity(pos);
        if (!(be instanceof KineticBlockEntity kte)) return;
        RotationPropagator.handleAdded(level, pos, kte);
    }

    @Override
    public Class<SplitShaftBlockEntity> getBlockEntityClass() {
        return SplitShaftBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return DesiresBETypes.OMNI_SPEED_CONTROLLER.get();
    }
}
