package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.item.DDItems;
import uwu.lopyluna.create_dd.item.ItemProperties.BobTiers;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.function.Consumer;

import static uwu.lopyluna.create_dd.item.ItemProperties.drilltool.ExcavtionMining.findVein;

@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExcavationDrillItem extends BackTankPickaxeItem {
    private static boolean excavating = false;
    static boolean excavatingDisplay = false;

    public ExcavationDrillItem(Properties builder) {
        super(BobTiers.Drill, 3, -3.0F, builder);
    }
    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState state) {
        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;

        if (state.is(DDTags.AllBlockTags.valid_excavation.tag) && player.isCrouching() && state.is(DDTags.AllBlockTags.drill_mineable.tag)) {
            excavatingDisplay = true;
        } else {
            excavatingDisplay = false;
        }

        if (state.is(DDTags.AllBlockTags.valid_excavation.tag) && player.isCrouching() && state.is(DDTags.AllBlockTags.drill_mineable.tag)) {
            return state.is(DDTags.AllBlockTags.valid_excavation.tag) && player.isCrouching() && state.is(DDTags.AllBlockTags.drill_mineable.tag) ? this.speed / 4 : 0.25F;
        } else {
            return state.is(DDTags.AllBlockTags.drill_mineable.tag) ? this.speed : 1.0F;
        }
    }

    public static void destroyVein(Level iWorld, BlockState state, BlockPos pos, Player player) {

        if (excavating || !state.is(DDTags.AllBlockTags.valid_excavation.tag) || !player.isCrouching() || !(iWorld instanceof  Level))
            return;
        Level worldIn = (Level) iWorld;

        excavating = true;
        findVein(worldIn, pos).destroyBlocks(worldIn, player, (dropPos, item) -> dropItemFromMineVein(worldIn, pos, dropPos, item));
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
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ExcavationDrillRender()));
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

}
