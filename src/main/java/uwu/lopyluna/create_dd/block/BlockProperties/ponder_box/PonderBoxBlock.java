package uwu.lopyluna.create_dd.block.BlockProperties.ponder_box;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.worldgen.ponder_dim.Pondering;


public class PonderBoxBlock extends Block implements IBE<PonderBoxBlockEntity>{

    private boolean visible;

    public PonderBoxBlock(Properties pProperties, boolean visible) {
        super(pProperties);
        this.visible = visible;
    }

    public PonderBoxBlock(Properties pProperties) {
        this(pProperties, false);
    }
    @Override
    public void fillItemCategory(@NotNull CreativeModeTab pCategory, @NotNull NonNullList<ItemStack> pItems) {
        if (visible)
            super.fillItemCategory(pCategory, pItems);
    }


    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {
        if (pLevel.isClientSide || player.level.dimension() == Pondering.PONDER) return InteractionResult.sidedSuccess(!pLevel.isClientSide);

        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof PonderBoxBlockEntity) {
            BlockPos teleportPos = ((PonderBoxBlockEntity) entity).getTeleportPos();
            PonderBoxBlockEntity.teleportPlayer(teleportPos, player, (PonderBoxBlockEntity) entity);
        }
        return InteractionResult.SUCCESS;
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


    @Override
    public Class<PonderBoxBlockEntity> getBlockEntityClass() {
        return PonderBoxBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PonderBoxBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.ponder_in_a_box.get();
    }

}