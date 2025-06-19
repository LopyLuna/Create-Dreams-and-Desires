package dev.lopyluna.dndesires.compat.jei.category;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.lopyluna.dndesires.content.recipes.SeethingRecipe;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SeethingCategory extends ProcessingViaFanCategory.MultiOutput<SeethingRecipe> {
    public SeethingCategory(Info<SeethingRecipe> info) {
        super(info);
    }
    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }
    @Override
    protected void renderAttachedBlock(@NotNull GuiGraphics graphics) {
        var offsetMain = (Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 0.5f) / 16f;
        var offset1 = offsetMain * 0.5f;
        var offset2 = offsetMain * -0.5f;
        var offsetHead = offsetMain * 0.25f;

        GuiGameElement.of(AllBlocks.BLAZE_BURNER.getDefaultState())
                .scale(SCALE).atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_SUPER)
                .rotate(0, 180, 0)
                .scale(SCALE * 1.1).atLocal(1, 0.1 + offsetHead, 2.65)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_BURNER_SUPER_RODS)
                .rotate(0, 180, 0)
                .scale(SCALE).atLocal(1, 0 + offset1, 3)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_BURNER_SUPER_RODS_2)
                .rotate(0, 180, 0)
                .scale(SCALE).atLocal(1, 0.2 + offset2, 3)
                .render(graphics);

        var spriteShift = AllSpriteShifts.SUPER_BURNER_FLAME;
        var spriteWidth = spriteShift.getTarget().getU1() - spriteShift.getTarget().getU0();
        var spriteHeight = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();

        var mc = Minecraft.getInstance();
        var level = mc.level;

        var time = AnimationTickHolder.getRenderTime(level);
        var speed = 1 / 32d + 1 / 128d;

        var vScroll = speed * time;
        vScroll = vScroll - Math.floor(vScroll);
        vScroll = vScroll * spriteHeight / 2;

        var uScroll = speed * time / 2;
        uScroll = uScroll - Math.floor(uScroll);
        uScroll = uScroll * spriteWidth / 2;

        var buffer = mc.renderBuffers().bufferSource();
        var vb = buffer.getBuffer(RenderType.cutoutMipped());
        CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, Blocks.AIR.defaultBlockState())
                .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(graphics.pose(), vb);

    }
}