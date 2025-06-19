package dev.lopyluna.dndesires.content.items.packages;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.logistics.box.PackageStyles.RARE_BOXES;
import static com.simibubi.create.content.logistics.box.PackageStyles.STANDARD_BOXES;

@ParametersAreNonnullByDefault
public class BurstPackageItem extends PackageItem {
    public BurstPackageItem(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);
        PackageStyles.ALL_BOXES.remove(this);
        (style.rare() ? RARE_BOXES : STANDARD_BOXES).remove(this);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof Player player) open(level, player, stack);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int ticks) {
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 0;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    public void open(Level level, Player playerIn, ItemStack box) {
        var contents = getContents(box);
        var particle = box.copy();

        box.shrink(1);

        if (!level.isClientSide()) for (int i = 0; i < contents.getSlots(); i++) {
            var itemstack = contents.getStackInSlot(i);
            if (itemstack.isEmpty()) continue;

            if (itemstack.getItem() instanceof SpawnEggItem sei && level instanceof ServerLevel sl) {
                EntityType<?> entitytype = sei.getType(itemstack);
                var entity = entitytype.spawn(sl, itemstack, null, BlockPos.containing(playerIn.position().add(playerIn.getLookAngle().multiply(1, 0, 1).normalize())), MobSpawnType.SPAWN_EGG, false, false);
                if (entity != null) itemstack.shrink(1);
            }

            playerIn.getInventory().placeItemBackInInventory(itemstack.copy());
        }

        var position = playerIn.position();
        AllSoundEvents.PACKAGE_POP.playOnServer(level, playerIn.blockPosition());

        if (level.isClientSide()) for (int i = 0; i < 10; i++) {
            var motion = VecHelper.offsetRandomly(Vec3.ZERO, level.getRandom(), .125f);
            var pos = position.add(0, 0.5, 0).add(playerIn.getLookAngle().scale(.5)).add(motion.scale(4));
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, particle), pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
        }
    }
}
