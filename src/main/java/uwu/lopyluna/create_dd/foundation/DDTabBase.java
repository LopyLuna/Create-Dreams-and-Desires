package uwu.lopyluna.create_dd.foundation;

import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import uwu.lopyluna.create_dd.init.DDItems;

public class DDTabBase extends DDCreativeModeTab {
	public DDTabBase() {
		super("base");
	}

	@Override
	public @NotNull ItemStack makeIcon() {
		return DDItems.spectral_ruby.asStack();
	}
}
