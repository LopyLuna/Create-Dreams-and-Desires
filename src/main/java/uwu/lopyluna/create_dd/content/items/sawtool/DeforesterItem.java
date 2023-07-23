package uwu.lopyluna.create_dd.content.items.sawtool;

import static uwu.lopyluna.create_dd.content.items.sawtool.TreeCutter.findTree;


import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.utility.VecHelper;

import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.content.items.DDToolTiers;
import uwu.lopyluna.create_dd.init.DDItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DeforesterItem extends DeforesterAxeItem {
    public static int maxUses;
    private static boolean deforesting = false; // required as to not run into "recursions" over forge events on tree cutting
    public DeforesterItem(Properties builder) {
        super(DDToolTiers.Deforester, 5.0F, -3.0F, builder);
    }

    // Moved away from Item#onBlockDestroyed as it does not get called in Creative
    public static void destroyTree(Level iWorld, BlockState state, BlockPos pos,
                                   Player player) {

        if (deforesting ||!(state.is(BlockTags.LOGS) || AllTags.AllBlockTags.SLIMY_LOGS.matches(state)) || !player.isCrouching() || !(iWorld instanceof  Level))
            return;
        Level worldIn = (Level) iWorld;
        Vec3 vec = player.getLookAngle();

        deforesting = true;
        findTree(worldIn, pos).destroyBlocks(worldIn, player, (dropPos, item) -> dropItemFromCutTree(worldIn, pos, vec, dropPos, item));
        deforesting = false;
    }

    public static void onBlockDestroyed(BlockEvents.BreakEvent event) {
        ItemStack heldItemMainhand = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);

        if (!DDItems.deforester_saw.isIn(heldItemMainhand))
            return;
        destroyTree((Level) event.getWorld(), event.getState(), event.getPos(), event.getPlayer());
    }

    public static void dropItemFromCutTree(Level world, BlockPos breakingPos, Vec3 fallDirection, BlockPos pos,
                                           ItemStack stack) {
        float distance = (float) Math.sqrt(pos.distSqr(breakingPos));
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
        entity.setDeltaMovement(fallDirection.scale(distance / 16f));
        world.addFreshEntity(entity);
    }


    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return BacktankUtil.isBarVisible(stack, maxUses());
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return BacktankUtil.getBarWidth(stack, maxUses());
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return BacktankUtil.getBarColor(stack, maxUses());
    }


    public static int maxUses() {
        return DDToolTiers.Deforester.getUses();
    }
}
