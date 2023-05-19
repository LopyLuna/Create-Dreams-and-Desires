package uwu.lopyluna.create_battles;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_battles.block.YIPPEE;
import uwu.lopyluna.create_battles.item.Pipebomb;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Battlescreate.MOD_ID)
public class Battlescreate
{
    public static final String MOD_ID = "create_battles";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Battlescreate.MOD_ID);

    public Battlescreate()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Pipebomb.register(eventBus);
        YIPPEE.register(eventBus);

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
