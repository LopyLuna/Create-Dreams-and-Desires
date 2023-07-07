package uwu.lopyluna.create_dd.access;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.FutureChain;

public class DDServerGamePacketListenerImpl extends ServerGamePacketListenerImpl {
    public DDServerGamePacketListenerImpl(MinecraftServer pServer, Connection pConnection, ServerPlayer pPlayer, MinecraftServer server, SignedMessageChain.Decoder signedMessageDecoder, FutureChain chatMessageChain) {
        super(pServer, pConnection, pPlayer);
    }
    public int aboveGroundTickCount;
}
