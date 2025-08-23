package dev.lopyluna.dndesires.content.items.handheld_drill;

import dev.lopyluna.dndesires.register.DesiresPackets;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.net.base.ServerboundPacketPayload;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

public record HandheldDrillKeyPacket(boolean activated) implements ServerboundPacketPayload {
    public static final StreamCodec<ByteBuf, HandheldDrillKeyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, HandheldDrillKeyPacket::activated,
            HandheldDrillKeyPacket::new
    );

    @Override
    public PacketTypeProvider getTypeProvider() {
        return DesiresPackets.HANDHELD_DRILL_KEY;
    }

    @Override
    public void handle(ServerPlayer player) {
        player.getPersistentData().putBoolean("HandheldDrillKey", activated);
    }
}
