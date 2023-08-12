package uwu.lopyluna.create_dd.block.BlockProperties.ponder_box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PonderBoxRenderer <T extends BlockEntity> implements BlockEntityRenderer<T> {

    public PonderBoxRenderer(BlockEntityRendererProvider.Context context) {
        super();
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }
}