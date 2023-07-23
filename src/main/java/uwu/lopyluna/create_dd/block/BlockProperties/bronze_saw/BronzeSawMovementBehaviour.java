package uwu.lopyluna.create_dd.block.BlockProperties.bronze_saw;

import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import com.simibubi.create.foundation.utility.TreeCutter;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Optional;

public class BronzeSawMovementBehaviour extends BlockBreakingMovementBehaviour {

    @Override
    public boolean isActive(MovementContext context) {
        return super.isActive(context)
                && !VecHelper.isVecPointingTowards(context.relativeMotion, context.state.getValue(BronzeSawBlock.FACING)
                .getOpposite());
    }

    @Override
    public Vec3 getActiveAreaOffset(MovementContext context) {
        return Vec3.atLowerCornerOf(context.state.getValue(BronzeSawBlock.FACING)
                        .getNormal())
                .scale(.65f);
    }

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        super.visitNewPosition(context, pos);
        Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(BronzeSawBlock.FACING)
                .getNormal());
        facingVec = context.rotation.apply(facingVec);

        Direction closestToFacing = Direction.getNearest(facingVec.x, facingVec.y, facingVec.z);
        if (closestToFacing.getAxis()
                .isVertical() && context.data.contains("BreakingPos")) {
            context.data.remove("BreakingPos");
            context.stall = false;
        }
    }

    @Override
    public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {
        return super.canBreak(world, breakingPos, state) && BronzeSawBlockEntity.isSawable(state);
    }

    @Override
    protected void onBlockBroken(MovementContext context, BlockPos pos, BlockState brokenState) {
        if (brokenState.is(BlockTags.LEAVES))
            return;

        Optional<AbstractBlockBreakQueue> dynamicTree = TreeCutter.findDynamicTree(brokenState.getBlock(), pos);
        if (dynamicTree.isPresent()) {
            dynamicTree.get()
                    .destroyBlocks(context.world, null, (stack, dropPos) -> dropItemFromCutTree(context, stack, dropPos));
            return;
        }

        TreeCutter.findTree(context.world, pos)
                .destroyBlocks(context.world, null, (stack, dropPos) -> dropItemFromCutTree(context, stack, dropPos));
    }

    public void dropItemFromCutTree(MovementContext context, BlockPos pos, ItemStack stack) {
        ItemStack remainder = ItemHandlerHelper.insertItem(context.contraption.getSharedInventory(), stack, false);
        if (remainder.isEmpty())
            return;

        Level world = context.world;
        Vec3 dropPos = VecHelper.getCenterOf(pos);
        float distance = context.position == null ? 1 : (float) dropPos.distanceTo(context.position);
        ItemEntity entity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, remainder);
        entity.setDeltaMovement(context.relativeMotion.scale(distance / 20f));
        world.addFreshEntity(entity);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                    ContraptionMatrices matrices, MultiBufferSource buffer) {
        BronzeSawRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }

    @Override
    protected boolean shouldDestroyStartBlock(BlockState stateToBreak) {
        return !TreeCutter.canDynamicTreeCutFrom(stateToBreak.getBlock());
    }

    @Override
    protected DamageSource getDamageSource() {
        return BronzeSawBlock.damageSourceSaw;
    }

    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption)
            lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption)
            lowerLimit = 2f;
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 175f, lowerLimit, 16f);
    }
}
