package uwu.lopyluna.create_dd.access;

import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class DDServerGamePacketListenerImpl extends ServerGamePacketListenerImpl {
    public DDServerGamePacketListenerImpl(MinecraftServer pServer, Connection pConnection, ServerPlayer pPlayer, MinecraftServer server) {
        super(pServer, pConnection, pPlayer);
    }
    public int aboveGroundTickCount;
}
