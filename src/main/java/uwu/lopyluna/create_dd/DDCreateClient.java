package uwu.lopyluna.create_dd;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.rando.DDParticleTypes;

public class DDCreateClient {


    public static void onCtorClient(IEventBus eventBus) {

        eventBus.addListener(DDCreateClient::clientInit);
        eventBus.addListener(DDParticleTypes::registerFactories);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        DDBlockPartialModel.init();
    }
}
