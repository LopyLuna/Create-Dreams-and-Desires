package uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BronzeEncasedFanBlockEntity extends KineticBlockEntity implements BronzeIAirCurrentSource {

    public BronzeAirCurrent airCurrentBronze;
    protected boolean BupdateAirFlow;
    protected static int airCurrentUpdateCooldown;
    protected int entitySearchCooldown;

    public BronzeEncasedFanBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        airCurrentBronze = new BronzeAirCurrent(this);
        BupdateAirFlow = true;
    }
    @Override
    public AirCurrent getAirCurrent() {
        return null;
    }
    
    @Nullable
    @Override
    public BronzeAirCurrent getBAirCurrent() {
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
        if (clientPacket)
            airCurrentBronze.rebuild();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
    }

    @javax.annotation.Nullable
    @Override
    public Level getAirCurrentWorld() {
        return level;
    }

    @Override
    public BlockPos getAirCurrentPos() {
        return worldPosition;
    }

    @Override
    public Direction getAirflowOriginSide() {
        return this.getBlockState()
                .getValue(BronzeEncasedFanBlock.FACING);
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
        Direction direction = getBlockState().getValue(BronzeEncasedFanBlock.FACING);
        if (!direction.getAxis()
                .isVertical())
            return;
        BlockEntity poweredChute = level.getBlockEntity(worldPosition.relative(direction));
        if (!(poweredChute instanceof ChuteBlockEntity))
            return;
        ChuteBlockEntity chuteBE = (ChuteBlockEntity) poweredChute;
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

        if (getSpeed() == 0)
            return;

        if (entitySearchCooldown-- <= 0) {
            entitySearchCooldown = 5;
            airCurrentBronze.findEntities();
        }

        airCurrentBronze.tick();
    }







}
