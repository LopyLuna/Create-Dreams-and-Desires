package dev.lopyluna.dndesires.compat.jei.category;

import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import dev.lopyluna.dndesires.content.recipes.SandingRecipe;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SandingCategory extends ProcessingViaFanCategory.MultiOutput<SandingRecipe> {
    public SandingCategory(Info<SandingRecipe> info) {
        super(info);
    }
    @Override
    protected void renderAttachedBlock(@NotNull GuiGraphics graphics) {
        GuiGameElement.of(Blocks.SAND.defaultBlockState())
                .scale(SCALE).atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);
    }
}

