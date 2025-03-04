package dev.lopyluna.create_d2d.mixins;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface FurnaceBEAccessor {
    @Accessor("cookingProgress")
    int getCookingProgress$D2D();
    @Accessor("cookingTotalTime")
    int getCookingTotalTime$D2D();
}
