package uwu.lopyluna.create_flavored.events;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_flavored.worldgen.YummyOreFeatures;

@Mod.EventBusSubscriber
public class PartyCommon {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        YummyOreFeatures.modifyBiomes(event);
    }
}
