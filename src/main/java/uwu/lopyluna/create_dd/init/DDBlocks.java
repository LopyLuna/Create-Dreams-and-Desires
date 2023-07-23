package uwu.lopyluna.create_dd.init;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static uwu.lopyluna.create_dd.DDcreate.registrate;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import uwu.lopyluna.create_dd.foundation.DDItemTabs;

public class DDBlocks {
	private static final CreateRegistrate REGISTRATE = registrate()
			.creativeModeTab(() -> DDItemTabs.BASE_CREATIVE_TAB);

	public static final BlockEntry<Block> andesite_asphalt_block = REGISTRATE.block("andesite_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.STONE))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> asurine_asphalt_block = REGISTRATE.block("asurine_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.COLOR_BLUE))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> calcite_asphalt_block = REGISTRATE.block("calcite_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_WHITE))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> crimsite_asphalt_block = REGISTRATE.block("crimsite_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.COLOR_RED))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> deepslate_asphalt_block = REGISTRATE.block("deepslate_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.DEEPSLATE))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> diorite_asphalt_block = REGISTRATE.block("diorite_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.QUARTZ))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> dripstone_asphalt_block = REGISTRATE.block("dripstone_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_BROWN))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DRIPSTONE_BLOCK))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> gabbro_asphalt_block = REGISTRATE.block("gabbro_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_LIGHT_GRAY))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> granite_asphalt_block = REGISTRATE.block("granite_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_CYAN))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> limestone_asphalt_block = REGISTRATE.block("limestone_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.SAND))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> ochrum_asphalt_block = REGISTRATE.block("ochrum_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_YELLOW))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.CALCITE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> potassic_asphalt_block = REGISTRATE.block("potassic_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_BLUE))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> scorchia_asphalt_block = REGISTRATE.block("scorchia_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_GRAY))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> scoria_asphalt_block = REGISTRATE.block("scoria_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.COLOR_BROWN))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> tuff_asphalt_block = REGISTRATE.block("tuff_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.TERRACOTTA_GRAY))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> veridium_asphalt_block = REGISTRATE.block("veridium_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.WARPED_NYLIUM))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.TUFF))
			.simpleItem()
			.register();

	public static final BlockEntry<Block> weathered_limestone_asphalt_block = REGISTRATE.block("weathered_limestone_asphalt_block", Block::new)
			.properties(p -> p.destroyTime(1.25f)
					.speedFactor(1.2F)
					.jumpFactor(1.2F)
					.friction(0.6F)
					.color(MaterialColor.COLOR_LIGHT_GRAY))
			.properties(p -> p.requiresCorrectToolForDrops().sound(SoundType.STONE))
			.simpleItem()
			.register();
	public static final BlockEntry<Block> potassic_cobble =
			REGISTRATE.block("potassic_cobble", Block::new)
					.initialProperties(() -> Blocks.DEEPSLATE)
					.properties(p -> p.destroyTime(2.25f).color(MaterialColor.TERRACOTTA_BLUE))
					.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
					.transform(pickaxeOnly())
					.simpleItem()
					.register();

	public static final BlockEntry<Block> asurine_cobble =
			REGISTRATE.block("asurine_cobble", Block::new)
					.initialProperties(() -> Blocks.DEEPSLATE)
					.properties(p -> p.destroyTime(2.25f).color(MaterialColor.COLOR_BLUE))
					.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
					.transform(pickaxeOnly())
					.simpleItem()
					.register();

	public static final BlockEntry<Block> crimsite_cobble =
			REGISTRATE.block("crimsite_cobble", Block::new)
					.initialProperties(() -> Blocks.DEEPSLATE)
					.properties(p -> p.destroyTime(2.25f).color(MaterialColor.COLOR_RED))
					.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
					.transform(pickaxeOnly())
					.simpleItem()
					.register();

	public static final BlockEntry<Block> ochrum_cobble =
			REGISTRATE.block("ochrum_cobble", Block::new)
					.initialProperties(() -> Blocks.CALCITE)
					.properties(p -> p.destroyTime(2.25f).color(MaterialColor.TERRACOTTA_YELLOW))
					.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
					.transform(pickaxeOnly())
					.simpleItem()
					.register();

	public static final BlockEntry<Block> veridium_cobble =
			REGISTRATE.block("veridium_cobble", Block::new)
					.initialProperties(() -> Blocks.TUFF)
					.properties(p -> p.destroyTime(2.25f).color(MaterialColor.WARPED_NYLIUM))
					.properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
					.transform(pickaxeOnly())
					.simpleItem()
					.register();


	public static void register() {}
}
