package uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BronzeEncasedFanBlockEntity extends EncasedFanBlockEntity implements BronzeIAirCurrentSource {

    public BronzeAirCurrent airCurrent;
    protected boolean updateAirFlow;

    public BronzeEncasedFanBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        airCurrent = new BronzeAirCurrent(this);
        updateAirFlow = true;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (clientPacket)
            airCurrent.rebuild();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
    }

    @Override
    public Direction getAirflowOriginSide() {
        return this.getBlockState()
                .getValue(BronzeEncasedFanBlock.FACING);
    }

    @Override
    public float getMaxDistance() {
        return super.getMaxDistance();
    }


    @Nullable
    @Override
    public BronzeAirCurrent getBAirCurrent() {
        return airCurrent;
    }


    @Override
    public boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking, LazyOptional<IFluidHandler> handler) {
        return super.containedFluidTooltip(tooltip, isPlayerSneaking, handler);
    }
}
