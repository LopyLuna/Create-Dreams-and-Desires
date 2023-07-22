package uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HydraulicPressBlockEntity extends MechanicalPressBlockEntity {
    public HydraulicPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static <C extends Container> boolean canCompress(Recipe<C> recipe) {
        if (!(recipe instanceof CraftingRecipe) || !true)
            return false;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return (ingredients.size() == 4 || ingredients.size() == 9) && ItemHelper.matchAllIngredients(ingredients);
    }

    @Override
    public boolean canProcessInBulk() {
        return true;
    }
}
