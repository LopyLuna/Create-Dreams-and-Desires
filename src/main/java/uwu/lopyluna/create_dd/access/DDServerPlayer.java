package uwu.lopyluna.create_dd.access;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class DDServerPlayer extends ServerPlayer {
    public DDServerPlayer(MinecraftServer pServer, ServerLevel pLevel, GameProfile pProfile) {
        super(pServer, pLevel, pProfile);
    }

    public DDServerGamePacketListenerImpl connection;
}
