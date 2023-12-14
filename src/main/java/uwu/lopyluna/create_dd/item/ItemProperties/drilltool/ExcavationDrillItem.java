package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.item.DDItems;
import uwu.lopyluna.create_dd.item.ItemProperties.BobTiers;
import uwu.lopyluna.create_dd.item.ItemProperties.sawtool.DeforesterRender;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.function.Consumer;

import static uwu.lopyluna.create_dd.item.ItemProperties.drilltool.ExcavtionMining.findVein;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExcavationDrillItem extends BackTankPickaxeItem {
    private static boolean excavating = false;

    public ExcavationDrillItem(Properties builder) {
        super(BobTiers.Drill, 3, -3.0F, builder);
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (pState.is(Tags.Blocks.ORES) || pState.is(DDTags.AllBlockTags.ore_stones.tag) || pState.is(DDTags.AllBlockTags.valid_excavation.tag)) {
            return pState.is(Tags.Blocks.ORES) || pState.is(DDTags.AllBlockTags.ore_stones.tag) || pState.is(DDTags.AllBlockTags.valid_excavation.tag) ? this.speed / 4 : 0.25F;
        } else {
            return pState.is(pState.getBlock()) ? this.speed : 1.0F;
        }
    }

    public static void destroyVein(Level iWorld, BlockState state, BlockPos pos, Player player) {

        if (excavating || !(state.is(Tags.Blocks.ORES) || !(state.is(DDTags.AllBlockTags.ore_stones.tag) || !(state.is(DDTags.AllBlockTags.valid_excavation.tag) || !player.isCrouching()))))
            return;

        excavating = true;
        findVein(iWorld, pos).destroyBlocks(iWorld, player, (dropPos, item) -> dropItemFromMineVein(iWorld, pos, dropPos, item));
        excavating = false;
    }

    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        ItemStack heldItemMainhand = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);

        if (!DDItems.excavation_drill.isIn(heldItemMainhand))
            return;
        destroyVein((Level) event.getLevel(), event.getState(), event.getPos(), event.getPlayer());
    }

    public static void dropItemFromMineVein(Level world, BlockPos breakingPos, BlockPos pos, ItemStack stack) {
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
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
        return BobTiers.Drill.getUses();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ExcavationDrillRender()));
    }
}
