package uwu.lopyluna.create_flavored.block;

import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

public class BoobaShape {


    public static final VoxelShape
            SMASHING_COLLISION_SHAPE = cuboid(0, 0, 0, 16, 16, 16);

    public static final VoxelShaper
            SMASHING_CONTROLLER_COLLISION = shape(0, 0, 0, 16, 13, 16).forDirectional(Direction.DOWN);


    private static Builder shape(VoxelShape shape) {
        return new BoobaShape.Builder(shape);
    }

    private static Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }
    private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static class Builder {

        private VoxelShape shape;

        public Builder(VoxelShape shape) {
            this.shape = shape;
        }

        public VoxelShape build() {
            return shape;
        }

        public VoxelShaper build(BiFunction<VoxelShape, Direction, VoxelShaper> factory, Direction direction) {
            return factory.apply(shape, direction);
        }
        public VoxelShaper forDirectional(Direction direction) {
            return build(VoxelShaper::forDirectional, direction);
        }
    }
}
