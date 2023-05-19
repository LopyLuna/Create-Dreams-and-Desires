package uwu.lopyluna.create_battles.block;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.contraptions.base.CasingBlock;
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
import uwu.lopyluna.create_battles.Battlescreate;
import uwu.lopyluna.create_battles.item.Pipebomb;
import uwu.lopyluna.create_battles.item.PipebombTab;
import java.util.function.Supplier;
import static uwu.lopyluna.create_battles.Battlescreate.REGISTRATE;

public class YIPPEE {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Battlescreate.MOD_ID);


    //YIPPEE BLOCK PIPEBOMB YUMMY
    public static final RegistryObject<Block> mithril_block = registerBlock("mithril_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(9f,9f).requiresCorrectToolForDrops()), PipebombTab.BattleCreate_TAB);
    public static final RegistryObject<Block> steel_block = registerBlock("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(9f,9f).requiresCorrectToolForDrops()), PipebombTab.BattleCreate_TAB);
    public static final BlockEntry<CasingBlock> MITHRIL_CASING = REGISTRATE.block("mithril_casing", CasingBlock::new)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(BuilderTransformers.casing(() -> AllSpriteShifts.BRASS_CASING))
            .simpleItem()
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
}
