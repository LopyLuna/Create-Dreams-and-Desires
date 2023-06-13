package uwu.lopyluna.create_flavored;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_flavored.block.YIPPEE;
import uwu.lopyluna.create_flavored.block.YIPPEEPalette;
import uwu.lopyluna.create_flavored.block.YIPPEEPaletteStoneTypes;
import uwu.lopyluna.create_flavored.fluid.SussyWhiteStuff;
import uwu.lopyluna.create_flavored.item.Pipebomb;
import uwu.lopyluna.create_flavored.item.PipebombTab;
import uwu.lopyluna.create_flavored.worldgen.YummyOreFeatures;


@Mod(Flavoredcreate.MOD_ID)
public class Flavoredcreate
{
    public static final String MOD_ID = "create_flavored";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Flavoredcreate.MOD_ID);

    public Flavoredcreate()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(eventBus);

        PipebombTab.init();
        YIPPEE.register();
        Pipebomb.register();
        SussyWhiteStuff.register();
        YIPPEEPalette.register();
        YummyOreFeatures.init();


        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }


    private void clientSetup(final FMLClientSetupEvent event) {
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

}
