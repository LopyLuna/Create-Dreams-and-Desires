package dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper;

import com.simibubi.create.content.logistics.chute.SmartChuteBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.VersionedInventoryTrackerBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

import static dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBlock.SUCK_AABB;

public class SmartHopperBE extends SmartBlockEntity implements MenuProvider {
    HopperInventory inv;
    FilteringBehaviour filtering;

    VersionedInventoryTrackerBehaviour invVersionTracker;
    private final EnumMap<Direction, BlockCapabilityCache<IItemHandler, @Nullable Direction>> capCaches = new EnumMap<>(Direction.class);


    public SmartHopperBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inv = new HopperInventory(5, this);
        setLazyTickRate(8);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DesiresBETypes.SMART_HOPPER.get(), (be, context) -> be.inv);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        if (level == null) return;

        var state = getBlockState();
        var facing = state.getValue(SmartHopperBlock.FACING);
        var clientSide = level.isClientSide && !isVirtual();

        if (inv != null) {
            if (!clientSide) {
                suckInItems(state, level);
                handleInput(grabCapability(Direction.UP), state);
            }
            var output = grabCapability(facing);
            if (handleOutput(output, state, true)) handleOutput(output, state, clientSide);
        }
    }

    private void handleInput(@Nullable IItemHandler inv, BlockState state) {
        if (inv == null) return;
        if (cantActivate(state)) return;
        if (invVersionTracker.stillWaiting(inv)) return;
        Predicate<ItemStack> canAccept = s -> !cantAcceptItem(s, state);
        int count = getExtractionAmount();
        var mode = getExtractionMode();
        if (mode == ItemHelper.ExtractionCountMode.UPTO || !ItemHelper.extract(inv, canAccept, mode, count, true).isEmpty()) {
            var extracted = ItemHelper.extract(inv, canAccept, mode, count, false);
            if (!extracted.isEmpty()) {
                insertItem(extracted, false);
                return;
            }
        }
        invVersionTracker.awaitNewVersion(inv);
    }

    private boolean handleOutput(IItemHandler inv, BlockState state, boolean simulate) {
        assert level != null;
        if (cantActivate(state)) return false;
        if (inv != null) {
            if (level.isClientSide && !isVirtual()) return false;
            if (invVersionTracker.stillWaiting(inv)) return false;
            var extracted = ItemHelper.extract(this.inv, s -> filtering.test(s), simulate);
            if (extracted.isEmpty()) return false;
            var remainder = ItemHandlerHelper.insertItemStacked(inv, extracted, simulate);
            if (!simulate) insertItem(remainder, false);
            if (remainder.getCount() != extracted.getCount()) return true;
            invVersionTracker.awaitNewVersion(inv);
        }
        return true;
    }

    public void insertItem(ItemStack stack, boolean simulate) {
        ItemHandlerHelper.insertItemStacked(inv, stack, simulate);
        invVersionTracker.reset();
        assert level != null;
        if (!level.isClientSide) notifyUpdate();
    }

    private @Nullable IItemHandler grabCapability(@NotNull Direction side) {
        var pos = worldPosition.relative(side);
        if (level == null) return null;
        if (capCaches.get(side) == null) {
            if (level instanceof ServerLevel serverLevel) {
                var cache = BlockCapabilityCache.create(Capabilities.ItemHandler.BLOCK, serverLevel, pos, side.getOpposite());
                capCaches.put(side, cache);
                return cache.getCapability();
            } else return level.getCapability(Capabilities.ItemHandler.BLOCK, pos, side.getOpposite());
        } else return capCaches.get(side).getCapability();
    }

    protected boolean cantAcceptItem(ItemStack stack, BlockState state) {
        return ItemStack.isSameItemSameComponents(ItemHandlerHelper.insertItem(inv, stack.copy(), true), stack) || cantActivate(state) || !filtering.test(stack);
    }

    protected boolean cantActivate(BlockState state) {
        return state.hasProperty(SmartChuteBlock.POWERED) && state.getValue(SmartChuteBlock.POWERED);
    }

    protected int getExtractionAmount() {
        return filtering.isCountVisible() && !filtering.anyAmount() ? filtering.getAmount() : 64;
    }

    protected ItemHelper.ExtractionCountMode getExtractionMode() {
        return filtering.isCountVisible() && !filtering.anyAmount() && !filtering.upTo ? ItemHelper.ExtractionCountMode.EXACTLY
                : ItemHelper.ExtractionCountMode.UPTO;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(filtering = new FilteringBehaviour(this, new SmartHopperFilterSlotPositioning()).showCount().withCallback($ -> invVersionTracker.reset()));
        behaviours.add(invVersionTracker = new VersionedInventoryTrackerBehaviour(this));
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inv);
    }

    @Override
    public void invalidate() {
        if (inv != null) invalidateCapabilities();
        capCaches.clear();
        super.invalidate();
    }

    public boolean isEmpty() {
        return inv.isEmpty();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        inv.setChanged();
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        inv.save(tag, registries);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        inv.load(tag, registries);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        if (inv == null) inv = new HopperInventory(5, this);
        return new HopperMenu(id, inventory, inv);
    }

    public void suckInItems(BlockState state, Level level) {
        if (cantActivate(state)) return;
        var blockpos = BlockPos.containing(getLevelX(), getLevelY() + (double)1.0F, getLevelZ());
        var blockstate = level.getBlockState(blockpos);
        var pos = getBlockPos();
        var flag = !blockstate.isCollisionShapeFullBlock(level, blockpos) || blockstate.is(BlockTags.DOES_NOT_BLOCK_HOPPERS);
        if (flag) for (var entity : getItemsAtAndAbove(level)) {
            var stack = entity.getItem().copy();
            if (stack.isEmpty() || !entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ()).intersects(SUCK_AABB)) continue;
            if (cantAcceptItem(stack, state)) continue;
            entity.setItem(handleExtracting(stack, state));
        }
    }

    public ItemStack handleExtracting(ItemStack stack, BlockState state) {
        if (stack.isEmpty()) return stack;
        int count = getExtractionAmount();
        var mode = getExtractionMode();
        int amountExtract = Math.min(count, stack.getCount());
        var extract = extract(stack, state, mode, count, amountExtract);
        if (ItemStack.isSameItemSameComponents(extract, stack)) return stack;
        if (mode == ItemHelper.ExtractionCountMode.UPTO || !extract.isEmpty()) {
            int newCount = stack.getCount();
            newCount -= amountExtract;
            var leftOver = ItemHandlerHelper.insertItemStacked(inv, extract, false);
            newCount += leftOver.getCount();
            return stack.copyWithCount(newCount);
        }
        return stack;
    }

    public ItemStack extract(ItemStack itemStack, BlockState state, ItemHelper.ExtractionCountMode mode, int amount, int amountExtract) {
        var empty = ItemStack.EMPTY;
        var toExtract = ItemStack.EMPTY;

        var amountRequired = mode == ItemHelper.ExtractionCountMode.EXACTLY;
        toExtract = itemStack.copyWithCount(amountExtract);

        if (toExtract.isEmpty() || cantAcceptItem(toExtract, state)) return empty;
        if (amountRequired && toExtract.getCount() < amount) return empty;
        return toExtract;
    }


    public List<ItemEntity> getItemsAtAndAbove(Level level) {
        return level.getEntitiesOfClass(ItemEntity.class, SUCK_AABB.move(getLevelX() - (double)0.5F, getLevelY() - (double)0.5F, getLevelZ() - (double)0.5F), EntitySelector.ENTITY_STILL_ALIVE);
    }

    public double getLevelX() {
        return (double)worldPosition.getX() + (double)0.5F;
    }
    public double getLevelY() {
        return (double)worldPosition.getY() + (double)0.5F;
    }
    public double getLevelZ() {
        return (double)worldPosition.getZ() + (double)0.5F;
    }
}
