package uwu.lopyluna.create_dd.item.ItemProperties.drilltool;

import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.item.DDItems;


@SuppressWarnings({"all"})
public class ExcavationDrillRender extends CustomRenderedItemModelRenderer {
    protected static final PartialModel ITEM = new PartialModel(DDCreate.asResource("item/excavation_drill/item"));
    protected static final PartialModel COG = new PartialModel(DDCreate.asResource("item/excavation_drill/cog"));
    protected static final PartialModel HEAD = new PartialModel(DDCreate.asResource("item/excavation_drill/head"));
    protected static final PartialModel ON = new PartialModel(DDCreate.asResource("item/excavation_drill/on"));
    protected static final PartialModel OFF = new PartialModel(DDCreate.asResource("item/excavation_drill/off"));

    private static final Vec3 ROTATION_OFFSET = new Vec3(0, -4 / 16f, 0);

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemTransforms.TransformType transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        TransformStack stacker = TransformStack.cast(ms);
        int i =  -2;
        boolean active = false;

        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;
        boolean validSimple = player.getMainHandItem().getItem() == DDItems.excavation_drill.get();
        boolean validSimpleOffHand = player.getOffhandItem().getItem() == DDItems.excavation_drill.get();
        boolean valid = player.getMainHandItem().getItem() == DDItems.excavation_drill.get() && player.swingTime > 0;
        if (valid && !active) {i--;if (i >= -16) {if (valid) {i = -16;active = false;} else {active = true;}}} else if (active) {i++;if (i <= 2) {if (!valid) {i = 2;}active = false;}}

        float boostCog = ((i * -2) + (valid ? (player.attackAnim) / 10f : 0) + 1);

        //if ( player.isCrouching() || player.isAutoSpinAttack() || player.isUsingItem() ) {} idfk maybe used for later

        int maxLight = 0xF000F0;


        ms.pushPose();
        if ( player.isCrouching() && validSimple && !validSimpleOffHand )
        renderer.renderSolidGlowing(ON.get(), maxLight);
        if ( !player.isCrouching() || validSimpleOffHand )
        renderer.renderSolidGlowing(OFF.get(), maxLight);
        ms.popPose();


        ms.pushPose();
        renderer.renderSolid(ITEM.get(), light);
        ms.popPose();

        // head
        ms.pushPose();
        float angle = AnimationTickHolder.getRenderTime() * boostCog;
        angle %= 360;
        stacker.translate(ROTATION_OFFSET)
                .rotateZ(angle)
                .translateBack(ROTATION_OFFSET);
        renderer.renderSolid(COG.get(), light);
        renderer.renderSolid(HEAD.get(), light);
        ms.popPose();

    }
}