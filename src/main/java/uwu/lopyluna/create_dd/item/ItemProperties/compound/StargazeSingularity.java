package uwu.lopyluna.create_dd.item.ItemProperties.compound;

import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StargazeSingularity extends NoGravMagical {

    public StargazeSingularity(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        Vec3 pos = entity.position();
        CompoundTag persistentData = entity.getPersistentData();

        if (world.isClientSide) {
            if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .6f);
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                world.addParticle(ParticleTypes.GLOW, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                world.addParticle(ParticleTypes.SNEEZE, ppos.x, pos.y, ppos.z, 0, -.05f, 0);
                world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                world.addParticle(ParticleTypes.SMOKE, ppos.x, pos.y, ppos.z, 0, .05f, 0);
                world.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, ppos.x, pos.y, ppos.z, 0, .05f, 0);
            }

            if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                Vec3 basemotion = new Vec3(0, -2, 0);
                world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0.025F, 0);
                for (int i = 0; i < 20; i++) {
                    Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.GLOW, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.SNEEZE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                }
                persistentData.putBoolean("PlayEffects", true);
            }

            return false;
        }

        entity.setNoGravity(true);

        if (!persistentData.contains("JustCreated"))
            return false;
        onCreated(entity, persistentData);
        return false;
    }

    @Override
    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        super.onCreated(entity, persistentData);
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.8, 0.75, 0.8));
    }

    @Override
    protected float getIdleParticleChance(ItemEntity entity) {
        return (float) (Mth.clamp(entity.getItem()
                .getCount() - 5, Mth.clamp(entity.getDeltaMovement().y * 20, 2, 5), 100) / 64f);
    }
}
