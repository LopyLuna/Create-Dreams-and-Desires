package uwu.lopyluna.create_flavored.block.BlockProperties;

import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class MagicBlock extends Block {

    public MagicBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (pRand.nextInt(6) != 0)
            return;
        Vec3 vec3 = VecHelper.clampComponentWise(VecHelper.offsetRandomly(Vec3.ZERO, pRand, 1f), 1f)
                .add(VecHelper.getCenterOf(pPos));
        pLevel.addParticle(ParticleTypes.END_ROD, vec3.x, vec3.y, vec3.z, pRand.nextGaussian() * 0.05D,
                pRand.nextGaussian() * 0.1D, pRand.nextGaussian() * 0.05D);
    }

}
