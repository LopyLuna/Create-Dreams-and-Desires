package dev.lopyluna.dndesires.events;

import com.simibubi.create.api.event.PipeCollisionEvent;
import com.simibubi.create.foundation.fluid.FluidHelper;
import dev.lopyluna.dndesires.register.DesiresFluids;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class FluidReactionsEvent {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void handlePipeFlowCollisionFallback(PipeCollisionEvent.Flow event) {
        var level = event.getLevel();
        var pos = event.getPos();
        var f1 = event.getFirstFluid();
        var f2 = event.getSecondFluid();

        if (!(f1 == Fluids.WATER && f2 == Fluids.LAVA || f2 == Fluids.WATER && f1 == Fluids.LAVA)) {
            if (f1 == Fluids.LAVA && FluidHelper.hasBlockState(f2)) {
                var lavaInteraction = DesiresFluids.getLavaInteractions(FluidHelper.convertToFlowing(f2).defaultFluidState(), level, pos);
                if (lavaInteraction != null) event.setState(lavaInteraction.get());
            } else if (f2 == Fluids.LAVA && FluidHelper.hasBlockState(f1)) {
                var lavaInteraction = DesiresFluids.getLavaInteractions(FluidHelper.convertToFlowing(f1).defaultFluidState(), level, pos);
                if (lavaInteraction != null) event.setState(lavaInteraction.get());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void handlePipeSpillCollisionFallback(PipeCollisionEvent.Spill event) {
        var level = event.getLevel();
        var pos = event.getPos();
        var pf = event.getPipeFluid();
        var wf = event.getWorldFluid();

        if (pf == Fluids.LAVA) {
            var lavaInteraction = DesiresFluids.getLavaInteractions(wf.defaultFluidState(), level, pos);
            if (lavaInteraction != null) event.setState(lavaInteraction.get());
        } else if (wf == Fluids.FLOWING_LAVA && FluidHelper.hasBlockState(pf)) {
            var lavaInteraction = DesiresFluids.getLavaInteractions(FluidHelper.convertToFlowing(pf).defaultFluidState(), level, pos);
            if (lavaInteraction != null) event.setState(lavaInteraction.get());
        }
    }
}
