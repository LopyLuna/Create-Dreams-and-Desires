package uwu.lopyluna.create_dd.access;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
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

    public DDProcessingCallback ddprocessingCallback;
    public DDPositionGetter ddpositionGetter;


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

    public DDTransportedItemStackHandlerBehaviour(SmartBlockEntity be, DDProcessingCallback ddprocessingCallback) {
        super(be, (ProcessingCallback) ddprocessingCallback);
        this.ddprocessingCallback = ddprocessingCallback;
        ddpositionGetter = t -> VecHelper.getCenterOf(be.getBlockPos());
    }

    public DDTransportedItemStackHandlerBehaviour withStackPlacement(DDPositionGetter function) {
        this.ddpositionGetter = function;
        return this;
    }

    public void ddhandleProcessingOnAllItems(Function<DDTransportedItemStack, DDTransportedResult> processFunction) {
        ddhandleCenteredProcessingOnAllItems(.51f, processFunction);
    }

    public void handleProcessingOnItem(DDTransportedItemStack item, DDTransportedResult processOutput) {
        ddhandleCenteredProcessingOnAllItems(.51f, (Function<DDTransportedItemStack, DDTransportedResult>) t -> {
            if (t == item)
                return processOutput;
            return null;
        });
    }

    public void ddhandleCenteredProcessingOnAllItems(float maxDistanceFromCenter, Function<DDTransportedItemStack, DDTransportedResult> processFunction) {
        this.ddprocessingCallback.applyToAllItems(maxDistanceFromCenter, processFunction);
    }

    public Vec3 getWorldPositionOf(DDTransportedItemStack transported) {
        return ddpositionGetter.getDDWorldPositionVector(transported);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @FunctionalInterface
    public interface DDProcessingCallback {
        void applyToAllItems(float maxDistanceFromCenter, Function<DDTransportedItemStack, DDTransportedResult> processFunction);
    }

    @FunctionalInterface
    public interface DDPositionGetter {
        Vec3 getDDWorldPositionVector(DDTransportedItemStack transported);
    }
    

}
