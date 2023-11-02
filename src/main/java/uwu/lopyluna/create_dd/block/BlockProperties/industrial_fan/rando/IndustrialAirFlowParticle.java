package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.rando;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialAirCurrent;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialAirCurrentSource;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing.IndustrialTypeFanProcessing;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing.InterfaceIndustrialProcessingType;

public class IndustrialAirFlowParticle extends SimpleAnimatedParticle {

    private final IndustrialAirCurrentSource source;
    private final IndustrialAirFlowParticle.Access access = new IndustrialAirFlowParticle.Access();

    protected IndustrialAirFlowParticle(ClientLevel world, IndustrialAirCurrentSource source, double x, double y, double z,
                              SpriteSet sprite) {
        super(world, x, y, z, sprite, world.random.nextFloat() * .5f);
        this.source = source;
        this.quadSize *= 0.75F;
        this.lifetime = 40;
        hasPhysics = false;
        selectSprite(7);
        Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, random, .25f);
        this.setPos(x + offset.x, y + offset.y, z + offset.z);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        setColor(0xEEEEEE);
        setAlpha(.25f);
    }

    @Nonnull
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        if (source == null || source.isSourceRemoved()) {
            remove();
            return;
        }
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            remove();
        } else {
            IndustrialAirCurrent airCurrent = source.getAirCurrent();
            if (airCurrent == null || !airCurrent.bounds.inflate(.25f).contains(x, y, z)) {
                remove();
                return;
            }

            Vec3 directionVec = Vec3.atLowerCornerOf(airCurrent.direction.getNormal());
            Vec3 motion = directionVec.scale(1 / 8f);
            if (!source.getAirCurrent().pushing)
                motion = motion.scale(-1);

            double distance = new Vec3(x, y, z).subtract(VecHelper.getCenterOf(source.getAirCurrentPos()))
                    .multiply(directionVec).length() - .5f;
            if (distance > airCurrent.maxDistance + 1 || distance < -.25f) {
                remove();
                return;
            }
            motion = motion.scale(airCurrent.maxDistance - (distance - 1f)).scale(.5f);

            InterfaceIndustrialProcessingType type = getType(distance);
            if (type == IndustrialTypeFanProcessing.NONE) {
                setColor(0xEEEEEE);
                setAlpha(.25f);
                selectSprite((int) Mth.clamp((distance / airCurrent.maxDistance) * 8 + random.nextInt(4),
                        0, 7));
            } else {
                type.morphAirFlow(access, random);
                selectSprite(random.nextInt(3));
            }

            xd = motion.x;
            yd = motion.y;
            zd = motion.z;

            if (this.onGround) {
                this.xd *= 0.7;
                this.zd *= 0.7;
            }
            this.move(this.xd, this.yd, this.zd);
        }
    }

    private InterfaceIndustrialProcessingType getType(double distance) {
        if (source.getAirCurrent() == null)
            return IndustrialTypeFanProcessing.NONE;
        return source.getAirCurrent().getTypeAt((float) distance);
    }

    public int getLightColor(float partialTick) {
        BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
        return this.level.isLoaded(blockpos) ? LevelRenderer.getLightColor(level, blockpos) : 0;
    }

    private void selectSprite(int index) {
        setSprite(sprites.get(index, 8));
    }

    public static class Factory implements ParticleProvider<DDAirFlowParticleData> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet animatedSprite) {
            this.spriteSet = animatedSprite;
        }

        @Override
        public Particle createParticle(DDAirFlowParticleData data, ClientLevel worldIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            BlockEntity be = worldIn.getBlockEntity(new BlockPos(data.posX, data.posY, data.posZ));
            if (!(be instanceof IndustrialAirCurrentSource))
                be = null;
            return new IndustrialAirFlowParticle(worldIn, (IndustrialAirCurrentSource) be, x, y, z, this.spriteSet);
        }
    }

    private class Access implements InterfaceIndustrialProcessingType.DDAirFlowParticleAccess {
        @Override
        public void setColor(int color) {
            IndustrialAirFlowParticle.this.setColor(color);
        }

        @Override
        public void setAlpha(float alpha) {
            IndustrialAirFlowParticle.this.setAlpha(alpha);
        }

        @Override
        public void spawnExtraParticle(ParticleOptions options, float speedMultiplier) {
            level.addParticle(options, x, y, z, xd * speedMultiplier, yd * speedMultiplier, zd * speedMultiplier);
        }
    }

}
