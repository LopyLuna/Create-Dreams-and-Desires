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

    public ProcessingCallback processingCallback;
    public PositionGetter positionGetter;

    public DDTransportedItemStackHandlerBehaviour(SmartBlockEntity be, TransportedItemStackHandlerBehaviour.ProcessingCallback processingCallback) {
        super(be, processingCallback);
    }

    public static class DDTransportedResult {
        List<DDTransportedItemStack> outputs;
        DDTransportedItemStack heldOutput;

        public static final DDTransportedResult DO_NOTHING = new DDTransportedResult(null, null);
        public static final DDTransportedResult REMOVE_ITEM = new DDTransportedResult(ImmutableList.of(), null);

        public static DDTransportedResult doNothing() {return DO_NOTHING;}

        public static DDTransportedResult removeItem() {return REMOVE_ITEM;}

        public static DDTransportedResult convertTo(DDTransportedItemStack output) {return new DDTransportedResult(ImmutableList.of(output), null);}

        public static DDTransportedResult convertTo(List<DDTransportedItemStack> outputs) {return new DDTransportedResult(outputs, null);}
        public static DDTransportedResult convertToAndLeaveHeld(List<DDTransportedItemStack> outputs, DDTransportedItemStack heldOutput) {return new DDTransportedResult(outputs, heldOutput);}

        public DDTransportedResult(List<DDTransportedItemStack> outputs, DDTransportedItemStack heldOutput) {this.outputs = outputs;this.heldOutput = heldOutput;}

        public boolean doesNothing() {return outputs == null;}

        public boolean didntChangeFrom(ItemStack stackBefore) {return doesNothing() || outputs.size() == 1 && outputs.get(0).stack.equals(stackBefore, false) && !hasHeldOutput();}

        public List<DDTransportedItemStack> getOutputs() {if (outputs == null) throw new IllegalStateException("Do not call getOutputs() on a Result that doesNothing().");return outputs;}

        public boolean hasHeldOutput() {return heldOutput != null;}

        @Nullable
        public DDTransportedItemStack getHeldOutput() {if (heldOutput == null) throw new IllegalStateException("Do not call getHeldOutput() on a Result with hasHeldOutput() == false.");return heldOutput;}

    }

    public DDTransportedItemStackHandlerBehaviour(SmartBlockEntity be, ProcessingCallback processingCallback) {
        super(be, (TransportedItemStackHandlerBehaviour.ProcessingCallback) processingCallback);
        this.processingCallback = processingCallback;
        positionGetter = t -> VecHelper.getCenterOf(be.getBlockPos());
    }

    public TransportedItemStackHandlerBehaviour withStackPlacement(PositionGetter function) {
        this.positionGetter = function;
        return this;
    }

    public void handleProcessingOnAllItems(Function<TransportedItemStack, TransportedResult> processFunction) {
        handleCenteredProcessingOnAllItems(.51f, processFunction);
    }

    public void handleProcessingOnItem(DDTransportedItemStack item, TransportedResult processOutput) {
        handleCenteredProcessingOnAllItems(.51f, t -> {
            if (t == item)
                return processOutput;
            return null;
        });
    }

    public void handleCenteredProcessingOnAllItems(float maxDistanceFromCenter, Function<TransportedItemStack, TransportedResult> processFunction) {
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
        public void applyToAllItems(float maxDistanceFromCenter,
                                    Function<TransportedItemStack, TransportedResult> processFunction);
    }

    @FunctionalInterface
    public interface PositionGetter {
        public Vec3 getWorldPositionVector(DDTransportedItemStack transported);
    }
    

}
