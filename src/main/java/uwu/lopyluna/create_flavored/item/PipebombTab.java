package uwu.lopyluna.create_flavored.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class PipebombTab {
    public static final CreativeModeTab FlavoredCreate_TAB = new CreativeModeTab("create_flavored_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Pipebomb.mithril_ingot.get());
        }
    };

}
