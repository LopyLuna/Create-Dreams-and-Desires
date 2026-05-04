package dev.lopyluna.dndesires.compat.sable;

import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.DesiresFanProcessingTypes;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FanSailParticles {
    private static final int TRACK_TTL = 80;
    private static final Map<String, TrackedFanSail> TRACKED = new HashMap<>();

    public static void track(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) return;
        if (getProcessingType(state) == null) return;
        if (Sable.HELPER.getContaining(level, pos) == null) return;

        TRACKED.put(key(level, pos), new TrackedFanSail(level, pos.immutable(), state, TRACK_TTL));
    }

    public static void tick() {
        final Iterator<TrackedFanSail> iterator = TRACKED.values().iterator();
        while (iterator.hasNext()) {
            final TrackedFanSail tracked = iterator.next();
            final Level level = tracked.level().get();
            if (level == null || !level.isClientSide || tracked.ttl() <= 0) {
                iterator.remove();
                continue;
            }

            tracked.decrementTtl();
            spawn(level, tracked.pos(), tracked.state(), level.random);
        }
    }

    public static void clear() {
        TRACKED.clear();
    }

    private static void spawn(Level level, BlockPos pos, BlockState state, RandomSource random) {
        if (random.nextFloat() >= 0.25) return;
        final FanProcessingType type = getProcessingType(state);
        if (type == null) return;

        final SubLevel subLevel = Sable.HELPER.getContaining(level, pos);
        if (subLevel == null) return;

        final double subLevelSpeed = Sable.HELPER.getVelocity(level, VecHelper.getCenterOf(pos)).length();
        final int morphPasses = Mth.clamp(Mth.ceil(subLevelSpeed * 0.5), 0, 10);
        if (morphPasses <= 0) return;

        final var localCenter = VecHelper.getCenterOf(pos);

        for (int i = 0; i < morphPasses; i++) {
            final var localOffset = VecHelper.offsetRandomly(net.minecraft.world.phys.Vec3.ZERO, random, .6f);
            final var spawnPos = Sable.HELPER.projectOutOfSubLevel(level, localCenter.add(localOffset));

            final class ParticleAccess implements FanProcessingType.AirFlowParticleAccess {
                private int color = 0xEEEEEE;
                private float alpha = 0.25f;

                @Override
                public void setColor(int color) {
                    this.color = color;
                }

                @Override
                public void setAlpha(float alpha) {
                    this.alpha = alpha;
                }

                @Override
                public void spawnExtraParticle(ParticleOptions options, float speedMultiplier) {
                    if (options instanceof BlockParticleOption) return;
                    level.addParticle(options, spawnPos.x, spawnPos.y, spawnPos.z, 0, 0, 0);
                }

                private void spawnMainParticle() {
                    final float clampedAlpha = Mth.clamp(alpha, 0f, 1f);
                    if (clampedAlpha <= 0f || random.nextFloat() > clampedAlpha) return;
                    level.addParticle(new DustParticleOptions(new Color(color).asVectorF(), 0.75f + clampedAlpha * 0.25f),
                            spawnPos.x, spawnPos.y, spawnPos.z, 0, 0, 0);
                }
            }

            final ParticleAccess particleAccess = new ParticleAccess();
            type.morphAirFlow(particleAccess, random);
            particleAccess.spawnMainParticle();
        }
    }

    private static @Nullable FanProcessingType getProcessingType(BlockState state) {
        final var block = state.getBlock();
        if (DesiresBlocks.SPLASHING_SAIL.is(block)) return AllFanProcessingTypes.SPLASHING;
        if (DesiresBlocks.HAUNTING_SAIL.is(block)) return AllFanProcessingTypes.HAUNTING;
        if (DesiresBlocks.SMOKING_SAIL.is(block)) return AllFanProcessingTypes.SMOKING;
        if (DesiresBlocks.BLASTING_SAIL.is(block)) return AllFanProcessingTypes.BLASTING;
        if (DesiresBlocks.SEETHING_SAIL.is(block)) return DesiresFanProcessingTypes.SEETHING_TYPE;
        if (DesiresBlocks.FREEZING_SAIL.is(block)) return DesiresFanProcessingTypes.FREEZING_TYPE;
        if (DesiresBlocks.SANDING_SAIL.is(block)) return DesiresFanProcessingTypes.SANDING_TYPE;
        if (DesiresBlocks.DRAGON_BREATHING_SAIL.is(block)) return DesiresFanProcessingTypes.DRAGON_BREATHING_TYPE;
        return null;
    }

    private static String key(Level level, BlockPos pos) {
        return System.identityHashCode(level) + ":" + pos.asLong();
    }

    private record TrackedFanSail(WeakReference<Level> level, BlockPos pos, BlockState state, int[] ttlBox) {
        private TrackedFanSail(Level level, BlockPos pos, BlockState state, int ttl) {
            this(new WeakReference<>(level), pos, state, new int[]{ttl});
        }

        private int ttl() {
            return ttlBox[0];
        }

        private void decrementTtl() {
            ttlBox[0]--;
        }
    }
}