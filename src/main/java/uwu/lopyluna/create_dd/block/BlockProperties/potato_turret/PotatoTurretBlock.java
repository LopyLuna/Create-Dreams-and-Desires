package uwu.lopyluna.create_dd.block.BlockProperties.potato_turret;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

public class PotatoTurretBlock extends HorizontalKineticBlock implements IBE<PotatoTurretBlockEntity>, IWrenchable, ICogWheel {
    public PotatoTurretBlock(Properties properties, boolean visible) {
        super(properties);
        this.visible = visible;
    }
    private boolean visible;

    public PotatoTurretBlock(Properties p_i48440_1_) {
        this(p_i48440_1_, false);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (visible)
            super.fillItemCategory(pCategory, pItems);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = getPreferredHorizontalFacing(context);
        if (preferred != null)
            return defaultBlockState().setValue(HORIZONTAL_FACING, preferred.getOpposite());
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }


    @Override
    public Class<PotatoTurretBlockEntity> getBlockEntityClass() {
        return PotatoTurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PotatoTurretBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.POTATO_TURRET.get();
    }
}