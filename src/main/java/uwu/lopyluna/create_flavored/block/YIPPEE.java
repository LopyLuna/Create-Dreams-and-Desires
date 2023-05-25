package uwu.lopyluna.create_flavored.block;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.create_flavored.Flavoredcreate;
import uwu.lopyluna.create_flavored.item.Pipebomb;
import uwu.lopyluna.create_flavored.item.PipebombTab;
import java.util.function.Supplier;

import static uwu.lopyluna.create_flavored.Flavoredcreate.REGISTRATE;

public class YIPPEE {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Flavoredcreate.MOD_ID);


    //YIPPEE BLOCK PIPEBOMB YUMMY
    public static final RegistryObject<Block> mithril_block = registerBlock("mithril_block", () -> new Block(BlockBehaviour.Properties
            .of(Material.METAL).sound(SoundType.NETHERITE_BLOCK)
            .strength(10f,24f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);
    public static final RegistryObject<Block> bronze_block = registerBlock("bronze_block", () -> new Block(BlockBehaviour.Properties
            .of(Material.METAL).sound(SoundType.NETHERITE_BLOCK)
            .strength(8f,10f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);
    public static final RegistryObject<Block> steel_block = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties
            .of(Material.METAL)
            .sound(SoundType.NETHERITE_BLOCK)
            .strength(6f,16f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);

    public static final RegistryObject<Block> tin_ore = registerBlock("tin_ore", () -> new Block(BlockBehaviour.Properties
            .of(Material.STONE)
            .sound(SoundType.STONE)
            .strength(3f,3f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);

    public static final RegistryObject<Block> deepslate_tin_ore = registerBlock("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties
            .of(Material.STONE)
            .sound(SoundType.DEEPSLATE)
            .strength(4.5f,3f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);

    public static final RegistryObject<Block> tin_block = registerBlock("tin_block", () -> new Block(BlockBehaviour.Properties
            .of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(3f,6f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);

    public static final RegistryObject<Block> raw_tin_block = registerBlock("raw_tin_block", () -> new Block(BlockBehaviour.Properties
            .of(Material.STONE)
            .sound(SoundType.STONE)
            .strength(5f,6f)
            .requiresCorrectToolForDrops()
    ), PipebombTab.FlavoredCreate_TAB);


    //BLOKYENTRY
    public static final BlockEntry<CasingBlock> MITHRIL_CASING = REGISTRATE.block("mithril_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.MITHRIL_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_CYAN))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .lang("Mithril Casing")
            .register();

    public static final BlockEntry<CasingBlock> BRONZE_CASING = REGISTRATE.block("bronze_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> YIPPEESpriteShifts.BRONZE_CASING))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_RED))
            .properties(p -> p.sound(SoundType.WOOD))
            .lang("Bronze Casing")
            .register();

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                           CreativeModeTab tab) {
        return Pipebomb.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    public static void register() {}
}
