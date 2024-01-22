package uwu.lopyluna.create_dd.block.BlockProperties.panels;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.lopyluna.create_dd.block.DDBlocks;
import uwu.lopyluna.create_dd.configs.DDConfigs;

public class RadiantPanelBlockEntity extends GeneratingKineticBlockEntity {

    public RadiantPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {super(type, pos, state);}
    BlockState state = getBlockState();
    int i = 0;
    int i2 = 0;
    public static boolean active = false;
    public static boolean weak_active = false;

    float x = 0;
    float y = 0;
    float yI = 0;
    float yT = 0;
    float z = 0;
    boolean yB = false;

    public float Generator() {
        assert level != null;
        float dt = level.dayTime() % 24000;

        if (!DDBlocks.RADIANT_PANEL.has(getBlockState()))
            return 0;

        boolean day_time =
                dt > DDConfigs.server().kinetics.day_min_time.get() &&
                        dt < DDConfigs.server().kinetics.day_max_time.get() + 5;
        boolean noon_time =
                dt > DDConfigs.server().kinetics.noon_min_time.get() &&
                        dt < DDConfigs.server().kinetics.noon_max_time.get() + 5;
        boolean sunset_time =
                dt > DDConfigs.server().kinetics.sunset_min_time.get() &&
                        dt < DDConfigs.server().kinetics.sunset_max_time.get() + 5;
        if (day_time) { active = false; weak_active = true;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 48 : 24;}
        else if (noon_time) { active = true; weak_active = false;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 64 : 48;}
        else if (sunset_time) { active = false; weak_active = true;
            return !state.getValue(BlockStateProperties.WATERLOGGED) ? 32 : 16;}
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

        yB = yT == 1;

        if (yB) {
            y = y - 1;
        } else {
            y = y + 1;
        }

        if ( yI > 20) {yT = yT + 1;yI = 0;} else {yI = yI + 1;}
        if (yT == 2) {yT = 0;}

        i2 = i2 + 1;

        if (!level.isClientSide && i2 > (20 * 4) - 1 && active && !weak_active) {
            level.playSound((Player)null, getBlockPos(), SoundEvents.BEACON_AMBIENT, SoundSource.AMBIENT, 0.5F, 1.5F + level.random.nextFloat() * 1.2F);
            i2 = 0;
        }
        if (!level.isClientSide && i2 > (20 * 4) - 1 && weak_active && !active) {
            level.playSound((Player)null, getBlockPos(), SoundEvents.BEACON_AMBIENT, SoundSource.AMBIENT, 0.25F, 1F + level.random.nextFloat() * 1.2F);
            i2 = 0;
        }
    }

    @Override
    public float getGeneratedSpeed() {return Generator();}

}
