package uwu.lopyluna.create_dd.access;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class DDTransportedItemStackHandlerBehaviour extends TransportedItemStackHandlerBehaviour {

    public static final BehaviourType<DDTransportedItemStackHandlerBehaviour> TYPE = new BehaviourType<>();

    private DDTransportedItemStackHandlerBehaviour.ProcessingCallback processingCallback;
    private DDTransportedItemStackHandlerBehaviour.PositionGetter positionGetter;

    public DDTransportedItemStackHandlerBehaviour(SmartBlockEntity be, TransportedItemStackHandlerBehaviour.ProcessingCallback processingCallback) {
        super(be, processingCallback);
    }

    public static class TransportedResult {
        List<DDTransportedItemStack> outputs;
        DDTransportedItemStack heldOutput;

        private static final DDTransportedItemStackHandlerBehaviour.TransportedResult DO_NOTHING = new DDTransportedItemStackHandlerBehaviour.TransportedResult(null, null);
        private static final DDTransportedItemStackHandlerBehaviour.TransportedResult REMOVE_ITEM = new DDTransportedItemStackHandlerBehaviour.TransportedResult(ImmutableList.of(), null);

        public static DDTransportedItemStackHandlerBehaviour.TransportedResult doNothing() {
            return DO_NOTHING;
        }

        public static DDTransportedItemStackHandlerBehaviour.TransportedResult removeItem() {
            return REMOVE_ITEM;
        }

        public static DDTransportedItemStackHandlerBehaviour.TransportedResult convertTo(DDTransportedItemStack output) {
            return new DDTransportedItemStackHandlerBehaviour.TransportedResult(ImmutableList.of(output), null);
        }

        public static DDTransportedItemStackHandlerBehaviour.TransportedResult convertTo(List<DDTransportedItemStack> outputs) {
            return new DDTransportedItemStackHandlerBehaviour.TransportedResult(outputs, null);
        }

        public static DDTransportedItemStackHandlerBehaviour.TransportedResult convertToAndLeaveHeld(List<DDTransportedItemStack> outputs,
                                                                                                   DDTransportedItemStack heldOutput) {
            return new DDTransportedItemStackHandlerBehaviour.TransportedResult(outputs, heldOutput);
        }

        private TransportedResult(List<DDTransportedItemStack> outputs, DDTransportedItemStack heldOutput) {
            this.outputs = outputs;
            this.heldOutput = heldOutput;
        }

        public boolean doesNothing() {
            return outputs == null;
        }

        public boolean didntChangeFrom(ItemStack stackBefore) {
            return doesNothing()
                    || outputs.size() == 1 && outputs.get(0).stack.equals(stackBefore, false) && !hasHeldOutput();
        }

        public List<DDTransportedItemStack> getOutputs() {
            if (outputs == null)
                throw new IllegalStateException("Do not call getOutputs() on a Result that doesNothing().");
            return outputs;
        }

        public boolean hasHeldOutput() {
            return heldOutput != null;
        }

        @Nullable
        public DDTransportedItemStack getHeldOutput() {
            if (heldOutput == null)
                throw new IllegalStateException(
                        "Do not call getHeldOutput() on a Result with hasHeldOutput() == false.");
            return heldOutput;
        }

    }

    public DDTransportedItemStackHandlerBehaviour withStackPlacement(DDTransportedItemStackHandlerBehaviour.PositionGetter function) {
        this.positionGetter = function;
        return this;
    }

    public void DDhandleProcessingOnAllItems(Function<DDTransportedItemStack, DDTransportedItemStackHandlerBehaviour.TransportedResult> processFunction) {
        DDhandleCenteredProcessingOnAllItems(.51f, processFunction);
    }

    public void handleProcessingOnItem(DDTransportedItemStack item, DDTransportedItemStackHandlerBehaviour.TransportedResult processOutput) {
        DDhandleCenteredProcessingOnAllItems(.51f, t -> {
            if (t == item)
                return processOutput;
            return null;
        });
    }

    public void DDhandleCenteredProcessingOnAllItems(float maxDistanceFromCenter, Function<DDTransportedItemStack, DDTransportedItemStackHandlerBehaviour.TransportedResult> processFunction) {
        this.processingCallback.applyToAllItems(maxDistanceFromCenter, processFunction);
    }

    public Vec3 getWorldPositionOf(DDTransportedItemStack transported) {
        return positionGetter.getWorldPositionVector(transported);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @FunctionalInterface
    public interface ProcessingCallback {
        void applyToAllItems(float maxDistanceFromCenter,
                                    Function<DDTransportedItemStack, DDTransportedItemStackHandlerBehaviour.TransportedResult> processFunction);
    }

    @FunctionalInterface
    public interface PositionGetter {
        Vec3 getWorldPositionVector(DDTransportedItemStack transported);
    }
}
