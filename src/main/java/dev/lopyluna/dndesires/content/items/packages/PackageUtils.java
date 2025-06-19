package dev.lopyluna.dndesires.content.items.packages;

import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

import static com.simibubi.create.content.logistics.box.PackageStyles.RARE_BOXES;
import static com.simibubi.create.content.logistics.box.PackageStyles.STANDARD_BOXES;

@SuppressWarnings("unused")
public class PackageUtils {
    public static ItemStack containingNormal(Level level, List<ItemStack> stacks) {
        var newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return containingNormal(level, newInv);
    }
    public static ItemStack containingNormal(Level level, ItemStackHandler stacks) {
        var box = getRandomNormalBox(level);
        box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks));
        return box;
    }
    public static ItemStack containingBurst(Level level, List<ItemStack> stacks) {
        var newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return containingBurst(level, newInv);
    }
    public static ItemStack containingBurst(Level level, ItemStackHandler stacks) {
        var box = getRandomBurstBox(level);
        box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks));
        return box;
    }


    public static ItemStack containingNormal(RandomSource random, List<ItemStack> stacks) {
        var newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return containingNormal(random, newInv);
    }
    public static ItemStack containingNormal(RandomSource random, ItemStackHandler stacks) {
        var box = getRandomNormalBox(random);
        box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks));
        return box;
    }
    public static ItemStack containingBurst(RandomSource random, List<ItemStack> stacks) {
        var newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return containingBurst(random, newInv);
    }
    public static ItemStack containingBurst(RandomSource random, ItemStackHandler stacks) {
        var box = getRandomBurstBox(random);
        box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks));
        return box;
    }

    public static ItemStack getRandomBurstBox(RandomSource random) {
        List<BurstPackageItem> rare = new ArrayList<>();
        for (var box : RARE_BOXES) if (box instanceof BurstPackageItem item) rare.add(item);
        List<BurstPackageItem> standard = new ArrayList<>();
        for (var box : STANDARD_BOXES) if (box instanceof BurstPackageItem item) standard.add(item);

        var pool = random.nextInt(7500) == 0 ? rare : standard;
        return new ItemStack(pool.get(random.nextInt(pool.size())));
    }

    public static ItemStack getRandomBurstBox(Level level) {
        return getRandomBurstBox(level.random);
    }

    public static ItemStack getRandomNormalBox(RandomSource random) {
        List<PackageItem> rare = new ArrayList<>();
        for (var box : RARE_BOXES) if (!(box instanceof BurstPackageItem)) rare.add(box);
        List<PackageItem> standard = new ArrayList<>();
        for (var box : STANDARD_BOXES) if (!(box instanceof BurstPackageItem)) standard.add(box);
        var pool = random.nextInt(7500) == 0 ? rare : standard;
        return new ItemStack(pool.get(random.nextInt(pool.size())));
    }

    public static ItemStack getRandomNormalBox(Level level) {
        return getRandomNormalBox(level.random);
    }
}
