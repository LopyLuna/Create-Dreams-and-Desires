package dev.lopyluna.dndesires.mixins.compat.sable;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import dev.lopyluna.dndesires.compat.sable.FanSailParticles;
import dev.lopyluna.dndesires.content.blocks.FanSailBlock;
import dev.ryanhcode.sable.api.block.BlockSubLevelCustomCenterOfMass;
import dev.ryanhcode.sable.api.block.BlockSubLevelLiftProvider;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FanSailBlock.class)
public abstract class FanSailBlockMixin extends WrenchableDirectionalBlock implements BlockSubLevelLiftProvider, BlockSubLevelCustomCenterOfMass {
    public FanSailBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Direction sable$getNormal(final BlockState state) {
        return state.getValue(BlockStateProperties.FACING).getOpposite();
    }

    @Override
    public Vector3dc getCenterOfMass(final BlockGetter blockGetter, final BlockState state) {
        return JOMLConversion.HALF;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        FanSailParticles.track(level, pos, state);
    }
}
