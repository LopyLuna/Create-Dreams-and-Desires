package uwu.lopyluna.create_dd.item.ItemProperties.sawtool;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import uwu.lopyluna.create_dd.DDCreate;

public class ForestRavagerRender extends CustomRenderedItemModelRenderer {
    protected static final PartialModel ITEM = new PartialModel(DDCreate.asResource("item/forest_ravager/item"));
    protected static final PartialModel GEAR = new PartialModel(DDCreate.asResource("item/forest_ravager/gear"));
    protected static final PartialModel CORE = new PartialModel(DDCreate.asResource("item/forest_ravager/core"));
    protected static final PartialModel CORE_GLOW = new PartialModel(DDCreate.asResource("item/forest_ravager/core_glow/item"));
    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        int maxLight = 0xF000F0;
        float worldTime = AnimationTickHolder.getRenderTime();

        renderer.renderSolid(ITEM.get(), light);
        renderer.renderSolidGlowing(CORE.get(), maxLight);
        renderer.renderGlowing(CORE_GLOW.get(), maxLight);

        float angle = worldTime * -.5f % 360;
        ms.mulPose(Vector3f.YP.rotationDegrees(angle));
        renderer.renderSolidGlowing(GEAR.get(), light);
    }
}