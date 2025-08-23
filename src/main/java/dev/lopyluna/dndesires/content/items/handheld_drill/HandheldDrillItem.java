package dev.lopyluna.dndesires.content.items.handheld_drill;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.CustomArmPoseItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.lopyluna.dndesires.content.items.IOnBlockBreak;
import dev.lopyluna.dndesires.register.DesiresItems;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dev.lopyluna.dndesires.register.DesiresTags.BlockTags.EXCAVATION_DRILL_VEIN_VALID;

@ParametersAreNonnullByDefault
public class HandheldDrillItem extends DiggerItem implements CustomArmPoseItem, IOnBlockBreak {
    private static boolean mining = false;
    public HandheldDrillItem(Tier tier, Properties properties) {
        super(tier, DesiresTags.BlockTags.MINEABLE_WITH_DRILL.tag, properties);
    }

    @Override
    public void onBlockBreak(ItemStack pStack, LevelAccessor pAccessor, BlockPos pPos, BlockState pState, Player pBreaker) {
        if (!DesiresItems.HANDHELD_DRILL.isIn(pStack) || !(pAccessor instanceof Level pLevel)) return;
        if (mining || !isValidMine(pState)) return;
        boolean flag = !(pBreaker instanceof FakePlayer);
        boolean playerHeldKey;
        if (flag) playerHeldKey = !pBreaker.getPersistentData().getBoolean("HandheldDrillKey");
        else playerHeldKey = false;
        if (playerHeldKey) return;

        var veins = pState.is(EXCAVATION_DRILL_VEIN_VALID.tag);
        Vec3 vec = pBreaker.getLookAngle();

        mining = true;
        if (veins) VeinMining.findVein(pLevel, pPos, EXCAVATION_DRILL_VEIN_VALID.tag, pState.is(DesiresTags.BlockTags.EXCAVATION_DRILL_VEIN_LARGE.tag) ? 32 : 12)
                .destroyBlocks(pLevel, pBreaker, (dropPos, item) -> dropItemFromExcavatedVein(pLevel, pPos, vec, dropPos, item));
        else if (pBreaker instanceof ServerPlayer pSPlayer) BoreMining.findBore(1, pPos, pSPlayer)
                .destroyBlocks(pLevel, pBreaker, (dropPos, item) -> dropItemFromExcavatedVein(pLevel, pPos, vec, dropPos, item));
        mining = false;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!BacktankUtil.canAbsorbDamage(attacker, maxUses() * 2)) stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        var tool = stack.get(DataComponents.TOOL);
        if (tool != null) {
            if (!BacktankUtil.canAbsorbDamage(miningEntity, maxUses() * 2) && !level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0)
                stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
            return true;
        }
        return false;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return state.is(DesiresTags.BlockTags.MINEABLE_WITH_DRILL.tag) || !state.requiresCorrectToolForDrops() || state.canBeReplaced();
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
        return AllConfigs.server().equipment.maxPotatoCannonShots.get();
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
        consumer.accept(SimpleCustomRenderer.create(this, new HandheldDrillRenderer()));
    }

    public boolean isValidMine(BlockState pState) {
        return pState.is(EXCAVATION_DRILL_VEIN_VALID.tag) || pState.is(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public static void dropItemFromExcavatedVein(Level world, BlockPos breakingPos, Vec3 fallDirection, BlockPos pos, ItemStack stack) {
        float distance = (float) Math.sqrt(pos.distSqr(breakingPos));
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
        entity.setDeltaMovement(fallDirection.scale(distance / 16f));
        world.addFreshEntity(entity);
    }

    public static class VeinMining {
        public static final Vein NO_VEIN = new Vein(Collections.emptyList());

        @Nonnull
        public static Vein findVein(@Nullable BlockGetter reader, BlockPos startPos, TagKey<Block> filterTag, int maxBlocks) {
            if (reader == null) return NO_VEIN;

            List<BlockPos> matchingBlocks = new ArrayList<>();
            Set<BlockPos> visited = new HashSet<>();
            Queue<BlockPos> frontier = new LinkedList<>();

            frontier.add(startPos);
            visited.add(startPos);

            while (!frontier.isEmpty() && matchingBlocks.size() < maxBlocks) {
                var currentPos = frontier.poll();
                if (currentPos == null) continue;

                var currentState = reader.getBlockState(currentPos);
                if (currentState.is(filterTag) && ((Level)reader).getWorldBorder().isWithinBounds(currentPos)) {
                    matchingBlocks.add(currentPos);
                    for (int dx = -1; dx <= 1; dx++) for (int dy = -1; dy <= 1; dy++) for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        var adjacentPos = currentPos.offset(dx, dy, dz);
                        if (!visited.contains(adjacentPos)) {
                            var adjacentState = reader.getBlockState(adjacentPos);
                            if (adjacentState.is(filterTag)) {
                                visited.add(adjacentPos);
                                frontier.add(adjacentPos);
                            }
                        }
                    }
                }
            }
            return new Vein(matchingBlocks);
        }

        public static class Vein extends AbstractBlockBreakQueue {
            private final List<BlockPos> ores;

            public Vein(List<BlockPos> ores) {
                this.ores = ores;
            }

            @Override
            public void destroyBlocks(Level world, ItemStack tool, @Nullable Player player, BiConsumer<BlockPos, ItemStack> dropConsumer) {
                if (player == null) return;
                ores.forEach(makeCallbackFor(world, 0.5f, tool, player, dropConsumer));
                player.causeFoodExhaustion(ores.size() * 0.5f);
            }

            @Override
            protected Consumer<BlockPos> makeCallbackFor(Level level, float effectChance, ItemStack toDamage, @Nullable Player player, BiConsumer<BlockPos, ItemStack> drop) {
                return pos -> {
                    var usedTool = toDamage.copy();
                    BlockHelper.destroyBlockAs(level, pos, player, toDamage, effectChance, stack -> drop.accept(pos, stack));
                    if (player != null && toDamage.isEmpty() && !usedTool.isEmpty()) EventHooks.onPlayerDestroyItem(player, usedTool, InteractionHand.MAIN_HAND);
                };
            }
        }
    }

    public static class BoreMining {
        public static VeinMining.Vein findBore(int range, BlockPos initalBlockPos, ServerPlayer player) {
            List<BlockPos> positions = new ArrayList<>();
            var level = player.level();
            var traceResult = level.clip(new ClipContext(player.getEyePosition(1f),
                    (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (traceResult.getType() == HitResult.Type.MISS) return VeinMining.NO_VEIN;
            var axis = traceResult.getDirection().getAxis();
            for(int x = -range; x <= range; x++) for(int y = -range; y <= range; y++) {
                var pos = expandPos(x, y, axis, initalBlockPos);
                if (level.getWorldBorder().isWithinBounds(pos)) positions.add(pos);
            }
            return new VeinMining.Vein(positions.stream().filter(pos -> {
                if (pos == initalBlockPos || !level.isLoaded(pos)) return false;
                var state = level.getBlockState(pos);
                return !state.isAir() && state.getDestroySpeed(level, pos) != -1;
            }).toList());
        }

        public static BlockPos expandPos(int x, int y, Direction.Axis axis, BlockPos initalBlockPos) {
            return switch (axis) {
                case X -> new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x);
                case Y -> new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y);
                case Z -> new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ());
            };
        }
    }
}
