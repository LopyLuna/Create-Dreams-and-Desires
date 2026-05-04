package dev.lopyluna.dndesires.mixins.compat.sable;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBE;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.lopyluna.dndesires.content.blocks.logistics.smart_hopper.SmartHopperBlock.SUCK_AABB;

@Mixin(SmartHopperBE.class)
public abstract class SmartHopperBEMixin extends SmartBlockEntity {
    @Shadow protected abstract boolean cantAcceptItem(ItemStack stack, BlockState state);
    @Shadow public abstract ItemStack handleExtracting(ItemStack stack, BlockState state);

    protected SmartHopperBEMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique
    private SubLevel DnDesires$getSubLevel() {
        return Sable.HELPER.getContaining(this);
    }

    @Inject(method = "suckInItems", at = @At("HEAD"), cancellable = true)
    private void DnDesires$suckInItems(BlockState state, Level level, CallbackInfo ci) {
        final SubLevel subLevel = DnDesires$getSubLevel();
        if (subLevel == null) return;

        final BlockPos blockPosAbove = this.worldPosition.above();
        final BlockState blockStateAbove = level.getBlockState(blockPosAbove);
        final boolean canSuck = !blockStateAbove.isCollisionShapeFullBlock(level, blockPosAbove)
                || blockStateAbove.is(net.minecraft.tags.BlockTags.DOES_NOT_BLOCK_HOPPERS);

        if (canSuck) {
            final Vec3 localCenter = Vec3.atCenterOf(this.worldPosition);
            final Vec3 worldCenter = Sable.HELPER.projectOutOfSubLevel(level, localCenter);
            final var worldSearchBox = SUCK_AABB.move(worldCenter.x - 0.5F, worldCenter.y - 0.5F, worldCenter.z - 0.5F);

            for (final ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, worldSearchBox, EntitySelector.ENTITY_STILL_ALIVE)) {
                final ItemStack stack = entity.getItem().copy();
                if (stack.isEmpty()) continue;

                final var localEntityBox = new BoundingBox3d(entity.getBoundingBox())
                        .transformInverse(subLevel.logicalPose(), new BoundingBox3d())
                        .toMojang();
                if (!localEntityBox.move(-this.worldPosition.getX(), -this.worldPosition.getY(), -this.worldPosition.getZ()).intersects(SUCK_AABB)) continue;
                if (cantAcceptItem(stack, state)) continue;

                entity.setItem(handleExtracting(stack, state));
            }
        }

        ci.cancel();
    }
}
