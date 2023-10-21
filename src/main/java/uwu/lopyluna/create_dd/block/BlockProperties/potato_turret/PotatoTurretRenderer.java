package uwu.lopyluna.create_dd.block.BlockProperties.potato_turret;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;


public class PotatoTurretRenderer extends KineticBlockEntityRenderer<PotatoTurretBlockEntity> {
    public PotatoTurretRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(PotatoTurretBlockEntity be, BlockState state) {
        return CachedBufferer.partial(DDBlockPartialModel.POTATO_TURRET_COG, state);
    }


    @Override
    protected void renderSafe(PotatoTurretBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        ms.pushPose();
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        TransformStack msr = TransformStack.cast(ms);
        msr.translate(1 / 2f, 0.5, 1 / 2f);
        BlockState blockState = be.getBlockState();
        int lightAbove = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().above());


        CachedBufferer.partial(DDBlockPartialModel.POTATO_TURRET_CONNECTOR, blockState)
                .centre()
                .rotateY(be.angleY.getValue())
                .light(lightAbove)
                .renderInto(ms, vb);

        CachedBufferer.partial(DDBlockPartialModel.POTATO_TURRET_SINGLE_BARREL, blockState)
                .centre()
                .rotateY(be.angleY.getValue())
                .rotateX(be.angleX.getValue())
                .light(lightAbove)
                .renderInto(ms, vb);

        ms.popPose();
    }
}