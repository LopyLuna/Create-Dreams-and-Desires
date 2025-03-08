package dev.lopyluna.create_d2d.content.blocks.hydraulic_press;

import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import net.minecraft.world.level.Level;

public class HydraulicPressingBehavior extends PressingBehaviour {
    HydraulicPressBE be;
    public <T extends HydraulicPressBE & PressingBehaviourSpecifics> HydraulicPressingBehavior(T be) {
        super(be);
        this.be = be;
    }

    @Override
    public void tick() {
        super.tick();
        Level level = getWorld();
        if (!level.isClientSide && runningTicks == CYCLE / 2 && specifics.getKineticSpeed() != 0) be.processRecipe();
    }
}
