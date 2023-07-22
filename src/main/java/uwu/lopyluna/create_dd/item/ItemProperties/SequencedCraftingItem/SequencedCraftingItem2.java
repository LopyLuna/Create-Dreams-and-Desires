package uwu.lopyluna.create_dd.item.ItemProperties.SequencedCraftingItem;

import com.simibubi.create.foundation.utility.Color;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SequencedCraftingItem2 extends Item {
    public SequencedCraftingItem2(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }
    public float getProgress(ItemStack stack) {return 0.35f;}

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab p_150895_1_, @NotNull NonNullList<ItemStack> p_150895_2_) {}

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round(getProgress(stack) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return Color.mixColors(0xFF_FFC074, 0xFF_46FFE0, getProgress(stack));
    }
}
