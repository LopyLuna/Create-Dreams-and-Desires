package uwu.lopyluna.create_dd.recipe;

import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.fan.FanProcessing.HauntingWrapper;
import com.simibubi.create.content.kinetics.fan.FanProcessing.SplashingWrapper;
import com.simibubi.create.content.kinetics.fan.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.SplashingRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.access.DDTransportedItemStack;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperHeatingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.getHeatLevelOf;

public class IndustrialFanProcessing {

    private static final DamageSource FIRE_DAMAGE_SOURCE = new DamageSource("create.fan_fire").setScalesWithDifficulty()
            .setIsFire();
    private static final DamageSource LAVA_DAMAGE_SOURCE = new DamageSource("create.fan_lava").setScalesWithDifficulty()
            .setIsFire();

    private static final RecipeWrapper RECIPE_WRAPPER = new RecipeWrapper(new ItemStackHandler(1));
    private static final SplashingWrapper SPLASHING_WRAPPER = new SplashingWrapper();
    private static final HauntingWrapper HAUNTING_WRAPPER = new HauntingWrapper();
    public static final FreezingWrapper FREEZING_WRAPPER = new FreezingWrapper();
    public static final SuperHeatingWrapper SUPERHEATING_WRAPPER = new SuperHeatingWrapper();
    
    public static boolean isWashableBronze(ItemStack stack, Level world) {
        SPLASHING_WRAPPER.setItem(0, stack);
        Optional<SplashingRecipe> recipe = AllRecipeTypes.SPLASHING.find(SPLASHING_WRAPPER, world);
        return recipe.isPresent();
    }

    public static boolean isHauntableBronze(ItemStack stack, Level world) {
        HAUNTING_WRAPPER.setItem(0, stack);
        Optional<HauntingRecipe> recipe = AllRecipeTypes.HAUNTING.find(HAUNTING_WRAPPER, world);
        return recipe.isPresent();
    }

