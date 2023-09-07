package uwu.lopyluna.create_dd;

import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.fluid.DDFluids;

@Mod.EventBusSubscriber
public class DDCommonEvents {
    @SubscribeEvent
    public static void whenFluidsMeet(BlockEvent.FluidPlaceBlockEvent event) {
        BlockState blockState = event.getOriginalState();
        FluidState fluidState = blockState.getFluidState();
        BlockPos pos = event.getPos();
        LevelAccessor world = event.getLevel();

        if (fluidState.isSource() && FluidHelper.isLava(fluidState.getType()))
            return;

        for (Direction direction : Iterate.directions) {
            FluidState metFluidState =
                    fluidState.isSource() ? fluidState : world.getFluidState(pos.relative(direction));
            if (!metFluidState.is(FluidTags.WATER))
                continue;
            BlockState lavaInteraction = DDFluids.getLavaInteraction(metFluidState);
            if (lavaInteraction == null)
                continue;
            event.setNewState(lavaInteraction);
            break;
        }
    }
}
