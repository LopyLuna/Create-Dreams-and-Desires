package uwu.lopyluna.create_flavored;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.worldgen.AllOreFeatureConfigEntries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_flavored.block.YIPPEE;
import uwu.lopyluna.create_flavored.block.YIPPEEentitytypes;
import uwu.lopyluna.create_flavored.item.Pipebomb;
import uwu.lopyluna.create_flavored.item.PipebombTab;
import uwu.lopyluna.create_flavored.worldgen.YummyOreFeatures;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flavoredcreate.MOD_ID)
public class Flavoredcreate
{
    public static final String MOD_ID = "create_flavored";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

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
        YIPPEEentitytypes.register();
        YummyOreFeatures.init();


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
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
