package uwu.lopyluna.create_dd.foundation.access;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;

import javax.annotation.Nullable;

public class DDServerPlayer extends ServerPlayer {
	public DDServerPlayer(MinecraftServer pServer, ServerLevel pLevel, GameProfile pProfile, @Nullable ProfilePublicKey pProfilePublicKey) {
		super(pServer, pLevel, pProfile, pProfilePublicKey);
	}

	public DDServerGamePacketListenerImpl connection;
}
