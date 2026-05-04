package dev.lopyluna.dndesires.mixins.compat.sable;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialFanBE;
import dev.lopyluna.dndesires.content.blocks.kinetics.industrial_fan.IndustrialFanBlock;
import dev.ryanhcode.sable.api.block.propeller.BlockEntityPropeller;
import dev.ryanhcode.sable.api.block.propeller.BlockEntitySubLevelPropellerActor;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(IndustrialFanBE.class)
public abstract class IndustrialFanBEMixin extends KineticBlockEntity implements BlockEntitySubLevelPropellerActor, BlockEntityPropeller {
    @Unique
    private boolean DnDesires$blocked;

    public IndustrialFanBEMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void sable$tick(final ServerSubLevel subLevel) {
        if (level == null) return;
        final var frontPos = worldPosition.relative(getBlockDirection());
        DnDesires$blocked = !level.getBlockState(frontPos).isAir();
    }

    @Override
    public BlockEntityPropeller getPropeller() {
        return this;
    }

    @Override
    public Direction getBlockDirection() {
        return getBlockState().getValue(IndustrialFanBlock.FACING);
    }

    @Unique
    protected float DnDesires$getPropSpeed() {
        final var rotationSpeed = convertToAngular(getSpeed()*2f);
        return getBlockDirection().getAxisDirection().getStep() * rotationSpeed * (10f/3f);
    }

    @Override
    public double getAirflow() {
        return 0.1f * DnDesires$getPropSpeed();
    }

    @Override
    public double getThrust() {
        return 0.3f * DnDesires$getPropSpeed();
    }

    @Override
    public boolean isActive() {
        return !DnDesires$blocked && Math.abs(DnDesires$getPropSpeed()) > 0.01f;
    }
}
