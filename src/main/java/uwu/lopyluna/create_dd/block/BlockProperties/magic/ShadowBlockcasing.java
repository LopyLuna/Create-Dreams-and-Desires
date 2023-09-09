package uwu.lopyluna.create_dd.block.BlockProperties.magic;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ShadowBlockcasing extends CasingBlock {

    public ShadowBlockcasing(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRand) {
        if (pRand.nextInt(5) != 0)
            return;
        Vec3 vec3 = VecHelper.clampComponentWise(VecHelper.offsetRandomly(Vec3.ZERO, pRand, 1.25f), 1f)
                .add(VecHelper.getCenterOf(pPos));
        pLevel.addParticle(ParticleTypes.REVERSE_PORTAL, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.REVERSE_PORTAL, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.WITCH, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pPos, Projectile pProj) {
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pPos.getBlockPos();
            pLevel.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.5F + pLevel.random.nextFloat() * 1.2F);
            pLevel.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.5F + pLevel.random.nextFloat() * 1.2F);
        }

    }

}
