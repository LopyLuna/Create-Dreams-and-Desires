package uwu.lopyluna.create_dd.jei.fan;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.recipe.Recipes.FreezingRecipe;

public class FanFreezingCategory extends DDProcessingViaFanCategory.MultiOutput<FreezingRecipe> {

    public FanFreezingCategory(Info<FreezingRecipe> info) {
        super(info);
    }

    @Override
    protected void renderAttachedBlock(@NotNull PoseStack matrixStack) {
        GuiGameElement.of(DDBlocks.freezing_sail.getDefaultState())
                .rotateBlock(0, 180, 0)
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(matrixStack);
    }

}
