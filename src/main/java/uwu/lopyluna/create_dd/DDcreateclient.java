package uwu.lopyluna.create_dd;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uwu.lopyluna.create_dd.block.YIPPEEPartialModel;
import uwu.lopyluna.create_dd.rando.DDParticleTypes;

public class DDcreateclient {


    public static void onCtorClient(IEventBus eventBus) {

        eventBus.addListener(DDcreateclient::clientInit);
        eventBus.addListener(DDParticleTypes::registerFactories);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        YIPPEEPartialModel.init();
    }
}
