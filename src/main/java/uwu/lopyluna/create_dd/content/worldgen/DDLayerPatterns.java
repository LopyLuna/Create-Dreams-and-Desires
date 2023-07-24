package uwu.lopyluna.create_dd.content.worldgen;

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.infrastructure.worldgen.LayerPattern;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.world.level.block.Blocks;
import uwu.lopyluna.create_dd.content.decoration.DDPaletteStoneTypes;

public class DDLayerPatterns {

	public static final NonNullSupplier<LayerPattern>
			potassic = () -> LayerPattern.builder()
			.layer(l -> l.weight(2)
					.block(DDPaletteStoneTypes.potassic.getBaseBlock())
					.size(2, 5))
			.layer(l -> l.weight(1)
					.block(Blocks.TUFF)
					.block(Blocks.DEEPSLATE)
					.size(2, 3))
			.layer(l -> l.weight(1)
					.blocks(Blocks.DEEPSLATE, Blocks.TUFF)
					.size(2, 2))
			.layer(l -> l.weight(1)
					.block(AllPaletteStoneTypes.ASURINE.getBaseBlock())
					.size(1, 2))
			.build();
	public static final NonNullSupplier<LayerPattern> gabbro = () -> LayerPattern.builder()
			.layer(l -> l.weight(2)
					.block(DDPaletteStoneTypes.gabbro.getBaseBlock())
					.size(2, 5))
			.layer(l -> l.weight(1)
					.block(Blocks.TUFF)
					.block(Blocks.DEEPSLATE)
					.size(2, 3))
			.layer(l -> l.weight(1)
					.blocks(Blocks.DEEPSLATE, Blocks.TUFF)
					.size(2, 2))
			.layer(l -> l.weight(1)
					.block(Blocks.CALCITE)
					.size(1, 2))
			.build();
	public static final NonNullSupplier<LayerPattern> weathered_limestone = () -> LayerPattern.builder()
			.layer(l -> l.weight(1)
					.passiveBlock())
			.layer(l -> l.weight(2)
					.block(Blocks.CALCITE))
			.layer(l -> l.weight(1)
					.block(Blocks.DIORITE))
			.layer(l -> l.weight(2)
					.block(DDPaletteStoneTypes.weathered_limestone.getBaseBlock())
					.size(2, 4))
			.build();
}
