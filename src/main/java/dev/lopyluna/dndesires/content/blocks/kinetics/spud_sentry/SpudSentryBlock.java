package dev.lopyluna.dndesires.content.blocks.kinetics.spud_sentry;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.ItemHandlerHelper;

@SuppressWarnings("NullableProblems")
public class SpudSentryBlock extends KineticBlock implements IBE<SpudSentryBE>, ICogWheel {
    public SpudSentryBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (placer instanceof Player player && !(player instanceof FakePlayer) && level.getBlockEntity(pos) instanceof SpudSentryBE be) {
            if (be.ownerUUID == null) {
                be.ownerUUID = placer.getUUID();
                be.owner = player;
            }
            be.xRot = be.getDesiredXRot(player);
            be.yRot = be.getDesiredYRot(player);
            be.lerpX.setValue(be.xRot);
            be.lerpX.updateChaseTarget(be.xRot);
            be.lerpY.setValue(be.yRot);
            be.lerpY.updateChaseTarget(be.yRot);
            be.setChanged();
            if (!level.isClientSide) be.notifyUpdate();
        }
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!(player instanceof FakePlayer) && level.getBlockEntity(pos) instanceof SpudSentryBE be && be.ownerUUID == null) {
            be.ownerUUID = player.getUUID();
            be.owner = player;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(player instanceof FakePlayer) && level.getBlockEntity(pos) instanceof SpudSentryBE be) {
            if (be.ownerUUID == null) {
                be.ownerUUID = player.getUUID();
                be.owner = player;
            }
            if (!stack.isEmpty()) {
                var count = stack.getCount();
                be.inputInv.insert(stack);
                if (count != stack.getCount()) {
                    level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(),
                            SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.5F + 1.0F) * 2.0F);
                    return ItemInteractionResult.SUCCESS;
                }
            } else {
                var input = be.getInput().copy();
                if (input.isEmpty()) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                be.inputInv.extractItem(0, input.getCount(), false);
                ItemHandlerHelper.giveItemToPlayer(player, input);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<SpudSentryBE> getBlockEntityClass() {
        return SpudSentryBE.class;
    }

    @Override
    public BlockEntityType<? extends SpudSentryBE> getBlockEntityType() {
        return DesiresBETypes.SPUD_SENTRY.get();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
