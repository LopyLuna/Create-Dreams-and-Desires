package uwu.lopyluna.create_dd.block.BlockProperties.potato_turret;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.core.Direction;
import uwu.lopyluna.create_dd.DDCreate;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;


public class PotatoTurretInstance extends SingleRotatingInstance<PotatoTurretBlockEntity> implements DynamicInstance {

    protected final OrientedData connector;
    protected final ModelData barrel;

    public PotatoTurretInstance(MaterialManager materialManager, PotatoTurretBlockEntity blockEntity) {
        super(materialManager, blockEntity);
        connector = getOrientedMaterial()
                .getModel(DDBlockPartialModel.POTATO_TURRET_CONNECTOR, blockState)
                .createInstance();
        //barrel = getOrientedMaterial()
        //        .getModel(DDBlockPartialModel.POTATO_TURRET_SINGLE_BARREL, blockState)
        //        .createInstance();

        barrel = getTransformMaterial()
                .getModel(DDBlockPartialModel.POTATO_TURRET_SINGLE_BARREL,blockState,Direction.SOUTH)
                .createInstance();



    }

    @Override
    public void beginFrame() {



        transformBarrel();
        transformConnector();





    }

    private void transformConnector() {
        //float speed = mixer.getRenderedHeadRotationSpeed(AnimationTickHolder.getPartialTicks());
        Quaternion baseRotation;
        baseRotation = Vector3f.YP.rotationDegrees(blockEntity.angleY.getValue());



        connector.setPosition(getInstancePosition())
                .setRotation(baseRotation)
                .nudge(0, 1, 0);
        //.setRotationalSpeed(speed * 2)
    }

    private void transformBarrel() {

        PoseStack ms = new PoseStack();
        TransformStack msr = TransformStack.cast(ms);


        msr.translate(getInstancePosition());
        msr.centre()
                .translateY(1)
                .rotate(Direction.UP, AngleHelper.rad(blockEntity.angleY.getValue()))
                .translateZ(0.4)
                .rotate(Direction.WEST, AngleHelper.rad(-blockEntity.angleX.getValue()))
                .translateZ(-0.4)


                .unCentre();
        barrel.setTransform(ms);
        //Quaternion baseRotation;
        //baseRotation = Vector3f.YP.rotationDegrees(blockEntity.angleY.getValue());
        //Quaternion xRotation;
        //xRotation = Vector3f.XP.rotationDegrees(blockEntity.angleX.getValue());
//
//
        //barrel.setPosition(getInstancePosition())
        //        .setRotation(baseRotation)
        //        .nudge(0, 1, 0);

    }



    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, connector,barrel);
    }
    @Override
    public void update() {
        super.update();

        updateLight();

    }
    @Override
    public void remove() {
        super.remove();
        connector.delete();
        barrel.delete();
    }
    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(DDBlockPartialModel.POTATO_TURRET_COG, blockEntity.getBlockState());
    }

}