package uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HydraulicPressBlockEntity extends MechanicalPressBlockEntity {
    public HydraulicPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        return false;
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> recipe) {
        return (recipe instanceof CraftingRecipe && !(recipe instanceof MechanicalCraftingRecipe) && canCompress(recipe)
                && !AllRecipeTypes.shouldIgnoreInAutomation(recipe))
                || recipe.getType() == AllRecipeTypes.COMPACTING.getType();
    }

    public static <C extends Container> boolean canCompress(Recipe<C> recipe) {
        return false;
    }

    @Override
    public void onPressingCompleted() {
    }

    @Override
    public void startProcessingBasin() {
    }

    @Override
    public boolean canProcessInBulk() {
        return !AllConfigs.server().recipes.bulkPressing.get();
    }
}
