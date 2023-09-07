package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.AbstractEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;


public class ReversedGearboxBlock extends AbstractEncasedShaftBlock implements IBE<SplitShaftBlockEntity> {

    public ReversedGearboxBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        if (!worldIn.hasNeighborSignal(pos)) {
            detachKinetics(worldIn, pos, true);
        }
    }

    @Override
    public Class<SplitShaftBlockEntity> getBlockEntityClass() {
        return SplitShaftBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SplitShaftBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.REVERSED_GEARSHIFT.get();
    }

    public void detachKinetics(Level worldIn, BlockPos pos, boolean reAttachNextTick) {
        BlockEntity be = worldIn.getBlockEntity(pos);
        if (be == null || !(be instanceof KineticBlockEntity))
            return;
        RotationPropagator.handleRemoved(worldIn, pos, (KineticBlockEntity) be);

        // Re-attach next tick
        if (reAttachNextTick)
            worldIn.scheduleTick(pos, this, 0, TickPriority.EXTREMELY_HIGH);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        BlockEntity be = worldIn.getBlockEntity(pos);
        if (be == null || !(be instanceof KineticBlockEntity))
            return;
        KineticBlockEntity kte = (KineticBlockEntity) be;
        RotationPropagator.handleAdded(worldIn, pos, kte);
    }
}