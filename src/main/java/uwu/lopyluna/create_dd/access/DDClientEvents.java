package uwu.lopyluna.create_dd.access;

import com.simibubi.create.foundation.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uwu.lopyluna.create_dd.block.BlockProperties.BronzeAirCurrent;

public class DDClientEvents extends ClientEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;

        Level world = Minecraft.getInstance().level;
        if (event.phase == TickEvent.Phase.START) {
            BronzeAirCurrent.tickClientPlayerSounds();
        }

    }
}
