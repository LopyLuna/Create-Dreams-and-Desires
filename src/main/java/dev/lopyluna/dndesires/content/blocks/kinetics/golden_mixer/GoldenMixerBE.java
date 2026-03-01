package dev.lopyluna.dndesires.content.blocks.kinetics.golden_mixer;

import java.util.List;
import java.util.Optional;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.content.fluids.potion.PotionMixingRecipes;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour.TankSegment;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.infrastructure.config.AllConfigs;

import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;


public class GoldenMixerBE extends BasinOperatingBlockEntity {
    private static final Object shapelessOrMixingRecipesKey = new Object();

    public int runningTicks;
    public int processingTicks;
    public boolean running;

    public GoldenMixerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public float getRenderedHeadOffset(float partialTicks) {
        int localTick;
        var offset = 0f;
        if (running) {
            if (runningTicks < ticksHigh()) {
                localTick = runningTicks;
                var num = (localTick + partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            } else if (runningTicks == ticksHigh()) {
                offset = 1;
            } else {
                localTick = ticksHigh() * 2 - runningTicks;
                var num = (localTick - partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            }
        }
        return offset + 7 / 16f;
    }

    public float getRenderedHeadRotationSpeed(float partialTicks) {
		float speed = getSpeed();
		if (running) {
			if (runningTicks < 15) {
				return speed;
			}
			if (runningTicks <= 20) {
				return speed * 2;
			}
			return speed;
		}
		return speed / 2;
	}

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.MIXER);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0, -1.5, 0);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        running = compound.getBoolean("Running");
        runningTicks = compound.getInt("Ticks");
        super.read(compound, registries, clientPacket);

