package dev.lopyluna.create_d2d.content.blocks.hydraulic_press;

import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class HydraulicPressingBehavior extends PressingBehaviour {
    HydraulicPressBE be;
    boolean nextTick;
    public <T extends HydraulicPressBE & PressingBehaviourSpecifics> HydraulicPressingBehavior(T be) {
        super(be);
        this.be = be;
    }

    @Override
    public void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        nextTick = compound.getBoolean("NextTick");
        super.read(compound, registries, clientPacket);
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("NextTick", nextTick);
        super.write(compound, registries, clientPacket);
    }

    @Override
    public void tick() {
        if (nextTick) {
            be.processRecipe();
            nextTick = false;
        }
        super.tick();
        Level level = getWorld();
        if (!level.isClientSide && runningTicks == CYCLE / 2 && specifics.getKineticSpeed() != 0) nextTick = true;
    }
}
