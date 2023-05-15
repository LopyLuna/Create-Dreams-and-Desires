package uwu.lopyluna.create_battles.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class PipebombTab {
    public static final CreativeModeTab BattleCreate_TAB = new CreativeModeTab("create_battles_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Pipebomb.mithril_ingot.get());
        }
    };

}
