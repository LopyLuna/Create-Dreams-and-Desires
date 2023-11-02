package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.fan_type;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing.InterfaceIndustrialProcessingType;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

import java.util.List;
import java.util.Optional;

public class SuperheatingType implements InterfaceIndustrialProcessingType {
    private static final SuperheatingRecipe.SuperheatingWrapper SUPERHEATING_WRAPPER = new SuperheatingRecipe.SuperheatingWrapper();

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        if (DDTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SUPERHEATING.matches(fluidState)) {
            return true;
        }
        BlockState blockState = level.getBlockState(pos);
        if (DDTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SUPERHEATING.matches(blockState)) {
            if (blockState.is(BlockTags.CAMPFIRES) && blockState.hasProperty(CampfireBlock.LIT) && !blockState.getValue(CampfireBlock.LIT)) {
                return false;
            }
            if (blockState.hasProperty(LitBlazeBurnerBlock.FLAME_TYPE) && blockState.getValue(LitBlazeBurnerBlock.FLAME_TYPE) != LitBlazeBurnerBlock.FlameType.SOUL) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        SUPERHEATING_WRAPPER.setItem(0, stack);
        Optional<SuperheatingRecipe> recipe = DDRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, level);
        return recipe.isPresent();
    }

    @Override
    @Nullable
    public List<ItemStack> process(ItemStack stack, Level level) {
        SUPERHEATING_WRAPPER.setItem(0, stack);
        Optional<SuperheatingRecipe> recipe = DDRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, level);
        if (recipe.isPresent())
            return RecipeApplier.applyRecipeOn(stack, recipe.get());
        return null;
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0)
            return;
        pos = pos.add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                .multiply(1, 0.05f, 1)
                .normalize()
                .scale(0.15f));
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
        if (level.random.nextInt(2) == 0)
            level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y + .25f, pos.z, 0, 0, 0);
    }

    @Override
    public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0x0, 0x126568, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 128f)
            particleAccess.spawnExtraParticle(ParticleTypes.SOUL_FIRE_FLAME, .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.SMOKE, .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide) {
            if (entity instanceof Horse) {
                Vec3 p = entity.getPosition(0);
                Vec3 v = p.add(0, 0.5f, 0)
                        .add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                                .multiply(1, 0.2f, 1)
                                .normalize()
                                .scale(1f));
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v.x, v.y, v.z, 0, 0.1f, 0);
                if (level.random.nextInt(3) == 0)
                    level.addParticle(ParticleTypes.LARGE_SMOKE, p.x, p.y + .5f, p.z,
                            (level.random.nextFloat() - .5f) * .5f, 0.1f, (level.random.nextFloat() - .5f) * .5f);
            }
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 0, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1, false, false));
        }
        if (entity instanceof Horse horse) {
            int progress = horse.getPersistentData()
                    .getInt("CreateSuperheating");
            if (progress < 100) {
                if (progress % 10 == 0) {
                    level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.NEUTRAL,
                            1f, 1.5f * progress / 100f);
                }
                horse.getPersistentData()
                        .putInt("CreateSuperheating", progress + 1);
                return;
            }

            level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                    SoundSource.NEUTRAL, 1.25f, 0.65f);

            SkeletonHorse skeletonHorse = EntityType.SKELETON_HORSE.create(level);
            CompoundTag serializeNBT = horse.saveWithoutId(new CompoundTag());
            serializeNBT.remove("UUID");
            if (!horse.getArmor()
                    .isEmpty())
                horse.spawnAtLocation(horse.getArmor());

            skeletonHorse.deserializeNBT(serializeNBT);
            skeletonHorse.setPos(horse.getPosition(0));
            level.addFreshEntity(skeletonHorse);
            horse.discard();
        }
    }
}
