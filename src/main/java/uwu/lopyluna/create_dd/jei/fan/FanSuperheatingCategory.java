package uwu.lopyluna.create_dd.jei.fan;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.recipe.Recipes.SuperheatingRecipe;

public class FanSuperheatingCategory extends DDProcessingViaFanCategory.MultiOutput<SuperheatingRecipe> {

    public FanSuperheatingCategory(Info<SuperheatingRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(@NotNull PoseStack matrixStack) {
        GuiGameElement.of(DDBlocks.superheating_sail.getDefaultState())
                .rotateBlock(0, 180, 0)
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(matrixStack);
    }

}