        if (clientPacket && hasLevel())
            getBasin().ifPresent(bte -> bte.setAreFluidsMoving(running && runningTicks <= ticksHigh()));
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("Running", running);
        compound.putInt("Ticks", runningTicks);
        super.write(compound, registries, clientPacket);
    }

    @Override
	public void tick() {
		super.tick();

		if (runningTicks >= 40) {
			running = false;
			runningTicks = 0;
			basinChecker.scheduleUpdate();
			return;
		}

		float speed = Math.abs(getSpeed());
		if (running && level != null) {
			if (level.isClientSide && runningTicks == 20)
				renderParticles();

			if (getSpeed() == 0 || !isSpeedRequirementFulfilled()) {
				if (runningTicks < 20)
					runningTicks = 40 - runningTicks;
				else if (runningTicks == 20)
					runningTicks++;
			}

			if ((!level.isClientSide || isVirtual()) && runningTicks == 20) {
				if (processingTicks < 0) {
					float recipeSpeed = 3;
					if (currentRecipe instanceof StandardProcessingRecipe) {
						int t = ((StandardProcessingRecipe<?>) currentRecipe).getProcessingDuration();
						if (t != 0)
							recipeSpeed = t / 100f;
					}

					processingTicks = Mth.clamp((Mth.log2((int) (512 / speed))) * Mth.ceil(recipeSpeed * 15) + 1, 1, 512);

					Optional<BasinBlockEntity> basin = getBasin();
					if (basin.isPresent()) {
						Couple<SmartFluidTankBehaviour> tanks = basin.get()
							.getTanks();
						if (!tanks.getFirst()
							.isEmpty()
							|| !tanks.getSecond()
							.isEmpty())
							level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
								SoundSource.BLOCKS, .75f, speed < 65 ? .75f : 1.5f);
					}

				} else {
					processingTicks--;
					if (processingTicks == 0) {
						runningTicks++;
						processingTicks = -1;
						applyBasinRecipe();
						sendData();
					}
				}
			}

			if (runningTicks != 20)
				runningTicks++;
		}
	}

    public void renderParticles() {
        var basin = getBasin();
        if (basin.isEmpty() || level == null) return;

        for (var inv : basin.get().getInvs()) for (int slot = 0; slot < inv.getSlots(); slot++) {
            var stackInSlot = inv.getItem(slot);
            if (stackInSlot.isEmpty()) continue;
            var data = new ItemParticleOption(ParticleTypes.ITEM, stackInSlot);
            spillParticle(level, data);
        }
        for (var behaviour : basin.get().getTanks()) {
            if (behaviour == null) continue;
            for (var tankSegment : behaviour.getTanks()) {
                if (tankSegment.isEmpty(0)) continue;
                spillParticle(level, FluidFX.getFluidParticle(tankSegment.getRenderedFluid()));
            }
        }
    }

    protected void spillParticle(Level level, ParticleOptions data) {
        var angle = level.random.nextFloat() * 360;
        var offset = new Vec3(0, 0, 0.25f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        var target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y).add(0, .25f, 0);
        var center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(data, center.x, center.y - 1.75f, center.z, target.x, target.y, target.z);
    }

    @SuppressWarnings("all")
    @Override
    protected List<Recipe<?>> getMatchingRecipes() {
        List<Recipe<?>> matchingRecipes = super.getMatchingRecipes();
        
        if (!AllConfigs.server().recipes.allowBrewingInMixer.get())
            return matchingRecipes;

        var basin = getBasin();
        if (basin.isEmpty())
            return matchingRecipes;
        
        var basinBlockEntity = basin.get();
        if (basin.isEmpty())
            return matchingRecipes;

        var availableItems = level.getCapability(Capabilities.ItemHandler.BLOCK, basinBlockEntity.getBlockPos(), null);
        if (availableItems == null)
            return matchingRecipes;
        
        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty())
                continue;
            
            List<MixingRecipe> list = PotionMixingRecipes.sortRecipesByItem(level).get(stack.getItem());
            if (list == null)
                continue;
            for (MixingRecipe mixingRecipe : list)
                if (matchBasinRecipe(mixingRecipe))
                    matchingRecipes.add(mixingRecipe);
        }
        return matchingRecipes;
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe) {
        var r = recipe.value();
        return ((r instanceof CraftingRecipe && !(r instanceof ShapedRecipe)
                && AllConfigs.server().recipes.allowShapelessInMixer.get() && r.getIngredients().size() > 1
                && !MechanicalPressBlockEntity.canCompress(r)) && !AllRecipeTypes.shouldIgnoreInAutomation(recipe)
                || r.getType() == AllRecipeTypes.MIXING.getType());
    }

    @Override
    public void startProcessingBasin() {
        if (running && runningTicks <= ticksHigh()) return;
        super.startProcessingBasin();
        running = true;
        runningTicks = 0;
    }

    @Override
    public boolean continueWithPreviousRecipe() {
        runningTicks = ticksHigh();
        return true;
    }

    @Override
    protected void onBasinRemoved() {
        if (!running) return;
        runningTicks = ticksHigh() * 2;
        running = false;
    }

    public int ticksHigh() {
        return 20;
    }

    public float speedMultiplier() {
        return 2.5f;
    }

    @Override
    protected Object getRecipeCacheKey() {
        return shapelessOrMixingRecipesKey;
    }

    @Override
    protected boolean isRunning() {
        return running;
    }

    @Override
    protected Optional<CreateAdvancement> getProcessedRecipeTrigger() {
        return Optional.of(AllAdvancements.MIXER);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        var slow = Math.abs(getSpeed() * speedMultiplier()) < 65;
        if (slow && AnimationTickHolder.getTicks() % 2 == 0) return;
        if (runningTicks == ticksHigh()) AllSoundEvents.MIXING.playAt(level, worldPosition, .75f, 1, true);
    }

    @Override
    protected boolean canPropagateDiagonally(IRotate block, BlockState state) {
        return state.getBlock() instanceof ICogWheel || block instanceof ICogWheel;
    }

    @Override
    public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
        for (var offset : BlockPos.betweenClosed(-1, -1, -1, 1, 1, 1)) if (offset.distSqr(BlockPos.ZERO) == 2) neighbours.add(worldPosition.offset(offset));
        return neighbours;
    }
}
