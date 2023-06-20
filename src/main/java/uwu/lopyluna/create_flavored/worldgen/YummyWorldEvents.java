package uwu.lopyluna.create_flavored.worldgen;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_flavored.Flavoredcreate;


@Mod.EventBusSubscriber(modid = Flavoredcreate.MOD_ID)
public class YummyWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        YummyOreGen.generateOres(event);
    }
}