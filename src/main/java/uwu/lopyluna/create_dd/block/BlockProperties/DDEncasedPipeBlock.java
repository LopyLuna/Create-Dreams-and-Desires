package uwu.lopyluna.create_dd.block.BlockProperties;

import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;

import java.util.function.Supplier;

public class DDEncasedPipeBlock extends EncasedPipeBlock {
    public DDEncasedPipeBlock(Properties properties, Supplier<Block> casing) {
        super(properties, casing);
    }

    @Override
    public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.ENCASED_FLUID_PIPE.get();
    }
}
