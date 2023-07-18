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

public class SpiritLogRotatedBlockPillar extends RotatedPillarBlock {
    public SpiritLogRotatedBlockPillar(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(YIPPEE.spirit_log.get())) {
                return YIPPEE.stripped_spirit_log.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(YIPPEE.spirit_wood.get())) {
                return YIPPEE.stripped_spirit_wood.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

}
