package uwu.lopyluna.create_dd.foundation;

import com.simibubi.create.infrastructure.worldgen.AllOreFeatureConfigEntries;

import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import uwu.lopyluna.create_dd.content.items.sawtool.DeforesterItem;
import uwu.lopyluna.create_dd.content.items.sawtool.ForestRavagerItem;
import uwu.lopyluna.create_dd.content.worldgen.DDOreFeatures;

public class DDCommonEvents {
	public static void onBlockBreak(BlockEvents.BreakEvent event) {
		DeforesterItem.onBlockDestroyed(event);
		ForestRavagerItem.onBlockDestroyed(event);
	}
	//public static void onBiomeLoad() {
	//	DDOreFeatures.modifyBiomes();
	//}
	public static void register() {
		BlockEvents.BLOCK_BREAK.register(DDCommonEvents::onBlockBreak);
		//DDCommonEvents.onBiomeLoad();
	}
}
