package uwu.lopyluna.create_dd.access;

import com.simibubi.create.foundation.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.IndustrialAirCurrent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DDClientEvents extends ClientEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;

        Level world = Minecraft.getInstance().level;
        if (event.phase == TickEvent.Phase.START) {
            IndustrialAirCurrent.tickClientPlayerSounds();
        }

    }
}
