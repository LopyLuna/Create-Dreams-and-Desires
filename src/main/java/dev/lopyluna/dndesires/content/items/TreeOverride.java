package dev.lopyluna.dndesires.content.items;

import com.simibubi.create.content.kinetics.saw.TreeCutter;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import com.simibubi.create.foundation.utility.BlockHelper;
import dev.lopyluna.dndesires.mixins.TreeAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TreeOverride extends AbstractBlockBreakQueue {
    public final List<BlockPos> logs;
    public final List<BlockPos> leaves;
    public final List<BlockPos> attachments;

    public TreeOverride(TreeCutter.Tree tree) {
        this.logs = ((TreeAccessor)tree).logs();
        this.leaves = ((TreeAccessor)tree).leaves();
        this.attachments = ((TreeAccessor)tree).attachments();
    }

    @Override
    public void destroyBlocks(Level level, ItemStack toDamage, Player player, BiConsumer<BlockPos, ItemStack> drop) {
        if (player == null) return;
        var dummy = toDamage.copy();
        dummy.set(DataComponents.UNBREAKABLE, new Unbreakable(false));
        attachments.forEach(makeCallbackFor(level, 1 / 32f, toDamage, player, drop));
        logs.forEach(makeCallbackFor(level, 1 / 2f, toDamage, player, drop));
        leaves.forEach(makeCallbackFor(level, 1 / 8f, dummy, player, drop));
        var i = attachments.size() + logs.size() + leaves.size();
        player.causeFoodExhaustion(i * 0.5f);
    }

    @Override
    protected Consumer<BlockPos> makeCallbackFor(Level level, float effectChance, ItemStack toDamage, Player player, BiConsumer<BlockPos, ItemStack> drop) {
        return pos -> {
            var usedTool = toDamage.copy();
            BlockHelper.destroyBlockAs(level, pos, player, toDamage, effectChance, stack -> drop.accept(pos, stack));
            if (toDamage.isEmpty() && !usedTool.isEmpty()) EventHooks.onPlayerDestroyItem(player, usedTool, InteractionHand.MAIN_HAND);
        };
    }
}
