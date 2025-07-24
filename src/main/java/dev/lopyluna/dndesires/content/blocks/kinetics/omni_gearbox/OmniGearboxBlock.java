package dev.lopyluna.dndesires.content.blocks.kinetics.omni_gearbox;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndesires.register.DesiresBETypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.NotNull;

public class OmniGearboxBlock extends KineticBlock implements IBE<OmniGearboxBE> {
    public static final BooleanProperty UP_SHAFT = BooleanProperty.create("top_shaft");
    public static final BooleanProperty DOWN_SHAFT = BooleanProperty.create("bottom_shaft");
    public static final BooleanProperty NORTH_SHAFT = BooleanProperty.create("north_shaft");
    public static final BooleanProperty EAST_SHAFT = BooleanProperty.create("east_shaft");
    public static final BooleanProperty SOUTH_SHAFT = BooleanProperty.create("south_shaft");
    public static final BooleanProperty WEST_SHAFT = BooleanProperty.create("west_shaft");

    public OmniGearboxBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(UP_SHAFT, true)
                .setValue(DOWN_SHAFT, true)
                .setValue(NORTH_SHAFT, true)
                .setValue(EAST_SHAFT, true)
                .setValue(SOUTH_SHAFT, true)
                .setValue(WEST_SHAFT, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP_SHAFT, DOWN_SHAFT, NORTH_SHAFT, EAST_SHAFT, SOUTH_SHAFT, WEST_SHAFT);
    }

    @Override
    public InteractionResult onWrenched(BlockState pState, UseOnContext pContext) {
        var pLevel = pContext.getLevel();
        var clickedFace = pContext.getClickedFace();
        var pStateNew = pState;
        var pClicked = pContext.getClickedPos();

        pStateNew = clickedFace == Direction.UP ? pStateNew.cycle(UP_SHAFT) : pStateNew;
        pStateNew = clickedFace == Direction.DOWN ? pStateNew.cycle(DOWN_SHAFT) : pStateNew;
        pStateNew = clickedFace == Direction.NORTH ? pStateNew.cycle(NORTH_SHAFT) : pStateNew;
        pStateNew = clickedFace == Direction.EAST ? pStateNew.cycle(EAST_SHAFT) : pStateNew;
        pStateNew = clickedFace == Direction.SOUTH ? pStateNew.cycle(SOUTH_SHAFT) : pStateNew;
        pStateNew = clickedFace == Direction.WEST ? pStateNew.cycle(WEST_SHAFT) : pStateNew;

        detachKinetics(pLevel, pClicked, true);
        KineticBlockEntity.switchToBlockState(pLevel, pClicked, updateAfterWrenched(pStateNew, pContext));
        //withBlockEntityDo(pLevel, pClicked, KineticBlockEntity::clearKineticInformation);

        //if (pContext.getLevel().getBlockEntity(pClicked) instanceof OmniGearboxBE be) be.setChanged();

        if (pLevel.getBlockState(pClicked) != pState) AllSoundEvents.WRENCH_ROTATE.playOnServer(pLevel, pClicked, 1, pLevel.random.nextFloat() + .5f);

        return InteractionResult.SUCCESS;
    }


    public void detachKinetics(Level pLevel, BlockPos pPos, boolean reAttachNextTick) {
        var be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof KineticBlockEntity)) return;
        RotationPropagator.handleRemoved(pLevel, pPos, (KineticBlockEntity) be);
        if (reAttachNextTick) pLevel.scheduleTick(pPos, this, 1, TickPriority.EXTREMELY_HIGH);
    }

    @Override
    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        var be = level.getBlockEntity(pos);
        if (!(be instanceof KineticBlockEntity kbe)) return;
        RotationPropagator.handleAdded(level, pos, kbe);
    }

    @Override
    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.PUSH_ONLY;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState pState) {
        return Direction.Axis.Y;
    }

    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        var NORTH = state.getValue(NORTH_SHAFT);
        var EAST = state.getValue(EAST_SHAFT);
        var SOUTH = state.getValue(SOUTH_SHAFT);
        var WEST = state.getValue(WEST_SHAFT);
        return switch (rotation) {
            case NONE -> state;
            case CLOCKWISE_90 -> state.setValue(NORTH_SHAFT, WEST).setValue(SOUTH_SHAFT, EAST).setValue(EAST_SHAFT, NORTH).setValue(WEST_SHAFT, SOUTH);
            case CLOCKWISE_180 -> state.setValue(NORTH_SHAFT, SOUTH).setValue(SOUTH_SHAFT, NORTH).setValue(EAST_SHAFT, WEST).setValue(WEST_SHAFT, EAST);
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH_SHAFT, EAST).setValue(SOUTH_SHAFT, WEST).setValue(EAST_SHAFT, SOUTH).setValue(WEST_SHAFT, NORTH);
        };
    }

    @Override
    protected @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return switch (mirror) {
            case NONE -> state;
            case LEFT_RIGHT -> state.setValue(NORTH_SHAFT, state.getValue(SOUTH_SHAFT)).setValue(SOUTH_SHAFT, state.getValue(NORTH_SHAFT));
            case FRONT_BACK -> state.setValue(EAST_SHAFT, state.getValue(WEST_SHAFT)).setValue(WEST_SHAFT, state.getValue(EAST_SHAFT));
        };
    }

    @Override
    public boolean hasShaftTowards(LevelReader pLevel, BlockPos pPos, BlockState pState, Direction pFace) {
        return (pFace == Direction.UP && pState.getValue(UP_SHAFT)) ||
                (pFace == Direction.DOWN && pState.getValue(DOWN_SHAFT)) ||
                (pFace == Direction.NORTH && pState.getValue(NORTH_SHAFT)) ||
                (pFace == Direction.EAST && pState.getValue(EAST_SHAFT)) ||
                (pFace == Direction.SOUTH && pState.getValue(SOUTH_SHAFT)) ||
                (pFace == Direction.WEST && pState.getValue(WEST_SHAFT));
    }

    @Override
    public Class<OmniGearboxBE> getBlockEntityClass() {
        return OmniGearboxBE.class;
    }

    @Override
    public BlockEntityType<? extends OmniGearboxBE> getBlockEntityType() {
        return DesiresBETypes.GEARBOX.get();
    }
}
