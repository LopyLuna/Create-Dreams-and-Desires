package uwu.lopyluna.create_dd.block.BlockProperties.flywheel.engine;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import uwu.lopyluna.create_dd.block.BlockResources.DDBlockPartialModel;
import uwu.lopyluna.create_dd.block.DDBlockEntityTypes;
import uwu.lopyluna.create_dd.block.DDBlocks;

@EventBusSubscriber
public class FurnaceEngineBlock extends EngineBlock implements IBE<FurnaceEngineBlockEntity> {

    public FurnaceEngineBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidBaseBlock(BlockState baseBlock, BlockGetter world, BlockPos pos) {
        return FurnaceEngineInteractions.getHandler(baseBlock).getHeatSource(baseBlock).isValid();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.FURNACE_ENGINE.get(state.getValue(FACING));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public PartialModel getFrameModel() {
        return DDBlockPartialModel.FURNACE_GENERATOR_FRAME;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (worldIn instanceof WrappedWorld)
            return;
        if (worldIn.isClientSide)
            return;

        if (fromPos.equals(getBaseBlockPos(state, pos)))
            if (canSurvive(state, worldIn, pos))
                withBlockEntityDo(worldIn, pos, FurnaceEngineBlockEntity::updateFurnace);
    }

    @SubscribeEvent
    public static void usingFurnaceEngineOnFurnacePreventsGUI(RightClickBlock event) {
        ItemStack item = event.getItemStack();
        if (!(item.getItem() instanceof BlockItem blockItem))
            return;
        if (blockItem.getBlock() != DDBlocks.FURNACE_ENGINE.get())
            return;
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (event.getFace().getAxis().isVertical())
            return;
        if (state.getBlock() instanceof AbstractFurnaceBlock)
            event.setUseBlock(Result.DENY);
    }

    @Override
    public Class<FurnaceEngineBlockEntity> getBlockEntityClass() {
        return FurnaceEngineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FurnaceEngineBlockEntity> getBlockEntityType() {
        return DDBlockEntityTypes.FURNACE_ENGINE.get();
    }


}