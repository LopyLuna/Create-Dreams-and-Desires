package dev.lopyluna.dndesires.content.blocks.roll_table;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class RollTableItemHandler implements IItemHandler {

    private final RollTableBE be;
    private final Direction side;

    public RollTableItemHandler(RollTableBE be, Direction side) {
        this.be = be;
        this.side = side;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return be.getHeldItemStack();
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!be.getHeldItemStack().isEmpty()) return stack;

        ItemStack returned = ItemHelper.limitCountToMaxStackSize(stack, simulate);
        if (!simulate) {
            TransportedItemStack heldItem = new TransportedItemStack(stack);
            heldItem.prevBeltPosition = 0;
            be.setHeldItem(heldItem, side.getOpposite());
            be.notifyUpdate();
        }
        return returned;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        TransportedItemStack held = be.heldItem;
        if (held == null) return ItemStack.EMPTY;

        ItemStack stack = held.stack.copy();
        ItemStack extracted = stack.split(amount);
        if (!simulate) {
            be.heldItem.stack = stack;
            if (stack.isEmpty()) be.heldItem = null;
            be.notifyUpdate();
        }
        return extracted;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

}