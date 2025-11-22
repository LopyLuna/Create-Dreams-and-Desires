package dev.lopyluna.dndesires.content.fan_types;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import dev.lopyluna.dndesires.register.DesiresRecipeTypes;
import dev.lopyluna.dndesires.register.DesiresTags;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.neoforged.neoforge.event.EventHooks.onEnderTeleport;

public class DragonBreathingType implements FanProcessingType {
    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        var fluidState = level.getFluidState(pos);
        if (DesiresTags.FluidTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.is(fluidState)) return true;
        var blockState = level.getBlockState(pos);
        if (DesiresTags.BlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.is(blockState)) {
            if (blockState.getBlock() instanceof WallSkullBlock skullBlock && skullBlock == Blocks.DRAGON_WALL_HEAD) {
                var skullFacing = level.getBlockState(pos).getValue(WallSkullBlock.FACING);
                var fanState = level.getBlockState(pos.relative(skullFacing.getOpposite()));
                var powered = level.hasNeighborSignal(pos);
                var sameDirection = fanState.is(DesiresTags.BlockTags.FAN_CATALYSTS_DRAGON_SUPPORT.tag) && fanState.hasProperty(BlockStateProperties.FACING) && fanState.getValue(BlockStateProperties.FACING) == skullFacing;
                return powered && sameDirection;
            }
            return true;
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 1500;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        return DesiresRecipeTypes.DRAGON_BREATHING.find(new SingleRecipeInput(stack), level).isPresent();
    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        return DesiresRecipeTypes.DRAGON_BREATHING.find(new SingleRecipeInput(stack), level)
                .map(RecipeHolder::value)
                .map(r -> RecipeApplier.applyRecipeOn(level, stack, r, true))
                .orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        level.addParticle(ParticleTypes.DRAGON_BREATH,
                pos.x + (level.random.nextFloat() - .5f) * .5f,
                pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(0xD36FD9, 0xC21BF5, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 128f) particleAccess.spawnExtraParticle(ParticleTypes.DRAGON_BREATH, .125f);
        if (random.nextFloat() < 1 / 32f) particleAccess.spawnExtraParticle(ParticleTypes.WITCH, .125f);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide) return;

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(livingEntity.damageSources().dragonBreath(), 1);
            var random = livingEntity.getRandom();
            double d0 = livingEntity.getX() + (random.nextDouble() - 0.5D) * 32.0D, d1 = livingEntity.getY() + (double)(random.nextInt(32) - 16), d2 = livingEntity.getZ() + (random.nextDouble() - 0.5D) * 32.0D;
            var pos = new BlockPos.MutableBlockPos(d0, d1, d2);
            while (pos.getY() > level.getMinBuildHeight() && !level.getBlockState(pos).blocksMotion()) pos = pos.move(Direction.DOWN);
            var blockstate = level.getBlockState(pos);
            var flag = blockstate.blocksMotion();
            var flag1 = !blockstate.getFluidState().is(FluidTags.WATER) && !blockstate.is(Blocks.COBWEB);
            if (flag && flag1) {
                var event = onEnderTeleport(livingEntity, d0, d1, d2);
                if (event.isCanceled()) return;
                if (livingEntity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    level.gameEvent(GameEvent.TELEPORT, livingEntity.position(), GameEvent.Context.of(livingEntity));
                    if (!livingEntity.isSilent()) {
                        level.playSound(null, livingEntity.xo, livingEntity.yo, livingEntity.zo, SoundEvents.ENDERMAN_TELEPORT, livingEntity.getSoundSource(), 1.0F, 1.0F);
                        livingEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
