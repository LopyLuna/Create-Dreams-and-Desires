package uwu.lopyluna.create_dd.worldgen.ponder_dim;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PonderTeleporting implements ITeleporter {
    BlockPos pos;
    public PonderTeleporting(BlockPos teleportPos) {this.pos = teleportPos;}
    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {return repositionEntity.apply(true);}
    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {return new PortalInfo(new Vec3(pos.getX(), pos.getY(), pos.getZ()), Vec3.ZERO, entity.getYRot(), entity.getXRot());}
    @Override
    public boolean isVanilla() {return this.getClass() == PonderTeleporting.class;}
}