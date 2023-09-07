package uwu.lopyluna.create_dd.block.BlockProperties.drill.radiant;

import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.math.Quaternion;
import com.simibubi.create.content.contraptions.actors.flwdata.ActorData;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorInstance;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.block.BlockPalette.gen.DDPaletteMaterialSpecs;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;

public class RadiantDrillActorInstance extends ActorInstance {

    ActorData drillHead;
    private final Direction facing;

    public RadiantDrillActorInstance(MaterialManager materialManager, VirtualRenderWorld contraption, MovementContext context) {
        super(materialManager, contraption, context);

        Material<ActorData> material = materialManager.defaultSolid()
                .material(DDPaletteMaterialSpecs.ACTORS);

        BlockState state = context.state;

        facing = state.getValue(RadiantDrillBlock.FACING);

        Direction.Axis axis = facing.getAxis();
        float eulerX = AngleHelper.verticalAngle(facing);

        float eulerY;
        if (axis == Direction.Axis.Y)
            eulerY = 0;
        else
            eulerY = facing.toYRot() + ((axis == Direction.Axis.X) ? 180 : 0);

        drillHead = material.getModel(DDBlockPartialModel.RADIANT_DRILL_HEAD, state).createInstance();

        drillHead.setPosition(context.localPos)
                .setBlockLight(15)
                .setRotationOffset(0)
                .setRotationAxis(0, 0, 1)
                .setLocalRotation(new Quaternion(eulerX, eulerY, 0, true))
                .setSpeed(getSpeed(facing));
    }

    @Override
    public void beginFrame() {
        drillHead.setSpeed(getSpeed(facing));
    }

    protected float getSpeed(Direction facing) {
        if (context.contraption.stalled || !VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite()))
            return context.getAnimationSpeed();
        return 0;
    }
}