    public static boolean isFreezable(ItemStack stack, Level world) {
        FREEZING_WRAPPER.setItem(0, stack);
        Optional<FreezingRecipe> recipe = DDRecipesTypes.FREEZING.find(FREEZING_WRAPPER, world);
        return recipe.isPresent();
    }
    public static boolean isSuperHeatable(ItemStack stack, Level world) {
        SUPERHEATING_WRAPPER.setItem(0, stack);
        Optional<SuperHeatingRecipe> recipe = DDRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, world);
        return recipe.isPresent();
    }

    public static boolean canProcessBronze(ItemEntity entity, IndustrialFanProcessing.FanType type) {
        if (entity.getPersistentData()
                .contains("CreateData")) {
            CompoundTag compound = entity.getPersistentData()
                    .getCompound("CreateData");
            if (compound.contains("Processing")) {
                CompoundTag processing = compound.getCompound("Processing");

                if (IndustrialFanProcessing.FanType.valueOf(processing.getString("FanType")) != type)
                    return type.canProcessBronze(entity.getItem(), entity.level);
                else if (processing.getInt("Time") >= 0)
                    return true;
                else if (processing.getInt("Time") == -1)
                    return false;
            }
        }
        return type.canProcessBronze(entity.getItem(), entity.level);
    }

    public static boolean applyProcessingBronze(ItemEntity entity, IndustrialFanProcessing.FanType type) {
        if (decrementProcessingTimeBronze(entity, type) != 0)
            return false;
        List<ItemStack> stacks = processBronze(entity.getItem(), type, entity.level);
        if (stacks == null)
            return false;
        if (stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        entity.setItem(stacks.remove(0));
        for (ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entity.getDeltaMovement());
            entity.level.addFreshEntity(entityIn);
        }
        return true;
    }

    private static List<ItemStack> processBronze(ItemStack stack, IndustrialFanProcessing.FanType type, Level world) {
        if (type == IndustrialFanProcessing.FanType.SPLASHING) {
            SPLASHING_WRAPPER.setItem(0, stack);
            Optional<SplashingRecipe> recipe = AllRecipeTypes.SPLASHING.find(SPLASHING_WRAPPER, world);
            return recipe.map(splashingRecipe -> RecipeApplier.applyRecipeOn(stack, splashingRecipe)).orElse(null);
        }
        if (type == IndustrialFanProcessing.FanType.HAUNTING) {
            HAUNTING_WRAPPER.setItem(0, stack);
            Optional<HauntingRecipe> recipe = AllRecipeTypes.HAUNTING.find(HAUNTING_WRAPPER, world);
            return recipe.map(hauntingRecipe -> RecipeApplier.applyRecipeOn(stack, hauntingRecipe)).orElse(null);
        }

        RECIPE_WRAPPER.setItem(0, stack);
        Optional<SmokingRecipe> smokingRecipe = world.getRecipeManager()
                .getRecipeFor(RecipeType.SMOKING, RECIPE_WRAPPER, world);

        if (type == IndustrialFanProcessing.FanType.BLASTING) {
            RECIPE_WRAPPER.setItem(0, stack);
            Optional<? extends AbstractCookingRecipe> smeltingRecipe = world.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, RECIPE_WRAPPER, world);
            if (smeltingRecipe.isEmpty()) {
                RECIPE_WRAPPER.setItem(0, stack);
                smeltingRecipe = world.getRecipeManager()
                        .getRecipeFor(RecipeType.BLASTING, RECIPE_WRAPPER, world);
            }

            if (smeltingRecipe.isPresent()) {
                if (smokingRecipe.isEmpty() || !ItemStack.isSame(smokingRecipe.get()
                                .getResultItem(),
                        smeltingRecipe.get()
                                .getResultItem())) {
                    return RecipeApplier.applyRecipeOn(stack, smeltingRecipe.get());
                }
            }

            return Collections.emptyList();
        }

        if (type == IndustrialFanProcessing.FanType.FREEZING) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = DDRecipesTypes.FREEZING.find(FREEZING_WRAPPER, world);
            return recipe.map(freezingRecipe -> RecipeApplier.applyRecipeOn(stack, freezingRecipe)).orElse(null);
        }
        if (type == IndustrialFanProcessing.FanType.SUPERHEATING) {
            SUPERHEATING_WRAPPER.setItem(0, stack);
            Optional<SuperHeatingRecipe> recipe = DDRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, world);
            return recipe.map(superHeatingRecipe -> RecipeApplier.applyRecipeOn(stack, superHeatingRecipe)).orElse(null);
        }

        if (type == IndustrialFanProcessing.FanType.SMOKING && smokingRecipe.isPresent())
            return RecipeApplier.applyRecipeOn(stack, smokingRecipe.get());

        return null;
    }

    private static int decrementProcessingTimeBronze(ItemEntity entity, IndustrialFanProcessing.FanType type) {
        CompoundTag nbt = entity.getPersistentData();

        if (!nbt.contains("CreateData"))
            nbt.put("CreateData", new CompoundTag());
        CompoundTag createData = nbt.getCompound("CreateData");

        if (!createData.contains("Processing"))
            createData.put("Processing", new CompoundTag());
        CompoundTag processing = createData.getCompound("Processing");

        if (!processing.contains("FanType") || FanType.valueOf(processing.getString("FanType")) != type) {
            processing.putString("FanType", type.name());
            int timeModifierForStackSize = ((entity.getItem()
                    .getCount() - 1) / 16) + 1;
            int processingTime =
                    (int) (AllConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            processing.putInt("Time", processingTime);
        }

        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }

    public static TransportedResult applyProcessingBronze(DDTransportedItemStack transported, Level world, IndustrialFanProcessing.FanType type) {
        TransportedResult ignore = TransportedResult.doNothing();
        if (transported.processedBy != type) {
            transported.processedBy = type;
            int timeModifierForStackSize = ((transported.stack.getCount() - 1) / 16) + 1;
            transported.processingTime = (int) (AllConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            if (!type.canProcessBronze(transported.stack, world))
                transported.processingTime = -1;
            return ignore;
        }
        if (transported.processingTime == -1)
            return ignore;
        if (transported.processingTime-- > 0)
            return ignore;

        List<ItemStack> stacks = processBronze(transported.stack, type, world);
        if (stacks == null)
            return ignore;

        List<DDTransportedItemStack> transportedStacks = new ArrayList<>();
        for (ItemStack additional : stacks) {
            DDTransportedItemStack newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedStacks.add(newTransported);
        }
        return TransportedResult.convertTo((DDTransportedItemStack) transportedStacks);
    }


    public enum FanType {
        SPLASHING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
                if (level.random.nextInt(8) != 0)
                    return;
                Vector3f color = new Color(0x0055FF).asVectorF();
                level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                        pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
                level.addParticle(ParticleTypes.SPIT, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                        pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
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

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                return isWashableBronze(stack, level);
            }
        },
        SMOKING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
                if (level.random.nextInt(8) != 0)
                    return;
                level.addParticle(ParticleTypes.POOF, pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
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

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                RECIPE_WRAPPER.setItem(0, stack);
                Optional<SmokingRecipe> recipe = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMOKING, RECIPE_WRAPPER, level);
                return recipe.isPresent();
            }
        },
        HAUNTING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
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

                    assert skeletonHorse != null;
                    skeletonHorse.deserializeNBT(serializeNBT);
                    skeletonHorse.setPos(horse.getPosition(0));
                    level.addFreshEntity(skeletonHorse);
                    horse.discard();
                }
            }

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                return isHauntableBronze(stack, level);
            }
        },
        FREEZING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
                if (level.random.nextInt(8) != 0)
                    return;
                Vector3f color = new Color(0xDDE8FF).asVectorF();
                level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                        pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
                level.addParticle(ParticleTypes.SNOWFLAKE, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                        pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            }

            @Override
            public void affectEntity(Entity entity, Level level) {
                if (level.isClientSide)
                    return;

                if (entity instanceof EnderMan || entity.getType() == EntityType.BLAZE) {
                    entity.hurt(DamageSource.FREEZE, 5);
                }

                if (entity instanceof SnowGolem snowgolem) {
                    snowgolem.heal(2);
                }

                if (entity instanceof Stray stray) {
                    stray.heal(2);
                }

                if (entity.isOnFire()) {
                    entity.clearFire();
                    level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                            SoundSource.NEUTRAL, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
                }

                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, 3, false, false));
                    livingEntity.hurt(DamageSource.FREEZE, 1f);
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

                    Stray Stray = EntityType.STRAY.create(level);
                    CompoundTag serializeNBT = skeleton.saveWithoutId(new CompoundTag());
                    serializeNBT.remove("UUID");

                    assert Stray != null;
                    Stray.deserializeNBT(serializeNBT);
                    Stray.setPos(skeleton.getPosition(0));
                    level.addFreshEntity(Stray);
                    skeleton.discard();
                }

            }


            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                return isFreezable(stack, level);
            }
        },
        SUPERHEATING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
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
            public void affectEntity(Entity entity, Level level) {
                if (level.isClientSide)
                    return;


                if (entity instanceof Blaze blaze) {
                    blaze.heal(2);
                }

                if (!entity.fireImmune()) {
                    entity.setSecondsOnFire(10);
                    entity.hurt(LAVA_DAMAGE_SOURCE, 4);
                }

            }

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                return isSuperHeatable(stack, level);
            }
        },
        BLASTING {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {
                if (level.random.nextInt(8) != 0)
                    return;
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
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

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
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
        },
        NONE {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {}

            @Override
            public void affectEntity(Entity entity, Level level) {}

            @Override
            public boolean canProcessBronze(ItemStack stack, Level level) {
                return false;
            }
        };

        public abstract boolean canProcessBronze(ItemStack stack, Level level);

        public abstract void spawnParticlesForProcessing(Level level, Vec3 pos);

        public abstract void affectEntity(Entity entity, Level level);

        public static IndustrialFanProcessing.FanType byBlock(BlockGetter reader, BlockPos pos) {
            FluidState fluidState = reader.getFluidState(pos);
            BlockState blockState = reader.getBlockState(pos);
            if (fluidState.getType() == Fluids.WATER
                    || fluidState.getType() == Fluids.FLOWING_WATER
                    || blockState.is(DDTags.AllBlockTags.splashing_type.tag))
                return IndustrialFanProcessing.FanType.SPLASHING;
            Block block = blockState.getBlock();
            if (block == Blocks.SOUL_FIRE
                    || block == Blocks.SOUL_CAMPFIRE && blockState.getOptionalValue(CampfireBlock.LIT).orElse(false)
                    || AllBlocks.LIT_BLAZE_BURNER.has(blockState) && blockState.getOptionalValue(LitBlazeBurnerBlock.FLAME_TYPE).map(flame -> flame == LitBlazeBurnerBlock.FlameType.SOUL).orElse(false)
                    || blockState.is(DDTags.AllBlockTags.haunting_type.tag))
                return IndustrialFanProcessing.FanType.HAUNTING;
            if (block == Blocks.FIRE
                    || blockState.is(BlockTags.CAMPFIRES) && blockState.getOptionalValue(CampfireBlock.LIT).orElse(false)
                    || AllBlocks.LIT_BLAZE_BURNER.has(blockState) && blockState.getOptionalValue(LitBlazeBurnerBlock.FLAME_TYPE).map(flame -> flame == LitBlazeBurnerBlock.FlameType.REGULAR).orElse(false)
                    || getHeatLevelOf(blockState) == BlazeBurnerBlock.HeatLevel.SMOULDERING
                    || blockState.is(DDTags.AllBlockTags.smoking_type.tag))
                return IndustrialFanProcessing.FanType.SMOKING;
            if (block == Blocks.LAVA
                    || getHeatLevelOf(blockState) == BlazeBurnerBlock.HeatLevel.FADING
                    || getHeatLevelOf(blockState) == BlazeBurnerBlock.HeatLevel.KINDLED
                    || blockState.is(DDTags.AllBlockTags.blasting_type.tag))
                return IndustrialFanProcessing.FanType.BLASTING;
            if (getHeatLevelOf(blockState) == BlazeBurnerBlock.HeatLevel.SEETHING
                    || blockState.is(DDTags.AllBlockTags.superheating_type.tag))
                return IndustrialFanProcessing.FanType.SUPERHEATING;
            if (block == Blocks.POWDER_SNOW
                    || blockState.is(DDTags.AllBlockTags.freezing_type.tag))
                return IndustrialFanProcessing.FanType.FREEZING;

            return IndustrialFanProcessing.FanType.NONE;
        }
    }

    public static class FreezingWrapper extends RecipeWrapper {
        public FreezingWrapper() {
            super(new ItemStackHandler(1));
        }
    }
    public static class SuperHeatingWrapper extends RecipeWrapper {
        public SuperHeatingWrapper() {
            super(new ItemStackHandler(1));
        }
    }
}
