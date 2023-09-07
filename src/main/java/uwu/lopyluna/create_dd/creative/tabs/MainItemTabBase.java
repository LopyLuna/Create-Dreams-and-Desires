package uwu.lopyluna.create_dd.creative.tabs;

import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_dd.item.DDItems;

public class MainItemTabBase extends DDMainItemTab {
    public MainItemTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return DDItems.spectral_ruby.asStack();
    }
}
