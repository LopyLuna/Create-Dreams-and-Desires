package uwu.lopyluna.create_dd.item.ItemProperties.compound;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.item.DDItems;

public class UnchargedStargazeSingularity extends Item {
    public UnchargedStargazeSingularity(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;


        double y = entity.getY();
        int maxHeight = world.getMaxBuildHeight();
        CompoundTag data = entity.getPersistentData();
        float gameTime = world.dayTime() % 24000;
        boolean maxTime = gameTime < DDConfigs.server().recipes.stargaze_singularity_max_time.get();
        boolean minTime = gameTime > DDConfigs.server().recipes.stargaze_singularity_min_time.get();
        boolean nightTime = maxTime && minTime;

        if (y > (maxHeight / DDConfigs.server().recipes.stargaze_singularity_max_height_division.get()) && nightTime && DDConfigs.server().recipes.stargaze_singularity_recipe.get()) {
            ItemStack newStack = DDItems.STARGAZE_SINGULARITY.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
        }


        return false;
    }
}
