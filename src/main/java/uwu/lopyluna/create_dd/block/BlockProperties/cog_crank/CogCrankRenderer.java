package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class CogCrankRenderer extends KineticBlockEntityRenderer<CogCrankBlockEntity> {

    public CogCrankRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    protected void renderSafe(CogCrankBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
        int light, int overlay) {
        if (be.shouldRenderCog())
            super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        Direction facing = be.getBlockState()
                .getValue(FACING);
        kineticRotationTransform(be.getRenderedHandle(), be, facing.getAxis(), be.getIndependentAngle(partialTicks),
                light).renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}
