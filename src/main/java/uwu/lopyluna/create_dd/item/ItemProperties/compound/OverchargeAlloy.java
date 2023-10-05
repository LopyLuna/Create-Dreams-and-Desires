package uwu.lopyluna.create_dd.item.ItemProperties.compound;

import com.simibubi.create.content.trains.CubeParticleData;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class OverchargeAlloy extends NoGravMagical {

    public OverchargeAlloy(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        Vec3 pos = entity.position();
        CompoundTag persistentData = entity.getPersistentData();

        if (world.isClientSide) {
            if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .5f);
                world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + 0.5f, ppos.z, 0, -0.1f, 0);
                world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + -0.25f, ppos.z, 0, .1f, 0);
                world.addParticle(ParticleTypes.WAX_OFF, ppos.x, pos.y + 0.25f, ppos.z, 0, -0.1f, 0);
            }

            if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                Vec3 basemotion = new Vec3(0, 1, 0);
                world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                for (int i = 0; i < 20; i++) {
                    Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                    world.addParticle(ParticleTypes.WAX_OFF, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
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
        entity.setDeltaMovement(entity.getDeltaMovement()
                .add(0, .25f, 0));
    }

    @Override
    protected float getIdleParticleChance(ItemEntity entity) {
        return (float) (Mth.clamp(entity.getItem()
                .getCount() - 10, Mth.clamp(entity.getDeltaMovement().y * 20, 1, 5), 100) / 64f);
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
        if (!ModList.get().isLoaded("createaddition")) {
            return;
        }
        super.fillItemCategory(p_150895_1_, p_150895_2_);
    }
}
