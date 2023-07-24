package uwu.lopyluna.create_dd;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.data.AllLangPartials;
import com.simibubi.create.foundation.data.LangMerger;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.data.TagLangGen;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.foundation.data.recipe.StandardRecipeGen;
import com.simibubi.create.infrastructure.worldgen.AllOreFeatureConfigEntries;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import uwu.lopyluna.create_dd.config.DDConfigs;
import uwu.lopyluna.create_dd.content.worldgen.DDOreFeatures;
import uwu.lopyluna.create_dd.foundation.DDCommonEvents;
import uwu.lopyluna.create_dd.foundation.DDItemTabs;
import uwu.lopyluna.create_dd.foundation.DDLangPartials;
import uwu.lopyluna.create_dd.foundation.DDTags;
import uwu.lopyluna.create_dd.init.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.init.DDBlocks;
import uwu.lopyluna.create_dd.init.DDFluids;
import uwu.lopyluna.create_dd.init.DDItems;
import uwu.lopyluna.create_dd.init.DDPalette;
import uwu.lopyluna.create_dd.init.DDParticleTypes;
import uwu.lopyluna.create_dd.init.DDRecipeTypes;
import uwu.lopyluna.create_dd.init.DDSpriteShifts;

public class DDcreate implements ModInitializer {
	public static final String ID = "create_dd";
	public static final String NAME = "Create: Dreams & Desires";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final String VERSION = "ALPHA.0.0.5a";
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping()
			.create();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);

		DDItemTabs.init();
		DDFluids.registerFluidInteractions();
		DDCommonEvents.register();
		DDItems.register();
		DDFluids.register();
		DDBlocks.register();
		DDPalette.register();
		DDBlockEntityTypes.register();
		DDParticleTypes.register();
		DDRecipeTypes.register();
		REGISTRATE.register();

		//DDConfigs.register();
		//DDOreFeatures.init();
	}
	//public static void gatherData(FabricDataGenerator gen, ExistingFileHelper helper) {
	//	DDTags.init();
	//	//TagLangGen.datagen();
	//	gen.addProvider(new LangMerger(gen, ID, NAME, DDLangPartials.values()));
	//	//gen.addProvider(AllSoundEvents.provider(gen));
	//	//gen.addProvider(new AllAdvancements(gen));
	//	//gen.addProvider(new StandardRecipeGen(gen));
	//	//gen.addProvider(new MechanicalCraftingRecipeGen(gen));
	//	//gen.addProvider(new SequencedAssemblyRecipeGen(gen));
	//	//ProcessingRecipeGen.registerAll(gen);
	////	DDOreFeatures.gatherData(gen);
	//}
	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
	public static CreateRegistrate registrate() {
		return REGISTRATE;
	}
}
