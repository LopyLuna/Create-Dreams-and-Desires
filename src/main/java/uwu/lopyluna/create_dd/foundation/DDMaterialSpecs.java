package uwu.lopyluna.create_dd.foundation;

import com.simibubi.create.foundation.render.AllMaterialSpecs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.DDcreate;
@Environment(EnvType.CLIENT)
public class DDMaterialSpecs extends AllMaterialSpecs {

	public static class Locations {
		public static final ResourceLocation ACTORS = DDcreate.asResource("actors");
	}
}
