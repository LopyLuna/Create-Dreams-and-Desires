package uwu.lopyluna.create_dd.block.BlockProperties;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CarpetBlock;
import org.jetbrains.annotations.NotNull;

public class MysteriousCarpetBlock extends CarpetBlock {
    public MysteriousCarpetBlock(Properties properties, boolean visible) {
        super(properties);
        this.visible = visible;
    }
    private final boolean visible;

    public MysteriousCarpetBlock(Properties properties) {
        this(properties, false);
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab pCategory, @NotNull NonNullList<ItemStack> pItems) {
        if (visible)
            super.fillItemCategory(pCategory, pItems);
    }
}
