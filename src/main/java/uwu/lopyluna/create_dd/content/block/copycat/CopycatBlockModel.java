package uwu.lopyluna.create_dd.content.block.copycat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.copycat.CopycatSpecialCases;
import com.simibubi.create.foundation.model.BakedModelHelper;
import com.simibubi.create.foundation.utility.Iterate;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CopycatBlockModel extends DDCopycatModel {

	protected static final AABB CUBE_AABB = new AABB(BlockPos.ZERO);

	public CopycatBlockModel(BakedModel originalModel) {
		super(originalModel);
	}
	@Override
	protected void emitBlockQuadsInner(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context, BlockState material, DDCopycatModel.CullFaceRemovalData cullFaceRemovalData, DDCopycatModel.OcclusionData occlusionData) {
		Direction facing = state.getOptionalValue(CopycatBlock.FACING)
				.orElse(Direction.UP);
		BlockRenderDispatcher blockRenderer = Minecraft.getInstance()
				.getBlockRenderer();

		BlockState specialCopycatModelState = null;
		if (CopycatSpecialCases.isBarsMaterial(material))
			specialCopycatModelState = AllBlocks.COPYCAT_BARS.getDefaultState();
		if (CopycatSpecialCases.isTrapdoorMaterial(material)) {
			((FabricBakedModel) blockRenderer.getBlockModel(material))
					.emitBlockQuads(blockView, state, pos, randomSupplier, context);
			return;
		}

		if (specialCopycatModelState != null) {
			BakedModel blockModel = blockRenderer
					.getBlockModel(specialCopycatModelState.setValue(DirectionalBlock.FACING, facing));
			if (blockModel instanceof DDCopycatModel cm) {
				cm.emitBlockQuadsInner(blockView, state, pos, randomSupplier, context, material, cullFaceRemovalData, occlusionData);
				return;
			}
		}

		BakedModel model = getModelOf(material);
		List<BakedQuad> templateQuads = model.getQuads(state, facing, randomSupplier.get());
		int size = templateQuads.size();
		SpriteFinder spriteFinder = SpriteFinder.get(Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS));

		List<BakedQuad> quads = new ArrayList<>();

		Vec3 normal = Vec3.atLowerCornerOf(facing.getNormal());
		Vec3 normalScaled16 = normal.scale(0);
		MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
		QuadEmitter emitter = meshBuilder.getEmitter();
		context.pushTransform(quad -> {
					if (cullFaceRemovalData.shouldRemove(quad.cullFace())) {
						quad.cullFace(null);
					} else if (occlusionData.isOccluded(quad.cullFace())) {
						// Add quad to mesh and do not render original quad to preserve quad render order
						// copyTo does not copy the material
						RenderMaterial quadMaterial = quad.material();
						quad.copyTo(emitter);
						emitter.material(quadMaterial);
						emitter.emit();
						return false;
					}
			for (boolean front : Iterate.trueAndFalse) {
				AABB bb = CUBE_AABB;

				Direction direction = quad.lightFace();

				if (front && direction == facing)
					continue;
				if (!front && direction == facing.getOpposite())
					continue;

				// copyTo does not copy the material
				RenderMaterial quadMaterial = quad.material();
				quad.copyTo(emitter);
				emitter.material(quadMaterial);
				BakedModelHelper.cropAndMove(emitter, spriteFinder.find(emitter, 0), bb, normalScaled16);
				emitter.emit();
			}

			return false;
		});
		((FabricBakedModel) model).emitBlockQuads(blockView, material, pos, randomSupplier, context);
		context.popTransform();
		context.meshConsumer().accept(meshBuilder.build());
	}
}
