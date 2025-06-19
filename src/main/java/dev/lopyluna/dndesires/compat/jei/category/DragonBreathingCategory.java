package dev.lopyluna.dndesires.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import dev.lopyluna.dndesires.content.recipes.DragonBreathingRecipe;
import dev.lopyluna.dndesires.register.DesiresBlocks;
import dev.lopyluna.dndesires.register.client.DesiresPartialModels;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DragonBreathingCategory extends ProcessingViaFanCategory.MultiOutput<DragonBreathingRecipe> {
    private static final ResourceLocation DRAGON_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/enderdragon/dragon.png");
    public DragonBreathingCategory(Info<DragonBreathingRecipe> info) {
        super(info);
    }

    @Override
    public void draw(DragonBreathingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        renderWidgets(graphics, recipe, mouseX, mouseY);

        PoseStack matrixStack = graphics.pose();

        matrixStack.pushPose();
        translateFan(matrixStack);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-12.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        AnimatedKinetics.defaultBlockElement(DesiresPartialModels.INDUSTRIAL_FAN_INNER)
                .rotateBlock(180, 0, AnimatedKinetics.getCurrentAngle() * 16)
                .scale(SCALE)
                .render(graphics);

        AnimatedKinetics.defaultBlockElement(DesiresPartialModels.INDUSTRIAL_FAN_POWER)
                .rotateBlock(180, 0, AnimatedKinetics.getCurrentAngle())
                .scale(SCALE)
                .render(graphics);

        AnimatedKinetics.defaultBlockElement(DesiresBlocks.INDUSTRIAL_FAN.getDefaultState())
                .rotateBlock(0, 180, 0)
                .atLocal(0, 0, 0)
                .scale(SCALE)
                .render(graphics);

        renderAttachedBlock(graphics);
        matrixStack.popPose();
    }

    private float animationTickCount = 0f;

    @Override
    protected void renderAttachedBlock(@NotNull GuiGraphics graphics) {
        var lever = Blocks.LEVER.defaultBlockState();
        lever = lever.setValue(LeverBlock.FACE, AttachFace.WALL);
        lever = lever.setValue(LeverBlock.FACING, Direction.WEST);
        lever = lever.setValue(LeverBlock.POWERED, true);
        GuiGameElement.of(lever)
                .scale(SCALE).atLocal(-1, 0, 0)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);

        var pose = graphics.pose();
        pose.pushPose();

        pose.scale(SCALE, SCALE, SCALE);
        pose.translate(0.4, -0.2, 2.5);
        pose.mulPose(Axis.YP.rotationDegrees(180));

        var mc = Minecraft.getInstance();
        var head = new DragonHeadModel(mc.getEntityModels().bakeLayer(ModelLayers.DRAGON_SKULL));
        var buffer = graphics.bufferSource();
        var vb = buffer.getBuffer(RenderType.entityCutoutNoCull(DRAGON_LOCATION));
        animationTickCount += 0.04f;
        head.setupAnim(this.animationTickCount, RotationSegment.convertToDegrees(RotationSegment.convertToSegment(Direction.SOUTH)), 0.0F);
        head.renderToBuffer(pose, vb, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        pose.popPose();
        buffer.endBatch();
    }
}
