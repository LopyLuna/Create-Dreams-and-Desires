package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.Processing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface InterfaceIndustrialProcessingType {
    boolean isValidAt(Level level, BlockPos pos);

    int getPriority();

    boolean canProcess(ItemStack stack, Level level);

    @Nullable
    List<ItemStack> process(ItemStack stack, Level level);

    void spawnProcessingParticles(Level level, Vec3 pos);

    void morphAirFlow(DDAirFlowParticleAccess particleAccess, RandomSource random);

    void affectEntity(Entity entity, Level level);

    static InterfaceIndustrialProcessingType parse(String str) {
        ResourceLocation id = ResourceLocation.tryParse(str);
        if (id == null) {
            return IndustrialTypeFanProcessing.NONE;
        }
        InterfaceIndustrialProcessingType type = DDFanProcessingTypeRegistry.getType(id);
        if (type == null) {
            return IndustrialTypeFanProcessing.NONE;
        }
        return type;
    }

    static InterfaceIndustrialProcessingType getAt(Level level, BlockPos pos) {
        for (InterfaceIndustrialProcessingType type : DDFanProcessingTypeRegistry.getSortedTypesView()) {
            if (type.isValidAt(level, pos)) {
                return type;
            }
        }
        return IndustrialTypeFanProcessing.NONE;
    }

    interface DDAirFlowParticleAccess {
        void setColor(int color);

        void setAlpha(float alpha);

        void spawnExtraParticle(ParticleOptions options, float speedMultiplier);
    }
}
