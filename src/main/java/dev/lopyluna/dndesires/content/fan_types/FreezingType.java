package dev.lopyluna.dndesires.content.fan_types;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezingType implements FanProcessingType {
    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        var fluidState = level.getFluidState(pos);
        if (DesiresTags.FluidTags.FAN_PROCESSING_CATALYSTS_FREEZING.is(fluidState)) return true;
        var blockState = level.getBlockState(pos);
        return DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.is(blockState);
    }

    @Override
    public int getPriority() {
        return 1100;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        return DesiresRecipeTypes.FREEZING.find(new SingleRecipeInput(stack), level).isPresent();
    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        return DesiresRecipeTypes.FREEZING.find(new SingleRecipeInput(stack), level).map(recipeRecipeHolder -> RecipeApplier.applyRecipeOn(level, stack, recipeRecipeHolder.value(), true)).orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        var color = new Color(0xDDE8FF).asVectorF();
        level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        level.addParticle(ParticleTypes.SNOWFLAKE, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0xEEEEFF, 0xDDE8FF, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 128f) particleAccess.spawnExtraParticle(ParticleTypes.SNOWFLAKE, .125f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle(ParticleTypes.POOF, .125f);

    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        entity.setIsInPowderSnow(true);
        if (level.isClientSide) return;
        if (entity.canFreeze()) {
            int i = entity.getTicksFrozen();
            entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() + 3, i + 3));
        }
        if (entity instanceof EnderMan || entity instanceof Blaze) entity.hurt(entity.damageSources().freeze(), 8);

        if (entity.isOnFire()) {
            entity.clearFire();
            level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.NEUTRAL, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
        }
    }
}
