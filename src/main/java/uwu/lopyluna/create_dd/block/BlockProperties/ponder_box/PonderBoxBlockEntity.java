package uwu.lopyluna.create_dd.block.BlockProperties.ponder_box;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.worldgen.ponder_dim.PonderTeleporting;
import uwu.lopyluna.create_dd.worldgen.ponder_dim.Pondering;

import java.util.List;
import java.util.Objects;

public class PonderBoxBlockEntity extends SmartBlockEntity {
    public BlockPos pos;

    public PonderBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    public BlockPos getTeleportPos() {
        return pos;
    }

    public void setTeleportPos(BlockPos teleportPos) {
        this.pos = teleportPos;
    }


    static void teleportPlayer(BlockPos storedPos, Player player, PonderBoxBlockEntity entity) {
        ServerLevel serverLevel = Objects.requireNonNull(player.level.getServer()).getLevel(Pondering.PONDER);

        if (serverLevel == null) return;

        if (storedPos == null) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(player.getX() / 8, player.getY(), player.getZ() / 8);
            BlockPos.MutableBlockPos spawnPosition = new BlockPos.MutableBlockPos(-1, -1, -1);

            int possibleYPosition;

            do {
                possibleYPosition = scanColumn(serverLevel, pos.getX(), pos.getZ(), pos.getY());
                if (possibleYPosition == -1) {
                    incrementColumn(pos, new BlockPos(player.getX(), player.getY(), player.getZ()));
                } else {
                    spawnPosition.set(pos.getX(), possibleYPosition, pos.getZ());
                }
            } while(spawnPosition.getY() == -1);

            entity.setTeleportPos(spawnPosition.immutable());
            entity.setChanged();
            storedPos = spawnPosition;
        }
        player.changeDimension(serverLevel, new PonderTeleporting(storedPos));
    }

    private static int scanColumn(ServerLevel destWorld, int x, int z, int targetY) {
        int possibleY = -1;

        for(int currentY = 4; currentY < destWorld.getHeight(); currentY++) {
            BlockPos pos = new BlockPos(x, currentY, z);

            boolean isBlockBelowSolid = destWorld.getBlockState(pos.below()).isValidSpawn(destWorld, pos, EntityType.PLAYER);
            boolean isLegBlockAir = destWorld.getBlockState(pos).getBlock() == Blocks.AIR;
            boolean isChestBlockAir = destWorld.getBlockState(pos.above()).getBlock() == Blocks.AIR;

            if (isBlockBelowSolid && isLegBlockAir && isChestBlockAir) {
                if (possibleY == -1) {
                    possibleY = currentY;
                } else {
                    if (Math.abs(possibleY - targetY) > Math.abs(pos.getY() - targetY)) {
                        possibleY = currentY;
                    }
                }
            }
        }
        return possibleY;
    }

    private static void incrementColumn(BlockPos.MutableBlockPos currentPos, BlockPos originalPos) {
        double tempPosIncX = originalPos.distToCenterSqr(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        double tempPosIncY = originalPos.distToCenterSqr(currentPos.getX(), currentPos.getY(), currentPos.getZ());

        if (tempPosIncX > tempPosIncY) {
            currentPos.set(currentPos.getX(), currentPos.getY(), currentPos.getZ() + 1);
        } else {
            currentPos.set(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        }
    }
}