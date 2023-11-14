package uwu.lopyluna.create_dd.creative.tabs;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.item.DDItems;

public class MainItemTabBase extends DDMainItemTab {
    public MainItemTabBase() {
        super("base");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return DDItems.spectral_ruby.asStack();
    }
}
