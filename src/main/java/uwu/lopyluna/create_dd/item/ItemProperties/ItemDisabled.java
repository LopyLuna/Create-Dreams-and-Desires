package uwu.lopyluna.create_dd.item.ItemProperties;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemDisabled extends Item {
    public ItemDisabled(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void fillItemCategory(@NotNull CreativeModeTab p_150895_1_, @NotNull NonNullList<ItemStack> p_150895_2_) {}
}
