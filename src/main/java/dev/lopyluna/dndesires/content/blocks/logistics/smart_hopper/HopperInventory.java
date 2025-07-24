package dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper;

import dev.lopyluna.dndesires.content.blocks.logistics.special.DirtyInventory;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HopperInventory extends DirtyInventory<SmartHopperBE> {
    SmartHopperBE be;

    public HopperInventory(int slots, SmartHopperBE be) {
        super(slots, be, 64, true);
        this.be = be;
    }

    @Override
    public void setChanged() {
        var state = be.getBlockState();
        var flag = state.hasProperty(SmartHopperBlock.POWERED) && !state.getValue(SmartHopperBlock.POWERED);
        extractionAllowed = flag;
        insertionAllowed = flag;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (be.filtering.test(stack)) return super.isItemValid(slot, stack);
        return false;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        stacks.set(slot, stack);
        stack.limitSize(getMaxStackSize(stack));
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int count) {
        return ContainerHelper.removeItem(stacks, slot, count);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(be, player);
    }
}
