package dev.lopyluna.dndesires.content.fan_types;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.trains.CubeParticleData;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeethingType implements FanProcessingType {
    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        var fluidState = level.getFluidState(pos);
        if (DesiresTags.FluidTags.FAN_PROCESSING_CATALYSTS_SEETHING.is(fluidState)) return true;
        var blockState = level.getBlockState(pos);
        if (DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.is(blockState))
            return !blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) || blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING);
        return false;
    }

    @Override
    public int getPriority() {
        return 1200;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        return DesiresRecipeTypes.SEETHING.find(new SingleRecipeInput(stack), level).isPresent();
    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        return DesiresRecipeTypes.SEETHING.find(new SingleRecipeInput(stack), level).map(recipeRecipeHolder -> RecipeApplier.applyRecipeOn(level, stack, recipeRecipeHolder)).orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        var color = new Color(0x1e0f3d).asVectorF();
        level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0x64C9FD, 0x3f74e8, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle(ParticleTypes.SOUL_FIRE_FLAME,  .125f);
        var colorBright = new Color(0x64C9FD).asVectorF();
        var colorDark = new Color(0x3f74e8).asVectorF();
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle((new DustParticleOptions(colorBright, 1)), .125f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle((new DustParticleOptions(colorDark, 1)), .125f);
        if (random.nextFloat() < 1 / 48f) particleAccess.spawnExtraParticle(ParticleTypes.SMOKE, .125f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle((new CubeParticleData(192, 122, 85, 0.075f, 10, true)), .125f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle((new CubeParticleData(191, 82, 91, 0.1f, 10, true)), .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (entity instanceof Blaze blaze) blaze.heal(2);
        if (!entity.fireImmune()) entity.lavaHurt();
    }
}
