package uwu.lopyluna.create_dd.content.items;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemDisabled extends Item {
	public ItemDisabled(Properties pProperties) {
		super(pProperties);
	}


	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {}
}
