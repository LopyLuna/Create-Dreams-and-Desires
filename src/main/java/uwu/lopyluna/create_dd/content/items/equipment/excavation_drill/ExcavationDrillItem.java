package uwu.lopyluna.create_dd.content.items.equipment.excavation_drill;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
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
import uwu.lopyluna.create_dd.content.items.equipment.BackTankPickaxeItem;
import uwu.lopyluna.create_dd.infrastructure.utility.BoreMining;
import uwu.lopyluna.create_dd.registry.DesiresItems;
import uwu.lopyluna.create_dd.registry.DesiresTags;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static uwu.lopyluna.create_dd.infrastructure.utility.VeinMining.findVein;
import static uwu.lopyluna.create_dd.registry.DesireTiers.Drill;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"all"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExcavationDrillItem extends BackTankPickaxeItem {
    private static final Set<BlockPos> hashedBlocks = new HashSet<>();
    private static boolean veinExcavating = false;
    public ExcavationDrillItem(Properties pProperties) {
        super(Drill, 1, -2.8F, pProperties);
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return pState.is(DesiresTags.forgeBlockTag("ores")) ? this.speed * 0.75f : this.speed;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static void destroyVein(Level pLevel, BlockState state, BlockPos pos,
                                   Player player) {
        boolean playerHeldShift = player.isShiftKeyDown();

        if (veinExcavating || !(state.is(DesiresTags.forgeBlockTag("ores"))) || !playerHeldShift)
            return;
        Vec3 vec = player.getLookAngle();

        veinExcavating = true;
        findVein(pLevel, pos).destroyBlocks(pLevel, player, (dropPos, item) -> dropItemFromExcavatedVein(pLevel, pos, vec, dropPos, item));
        veinExcavating = false;
    }

    public static void dropItemFromExcavatedVein(Level world, BlockPos breakingPos, Vec3 fallDirection, BlockPos pos,
                                           ItemStack stack) {
        float distance = (float) Math.sqrt(pos.distSqr(breakingPos));
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack);
        entity.setDeltaMovement(fallDirection.scale(distance / 16f));
        world.addFreshEntity(entity);
    }

    public static boolean validBlocks(BlockState pState, BlockEvent.BreakEvent event, BlockPos blockPos) {
        ItemStack heldItemMainhand = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        return pState.getDestroySpeed(event.getLevel(), blockPos) != 0.0F ||
                    !pState.is(DesiresTags.forgeBlockTag("ores")) ||
                    DesiresItems.EXCAVATION_DRILL.get().isCorrectToolForDrops(heldItemMainhand, pState);
    }

    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        boolean playerHeldShift = player.isShiftKeyDown();
        Level level = (Level) event.getLevel();
        ItemStack heldItemMainhand = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        BlockPos blockPos = event.getPos();

        boolean validOres = event.getLevel().getBlockState(blockPos).is(DesiresTags.forgeBlockTag("ores"));

        if (DesiresItems.EXCAVATION_DRILL.isIn(heldItemMainhand) && validOres) {
            destroyVein((Level) event.getLevel(), event.getState(), event.getPos(), event.getPlayer());
        }

        if (DesiresItems.EXCAVATION_DRILL.isIn(heldItemMainhand) && player instanceof ServerPlayer serverPlayer && playerHeldShift && !validOres) {
            BlockPos initalBlockPos = event.getPos();

            if (hashedBlocks.contains(initalBlockPos)) {
                return;
            }

            //=======//
            //This check is required since .getBlocksToBeDestroyed can return null, which might lead to NullPointerException and crash the game/server.
            List<BlockPos> positions = BoreMining.getBlocksToBeDestroyed(1, initalBlockPos, serverPlayer);

            if (positions == null) {
                return;
            }
            //=======//

            for (BlockPos pos : positions) {
                if(pos == initalBlockPos || !(event.getLevel().getBlockState(pos).getDestroySpeed(event.getLevel(), pos) != 0.0F) ||
                        event.getLevel().getBlockState(pos).is(DesiresTags.forgeBlockTag("ores")) ||
                        !DesiresItems.EXCAVATION_DRILL.get().isCorrectToolForDrops(heldItemMainhand, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                hashedBlocks.add(pos);
                Vec3 vec = VecHelper.offsetRandomly(VecHelper.getCenterOf(pos), level.random, .125f);
                BlockHelper.destroyBlockAs(level, pos, player, heldItemMainhand, 1f, (stack) -> {
                    if (stack.isEmpty())
                        return;
                    if (!level.getGameRules()
                            .getBoolean(GameRules.RULE_DOBLOCKDROPS))
                        return;
                    if (level.restoringBlockSnapshots)
                        return;

                    ItemEntity itementity = new ItemEntity(level, vec.x, vec.y, vec.z, stack);
                    itementity.setDefaultPickUpDelay();
                    itementity.setDeltaMovement(Vec3.ZERO);
                    level.addFreshEntity(itementity);
                });
                hashedBlocks.remove(pos);
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    //@Override
    //@OnlyIn(Dist.CLIENT)
    //public HumanoidModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
    //    if (!player.swinging) {
    //        return HumanoidModel.ArmPose.CROSSBOW_HOLD;
    //    } else {
    //        return HumanoidModel.ArmPose.ITEM;
    //    }
    //}

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ExcavationDrillRenderer()));
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
        return Drill.getUses();
    }
}
