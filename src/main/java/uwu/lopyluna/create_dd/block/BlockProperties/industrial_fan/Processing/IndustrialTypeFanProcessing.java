package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing;

import com.mojang.math.Vector3f;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import com.simibubi.create.content.trains.CubeParticleData;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.VecHelper;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.recipe.DDRecipesTypes;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IndustrialTypeFanProcessing {
    public static final NoneType NONE = register("none", new NoneType());
    public static final BlastingType BLASTING = register("blasting", new BlastingType());
    public static final HauntingType HAUNTING = register("haunting", new HauntingType());
    public static final SmokingType SMOKING = register("smoking", new SmokingType());
    public static final SplashingType SPLASHING = register("splashing", new SplashingType());
    public static final FreezingType FREEZING = register("freezing", new FreezingType());
    public static final SuperheatingType SUPERHEATING = register("superheating", new SuperheatingType());

    private static final Map<String, InterfaceIndustrialProcessingType> LEGACY_NAME_MAP;

    static {
        Object2ReferenceOpenHashMap<String, InterfaceIndustrialProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("NONE", NONE);
        map.put("BLASTING", BLASTING);
        map.put("HAUNTING", HAUNTING);
        map.put("SMOKING", SMOKING);
        map.put("SPLASHING", SPLASHING);
        map.put("FREEZING", FREEZING);
        map.put("SUPERHEATING", SUPERHEATING);
        map.trim();
        LEGACY_NAME_MAP = map;
    }

    private static <T extends InterfaceIndustrialProcessingType> T register(String id, T type) {
        DDFanProcessingTypeRegistry.register(DDCreate.asResource(id), type);
        return type;
    }

    @Nullable
    public static InterfaceIndustrialProcessingType ofLegacyName(String name) {
        return LEGACY_NAME_MAP.get(name);
    }

    public static InterfaceIndustrialProcessingType parseLegacy(String str) {
        InterfaceIndustrialProcessingType type = ofLegacyName(str);
        if (type != null) {
            return type;
        }
        return InterfaceIndustrialProcessingType.parse(str);
    }

    public static class NoneType implements InterfaceIndustrialProcessingType {
        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            return true;
        }

        @Override
        public int getPriority() {
            return -1000000;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            return false;
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            return null;
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
        }
    }

    public static class SuperheatingType implements InterfaceIndustrialProcessingType {
        private static final SuperheatingRecipe.SuperheatingWrapper SUPERHEATING_WRAPPER = new SuperheatingRecipe.SuperheatingWrapper();

        private static final DamageSource LAVA_DAMAGE_SOURCE = new DamageSource("create.fan_super_lava").setScalesWithDifficulty()
                .setIsFire();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (DDTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SUPERHEATING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (DDTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SUPERHEATING.matches(blockState)) {
                if (blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) && !blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 500;
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
            Vector3f color = new Color(0x1e0f3d).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x64C9FD, 0x3f74e8, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.SOUL_FIRE_FLAME,  .125f);
            Vector3f colorBright = new Color(0x64C9FD).asVectorF();
            Vector3f colorDark = new Color(0x3f74e8).asVectorF();
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorBright, 1)), .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorDark, 1)), .125f);
            if (random.nextFloat() < 1 / 48f)
                particleAccess.spawnExtraParticle(ParticleTypes.SMOKE, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new CubeParticleData(192, 122, 85, 0.075f, 10, true)), .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new CubeParticleData(191, 82, 91, 0.1f, 10, true)), .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide)
                return;

            if (entity instanceof Blaze blaze) {
                blaze.heal(4);
            }

            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(10);
                entity.hurt(LAVA_DAMAGE_SOURCE, 10);
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 0, false, false));
            }
        }
    }


    public static class BlastingType implements InterfaceIndustrialProcessingType {
        private static final RecipeWrapper RECIPE_WRAPPER = new RecipeWrapper(new ItemStackHandler(1));
        private static final DamageSource LAVA_DAMAGE_SOURCE = new DamageSource("create.fan_lava").setScalesWithDifficulty()
                .setIsFire();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_BLASTING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_BLASTING.matches(blockState)) {
                if (blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) && !blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 100;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            RECIPE_WRAPPER.setItem(0, stack);
            Optional<SmeltingRecipe> smeltingRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, RECIPE_WRAPPER, level);

            if (smeltingRecipe.isPresent())
                return true;

            RECIPE_WRAPPER.setItem(0, stack);
            Optional<BlastingRecipe> blastingRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.BLASTING, RECIPE_WRAPPER, level);

            if (blastingRecipe.isPresent())
                return true;

            return !stack.getItem()
                    .isFireResistant();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            RECIPE_WRAPPER.setItem(0, stack);
            Optional<SmokingRecipe> smokingRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMOKING, RECIPE_WRAPPER, level);

            RECIPE_WRAPPER.setItem(0, stack);
            Optional<? extends AbstractCookingRecipe> smeltingRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, RECIPE_WRAPPER, level);
            if (!smeltingRecipe.isPresent()) {
                RECIPE_WRAPPER.setItem(0, stack);
                smeltingRecipe = level.getRecipeManager()
                        .getRecipeFor(RecipeType.BLASTING, RECIPE_WRAPPER, level);
            }

            if (smeltingRecipe.isPresent()) {
                if (!smokingRecipe.isPresent() || !ItemStack.isSame(smokingRecipe.get()
                                .getResultItem(),
                        smeltingRecipe.get()
                                .getResultItem())) {
                    return RecipeApplier.applyRecipeOn(stack, smeltingRecipe.get());
                }
            }

            return Collections.emptyList();
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xFF4400, 0xFF8855, random.nextFloat()));
            particleAccess.setAlpha(.5f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.FLAME, .25f);
            if (random.nextFloat() < 1 / 16f)
                particleAccess.spawnExtraParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.LAVA.defaultBlockState()), .25f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide)
                return;

            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(10);
                entity.hurt(LAVA_DAMAGE_SOURCE, 4);
            }
        }
    }

    public static class HauntingType implements InterfaceIndustrialProcessingType {
        private static final HauntingRecipe.HauntingWrapper HAUNTING_WRAPPER = new HauntingRecipe.HauntingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_HAUNTING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_HAUNTING.matches(blockState)) {
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
            HAUNTING_WRAPPER.setItem(0, stack);
            Optional<HauntingRecipe> recipe = AllRecipeTypes.HAUNTING.find(HAUNTING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            HAUNTING_WRAPPER.setItem(0, stack);
            Optional<HauntingRecipe> recipe = AllRecipeTypes.HAUNTING.find(HAUNTING_WRAPPER, level);
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
                        .getInt("CreateHaunting");
                if (progress < 100) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 100f);
                    }
                    horse.getPersistentData()
                            .putInt("CreateHaunting", progress + 1);
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

    public static class SmokingType implements InterfaceIndustrialProcessingType {
        private static final RecipeWrapper RECIPE_WRAPPER = new RecipeWrapper(new ItemStackHandler(1));
        private static final DamageSource FIRE_DAMAGE_SOURCE = new DamageSource("create.fan_fire").setScalesWithDifficulty()
                .setIsFire();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SMOKING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SMOKING.matches(blockState)) {
                if (blockState.is(BlockTags.CAMPFIRES) && blockState.hasProperty(CampfireBlock.LIT) && !blockState.getValue(CampfireBlock.LIT)) {
                    return false;
                }
                if (blockState.hasProperty(LitBlazeBurnerBlock.FLAME_TYPE) && blockState.getValue(LitBlazeBurnerBlock.FLAME_TYPE) != LitBlazeBurnerBlock.FlameType.REGULAR) {
                    return false;
                }
                if (blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) && blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.SMOULDERING) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 200;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            RECIPE_WRAPPER.setItem(0, stack);
            Optional<SmokingRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMOKING, RECIPE_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            RECIPE_WRAPPER.setItem(0, stack);
            Optional<SmokingRecipe> smokingRecipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMOKING, RECIPE_WRAPPER, level);

            if (smokingRecipe.isPresent())
                return RecipeApplier.applyRecipeOn(stack, smokingRecipe.get());

            return null;
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            level.addParticle(ParticleTypes.POOF, pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x0, 0x555555, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.SMOKE, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.LARGE_SMOKE, .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide)
                return;

            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(2);
                entity.hurt(FIRE_DAMAGE_SOURCE, 2);
            }
        }
    }

    public static class SplashingType implements InterfaceIndustrialProcessingType {
        private static final SplashingRecipe.SplashingWrapper SPLASHING_WRAPPER = new SplashingRecipe.SplashingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SPLASHING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (AllTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SPLASHING.matches(blockState)) {
                return true;
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 400;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            SPLASHING_WRAPPER.setItem(0, stack);
            Optional<SplashingRecipe> recipe = AllRecipeTypes.SPLASHING.find(SPLASHING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            SPLASHING_WRAPPER.setItem(0, stack);
            Optional<SplashingRecipe> recipe = AllRecipeTypes.SPLASHING.find(SPLASHING_WRAPPER, level);
            if (recipe.isPresent())
                return RecipeApplier.applyRecipeOn(stack, recipe.get());
            return null;
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0x0055FF).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SPIT, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x4499FF, 0x2277FF, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE_POP, .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide)
                return;

            if (entity instanceof EnderMan || entity.getType() == EntityType.SNOW_GOLEM
                    || entity.getType() == EntityType.BLAZE) {
                entity.hurt(DamageSource.DROWN, 2);
            }
            if (entity.isOnFire()) {
                entity.clearFire();
                level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                        SoundSource.NEUTRAL, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            }
        }
    }


    public static class FreezingType implements InterfaceIndustrialProcessingType {
        private static final FreezingRecipe.FreezingWrapper FREEZING_WRAPPER = new FreezingRecipe.FreezingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (DDTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_FREEZING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (DDTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.matches(blockState)) {
                return true;
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 600;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = DDRecipesTypes.FREEZING.find(FREEZING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = DDRecipesTypes.FREEZING.find(FREEZING_WRAPPER, level);
            if (recipe.isPresent())
                return RecipeApplier.applyRecipeOn(stack, recipe.get());
            return null;
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0xDDE8FF).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SNOWFLAKE, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xEEEEFF, 0xDDE8FF, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 128f)
                particleAccess.spawnExtraParticle(ParticleTypes.SNOWFLAKE, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.POOF, .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide) {

                if (entity instanceof Skeleton) {
                    Vec3 p = entity.getPosition(0);
                    Vec3 v = p.add(0, 0.5f, 0)
                            .add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                                    .multiply(1, 0.2f, 1)
                                    .normalize()
                                    .scale(1f));
                    level.addParticle(ParticleTypes.SNOWFLAKE, v.x, v.y, v.z, 0, 0.1f, 0);
                    if (level.random.nextInt(3) == 0)
                        level.addParticle(ParticleTypes.SNOWFLAKE, p.x, p.y + .5f, p.z,
                                (level.random.nextFloat() - .5f) * .5f, 0.1f, (level.random.nextFloat() - .5f) * .5f);
                }
                return;
            }

            if (entity instanceof EnderMan || entity.getType() == EntityType.BLAZE) {
                entity.hurt(DamageSource.FREEZE, 8);
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 7, false, false));
            }

            if (entity instanceof SnowGolem snowgolem) {
                snowgolem.heal(4);
            }

            if (entity instanceof Stray stray) {
                stray.heal(2);
            }

            if (entity.isOnFire()) {
                entity.clearFire();
                level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                        SoundSource.NEUTRAL, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            }

            if (entity instanceof Skeleton skeleton) {
                int progress = skeleton.getPersistentData()
                        .getInt("CreateFreezing");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.STRAY_AMBIENT, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 50f);
                    }
                    skeleton.getPersistentData()
                            .putInt("CreateFreezing", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.SKELETON_CONVERTED_TO_STRAY,
                        SoundSource.NEUTRAL, 1.25f, 0.65f);

                Stray stray = EntityType.STRAY.create(level);
                CompoundTag serializeNBT = skeleton.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert stray != null;
                stray.deserializeNBT(serializeNBT);
                stray.setPos(skeleton.getPosition(0));
                level.addFreshEntity(stray);
                skeleton.discard();
            }
        }
    }




    public static void register() {
    }
}
