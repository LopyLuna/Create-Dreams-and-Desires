package uwu.lopyluna.create_dd.block.BlockProperties.copycat;

import com.simibubi.create.foundation.model.BakedModelHelper;
import com.simibubi.create.foundation.model.BakedQuadHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

import java.util.ArrayList;
import java.util.List;

public class BlockcopycatBlockModel extends com.simibubi.create.content.decoration.copycat.CopycatModel {

    protected static final AABB CUBE_AABB = new AABB(BlockPos.ZERO);

    public BlockcopycatBlockModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected List<BakedQuad> getCroppedQuads(BlockState state, Direction side, RandomSource rand, BlockState material,
                                              ModelData wrappedData, RenderType renderType) {
            BakedModel originalModel = getModelOf(material);
            if (originalModel instanceof BlockcopycatBlockModel impl)
                return impl.originalModel.getQuads(state, side, rand, wrappedData, renderType);

        BakedModel model = getModelOf(material);
        List<BakedQuad> templateQuads = model.getQuads(material, side, rand, wrappedData, renderType);

        List<BakedQuad> quads = new ArrayList<>();
        for (BakedQuad quad : templateQuads) {
            quads.add(BakedQuadHelper.cloneWithCustomGeometry(quad,
                    BakedModelHelper.cropAndMove(quad.getVertices(), quad.getSprite(), CUBE_AABB, Vec3.ZERO)));
        }

        return quads;
    }

    public static BlockcopycatBlockModel create(BakedModel bakedModel) {
        return new BlockcopycatBlockModel(bakedModel);
    }

}