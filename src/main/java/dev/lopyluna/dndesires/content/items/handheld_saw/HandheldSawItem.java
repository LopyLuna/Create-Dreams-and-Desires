package dev.lopyluna.dndesires.content.items.handheld_saw;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.kinetics.saw.TreeCutter;
import com.simibubi.create.foundation.item.CustomArmPoseItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.lopyluna.dndesires.content.items.IOnBlockBreak;
import dev.lopyluna.dndesires.content.items.TreeOverride;
import dev.lopyluna.dndesires.mixins.AxeItemAccessor;
import dev.lopyluna.dndesires.register.DesiresItems;
import dev.lopyluna.dndesires.register.DesiresConfigs;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class HandheldSawItem extends AxeItem implements CustomArmPoseItem, IOnBlockBreak {
    private static boolean deforesting = false;
    public HandheldSawItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void onBlockBreak(ItemStack pStack, LevelAccessor pAccessor, BlockPos pPos, BlockState pState, Player pBreaker) {
        if (!DesiresItems.HANDHELD_SAW.isIn(pStack) || !(pAccessor instanceof Level pLevel)) return;
        var flag = !(pBreaker instanceof FakePlayer);
        if (deforesting || !isValidTree(pState)) return;
        boolean playerHeldKey;
        if (flag) playerHeldKey = !pBreaker.getPersistentData().getBoolean("HandheldSawKey");
        else playerHeldKey = false;
        if (playerHeldKey) return;
        Vec3 vec = pBreaker.getLookAngle();

        deforesting = true;
        var dynamicTree = TreeCutter.findDynamicTree(pState.getBlock(), pPos);
        if (dynamicTree.isPresent()) {
            dynamicTree.get().destroyBlocks(pLevel, pBreaker, (dropPos, item) -> dropItemFromCutTree(pLevel, pPos, vec, dropPos, item));
            deforesting = false;
            return;
        }
        new TreeOverride(TreeCutter.findTree(pLevel, pPos, pState)).destroyBlocks(pLevel, pBreaker, (dropPos, item) -> dropItemFromCutTree(pLevel, pPos, vec, dropPos, item));
        deforesting = false;
    }

    public boolean isValidTree(BlockState pState) {
        return TreeCutter.isVerticalPlant(pState) || TreeCutter.isChorus(pState) || TreeCutter.isLog(pState) || TreeCutter.isRoot(pState);
    }

    public static void dropItemFromCutTree(Level world, BlockPos breakingPos, Vec3 fallDirection, BlockPos pos, ItemStack stack) {
        var distance = (float) Math.sqrt(pos.distSqr(breakingPos));
        var dropPos = VecHelper.getCenterOf(pos);
        var entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
        entity.setDeltaMovement(fallDirection.scale(distance / 16f));
        world.addFreshEntity(entity);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        return axeUseOn(context);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!BacktankUtil.canAbsorbDamage(attacker, maxUses())) stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
    }

    //Original:
//  @Override
//  public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
//      var tool = stack.get(DataComponents.TOOL);
//        if (tool != null) {
//          if (!BacktankUtil.canAbsorbDamage(miningEntity, maxUses()) && !level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0)
//              stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
//          return true;
//      }
//      return false;
//  }
@Override
public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
    var tool = stack.get(DataComponents.TOOL);
    if (tool != null) {

        // Do not consume extra air for blocks broken by the automatic tree-cutting chain.
        // The first manually broken block already paid the air cost.
        if (deforesting && !DesiresConfigs.server().handheldSawConsumesAirPerTreeBlock.get()) {
            return true;
        }

        var backtanksBeforeUse = BacktankUtil.getAllWithAir(miningEntity);
        ItemStack usedBacktank = backtanksBeforeUse.isEmpty() ? ItemStack.EMPTY : backtanksBeforeUse.getFirst();
        int airBefore = usedBacktank.isEmpty() ? 0 : BacktankUtil.getAir(usedBacktank);
        int maxAir = usedBacktank.isEmpty() ? BacktankUtil.maxAirWithoutEnchants() : BacktankUtil.maxAir(usedBacktank);

        boolean absorbedWithAir = BacktankUtil.canAbsorbDamage(miningEntity, maxUses());

        if (!level.isClientSide && miningEntity instanceof Player player) {
            if (!usedBacktank.isEmpty()) {
                int airAfter = BacktankUtil.getAir(usedBacktank);
                int airUsed = airBefore - airAfter;

                player.displayClientMessage(
                    Component.literal("Handheld Saw air: " + airAfter + "/" + maxAir + " used: " + airUsed),
                    true
                );
            } else {
                player.displayClientMessage(
                    Component.literal("Handheld Saw air: no backtank air found"),
                    true
                );
            }
        }

        if (!absorbedWithAir
            && !level.isClientSide
            && state.getDestroySpeed(level, pos) != 0.0F
            && tool.damagePerBlock() > 0) {
            stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    return false;
}

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return state.is(BlockTags.MINEABLE_WITH_AXE) || !state.requiresCorrectToolForDrops() || state.canBeReplaced();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BacktankUtil.isBarVisible(stack, maxUses());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return BacktankUtil.getBarWidth(stack, maxUses());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BacktankUtil.getBarColor(stack, maxUses());
    }

    private static int maxUses() {
        // Original = airInBacktank = 900 / maxPotatoCannonShots = 200
        // return AllConfigs.server().equipment.maxPotatoCannonShots.get();
        // New own made (not tested, 1 Air usage instead of 4
        // return AllConfigs.server().equipment.maxPotatoCannonShots.get() * 4;
        
        int baseUses = AllConfigs.server().equipment.maxPotatoCannonShots.get();
        int multiplier = DesiresConfigs.server().handheldSawAirEfficiencyMultiplier.get();

        return baseUses * multiplier;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        return true;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging) return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        return null;
    }

    @SuppressWarnings("removal")
    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new HandheldSawRenderer()));
    }

    public InteractionResult axeUseOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();
        if (AxeItemAccessor.playerHasShieldUseIntent(context)) return InteractionResult.PASS;
        else {
            Optional<BlockState> optional = evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), context);
            if (optional.isEmpty()) return InteractionResult.PASS;
            else {
                ItemStack itemstack = context.getItemInHand();
                if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockpos, itemstack);

                level.setBlock(blockpos, optional.get(), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, optional.get()));
                if (player != null) itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext p_40529_) {
        Optional<BlockState> optional = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_STRIP, false));
        if (optional.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional1 = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_SCRAPE, false));
            if (optional1.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                return optional1;
            } else {
                Optional<BlockState> optional2 = Optional.ofNullable(state.getToolModifiedState(p_40529_, net.neoforged.neoforge.common.ItemAbilities.AXE_WAX_OFF, false));
                if (optional2.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3004, pos, 0);
                    return optional2;
                } else return Optional.empty();
            }
        }
    }
}
