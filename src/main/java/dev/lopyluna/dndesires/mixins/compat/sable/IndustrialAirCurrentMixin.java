package dev.lopyluna.dndesires.mixins.compat.sable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialAirCurrent;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.ref.WeakReference;

@Mixin(IndustrialAirCurrent.class)
public abstract class IndustrialAirCurrentMixin extends AirCurrent {
    public IndustrialAirCurrentMixin(IAirCurrentSource source) { super(source); }

    @Unique private WeakReference<SubLevel> sable$subLevelReference;

    @Override
    public void tick() {
        if (this.sable$subLevelReference == null) this.sable$subLevelReference = new WeakReference<>(Sable.HELPER.getContaining(this.source.getAirCurrentWorld(), this.source.getAirCurrentPos()));
        super.tick();
    }

    @Redirect(method = "tickAffectedEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
    public AABB sable$reverseProjectEntityBB(final Entity instance) {
        final SubLevel subLevel = this.sable$subLevelReference.get();
        if (subLevel != null) return new BoundingBox3d(instance.getBoundingBox()).transformInverse(subLevel.logicalPose(), new BoundingBox3d()).toMojang();
        return instance.getBoundingBox();
    }

    @WrapOperation(method = "tickAffectedEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    public void sable$transformFlowVector(final Entity instance, final Vec3 vec3, final Operation<Void> original, @Local(name = "flow") final Vec3i flow, @Local(name = "acceleration") final float acceleration, @Local(name = "previousMotion") final Vec3 previousMotion) {
        final SubLevel subLevel = this.sable$subLevelReference.get();
        if (subLevel != null) {
            final Vector3d nonIntFlow = JOMLConversion.atLowerCornerOf(flow);
            subLevel.logicalPose().transformNormal(nonIntFlow);

            final double xIn = Mth.clamp((double) ((float) nonIntFlow.get(0) * acceleration) - previousMotion.x, -5, 5);
            final double yIn = Mth.clamp((double) ((float) nonIntFlow.get(1) * acceleration) - previousMotion.y, -5, 5);
            final double zIn = Mth.clamp((double) ((float) nonIntFlow.get(2) * acceleration) - previousMotion.z, -5, 5);

            original.call(instance, previousMotion.add(new Vec3(xIn, yIn, zIn).scale(0.125f)));
            return;
        }

        original.call(instance, vec3);
    }

    @Redirect(method = "tickAffectedEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;position()Lnet/minecraft/world/phys/Vec3;"))
    public Vec3 sable$reverseProjectAllPositions(final Entity instance) {
        final SubLevel subLevel = this.sable$subLevelReference.get();
        if (subLevel != null) return subLevel.logicalPose().transformPositionInverse(instance.position());
        return instance.position();
    }
}
