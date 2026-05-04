package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PotatoProjectileEntity.class)
public interface PotatoProjectileEntityAccessor {
    @Accessor("recoveryChance")
    void recoveryChance(float chance);
}
