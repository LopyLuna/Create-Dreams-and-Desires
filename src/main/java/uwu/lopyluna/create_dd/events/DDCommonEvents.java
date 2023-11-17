package uwu.lopyluna.create_dd.events;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.worldgen.DDOreFeatureConfigEntries;

@Mod.EventBusSubscriber
public class DDCommonEvents {


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        DDOreFeatureConfigEntries.modifyBiomes(event);
    }
}
