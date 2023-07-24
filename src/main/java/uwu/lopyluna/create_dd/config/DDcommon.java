package uwu.lopyluna.create_dd.config;

import com.simibubi.create.foundation.config.ConfigBase;

import uwu.lopyluna.create_dd.DDcreate;

public class DDcommon extends ConfigBase {

	public final DDworldGen worldGen = nested(0, DDworldGen::new, Comments.worldGen);

	@Override
	public String getName() {
		return "common";
	}

	private static class Comments {
		static String worldGen = "Modify " + DDcreate.NAME + "' impact on your terrain";
	}

}
