package uwu.lopyluna.create_flavored.entity.Bobert;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class SmartBuilderEntity extends AbstractGolem implements NeutralMob {

    public SmartBuilderEntity(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0F;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.5f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 1.0f).build();
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setAlertOthers());
    }


    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.5F);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.GENERIC_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        SmartBuilderEntity.Crackiness smartbuilder$crackiness = this.getCrackiness();
        boolean flag = super.hurt(pSource, pAmount);
        if (flag && this.getCrackiness() != smartbuilder$crackiness) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
        }

        return flag;
    }

    public SmartBuilderEntity.Crackiness getCrackiness() {
        return SmartBuilderEntity.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
    }

    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(AllItems.BRASS_INGOT.get())) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(2.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, f1);
                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
    }


    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource pCause) {
        super.die(pCause);
    }


    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.875F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }


    public static enum Crackiness {
        NONE(1.0F),
        LOW(0.75F),
        MEDIUM(0.5F),
        HIGH(0.25F);

        private static final List<SmartBuilderEntity.Crackiness> BY_DAMAGE = Stream.of(values()).sorted(Comparator.comparingDouble((p_28904_) -> {
            return (double)p_28904_.fraction;
        })).collect(ImmutableList.toImmutableList());
        private final float fraction;

        private Crackiness(float pFraction) {
            this.fraction = pFraction;
        }

        public static SmartBuilderEntity.Crackiness byFraction(float pFraction) {
            for(SmartBuilderEntity.Crackiness smartbuilder$crackiness : BY_DAMAGE) {
                if (pFraction < smartbuilder$crackiness.fraction) {
                    return smartbuilder$crackiness;
                }
            }

            return NONE;
        }
    }
}
