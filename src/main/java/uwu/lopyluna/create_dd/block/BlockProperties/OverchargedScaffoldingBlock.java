package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class OverchargedScaffoldingBlock extends MetalScaffoldingBlock {
    public OverchargedScaffoldingBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void fillItemCategory(CreativeModeTab p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
        if (!ModList.get().isLoaded("createaddition")) {
            return;
        }
        super.fillItemCategory(p_150895_1_, p_150895_2_);
    }
}
