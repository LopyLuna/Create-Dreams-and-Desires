package dev.lopyluna.dndesires.content.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MilkshakeItem extends Item {
    public MilkshakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        var result = super.finishUsingItem(stack, level, livingEntity);
        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
            if (result.isEmpty()) return Items.GLASS_BOTTLE.getDefaultInstance();
            else player.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());
        }
        return result;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 21;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
