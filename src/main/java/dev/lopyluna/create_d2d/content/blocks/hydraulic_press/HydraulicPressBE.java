package dev.lopyluna.create_d2d.content.blocks.hydraulic_press;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.lopyluna.create_d2d.register.DesiresBETypes;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class HydraulicPressBE extends BasinOperatingBlockEntity implements HydraulicPressingBehavior.PressingBehaviourSpecifics {

    private static final Object compressingRecipesKey = new Object();
    public HydraulicPressingBehavior pressingBehaviour;
    public SmartFluidTankBehaviour tank;

    public HydraulicPressBE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        var mb = CreateLang.translate("generic.unit.millibuckets");
        CreateLang.translate("gui.goggles.fluid_container")
                .forGoggles(tooltip);
        var handler = tank.getPrimaryHandler();
        var capacity = handler.getCapacity();
        var fluidStack = handler.getFluid();
        if (!fluidStack.isEmpty()) {
            var amount = fluidStack.getAmount();
            CreateLang.fluidName(fluidStack).style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
            CreateLang.builder().add(CreateLang.number(amount).add(mb).style(1000 > amount ? ChatFormatting.RED : ChatFormatting.GOLD))
                    .text(ChatFormatting.GRAY, " / ")
                    .add(CreateLang.number(capacity).add(mb).style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);
        } else CreateLang.translate("gui.goggles.fluid_container.capacity")
                .add(CreateLang.number(capacity).add(mb).style(ChatFormatting.GOLD))
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip, 1);
        CreateLang.translate("tooltip.stressImpact").style(ChatFormatting.GRAY).forGoggles(tooltip);
        float stressTotal = calculateStressApplied() * Math.abs(getTheoreticalSpeed());
        CreateLang.number(stressTotal)
                .translate("generic.unit.stress")
                .style(ChatFormatting.AQUA)
                .space()
                .add(CreateLang.translate("gui.goggles.at_current_speed").style(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);
        return true;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, DesiresBETypes.HYDRAULIC_PRESS.get(), (be, context) -> {
            if (context != null &&
                    context.getAxis() != be.getBlockState().getValue(HydraulicPressBlock.HORIZONTAL_FACING).getAxis() &&
                    context.getAxis() != Direction.Axis.Y)
                return be.tank.getCapability();
            return null;
        });
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0, -1.5, 0)
                .expandTowards(0, 1, 0);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        tank = SmartFluidTankBehaviour.single(this, 3000);
        behaviours.add(tank);

        pressingBehaviour = new HydraulicPressingBehavior(this);
        behaviours.add(pressingBehaviour);
    }


    public void onItemPressed() {
        award(AllAdvancements.PRESS);
    }

    @Override
    public boolean canProcessInBulk() {
        return true;
    }

    @Override
    public int getParticleAmount() {
        return 15;
    }

    @Override
    public float getKineticSpeed() {
        return getSpeed() * 0.25f;
    }

    @Override
    protected boolean isRunning() {
        return pressingBehaviour.running;
    }

    @Override
    protected void onBasinRemoved() {
        pressingBehaviour.particleItems.clear();
        pressingBehaviour.running = false;
        pressingBehaviour.runningTicks = 0;
        sendData();
    }

    public Optional<RecipeHolder<PressingRecipe>> getRecipe(ItemStack item) {
        if (level == null) return Optional.empty();
        Optional<RecipeHolder<PressingRecipe>> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(level, item, AllRecipeTypes.PRESSING.getType(), PressingRecipe.class);
        if (assemblyRecipe.isPresent())
            return assemblyRecipe;

        return AllRecipeTypes.PRESSING.find(new SingleRecipeInput(item), level);
    }

    public static boolean canCompress(Recipe<?> recipe) {
        if (!(recipe instanceof CraftingRecipe) || !AllConfigs.server().recipes.allowShapedSquareInPress.get())
            return false;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return (ingredients.size() == 4 || ingredients.size() == 9) && ItemHelper.matchAllIngredients(ingredients);
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe) {
        return (recipe.value() instanceof CraftingRecipe && !(recipe.value() instanceof MechanicalCraftingRecipe) && canCompress(recipe.value())
                && !AllRecipeTypes.shouldIgnoreInAutomation(recipe))
                || recipe.value().getType() == AllRecipeTypes.COMPACTING.getType();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return compressingRecipesKey;
    }

    @Override
    protected boolean updateBasin() {
        if (isNotReady()) return true;
        if (!isSpeedRequirementFulfilled()) return true;
        if (getSpeed() == 0) return true;
        if (isRunning()) return true;
        if (level == null || level.isClientSide) return true;
        Optional<BasinBlockEntity> basin = getBasin();
        if (basin.filter(BasinBlockEntity::canContinueProcessing).isEmpty()) return true;

        List<Recipe<?>> recipes = getMatchingRecipes();
        if (recipes.isEmpty()) return true;
        currentRecipe = recipes.getFirst();
        startProcessingBasin();
        sendData();
        return true;
    }

    @Override
    public void startProcessingBasin() {
        if (isNotReady()) return;
        if (pressingBehaviour.running && pressingBehaviour.runningTicks <= PressingBehaviour.CYCLE / 2)
            return;
        super.startProcessingBasin();
        pressingBehaviour.start(PressingBehaviour.Mode.BASIN);
    }

    @Override
    public void onPressingCompleted() {
        if (pressingBehaviour.onBasin() && matchBasinRecipe(currentRecipe) && getBasin().filter(BasinBlockEntity::canContinueProcessing).isPresent())
            startProcessingBasin();
        else basinChecker.scheduleUpdate();
    }

    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        if (isNotReady()) return false;
        applyBasinRecipe();
        Optional<BasinBlockEntity> basin = getBasin();
        if (basin.isPresent()) {
            SmartInventory inputs = basin.get().getInputInventory();
            for (int slot = 0; slot < inputs.getSlots(); slot++) {
                ItemStack stackInSlot = inputs.getItem(slot);
                if (stackInSlot.isEmpty()) continue;
                pressingBehaviour.particleItems.add(stackInSlot);
            }
        }
        return true;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<RecipeHolder<PressingRecipe>> recipe = getRecipe(input.stack);
        if (recipe.isEmpty()) return false;
        if (isNotReady()) return false;
        if (simulate) return true;
        if (level == null) return false;
        pressingBehaviour.particleItems.add(input.stack);
        List<ItemStack> outputs = RecipeApplier.applyRecipeOn(level, canProcessInBulk() ? input.stack : input.stack.copyWithCount(1), recipe.get());
        for (ItemStack created : outputs) if (!created.isEmpty()) {
            onItemPressed();
            break;
        }
        outputList.addAll(outputs);
        return true;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getItem();
        Optional<RecipeHolder<PressingRecipe>> recipe = getRecipe(item);
        if (recipe.isEmpty()) return false;
        if (isNotReady()) return false;
        if (simulate) return true;
        if (level == null) return false;
        ItemStack itemCreated = ItemStack.EMPTY;
        pressingBehaviour.particleItems.add(item);
        if (canProcessInBulk() || item.getCount() == 1) {
            RecipeApplier.applyRecipeOn(itemEntity, recipe.get().value());
            itemCreated = itemEntity.getItem().copy();
        } else {
            for (ItemStack result : RecipeApplier.applyRecipeOn(level, item.copyWithCount(1), recipe.get())) {
                if (itemCreated.isEmpty()) itemCreated = result.copy();
                ItemEntity created = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                created.setDefaultPickUpDelay();
                created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, .05f));
                level.addFreshEntity(created);
            }
            item.shrink(1);
        }
        if (!itemCreated.isEmpty()) onItemPressed();
        return true;
    }

    private FluidStack getCurrentFluidInTank() {
        return tank.getPrimaryHandler().getFluid();
    }

    public boolean isNotReady() {
        return !isSpeedRequirementFulfilled() || getCurrentFluidInTank().getFluidType().isLighterThanAir() || 1000 > tank.getPrimaryHandler().getFluidAmount();
    }
    public void processRecipe() {
        var fluid = getCurrentFluidInTank();
        fluid.setAmount(fluid.getAmount() - 1000);
        tank.getPrimaryHandler().setFluid(fluid);
    }
}
