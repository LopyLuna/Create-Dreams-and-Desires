package dev.lopyluna.dndesires.content.blocks.logistics.bore_block;

import com.simibubi.create.content.contraptions.ContraptionWorld;
import dev.lopyluna.dndesires.compat.LoadedMods;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@SuppressWarnings("NullableProblems")
public class BoreBlock extends Block {

    private static AABB simpleBox = new AABB(0.0, 0.0, 0.0, 0.5, 0.5, 0.5);
    static final VoxelShape[] subShapes = new VoxelShape[]{
        shift(simpleBox, false, false, false),
        shift(simpleBox, false, false, true),
        shift(simpleBox, false, true, false),
        shift(simpleBox, false, true, true),
        shift(simpleBox, true, false, false),
        shift(simpleBox, true, false, true),
        shift(simpleBox, true, true, false),
        shift(simpleBox, true, true, true),
    };
    static final VoxelShape[] defCollisionShapes = Util.make(() -> {
        VoxelShape[] shapes = new VoxelShape[0b11111111 + 1];
        for(int state = 0; state < shapes.length; state++) {
            VoxelShape cur = Shapes.empty();
            for(int simpleShapeI = 0; simpleShapeI < subShapes.length; simpleShapeI++) {
                if((state & (1 << simpleShapeI)) != 0) {
                    cur = Shapes.or(cur, subShapes[simpleShapeI]);
                }
            }
            shapes[state] = cur;
        }
        shapes[shapes.length - 1] = Shapes.block();
        return shapes;
    });
    public static final IntegerProperty BORE_STATE = IntegerProperty.create("bore_state", 0, defCollisionShapes.length - 1);

    protected static final int[] bitMasks = Util.make(() -> {
        int[] masks = new int[8];
        int zero = 1;
        for(int i = 0; i < 8; i++) {
            int dx = (i & 4) == 0 ? 0 : 2;
            int dy = (i & 2) == 0 ? 0 : 2;
            int dz = (i & 1) == 0 ? 0 : 2;

            int mask = 0;
            mask |= 1 << (dx + zero * 3 + zero * 9);
            mask |= 1 << (zero + dy * 3 + zero * 9);
            mask |= 1 << (zero + zero * 3 + dz * 9);
            mask |= 1 << (dx + dy * 3 + zero * 9);
            mask |= 1 << (zero + dy * 3 + dz * 9);
            mask |= 1 << (dx + zero * 3 + dz * 9);
            mask |= 1 << (dx + dy * 3 + dz * 9);
            masks[i] = mask;


        }
        return masks;
    });
    public final boolean isSable;

    protected static VoxelShape shift(AABB aabb, boolean isEast, boolean isTop, boolean isSouth) {
        return Shapes.create(aabb.move(new Vec3(isEast ? 0.5 : 0, isTop ? 0.5 : 0, isSouth ? 0.5 : 0)));
    }

    protected final VoxelShape[] collisionShapes = defs();

    protected VoxelShape @NotNull [] defs() {
        return defCollisionShapes;
    }

    protected BoreBlock(Properties properties, boolean isSable) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(BORE_STATE, 15));
        this.isSable = isSable;
    }

    private BoreBlock(Properties properties) {
        this(properties, false);
        registerDefaultState(getStateDefinition().any().setValue(BORE_STATE, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BORE_STATE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(!context.equals(CollisionContext.empty())) return super.getShape(state, level, pos, context);
        if(pos == BlockPos.ZERO && level.getBlockState(pos) != state) return super.getShape(state, level, pos, context);
        if(!(level instanceof ContraptionWorld) && !(level instanceof SubLevel))
            return super.getShape(state, level, pos, context);

        return collisionShapes[state.getValue(BORE_STATE)];
    }

    private int getShapeState(BlockGetter level, BlockPos pos) {
        int shapeState = 0;
        int airNeighbours = 0;
        int count = 0;
        int posZ = pos.getZ();
        int posY = pos.getY();
        int posX = pos.getX();
        for(BlockPos blockPos : BlockPos.betweenClosed(posX - 1, posY - 1, posZ - 1, posX + 1, posY + 1, posZ + 1)) {
            if(!isMe(level.getBlockState(blockPos))) {
                airNeighbours |= 1 << count;
            }
            count++;
        }

        for(int i = 0; i < 8; i++) {
            if((bitMasks[i] & airNeighbours) != 0) continue;
            shapeState |= 1 << i;
        }
        return shapeState;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        //state.updateNeighbourShapes(level, pos, 3);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return defaultBlockState().setValue(BORE_STATE, getShapeState(level, pos));
        //return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private boolean isMe(BlockState state) {
        return state.getBlock() instanceof BoreBlock;
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }


    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    public static BoreBlock make(Properties properties) {
        Function<Properties, BoreBlock> sableBoreMaker = LoadedMods.sableBoreMaker;
        if(sableBoreMaker != null) return sableBoreMaker.apply(properties);
        return new BoreBlock(properties);
    }
}
