package dev.lopyluna.create_d2d.content.items.gatling_breaker;

import com.simibubi.create.content.equipment.zapper.ShootableGadgetItemMethods;
import com.simibubi.create.content.equipment.zapper.ZapperBeamPacket;
import com.simibubi.create.foundation.item.CustomArmPoseItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("removal")
@ParametersAreNonnullByDefault
public class GatlingBreakerItem extends Item implements CustomArmPoseItem {
    HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = null;
    boolean shooting = false;
    int shotSpeed = 20;
    int tick = 0;
    int reloadTick = 0;

    public GatlingBreakerItem(Properties properties) {
        super(properties.durability(2000)
                .component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)), 1.0F, 1)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new GatlingBreakerRenderer()));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.NETHERITE_SCRAP) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return super.use(level, player, usedHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (level.isClientSide) return;
        if (livingEntity instanceof ServerPlayer player && !(player instanceof FakePlayer)) {
            var speedEff = enchantmentLookup == null ? 1 : getSpeedEfficiency(stack) + 1;
            var max = 8 - speedEff;
            var hand = livingEntity.getUsedItemHand();
            var isMain = hand == InteractionHand.MAIN_HAND;

            if (!shooting) {
                shooting = true;
                reloadTick = 0;
            }
            if (tick >= shotSpeed) {
                breakBlock(level, player, stack, hand, isMain);
                if (shotSpeed > max) shotSpeed -= speedEff;
                if (shotSpeed < max) shotSpeed = max;
                tick = 0;
            }
            tick++;
        }
    }

    public int getSpeedEfficiency(ItemStack stack) {
        return stack.getEnchantmentLevel(enchantmentLookup.getOrThrow(Enchantments.EFFICIENCY));
    }

    public void breakBlock(Level level, ServerPlayer player, ItemStack stack, InteractionHand hand, boolean isMain) {
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 range = player.getLookAngle().scale(getDefaultRange());
        BlockHitResult raytrace = level.clip(new ClipContext(start, start.add(range), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        Vec3 barrelPos = ShootableGadgetItemMethods.getGunBarrelVec(player, isMain, new Vec3(.35f, -0.1f, 1));
        ShootableGadgetItemMethods.sendPackets(player, b -> new ZapperBeamPacket(barrelPos, hand, b, raytrace.getLocation()));
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

        BlockPos pos = raytrace.getBlockPos();
        BlockState stateReplaced = level.getBlockState(pos);
        var hardness = stateReplaced.getDestroySpeed(level, pos);
        if (stateReplaced.isAir() || !level.isInWorldBounds(pos) || hardness == -1 || hardness > 50) return;

        level.destroyBlock(pos, false);
        if (!stateReplaced.requiresCorrectToolForDrops() || !stateReplaced.is(BlockTags.INCORRECT_FOR_NETHERITE_TOOL))
            Block.dropResources(stateReplaced, level, player.blockPosition(), level.getBlockEntity(pos), player, stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        tick = 0;
        shooting = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide) return;
        if (enchantmentLookup == null) enchantmentLookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        if (entity instanceof ServerPlayer player && !(player instanceof FakePlayer)) {
            if (!shooting && 20 > shotSpeed) {
                if (reloadTick >= shotSpeed) {
                    shotSpeed++;
                    reloadTick = 0;
                }
                reloadTick++;
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Nullable
    @Override
    public HumanoidModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging) return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        return null;
    }

    public int getDefaultRange() {
        return 15;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }
}
