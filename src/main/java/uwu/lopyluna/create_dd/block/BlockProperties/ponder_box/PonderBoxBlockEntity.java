package uwu.lopyluna.create_dd.block.BlockProperties.ponder_box;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PonderBoxBlockEntity extends BlockEntity {
    public PonderBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private BlockPos pos;

    @Override
    public void load(CompoundTag tag) {
        if (tag.get("teleportPos") != null) this.pos = NbtUtils.readBlockPos((CompoundTag) Objects.requireNonNull(tag.get("teleportPos")));

        super.load(tag);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if (pos != null) tag.put("teleportPos", NbtUtils.writeBlockPos(pos));
    }

    public BlockPos getTeleportPos() {
        return pos;
    }

    public void setTeleportPos(BlockPos teleportPos) {
        this.pos = teleportPos;
    }
}