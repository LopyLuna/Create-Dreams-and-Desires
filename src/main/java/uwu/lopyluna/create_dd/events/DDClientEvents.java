package uwu.lopyluna.create_dd.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialAirCurrent;
import uwu.lopyluna.create_dd.item.ItemProperties.drilltool.ExcavationDrillRenderHandler;

@SuppressWarnings({"unused"})
@Mod.EventBusSubscriber(Dist.CLIENT)
public class DDClientEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;

        Level world = Minecraft.getInstance().level;
        if (event.phase == TickEvent.Phase.START) {
            IndustrialAirCurrent.tickClientPlayerSounds();
        }

        ExcavationDrillRenderHandler.tick();

    }

    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }
}
