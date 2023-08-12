package uwu.lopyluna.create_dd.block.BlockProperties.ponder_box;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.YIPPEEEntityTypes;
import uwu.lopyluna.create_dd.worldgen.PonderTeleporting;
import uwu.lopyluna.create_dd.worldgen.Pondering;

import java.util.Objects;


public class PonderBoxBlock extends Block {

    public PonderBoxBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    public BlockEntityType<? extends PonderBoxBlockEntity> getBlockEntityType() {
        return YIPPEEEntityTypes.ponder_in_a_box.get();
    }


    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        if (pLevel.isClientSide || player.level.dimension() == Pondering.PONDER) return InteractionResult.sidedSuccess(!pLevel.isClientSide);

        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof PonderBoxBlockEntity) {
            BlockPos teleportPos = ((PonderBoxBlockEntity) entity).getTeleportPos();
            teleportPlayer(teleportPos, player, (PonderBoxBlockEntity) entity);
        }
        return InteractionResult.SUCCESS;
    }

    private void teleportPlayer(BlockPos storedPos, Player player, PonderBoxBlockEntity entity) {
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

    private int scanColumn(ServerLevel destWorld, int x, int z, int targetY) {
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

    private void incrementColumn(BlockPos.MutableBlockPos currentPos, BlockPos originalPos) {
        double tempPosIncX = originalPos.distToCenterSqr(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        double tempPosIncY = originalPos.distToCenterSqr(currentPos.getX(), currentPos.getY(), currentPos.getZ());

        if (tempPosIncX > tempPosIncY) {
            currentPos.set(currentPos.getX(), currentPos.getY(), currentPos.getZ() + 1);
        } else {
            currentPos.set(currentPos.getX() + 1, currentPos.getY(), currentPos.getZ());
        }
    }

    public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double d0 = (double)pPos.getX() + pRandom.nextDouble();
        double d1 = (double)pPos.getY() + pRandom.nextDouble();
        double d2 = (double)pPos.getZ() + pRandom.nextDouble();
        pLevel.addParticle(ParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }


    @SuppressWarnings("deprecation")
    @NotNull
    public ItemStack getCloneItemStack(@NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        return ItemStack.EMPTY;
    }

    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(@NotNull BlockState pState, @NotNull Fluid pFluid) {
        return false;
    }


}