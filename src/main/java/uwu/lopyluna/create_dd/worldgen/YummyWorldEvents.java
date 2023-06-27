package uwu.lopyluna.create_dd.worldgen;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.DDcreate;


@Mod.EventBusSubscriber(modid = DDcreate.MOD_ID)
public class YummyWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        YummyOreGen.generateOres(event);
    }
}