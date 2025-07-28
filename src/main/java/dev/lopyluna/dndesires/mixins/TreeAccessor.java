package dev.lopyluna.dndesires.mixins;

import com.simibubi.create.content.kinetics.saw.TreeCutter;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = TreeCutter.Tree.class, remap = false)
public interface TreeAccessor {
    @Accessor("logs")
    List<BlockPos> logs();
    @Accessor("leaves")
    List<BlockPos> leaves();
    @Accessor("attachments")
    List<BlockPos> attachments();
}
