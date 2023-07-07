package uwu.lopyluna.create_dd.recipes;

import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.FanProcessing;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.Color;
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
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
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
import uwu.lopyluna.create_dd.access.DDTransportedItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.getHeatLevelOf;

public class BakingFanProcessing {

    public static final FreezingWrapper FREEZING_WRAPPER = new FreezingWrapper();
    public static final SuperHeatingWrapper SUPERHEATING_WRAPPER = new SuperHeatingWrapper();

    public static boolean canProcess(ItemEntity entity, Type type) {
        if (entity.getPersistentData()
                .contains("CreateData")) {
            CompoundTag compound = entity.getPersistentData()
                    .getCompound("CreateData");
            if (compound.contains("Processing")) {
                CompoundTag processing = compound.getCompound("Processing");

                if (Type.valueOf(processing.getString("Type")) != type)
                    return type.canProcess(entity.getItem(), entity.level);
                else if (processing.getInt("Time") >= 0)
                    return true;
                else if (processing.getInt("Time") == -1)
                    return false;
            }
        }
        return type.canProcess(entity.getItem(), entity.level);
    }

    public static boolean isFreezable(ItemStack stack, Level world) {
        FREEZING_WRAPPER.setItem(0, stack);
        Optional<FreezingRecipe> recipe = BakingRecipesTypes.FREEZING.find(FREEZING_WRAPPER, world);
        return recipe.isPresent();
    }
    public static boolean isSuperHeatable(ItemStack stack, Level world) {
        SUPERHEATING_WRAPPER.setItem(0, stack);
        Optional<SuperHeatingRecipe> recipe = BakingRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, world);
        return recipe.isPresent();
    }


    public static boolean applyProcessing(ItemEntity entity, Type type) {
        if (decrementProcessingTime(entity, type) != 0)
            return false;
        List<ItemStack> stacks = process(entity.getItem(), type, entity.level);
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

    public static TransportedItemStackHandlerBehaviour.TransportedResult applyProcessing(DDTransportedItemStack transported, Level world, Type type) {
        TransportedItemStackHandlerBehaviour.TransportedResult ignore = TransportedItemStackHandlerBehaviour.TransportedResult.doNothing();
        if (transported.processedBy != type) {
            transported.processedBy = type;
            int timeModifierForStackSize = ((transported.stack.getCount() - 1) / 16) + 1;
            int processingTime =
                    (int) (AllConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize) + 1;
            transported.processingTime = processingTime;
            if (!type.canProcess(transported.stack, world))
                transported.processingTime = -1;
            return ignore;
        }
        if (transported.processingTime == -1)
            return ignore;
        if (transported.processingTime-- > 0)
            return ignore;

        List<ItemStack> stacks = process(transported.stack, type, world);
        if (stacks == null)
            return ignore;

        List<TransportedItemStack> transportedStacks = new ArrayList<>();
        for (ItemStack additional : stacks) {
            TransportedItemStack newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedStacks.add(newTransported);
        }
        return TransportedItemStackHandlerBehaviour.TransportedResult.convertTo(transportedStacks);
    }

    private static List<ItemStack> process(ItemStack stack, Type type, Level world) {
        if (type == Type.FREEZING) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = BakingRecipesTypes.FREEZING.find(FREEZING_WRAPPER, world);
            if (recipe.isPresent())
                return RecipeApplier.applyRecipeOn(stack, recipe.get());
            return null;
        }
        if (type == Type.SUPERHEATING) {
            SUPERHEATING_WRAPPER.setItem(0, stack);
            Optional<SuperHeatingRecipe> recipe = BakingRecipesTypes.SUPERHEATING.find(SUPERHEATING_WRAPPER, world);
            if (recipe.isPresent())
                return RecipeApplier.applyRecipeOn(stack, recipe.get());
            return null;
        }
        return null;
    }

    private static int decrementProcessingTime(ItemEntity entity, Type type) {
        CompoundTag nbt = entity.getPersistentData();

        if (!nbt.contains("CreateData"))
            nbt.put("CreateData", new CompoundTag());
        CompoundTag createData = nbt.getCompound("CreateData");

        if (!createData.contains("Processing"))
            createData.put("Processing", new CompoundTag());
        CompoundTag processing = createData.getCompound("Processing");

        if (!processing.contains("Type") || Type.valueOf(processing.getString("Type")) != type) {
            processing.putString("Type", type.name());
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


    public enum Type {
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

                entity.canFreeze();
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

                    Stray.deserializeNBT(serializeNBT);
                    Stray.setPos(skeleton.getPosition(0));
                    level.addFreshEntity(Stray);
                    skeleton.discard();
                }

            }


            @Override
            public boolean canProcess(ItemStack stack, Level level) {
                return isFreezable(stack, level);
            }
        },
        SUPERHEATING {
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

            }
            

            @Override
            public boolean canProcess(ItemStack stack, Level level) {
                return isSuperHeatable(stack, level);
            }
        },
        NONE {
            @Override
            public void spawnParticlesForProcessing(Level level, Vec3 pos) {}

            @Override
            public void affectEntity(Entity entity, Level level) {}

            @Override
            public boolean canProcess(ItemStack stack, Level level) {
                return false;
            }
        };

        public abstract boolean canProcess(ItemStack stack, Level level);

        public abstract void spawnParticlesForProcessing(Level level, Vec3 pos);

        public abstract void affectEntity(Entity entity, Level level);

        public static Type byBlock(BlockGetter reader, BlockPos pos) {
            BlockState blockState = reader.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block == Blocks.POWDER_SNOW)
                return Type.FREEZING;
            if (getHeatLevelOf(blockState).isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING))
                return Type.SUPERHEATING;

            return Type.NONE;
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
