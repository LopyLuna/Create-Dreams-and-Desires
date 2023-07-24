package uwu.lopyluna.create_dd;

import com.simibubi.create.Create;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DDdata implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
		DDcreate.REGISTRATE.setupDatagen(generator, helper);
		//DDcreate.gatherData(generator, helper);
	}
}
