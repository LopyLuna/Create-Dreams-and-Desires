package dev.lopyluna.dndesires.compat.jei.category;

import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public class AnimatedHydraulic extends AnimatedKinetics {
    private final boolean basin;

    public AnimatedHydraulic(boolean basin) {
        this.basin = basin;
    }

    @Override
    public void draw(@NotNull GuiGraphics graphics, int xOffset, int yOffset) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = basin ? 23 : 24;

        blockElement(shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(scale)
                .render(graphics);

        blockElement(DesiresBlocks.HYDRAULIC_PRESS.getDefaultState())
                .scale(scale)
                .render(graphics);

        blockElement(DesiresPartialModels.HYDRAULIC_HEAD)
                .atLocal(0, -getAnimatedHeadOffset(), 0)
                .scale(scale)
                .render(graphics);

        if (basin) blockElement(AllBlocks.BASIN.getDefaultState())
                .atLocal(0, 1.65, 0)
                .scale(scale)
                .render(graphics);

        matrixStack.popPose();
    }

    private float getAnimatedHeadOffset() {
        var cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % 30;
        if (cycle < 10) {
            var progress = cycle / 10;
            return -(progress * progress * progress);
        }
        if (cycle < 15) return -1;
        if (cycle < 20) return -1 + (1 - ((20 - cycle) / 5));
        return 0;
    }
}
