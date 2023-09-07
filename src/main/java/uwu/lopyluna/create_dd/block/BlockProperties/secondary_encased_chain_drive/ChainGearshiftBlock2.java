package uwu.lopyluna.create_dd.block.BlockProperties.secondary_encased_chain_drive;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

public class ChainGearshiftBlock2 extends ChainDriveBlock2 {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ChainGearshiftBlock2(Properties properties, boolean visible) {
        super(properties);
        this.visible = visible;
        registerDefaultState(defaultBlockState().setValue(POWERED, false));
    }

    private boolean visible;

    public ChainGearshiftBlock2(Properties p_i48440_1_) {
        this(p_i48440_1_, false);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (visible)
            super.fillItemCategory(pCategory, pItems);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POWERED));
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        if (oldState.getBlock() == state.getBlock())
            return;
        withBlockEntityDo(worldIn, pos, kbe -> ((ChainGearshiftBlock2Entity) kbe).neighbourChanged());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(POWERED, context.getLevel()
                .hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        return super.areStatesKineticallyEquivalent(oldState, newState)
                && oldState.getValue(POWERED) == newState.getValue(POWERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        withBlockEntityDo(worldIn, pos, kbe -> ((ChainGearshiftBlock2Entity) kbe).neighbourChanged());

        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != worldIn.hasNeighborSignal(pos))
            worldIn.setBlock(pos, state.cycle(POWERED), 18);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.secondary_adjustable_chain_gearshift.get();
    }

}
