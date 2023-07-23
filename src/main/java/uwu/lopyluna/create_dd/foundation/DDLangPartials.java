package uwu.lopyluna.create_dd.foundation;

import com.google.common.base.Supplier;
import com.google.gson.JsonElement;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.Lang;

import uwu.lopyluna.create_dd.DDcreate;

public enum DDLangPartials implements LangPartial {
	INTERFACE("UI & Messages"),
	//SUBTITLES("Subtitles", AllSoundEvents::provideLangEntries),
	TOOLTIPS("Item Descriptions"),
	//PONDER("Ponder Content", PonderLocalization::provideLangEntries),

	;

	private final String displayName;
	private final Supplier<JsonElement> provider;

	private DDLangPartials(String displayName) {
		this.displayName = displayName;
		String fileName = Lang.asId(name());
		this.provider = () -> LangPartial.fromResource(DDcreate.ID, fileName);
	}

	private DDLangPartials(String displayName, Supplier<JsonElement> provider) {
		this.displayName = displayName;
		this.provider = provider;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public JsonElement provide() {
		return provider.get();
	}
}
