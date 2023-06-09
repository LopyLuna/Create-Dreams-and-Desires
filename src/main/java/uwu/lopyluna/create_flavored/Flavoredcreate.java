package uwu.lopyluna.create_flavored;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_flavored.block.YIPPEE;
import uwu.lopyluna.create_flavored.entity.Bobert.SmartBuilderRenderer;
import uwu.lopyluna.create_flavored.entity.bobert;
import uwu.lopyluna.create_flavored.fluid.SussyWhiteStuff;
import uwu.lopyluna.create_flavored.item.Pipebomb;
import uwu.lopyluna.create_flavored.item.PipebombTab;
import uwu.lopyluna.create_flavored.worldgen.YummyOreFeatures;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flavoredcreate.MOD_ID)
public class Flavoredcreate
{
    public static final String MOD_ID = "create_flavored";
    public static final Logger LOGGER = LogUtils.getLogger();
    // Directly reference a slf4j logger

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Flavoredcreate.MOD_ID);

    public Flavoredcreate()
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(eventBus);

        PipebombTab.init();
        YIPPEE.register();
        Pipebomb.register();
        SussyWhiteStuff.register();
        bobert.register(eventBus);
        YummyOreFeatures.init();


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }
    private void clientSetup(final FMLClientSetupEvent event) {
    EntityRenderers.register(bobert.SMART_BUILDER.get(), SmartBuilderRenderer::new);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
