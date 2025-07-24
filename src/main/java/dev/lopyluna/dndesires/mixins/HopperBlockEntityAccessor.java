package dev.lopyluna.dndesires.mixins;

import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {
    @Accessor("tickedGameTime")
    long tickedGameTime();
}
