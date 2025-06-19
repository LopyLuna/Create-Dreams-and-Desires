package dev.lopyluna.dndesires.content.blocks.logistics.roll_table;

import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class RollTableBE extends SmartBlockEntity {
    TransportedItemStack heldItem;
    Map<Direction, RollTableItemHandler> itemHandlers;

    public RollTableBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        itemHandlers = new IdentityHashMap<>();
        for (Direction d : Iterate.horizontalDirections) itemHandlers.put(d, new RollTableItemHandler(this, d));
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DesiresBETypes.ROLL_TABLE.get(), (be, context) -> {
            if (context != null && context.getAxis().isHorizontal()) return be.itemHandlers.get(context);
            return null;
        });
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this).allowingBeltFunnels().setInsertionHandler(this::tryInsertingFromSide));
        registerAwardables(behaviours, AllAdvancements.CHAINED_DRAIN);
    }

    public ItemStack getHeldItemStack() {
        return heldItem == null ? ItemStack.EMPTY : heldItem.stack;
    }

    public ItemStack tryInsertingFromSide(TransportedItemStack transportedStack, Direction side, boolean simulate) {
        ItemStack inserted = transportedStack.stack;
        ItemStack returned = ItemStack.EMPTY;
        if (!getHeldItemStack().isEmpty()) return inserted;

        if (inserted.getCount() > 1 && GenericItemEmptying.canItemBeEmptied(level, inserted)) {
            returned = inserted.copyWithCount(inserted.getCount() - 1);
            inserted = inserted.copyWithCount(1);
        }
        if (simulate) return returned;
        transportedStack = transportedStack.copy();
        transportedStack.stack = inserted.copy();
        transportedStack.beltPosition = side.getAxis().isVertical() ? .5f : 0;
        transportedStack.prevSideOffset = transportedStack.sideOffset;
        transportedStack.prevBeltPosition = transportedStack.beltPosition;
        setHeldItem(transportedStack, side);
        setChanged();
        sendData();
        return returned;
    }

    public void setHeldItem(TransportedItemStack heldItem, Direction insertedFrom) {
        this.heldItem = heldItem;
        this.heldItem.insertedFrom = insertedFrom;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        if (heldItem != null) tag.put("HeldItem", heldItem.serializeNBT(registries));
        super.write(tag, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        heldItem = null;
        if (tag.contains("HeldItem")) heldItem = TransportedItemStack.read(tag.getCompound("HeldItem"), registries);
        super.read(tag, registries, clientPacket);
    }

    private float itemMovementPerTick() {
        return 1 / 8f;
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || heldItem == null) return;

        boolean onClient = level.isClientSide && !isVirtual();


        heldItem.prevBeltPosition = heldItem.beltPosition;
        heldItem.prevSideOffset = heldItem.sideOffset;

        heldItem.beltPosition += itemMovementPerTick();
        if (heldItem.beltPosition > 1) {
            heldItem.beltPosition = 1;
            if (onClient) return;
            Direction side = heldItem.insertedFrom;

            ItemStack tryExportingToBeltFunnel = getBehaviour(DirectBeltInputBehaviour.TYPE).tryExportingToBeltFunnel(heldItem.stack, side.getOpposite(), false);
            if (tryExportingToBeltFunnel != null) {
                if (tryExportingToBeltFunnel.getCount() != heldItem.stack.getCount()) {
                    if (tryExportingToBeltFunnel.isEmpty()) heldItem = null;
                    else heldItem.stack = tryExportingToBeltFunnel;
                    notifyUpdate();
                    return;
                }
                if (!tryExportingToBeltFunnel.isEmpty()) return;
            }
            BlockPos nextPosition = worldPosition.relative(side);
            DirectBeltInputBehaviour directBeltInputBehaviour = BlockEntityBehaviour.get(level, nextPosition, DirectBeltInputBehaviour.TYPE);
            if (directBeltInputBehaviour == null) {
                if (!BlockHelper.hasBlockSolidSide(level.getBlockState(nextPosition), level, nextPosition, side.getOpposite())) {
                    ItemStack ejected = heldItem.stack;
                    Vec3 outPos = VecHelper.getCenterOf(worldPosition).add(Vec3.atLowerCornerOf(side.getNormal()).scale(.75));
                    float movementSpeed = itemMovementPerTick();
                    Vec3 outMotion = Vec3.atLowerCornerOf(side.getNormal()).scale(movementSpeed).add(0, 1 / 8f, 0);
                    outPos.add(outMotion.normalize());
                    ItemEntity entity = new ItemEntity(level, outPos.x, outPos.y + 6 / 16f, outPos.z, ejected);
                    entity.setDeltaMovement(outMotion);
                    entity.setDefaultPickUpDelay();
                    entity.hurtMarked = true;
                    level.addFreshEntity(entity);
                    heldItem = null;
                    notifyUpdate();
                }
                return;
            }
            if (!directBeltInputBehaviour.canInsertFromSide(side)) return;
            ItemStack returned = directBeltInputBehaviour.handleInsertion(heldItem.copy(), side, false);
            if (returned.isEmpty()) {
                if (level.getBlockEntity(nextPosition) instanceof RollTableBE) award(AllAdvancements.CHAINED_DRAIN);
                heldItem = null;
                notifyUpdate();
                return;
            }
            if (returned.getCount() != heldItem.stack.getCount()) {
                heldItem.stack = returned;
                notifyUpdate();
                return;
            }
            return;
        }

        if (heldItem.prevBeltPosition < .5f && heldItem.beltPosition >= .5f) {
            if (!GenericItemEmptying.canItemBeEmptied(level, heldItem.stack)) return;
            heldItem.beltPosition = .5f;
            if (onClient) return;
            sendData();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }
}
