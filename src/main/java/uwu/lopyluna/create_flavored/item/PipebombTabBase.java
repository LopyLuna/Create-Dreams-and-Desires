package uwu.lopyluna.create_flavored.item;

import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_flavored.block.YIPPEE;

public class PipebombTabBase extends FlavoredPipebombTab {
    public PipebombTabBase() {
        super("base");
    }

    @Override
    public ItemStack makeIcon() {
        return YIPPEE.bronze_casing.asStack();
    }
}
