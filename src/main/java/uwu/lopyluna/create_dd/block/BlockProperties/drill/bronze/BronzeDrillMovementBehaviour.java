package uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze;

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
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_dd.DDTags;

import javax.annotation.Nullable;

public class BronzeDrillMovementBehaviour extends BlockBreakingMovementBehaviour {

    @Override
    public boolean isActive(MovementContext context) {
        return super.isActive(context)
                && !VecHelper.isVecPointingTowards(context.relativeMotion, context.state.getValue(BronzeDrillBlock.FACING)
                .getOpposite());
    }

    @Override
    public Vec3 getActiveAreaOffset(MovementContext context) {
        return Vec3.atLowerCornerOf(context.state.getValue(BronzeDrillBlock.FACING)
                .getNormal()).scale(.65f);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                    ContraptionMatrices matrices, MultiBufferSource buffer) {
        if (!ContraptionRenderDispatcher.canInstance())
            BronzeDrillRenderer.renderInContraption(context, renderWorld, matrices, buffer);
    }

    @Override
    public boolean hasSpecialInstancedRendering() {
        return true;
    }

    @Nullable
    @Override
    public ActorInstance createInstance(MaterialManager materialManager, VirtualRenderWorld simulationWorld, MovementContext context) {
        return new BronzeDrillActorInstance(materialManager, simulationWorld, context);
    }

    @Override
    protected DamageSource getDamageSource() {
        return BronzeDrillBlock.damageSourceDrill;
    }

    @Override
    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption)
            lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption)
            lowerLimit = 2f;
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 200f, lowerLimit, 16f);
    }

    @Override
    public boolean canBreak(Level world, BlockPos breakingPos, BlockState state) {
        return super.canBreak(world, breakingPos, state) && !state.getCollisionShape(world, breakingPos)
                .isEmpty() && !AllTags.AllBlockTags.TRACKS.matches(state) && !DDTags.AllBlockTags.bronze_drill_immune.matches(state);
    }

}