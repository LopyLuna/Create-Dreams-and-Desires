package uwu.lopyluna.create_dd.item;

import net.minecraft.world.item.ItemStack;

public class PipebombTabBase extends DDPipebombTab {
    public PipebombTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return Pipebomb.spectral_ruby.asStack();
    }
}
