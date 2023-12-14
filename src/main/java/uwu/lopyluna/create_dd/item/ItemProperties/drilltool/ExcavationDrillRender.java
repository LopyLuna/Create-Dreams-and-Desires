package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DDCreate;

public class ExcavationDrillRender extends CustomRenderedItemModelRenderer {
    protected static final PartialModel ITEM = new PartialModel(DDCreate.asResource("item/excavation_drill/item"));
    protected static final PartialModel COG = new PartialModel(DDCreate.asResource("item/excavation_drill/cog"));

    private static final Vec3 COG_ROTATION_OFFSET = new Vec3(0, 1 / 16f, 0);


    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        TransformStack stacker = TransformStack.cast(ms);
        boolean leftHand = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
        boolean rightHand = transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;

        ms.pushPose();
        renderer.renderSolid(ITEM.get(), light);
        ms.popPose();

        // head
        ms.pushPose();
        float angle = AnimationTickHolder.getRenderTime() * -2;
        angle %= 360;
        stacker.translate(COG_ROTATION_OFFSET)
                .rotateZ(angle)
                .translateBack(COG_ROTATION_OFFSET);
        renderer.renderSolid(COG.get(), light);
        ms.popPose();

    }
}