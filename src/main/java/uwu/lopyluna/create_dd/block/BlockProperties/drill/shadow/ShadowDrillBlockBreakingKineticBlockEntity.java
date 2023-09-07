package uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DDTags;

import java.util.function.Consumer;

import static com.simibubi.create.foundation.utility.BlockHelper.destroyBlockAs;

public abstract class ShadowDrillBlockBreakingKineticBlockEntity extends BlockBreakingKineticBlockEntity {

    public ShadowDrillBlockBreakingKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide)
            return;
        if (!shouldRun())
            return;
        if (getSpeed() == 0)
            return;

        breakingPos = getBreakingPos();

        if (ticksUntilNextProgress < 0)
            return;
        if (ticksUntilNextProgress-- > 0)
            return;

        BlockState stateToBreak = level.getBlockState(breakingPos);
        float blockHardness = stateToBreak.getDestroySpeed(level, breakingPos);

        if (!canBreak(stateToBreak, blockHardness)) {
            if (destroyProgress != 0) {
                destroyProgress = 0;
                level.destroyBlockProgress(breakerId, breakingPos, -1);
            }
            return;
        }

        float breakSpeed = getBreakSpeed();
        destroyProgress += Mth.clamp((int) (breakSpeed / blockHardness), 1, 10 - destroyProgress);
        level.playSound(null, worldPosition, stateToBreak.getSoundType()
                .getHitSound(), SoundSource.NEUTRAL, .25f, 1);

        if (destroyProgress >= 10) {
            onBlockBroken(stateToBreak);
            destroyProgress = 0;
            ticksUntilNextProgress = -1;
            level.destroyBlockProgress(breakerId, breakingPos, -1);
            return;
        }

        ticksUntilNextProgress = (int) (blockHardness / breakSpeed);
        level.destroyBlockProgress(breakerId, breakingPos, (int) destroyProgress);
    }

    public boolean canBreak(BlockState stateToBreak, float blockHardness) {

        return isBreakable(stateToBreak, blockHardness);
    }

    public static boolean isBreakable(BlockState stateToBreak, float blockHardness) {
        return !(stateToBreak.getMaterial().isLiquid() ||
                stateToBreak.getBlock() instanceof AirBlock ||
                blockHardness == -1 ||
                DDTags.AllBlockTags.shadow_drill_immune.matches(stateToBreak)
        );
    }

    public static void silkdestroyBlock(Level world, BlockPos pos, float effectChance,
                                           Consumer<ItemStack> droppedItemCallback) {
        ItemStack iStack = Items.MILK_BUCKET.getDefaultInstance();
        iStack.enchant(Enchantments.SILK_TOUCH, 1);
        destroyBlockAs(world, pos, null, iStack, effectChance, droppedItemCallback);
    }

    public void onBlockBroken(BlockState stateToBreak) {
        Vec3 vec = VecHelper.offsetRandomly(VecHelper.getCenterOf(breakingPos), level.random, .05f);
        silkdestroyBlock(level, breakingPos, 1f, (stack) -> {
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
            itementity.setInvulnerable(true);
            itementity.setNoGravity(true);
            level.addFreshEntity(itementity);

        });
    }



    protected float getBreakSpeed() {
        return Math.abs(getSpeed() / 20);
    }

}
