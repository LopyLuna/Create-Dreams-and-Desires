package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.logistics.chute.ChuteBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.DDBlocks;

import java.util.List;

public class IndustrialFanBlockEntity extends GeneratingKineticBlockEntity implements IndustrialAirCurrentSource {

    public IndustrialAirCurrent airCurrentBronze;
    protected boolean BupdateAirFlow;
    protected static int airCurrentUpdateCooldown;
    protected int entitySearchCooldown;

    protected boolean isGenerator;
    protected boolean updateGenerator;



    public IndustrialFanBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        isGenerator = false;
        airCurrentBronze = new IndustrialAirCurrent(this);
        BupdateAirFlow = true;
        updateGenerator = false;
    }
    @Override
    public AirCurrent getAirCurrent() {
        return null;
    }
    
    @Nullable
    @Override
    public IndustrialAirCurrent getBAirCurrent() {
        return airCurrentBronze;
    }


    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.ENCASED_FAN, AllAdvancements.FAN_PROCESSING);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (!wasMoved)
            isGenerator = compound.getBoolean("Generating");
        if (clientPacket)
            airCurrentBronze.rebuild();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putBoolean("Generating", isGenerator);
        super.write(compound, clientPacket);
    }

    @Override
    public float calculateAddedStressCapacity() {
        return isGenerator ? super.calculateAddedStressCapacity() : 0;
    }

    @Override
    public float calculateStressApplied() {
        return isGenerator ? 0 : super.calculateStressApplied();
    }

    @Override
    public float getGeneratedSpeed() {
        return isGenerator ? 64 : 0;
    }

    public void queueGeneratorUpdate() {
        updateGenerator = true;
    }

    public void updateGenerator() {
        BlockState blockState = getBlockState();
        boolean shouldGenerate = DDBlocks.industrial_fan.has(blockState);

        if (shouldGenerate && blockState.getValue(IndustrialFanBlock.FACING) != Direction.DOWN && blockBelowIsHot())
            shouldGenerate = false;

        if (shouldGenerate)
            shouldGenerate = level != null
                    && !blockBelowIsHot()
                    && level.hasSignal(worldPosition, Direction.DOWN)
                    && level.hasNeighborSignal(worldPosition.below())
                    && blockState.getValue(IndustrialFanBlock.FACING) == Direction.DOWN;

        if (shouldGenerate == isGenerator)
            return;
        isGenerator = shouldGenerate;
        updateGeneratedRotation();
    }

    public boolean blockBelowIsHot() {
        assert level != null;
        BlockState blockState = level.getBlockState(worldPosition.below());
        return !DDTags.AllBlockTags.fan_heaters.matches(blockState);
    }

    @javax.annotation.Nullable
    @Override
    public Level getAirCurrentWorld() {
        return level;
    }
    @NotNull
    @Override
    public BlockPos getAirCurrentPos() {
        return worldPosition;
    }

    @NotNull
    @Override
    public Direction getAirflowOriginSide() {
        return this.getBlockState()
                .getValue(IndustrialFanBlock.FACING);
    }

    @Override
    public Direction getAirFlowDirection() {
        float speed = getSpeed();
        if (speed == 0)
            return null;
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        speed = convertToDirection(speed, facing);
        return speed > 0 ? facing : facing.getOpposite();
    }

    @Override
    public void remove() {
        super.remove();
        updateChute();
    }

    @Override
    public boolean isSourceRemoved() {
        return remove;
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        BupdateAirFlow = true;
        updateChute();
    }

    public void updateChute() {
        Direction direction = getBlockState().getValue(IndustrialFanBlock.FACING);
        if (!direction.getAxis()
                .isVertical())
            return;
        assert level != null;
        BlockEntity poweredChute = level.getBlockEntity(worldPosition.relative(direction));
        if (!(poweredChute instanceof ChuteBlockEntity chuteBE))
            return;
        if (direction == Direction.DOWN)
            chuteBE.updatePull();
        else
            chuteBE.updatePush(1);
    }

    public void blockInFrontChanged() {
        BupdateAirFlow = true;
    }

    @Override
    public void tick() {
        super.tick();

        assert level != null;
        boolean server = !level.isClientSide || isVirtual();

        if (server && airCurrentUpdateCooldown-- <= 0) {
            airCurrentUpdateCooldown = AllConfigs.server().kinetics.fanBlockCheckRate.get();
            BupdateAirFlow = true;
        }

        if (BupdateAirFlow) {
            BupdateAirFlow = false;
            airCurrentBronze.rebuild();
            if (airCurrentBronze.maxDistance > 0)
                award(AllAdvancements.ENCASED_FAN);
            sendData();
        }

        if (updateGenerator) {
            updateGenerator = false;
            updateGenerator();
        }

        if (isGenerator || getSpeed() == 0)
            return;

        if (entitySearchCooldown-- <= 0) {
            entitySearchCooldown = 5;
            airCurrentBronze.findEntities();
        }

        airCurrentBronze.tick();
    }







}
