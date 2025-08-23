package dev.lopyluna.dndesires.content.items.handheld_saw;

import dev.lopyluna.dndesires.register.DesiresPackets;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.net.base.ServerboundPacketPayload;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

public record HandheldSawKeyPacket(boolean activated) implements ServerboundPacketPayload {
    public static final StreamCodec<ByteBuf, HandheldSawKeyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, HandheldSawKeyPacket::activated,
            HandheldSawKeyPacket::new
    );

    @Override
    public PacketTypeProvider getTypeProvider() {
        return DesiresPackets.HANDHELD_SAW_KEY;
    }

    @Override
    public void handle(ServerPlayer player) {
        player.getPersistentData().putBoolean("HandheldSawKey", activated);
    }
}
