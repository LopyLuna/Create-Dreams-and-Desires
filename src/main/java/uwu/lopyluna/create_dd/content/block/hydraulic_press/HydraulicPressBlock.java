package uwu.lopyluna.create_dd.content.block.hydraulic_press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;

import net.minecraft.world.level.block.entity.BlockEntityType;
import uwu.lopyluna.create_dd.init.DDBlockEntityTypes;

public class HydraulicPressBlock  extends MechanicalPressBlock {
	public HydraulicPressBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntityType<? extends HydraulicPressBlockEntity> getBlockEntityType() {
		return DDBlockEntityTypes.hydraulic_press.get();
	}
}
