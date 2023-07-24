package uwu.lopyluna.create_dd.config;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.config.ConfigBase;

import net.minecraftforge.common.ForgeConfigSpec;
import uwu.lopyluna.create_dd.DDcreate;
import uwu.lopyluna.create_dd.content.worldgen.DDOreFeatures;

public class DDworldGen extends ConfigBase {

	/**
	 * Increment this number if all worldgen config entries should be overwritten
	 * in this update. Worlds from the previous version will overwrite potentially
	 * changed values with the new defaults.
	 */
	public static final int FORCED_UPDATE_VERSION = 2;

	public final ConfigBool disable = b(false, "disableWorldGen", Comments.disable);

	@Override
	public void registerAll(ForgeConfigSpec.Builder builder) {
		super.registerAll(builder);
		DDOreFeatures.fillConfig(builder, DDcreate.ID);
	}

	@Override
	public String getName() {
		return "worldgen.v" + FORCED_UPDATE_VERSION;
	}

	private static class Comments {
		static String disable = "Prevents all worldgen added by " + DDcreate.NAME + " from taking effect";
	}
}
