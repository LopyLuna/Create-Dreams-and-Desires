package uwu.lopyluna.create_dd.block.BlockProperties.bronze_encased_fan;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import uwu.lopyluna.create_dd.block.YIPPEEEntityTypes;

public class BronzeEncasedFanBlock extends EncasedFanBlock {
    public BronzeEncasedFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends BronzeEncasedFanBlockEntity> getBlockEntityType() {
        return YIPPEEEntityTypes.BRONZE_ENCASED_FAN.get();
    }
}