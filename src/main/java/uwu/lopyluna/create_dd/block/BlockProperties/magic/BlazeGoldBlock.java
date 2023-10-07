package uwu.lopyluna.create_dd.block.BlockProperties.magic;

import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlazeGoldBlock extends Block {

    public BlazeGoldBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRand) {
        if (pRand.nextInt(5) != 0)
            return;
        Vec3 vec3 = VecHelper.clampComponentWise(VecHelper.offsetRandomly(Vec3.ZERO, pRand, 1.25f), 1f)
                .add(VecHelper.getCenterOf(pPos));
        pLevel.addParticle(ParticleTypes.FLAME, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.FALLING_LAVA, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.05D, pRand.nextGaussian() * 0.05D);
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pPos, Projectile pProj) {
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pPos.getBlockPos();
            pLevel.playSound((Player)null, blockpos, SoundEvents.BLAZE_HURT, SoundSource.BLOCKS, 0.25F, 0.5F + pLevel.random.nextFloat() * 1.05F);
            pLevel.playSound((Player)null, blockpos, SoundEvents.BLAZE_BURN, SoundSource.BLOCKS, 0.5F, 0.75F + pLevel.random.nextFloat() * 1.05F);
        }

        BlockPos blockpos = pPos.getBlockPos();
        Vec3 vec3 = VecHelper.clampComponentWise(VecHelper.offsetRandomly(Vec3.ZERO, pLevel.random, 1.25f), 1f)
                .add(VecHelper.getCenterOf(blockpos));
        pLevel.addParticle(ParticleTypes.FLAME, vec3.x, vec3.y, vec3.z, pLevel.random.nextGaussian() * 0.05D,
                pLevel.random.nextGaussian() * 0.05D, pLevel.random.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.FALLING_LAVA, vec3.x, vec3.y, vec3.z, pLevel.random.nextGaussian() * 0.05D,
                pLevel.random.nextGaussian() * 0.05D, pLevel.random.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, pLevel.random.nextGaussian() * 0.05D,
                pLevel.random.nextGaussian() * 0.05D, pLevel.random.nextGaussian() * 0.05D);
        pLevel.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, pLevel.random.nextGaussian() * 0.05D,
                pLevel.random.nextGaussian() * 0.05D, pLevel.random.nextGaussian() * 0.05D);
    }

}
