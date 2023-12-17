package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.configs.DDConfigs;
import uwu.lopyluna.create_dd.sounds.DDSoundEvents;

public class ShadowPanelBlockEntity extends GeneratingKineticBlockEntity {
    public ShadowPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {super(type, pos, state);}
    BlockState state = getBlockState();
    int i = 0;
    int i2 = 0;
    public static boolean active = false;
    public static boolean weak_active = false;

    public float Generator() {

        assert level != null;
        float dt = level.dayTime() % 24000;

        if (!DDBlocks.SHADOW_PANEL.has(getBlockState()))
            return 0;

        boolean night_min_time =
                dt > DDConfigs.server().kinetics.night_min_time.get() &&
                        dt < DDConfigs.server().kinetics.night_max_time.get();
        boolean midnight_min_time =
                dt > DDConfigs.server().kinetics.midnight_min_time.get() &&
                        dt < DDConfigs.server().kinetics.midnight_max_time.get();
        boolean sunrise_max_time =
                dt > DDConfigs.server().kinetics.sunrise_min_time.get() &&
                        dt < DDConfigs.server().kinetics.sunrise_max_time.get();
        if (night_min_time) { active = false; weak_active = true;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 32 : 16;}
        else if (midnight_min_time) { active = true; weak_active = false;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 48 : 24;}
        else if (sunrise_max_time) { active = false; weak_active = true;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 24 : 12;}
        else { active = false; weak_active = false;
            return 0;}
    }

    public void tick() {
        super.tick();

        i = i + 1;
        if (i > DDConfigs.server().kinetics.PanelBlockCheckRate.get()) {
            updateGeneratedRotation();
            i = 0;
        }

        i2 = i2 + 1;
        if (!level.isClientSide && i2 > (20 * 5) && active && !weak_active) {
            level.playSound((Player)null, getBlockPos(), DDSoundEvents.shadow_panel.get(), SoundSource.AMBIENT, 1.0F, 1.0F + level.random.nextFloat() * 1.2F);
            i2 = 0;
        }
        if (!level.isClientSide && i2 > (20 * 5) && weak_active && !active) {
            level.playSound((Player)null, getBlockPos(), DDSoundEvents.shadow_panel.get(), SoundSource.AMBIENT, 0.5F, 1.0F + level.random.nextFloat() * 1.2F);
            i2 = 0;
        }
    }

    @Override
    public float getGeneratedSpeed() {return Generator();}
}
