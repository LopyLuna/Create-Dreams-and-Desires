package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.content.contraptions.bearing.SailBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FanSailBlock extends SailBlock {
    public static @NotNull FanSailBlock sail(Properties properties) {
        return new FanSailBlock(properties, false, null);
    }

    protected FanSailBlock(Properties properties, boolean frame, DyeColor color) {
        super(properties, frame, color);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
            super.fillItemCategory(tab, items);
    }
}
