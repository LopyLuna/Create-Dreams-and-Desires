package uwu.lopyluna.create_dd.item.ItemProperties;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
        boolean maxTime = gameTime < 20000;
        boolean minTime = gameTime > 16000;
        boolean nightTime = maxTime && minTime;

        if (y > (maxHeight / 1.25) && nightTime) {
            ItemStack newStack = DDItems.STARGAZE_SINGULARITY.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
        }


        return false;
    }
}
