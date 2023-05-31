package uwu.lopyluna.create_flavored.item.ItemProperties;


import com.simibubi.create.content.equipment.zapper.PlacementPatterns;
import com.simibubi.create.content.equipment.zapper.ZapperItem;
import com.simibubi.create.content.equipment.zapper.terrainzapper.*;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class BlockZapperItem extends ZapperItem {

    public BlockZapperItem(Properties properties) {
        super(properties);
    }

    @Override
    protected int getZappingRange(ItemStack stack) {
        return 32;
    }

    @Override
    protected int getCooldownDelay(ItemStack item) {
        return 8;
    }


    @Override
    public Component validateUsage(ItemStack item) {
        if (!item.getOrCreateTag()
                .contains("BrushParams"))
            return Lang.translateDirect("terrainzapper.shiftRightClickToSet");
        return super.validateUsage(item);
    }

    @Override
    protected boolean activate(Level world, Player player, ItemStack item, BlockState stateToUse, BlockHitResult raytrace, CompoundTag data) {

        return false;
    }

    @Override
    protected boolean canActivateWithoutSelectedBlock(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        TerrainTools tool = NBTHelper.readEnum(tag, "Tool", TerrainTools.class);
        return !tool.requiresSelectedBlock();
    }

    public static void configureSettings(ItemStack stack, PlacementPatterns pattern, TerrainBrushes brush,
                                         int brushParamX, int brushParamY, int brushParamZ, TerrainTools tool, PlacementOptions placement) {
        ZapperItem.configureSettings(stack, pattern);
        CompoundTag nbt = stack.getOrCreateTag();
        NBTHelper.writeEnum(nbt, "Brush", brush);
        nbt.put("BrushParams", NbtUtils.writeBlockPos(new BlockPos(brushParamX, brushParamY, brushParamZ)));
        NBTHelper.writeEnum(nbt, "Tool", tool);
        NBTHelper.writeEnum(nbt, "Placement", placement);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient( Consumer<IItemRenderProperties> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new WorldshaperItemRenderer()));
    }


    @Override
    protected void openHandgunGUI(ItemStack item, InteractionHand hand) {

    }

}