package uwu.lopyluna.create_dd.foundation;

import static com.simibubi.create.AllTags.NameSpace.MOD;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import uwu.lopyluna.create_dd.DDcreate;

public class DDTags {
	public static <T> TagKey<T> optionalTag(Registry<T> registry,
											ResourceLocation id) {
		return TagKey.create(registry.key(), id);
	}

	public static <T> TagKey<T> forgeTag(Registry<T> registry, String path) {
		return optionalTag(registry, new ResourceLocation("c", path));
	}

	public static TagKey<Block> forgeBlockTag(String path) {
		return forgeTag(Registry.BLOCK, path);
	}

	public enum NameSpace {
		MOD(DDcreate.ID, false, true),
		FORGE("c"),
		TIC("tconstruct"),
		QUARK("quark")

		;

		public final String id;
		public final boolean optionalDefault;
		public final boolean alwaysDatagenDefault;

		NameSpace(String id) {
			this(id, true, false);
		}

		NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
			this.id = id;
			this.optionalDefault = optionalDefault;
			this.alwaysDatagenDefault = alwaysDatagenDefault;
		}
	}

	public enum AllBlockTags {

		potassic_replaceable,
		weathered_limestone_replaceable,
		limestone_replaceable,
		gabbro_replaceable,
		bronze_drill_immune,

		;

		public final TagKey<Block> tag;
		public final boolean alwaysDatagen;

		AllBlockTags() {
			this(MOD);
		}

		AllBlockTags(AllTags.NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllBlockTags(AllTags.NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllBlockTags(AllTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		AllBlockTags(AllTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			tag = optionalTag(Registry.BLOCK, id);
			this.alwaysDatagen = alwaysDatagen;
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Block block) {
			return block.builtInRegistryHolder()
					.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
		}

		public boolean matches(BlockState state) {
			return state.is(tag);
		}

		private static void init() {
		}


	}

	public static void init() {
		AllBlockTags.init();
	}
}
