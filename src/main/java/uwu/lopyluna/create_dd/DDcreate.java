package uwu.lopyluna.create_dd;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.AllLangPartials;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.LangMerger;
import com.simibubi.create.foundation.data.TagGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.create_dd.block.YIPPEE;
import uwu.lopyluna.create_dd.block.YIPPEEEntityTypes;
import uwu.lopyluna.create_dd.block.YIPPEEPalette;
import uwu.lopyluna.create_dd.block.YIPPEEPartialModel;
import uwu.lopyluna.create_dd.fluid.SussyWhiteStuff;
import uwu.lopyluna.create_dd.item.Pipebomb;
import uwu.lopyluna.create_dd.item.PipebombTab;
import uwu.lopyluna.create_dd.rando.DDParticleTypes;
import uwu.lopyluna.create_dd.recipes.BakingRecipesTypes;
import uwu.lopyluna.create_dd.worldgen.YummyOreFeatures;
import uwu.lopyluna.create_dd.worldgen.YummyOrePlacedFeatures;
import uwu.lopyluna.create_dd.worldgen.YummyTags;


@Mod(DDcreate.MOD_ID)
public class DDcreate
{
    public static final String NAME = "Create: Flavored";
    public static final String MOD_ID = "create_dd";
    public static final String VERSION = "ALPHA.0.0.3a";
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

        DDParticleTypes.register(eventBus);
        BakingRecipesTypes.register(eventBus);

        YummyTags.init();
        YummyOreFeatures.register(eventBus);
        YummyOrePlacedFeatures.register(eventBus);

        eventBus.addListener(this::clientSetup);

        eventBus.addListener(DDcreate::init);
        eventBus.addListener(EventPriority.LOWEST, DDcreate::gatherData);


        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DDcreateclient.onCtorClient(eventBus));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SussyWhiteStuff.registerFluidInteractions();
        });
    }
    private void clientSetup(final FMLClientSetupEvent event) {
    }

    public static void gatherData(GatherDataEvent event) {
        TagGen.datagen();
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(true, new LangMerger(gen, MOD_ID, NAME, AllLangPartials.values()));
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(DDcreate.MOD_ID, path);
    }

}
