package uwu.lopyluna.create_dd.item;

import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_dd.block.YIPPEE;

public class PipebombTabBase extends FlavoredPipebombTab {
    public PipebombTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return Pipebomb.spectral_ruby.asStack();
    }
}
