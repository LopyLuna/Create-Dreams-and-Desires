package uwu.lopyluna.create_flavored.item;

import net.minecraft.world.item.ItemStack;

public class PipebombTabBase extends FlavoredPipebombTab {
    public PipebombTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return Pipebomb.mithril_ingot.asStack();
    }
}
