package uwu.lopyluna.create_dd.block.BlockProperties.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.block.YIPPEE;

public class HotAssRotatedBlockPillar extends RotatedPillarBlock {
    public HotAssRotatedBlockPillar(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 2;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 3;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(YIPPEE.rose_log.get())) {
                return YIPPEE.stripped_rose_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(YIPPEE.rose_wood.get())) {
                return YIPPEE.stripped_rose_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(YIPPEE.smoked_log.get())) {
                return YIPPEE.stripped_smoked_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(YIPPEE.smoked_wood.get())) {
                return YIPPEE.stripped_smoked_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

}
