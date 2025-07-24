package dev.lopyluna.dndesires.content.blocks.logistics.special;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public abstract class DirtyInventory<BE extends SyncedBlockEntity> implements Container, IItemHandlerModifiable {
    public NonNullList<ItemStack> stacks;
    public BE be;
    public boolean extractionAllowed;
    public boolean insertionAllowed;
    public boolean stackNonStackables;
    public int stackSize;

    public DirtyInventory(int slots, BE be, int stackSize, boolean stackNonStackables) {
        this.stacks = NonNullList.withSize(slots, ItemStack.EMPTY);
        this.be = be;
        this.stackNonStackables = stackNonStackables;
        insertionAllowed = true;
        extractionAllowed = true;
        this.stackSize = stackSize;
    }

    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        ContainerHelper.loadAllItems(tag, stacks, registries);
    }

    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        ContainerHelper.saveAllItems(tag, stacks, registries);
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (var stack : stacks) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return stacks.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        var stack = ContainerHelper.removeItem(stacks, slot, amount);
        if (!stack.isEmpty()) {
            setChanged();
            onContentsChanged(slot);
        }
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(stacks, slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        stacks.set(slot, stack);
        stack.limitSize(getMaxStackSize(stack));
        setChanged();
        onContentsChanged(slot);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(be, player);
    }

    @Override
    public void clearContent() {
        stacks.clear();
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        validateSlotIndex(slot);
        setItem(slot, stack);
    }

    @Override
    public int getSlots() {
        return getContainerSize();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return getItem(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!insertionAllowed) return stack;
        if (stack.isEmpty()) return ItemStack.EMPTY;
        else if (!isItemValid(slot, stack)) return stack;
        else {
            validateSlotIndex(slot);
            var existing = getItem(slot);
            int limit = getStackLimit(slot, stack);
            if (!existing.isEmpty()) {
                if (!ItemStack.isSameItemSameComponents(stack, existing)) return stack;
                limit -= existing.getCount();
            }

            if (limit <= 0) return stack;
            else {
                var reachedLimit = stack.getCount() > limit;
                if (!simulate) {
                    if (existing.isEmpty()) setItem(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
                    else existing.grow(reachedLimit ? limit : stack.getCount());
                    onContentsChanged(slot);
                }
                return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
            }
        }
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!extractionAllowed) return ItemStack.EMPTY;
        if (stackNonStackables) {
            var extractItem = extractingItem(slot, amount, true);
            if (!extractItem.isEmpty() && extractItem.getOrDefault(DataComponents.MAX_STACK_SIZE, 64) < extractItem.getCount()) amount = extractItem.getOrDefault(DataComponents.MAX_STACK_SIZE, 64);
        }
        return extractingItem(slot, amount, simulate);
    }

    protected ItemStack extractingItem(int slot, int amount, boolean simulate) {
        if (amount == 0) return ItemStack.EMPTY;
        else {
            validateSlotIndex(slot);
            var existing = this.stacks.get(slot);
            if (existing.isEmpty()) return ItemStack.EMPTY;
            else {
                int toExtract = Math.min(amount, existing.getMaxStackSize());
                if (existing.getCount() <= toExtract) {
                    if (!simulate) {
                        setItem(slot, ItemStack.EMPTY);
                        onContentsChanged(slot);
                        return existing;
                    } else return existing.copy();
                } else {
                    if (!simulate) {
                        setItem(slot, existing.copyWithCount(existing.getCount() - toExtract));
                        onContentsChanged(slot);
                    }
                    return existing.copyWithCount(toExtract);
                }
            }
        }
    }

    private Consumer<Integer> updateCallback;

    @SuppressWarnings("unused")
    public void whenContentsChange(Consumer<Integer> updateCallback) {
        this.updateCallback = updateCallback;
    }

    protected void onContentsChanged(int slot) {
        if (updateCallback != null) updateCallback.accept(slot);
        be.notifyUpdate();
    }


    @Override
    public int getSlotLimit(int slot) {
        return Math.min(stackNonStackables ? 64 : 99, stackSize);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    public int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 64));
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.stacks.size()) throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.stacks.size() + ")");
    }

    public NonNullList<ItemStack> getItems() {
        return stacks;
    }
}
