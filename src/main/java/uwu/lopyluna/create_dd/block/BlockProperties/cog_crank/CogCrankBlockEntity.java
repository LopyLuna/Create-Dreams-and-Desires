package uwu.lopyluna.create_dd.block.BlockProperties.cog_crank;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.block.DDBlocks;

public class CogCrankBlockEntity extends GeneratingKineticBlockEntity {

    public int inUse;
    public boolean backwards;
    public float independentAngle;
    public float chasingVelocity;

    public CogCrankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void turn(boolean back) {
        boolean update = false;

        if (getGeneratedSpeed() == 0 || back != backwards)
            update = true;

        inUse = 10;
        this.backwards = back;
        if (update && !level.isClientSide)
            updateGeneratedRotation();
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }

    @Override
    public float getGeneratedSpeed() {
        Block block = getBlockState().getBlock();
        if (!(block instanceof CogCrankBlock))
            return 0;
        CogCrankBlock crank = (CogCrankBlock) block;
        int speed = (inUse == 0 ? 0 : clockwise() ? -1 : 1) * crank.getRotationSpeed();
        return speed;
    }

    protected boolean clockwise() {
        return backwards;
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("InUse", inUse);
        compound.putBoolean("Backwards", backwards);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        inUse = compound.getInt("InUse");
        backwards = compound.getBoolean("Backwards");
        super.read(compound, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();

        float actualSpeed = getSpeed();
        chasingVelocity += ((actualSpeed * 10 / 3f) - chasingVelocity) * .25f;
        independentAngle += chasingVelocity;

        if (inUse > 0) {
            inUse--;

            if (inUse == 0 && !level.isClientSide) {
                sequenceContext = null;
                updateGeneratedRotation();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public SuperByteBuffer getRenderedHandle() {
        BlockState blockState = getBlockState();
        Direction facing = blockState.getOptionalValue(HandCrankBlock.FACING)
                .orElse(Direction.UP);
        return CachedBufferer.partialFacing(DDBlockPartialModel.HAND_CRANK_HANDLE, blockState, facing.getOpposite());
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderCog() {
        return true;
    }

    @Override
    protected Block getStressConfigKey() {
        return DDBlocks.cogCrank.has(getBlockState()) ? DDBlocks.cogCrank.get()
                : AllBlocks.COPPER_VALVE_HANDLE.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        if (inUse > 0 && AnimationTickHolder.getTicks() % 10 == 0) {
            if (!DDBlocks.cogCrank.has(getBlockState()))
                return;
            AllSoundEvents.CRANKING.playAt(level, worldPosition, (inUse) / 2.5f, .65f + (10 - inUse) / 10f, true);
        }
    }
}
