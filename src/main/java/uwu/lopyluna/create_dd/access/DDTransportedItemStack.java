package uwu.lopyluna.create_dd.access;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_dd.recipes.BakingFanProcessing;

public class DDTransportedItemStack extends TransportedItemStack {
    public DDTransportedItemStack(ItemStack stack) {
        super(stack);
    }

    public BakingFanProcessing.Type processedBy;
}
