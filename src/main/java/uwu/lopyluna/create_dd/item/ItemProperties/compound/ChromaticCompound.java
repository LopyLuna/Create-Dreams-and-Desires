package uwu.lopyluna.create_dd.item.ItemProperties.compound;

import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import uwu.lopyluna.create_dd.item.DDItems;


public class ChromaticCompound extends Item {
    public ChromaticCompound(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level;
        CRecipes config = AllConfigs.server().recipes;

        double y = entity.getY();
        double yMotion = entity.getDeltaMovement().y;
        int minHeight = world.getMinBuildHeight();
        CompoundTag data = entity.getPersistentData();

        // Convert to Shadow steel if in void
        if (y < minHeight && y - yMotion < -10 + minHeight && config.enableShadowSteelRecipe.get()) {
            ItemStack newStack = DDItems.SHADOW_STEEL.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
        }

        if (!config.enableRefinedRadianceRecipe.get())
            return false;

        // Is inside beacon beam?
        boolean isOverBeacon = false;
        int entityX = Mth.floor(entity.getX());
        int entityZ = Mth.floor(entity.getZ());
        int localWorldHeight = world.getHeight(Heightmap.Types.WORLD_SURFACE, entityX, entityZ);

        BlockPos.MutableBlockPos testPos =
                new BlockPos.MutableBlockPos(entityX, Math.min(Mth.floor(entity.getY()), localWorldHeight), entityZ);

        while (testPos.getY() > 0) {
            testPos.move(Direction.DOWN);
            BlockState state = world.getBlockState(testPos);
            if (state.getLightBlock(world, testPos) >= 15 && state.getBlock() != Blocks.BEDROCK)
                break;
            if (state.getBlock() == Blocks.BEACON) {
                BlockEntity be = world.getBlockEntity(testPos);

                if (!(be instanceof BeaconBlockEntity))
                    break;

                BeaconBlockEntity bte = (BeaconBlockEntity) be;

                if (!bte.getBeamSections().isEmpty())
                    isOverBeacon = true;

                break;
            }
        }

        if (isOverBeacon) {
            ItemStack newStack = DDItems.REFINED_RADIANCE.asStack();
            newStack.setCount(stack.getCount());
            data.putBoolean("JustCreated", true);
            entity.setItem(newStack);
            return false;
        }

        return false;
    }
}
