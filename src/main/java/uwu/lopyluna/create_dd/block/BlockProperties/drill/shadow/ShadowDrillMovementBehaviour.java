package uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.content.contraptions.render.ActorInstance;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static uwu.lopyluna.create_dd.block.BlockProperties.drill.shadow.ShadowDrillBlockBreakingKineticBlockEntity.silkdestroyBlock;

public class ShadowDrillMovementBehaviour extends BlockBreakingMovementBehaviour {

    @Override
    public void tickBreaker(MovementContext context) {
        CompoundTag data = context.data;
        if (context.world.isClientSide)
            return;
        if (!data.contains("BreakingPos")) {
            context.stall = false;
            return;
        }
        if (context.relativeMotion.equals(Vec3.ZERO)) {
            context.stall = false;
            return;
        }

        int ticksUntilNextProgress = data.getInt("TicksUntilNextProgress");
        if (ticksUntilNextProgress-- > 0) {
            data.putInt("TicksUntilNextProgress", ticksUntilNextProgress);
            return;
        }

        Level world = context.world;
        BlockPos breakingPos = NbtUtils.readBlockPos(data.getCompound("BreakingPos"));
        int destroyProgress = data.getInt("Progress");
        int id = data.getInt("BreakerId");
        BlockState stateToBreak = world.getBlockState(breakingPos);
        float blockHardness = stateToBreak.getDestroySpeed(world, breakingPos);

        if (!canBreak(world, breakingPos, stateToBreak)) {
            if (destroyProgress != 0) {
                destroyProgress = 0;
                data.remove("Progress");
                data.remove("TicksUntilNextProgress");
                data.remove("BreakingPos");
                world.destroyBlockProgress(id, breakingPos, -1);
            }
            context.stall = false;
            return;
        }

        float breakSpeed = getBlockBreakingSpeed(context);
        destroyProgress += Mth.clamp((int) (breakSpeed / blockHardness), 1, 10 - destroyProgress);
        world.playSound(null, breakingPos, stateToBreak.getSoundType()
                .getHitSound(), SoundSource.NEUTRAL, .25f, 1);

        if (destroyProgress >= 10) {
            world.destroyBlockProgress(id, breakingPos, -1);

            // break falling blocks from top to bottom
            BlockPos ogPos = breakingPos;
            BlockState stateAbove = world.getBlockState(breakingPos.above());
            while (stateAbove.getBlock() instanceof FallingBlock) {
                breakingPos = breakingPos.above();
                stateAbove = world.getBlockState(breakingPos.above());
            }
            stateToBreak = world.getBlockState(breakingPos);

            context.stall = false;
            if (shouldDestroyStartBlock(stateToBreak))
                shadowDestroyBlock(context, breakingPos);
            onBlockBroken(context, ogPos, stateToBreak);
            ticksUntilNextProgress = -1;
            data.remove("Progress");
            data.remove("TicksUntilNextProgress");
            data.remove("BreakingPos");
            return;
        }

        ticksUntilNextProgress = (int) (blockHardness / breakSpeed);
        world.destroyBlockProgress(id, breakingPos, (int) destroyProgress);
        data.putInt("TicksUntilNextProgress", ticksUntilNextProgress);
        data.putInt("Progress", destroyProgress);
    }


    protected void shadowDestroyBlock(MovementContext context, BlockPos breakingPos) {
        silkdestroyBlock(context.world, breakingPos, 1f, stack -> this.dropItem(context, stack));
    }
    @Override
    protected void destroyBlock(MovementContext context, BlockPos breakingPos) {
        silkdestroyBlock(context.world, breakingPos, 1f, stack -> this.dropItem(context, stack));
    }
    @Override
    public boolean isActive(MovementContext context) {
        return super.isActive(context)
                && !VecHelper.isVecPointingTowards(context.relativeMotion, context.state.getValue(ShadowDrillBlock.FACING)
                .getOpposite());
    }

    @Override
    public Vec3 getActiveAreaOffset(MovementContext context) {
        return Vec3.atLowerCornerOf(context.state.getValue(ShadowDrillBlock.FACING)
                .getNormal()).scale(.65f);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                    ContraptionMatrices matrices, MultiBufferSource buffer) {
        if (!ContraptionRenderDispatcher.canInstance())
            ShadowDrillRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }

    @Override
    public boolean hasSpecialInstancedRendering() {
        return true;
    }

    @Nullable
    @Override
    public ActorInstance createInstance(MaterialManager materialManager, VirtualRenderWorld simulationWorld, MovementContext context) {
        return new ShadowDrillActorInstance(materialManager, simulationWorld, context);
    }

    @Override
    protected DamageSource getDamageSource() {
        return ShadowDrillBlock.damageSourceDrill;
    }


    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption)
            lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption)
            lowerLimit = 2f;
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 75f, lowerLimit, 16f);
    }

    @Override
    public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {
        return super.canBreak(world, breakingPos, state) && !state.getCollisionShape(world, breakingPos)
                .isEmpty() && !AllTags.AllBlockTags.TRACKS.matches(state);
    }

}