package uwu.lopyluna.create_dd;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.AllLangPartials;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.LangMerger;
import com.simibubi.create.foundation.data.TagGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.slf4j.Logger;
import uwu.lopyluna.create_dd.block.YIPPEE;
import uwu.lopyluna.create_dd.block.YIPPEEEntityTypes;
import uwu.lopyluna.create_dd.block.YIPPEEPalette;
import uwu.lopyluna.create_dd.block.YIPPEEPartialModel;
import uwu.lopyluna.create_dd.fluid.SussyWhiteStuff;
import uwu.lopyluna.create_dd.item.Pipebomb;
import uwu.lopyluna.create_dd.item.PipebombTab;


@Mod(DDcreate.MOD_ID)
public class DDcreate
{
    public static final String NAME = "Create: Flavored";
    public static final String MOD_ID = "create_dd";
    public static final String VERSION = "ALPHA.0.0.1a";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(DDcreate.MOD_ID);

    public DDcreate()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(eventBus);

        PipebombTab.init();
        YIPPEEPartialModel.init();
        YIPPEEEntityTypes.register();
        YIPPEE.register();
        Pipebomb.register();
        SussyWhiteStuff.register();
        YIPPEEPalette.register();


        eventBus.addListener(this::clientSetup);

        eventBus.addListener(EventPriority.LOWEST, DDcreate::gatherData);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }


    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    public static void gatherData(GatherDataEvent event) {
        TagGen.datagen();
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(new LangMerger(gen, DDcreate.MOD_ID, NAME, AllLangPartials.values()));
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(DDcreate.MOD_ID, path);
    }

}
