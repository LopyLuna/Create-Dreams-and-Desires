package uwu.lopyluna.create_dd.block.BlockProperties.flywheel;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FlywheelBlockEntity extends GeneratingKineticBlockEntity {

        private float generatedCapacity;
        private float generatedSpeed;
        private int stoppingCooldown;

        // Client
        LerpedFloat visualSpeed = LerpedFloat.linear();
        float angle;

        public FlywheelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
                super(type, pos, state);
        }

        public void setRotation(float speed, float capacity) {

                if (generatedSpeed != speed || generatedCapacity != capacity) {

                        if (speed == 0) {
                                if (stoppingCooldown == 0)
                                        stoppingCooldown = 40;
                                return;
                        }

                        stoppingCooldown = 0;
                        generatedSpeed = speed;
                        generatedCapacity = capacity;
                        updateGeneratedRotation();
                }
        }

        @Override
        public float getGeneratedSpeed() {
                return convertToDirection(generatedSpeed, getBlockState().getValue(FlywheelBlock.HORIZONTAL_FACING));
        }

        @Override
        public float calculateAddedStressCapacity() {
                return lastCapacityProvided = generatedCapacity;
        }

        @Override
        protected AABB createRenderBoundingBox() {
                return super.createRenderBoundingBox().inflate(2);
        }

        @Override
        public void write(CompoundTag compound, boolean clientPacket) {
                compound.putFloat("GeneratedSpeed", generatedSpeed);
                compound.putFloat("GeneratedCapacity", generatedCapacity);
                compound.putInt("Cooldown", stoppingCooldown);
                super.write(compound, clientPacket);
        }

        @Override
        protected void read(CompoundTag compound, boolean clientPacket) {
                generatedSpeed = compound.getFloat("GeneratedSpeed");
                generatedCapacity = compound.getFloat("GeneratedCapacity");
                stoppingCooldown = compound.getInt("Cooldown");
                super.read(compound, clientPacket);
                if (clientPacket)
                        visualSpeed.chase(getGeneratedSpeed(),1 / 32f, LerpedFloat.Chaser.EXP);
        }

        @Override
        public void tick() {
                super.tick();

                if (level.isClientSide) {
                        float targetSpeed = isVirtual() ? speed : getGeneratedSpeed();
                        visualSpeed.updateChaseTarget(targetSpeed);
                        visualSpeed.tickChaser();
                        angle += visualSpeed.getValue() * 3 / 10f;
                        angle %= 360;
                        return;
                }

                /*
                 * After getting moved by pistons the generatedSpeed attribute reads 16 but the
                 * actual speed stays at 0, if it happens update rotation
                 */
                if (getGeneratedSpeed() != 0 && getSpeed() == 0)
                        updateGeneratedRotation();

                if (stoppingCooldown == 0)
                        return;

                stoppingCooldown--;
                if (stoppingCooldown == 0) {
                        generatedCapacity = 0;
                        generatedSpeed = 0;
                        updateGeneratedRotation();
                }
        }
}