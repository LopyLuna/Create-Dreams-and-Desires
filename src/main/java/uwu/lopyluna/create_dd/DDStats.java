package uwu.lopyluna.create_dd;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.item.KineticStats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialFanBlock;

public class DDStats extends KineticStats {
    public DDStats(Block block) {
        super(block);
    }

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof IRotate || block instanceof IndustrialFanBlock) {
                return new KineticStats(block);
            }
        }
        return null;
    }
}
