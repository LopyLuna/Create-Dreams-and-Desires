package uwu.lopyluna.create_flavored.item;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class PipebombTabBase extends CreateCreativeModeTab {
    public PipebombTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return Pipebomb.mithril_ingot.asStack();
    }
}
