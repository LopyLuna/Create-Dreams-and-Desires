package dev.lopyluna.dndesires.register;

import dev.lopyluna.dndesires.DnDesires;
import dev.lopyluna.dndesires.content.blocks.kinetics.multimeter.GaugeObservedPacket;
import dev.lopyluna.dndesires.content.items.handheld_drill.HandheldDrillKeyPacket;
import dev.lopyluna.dndesires.content.items.handheld_saw.HandheldSawKeyPacket;
import net.createmod.catnip.net.base.BasePacketPayload;
import net.createmod.catnip.net.base.CatnipPacketRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Locale;

public enum DesiresPackets implements BasePacketPayload.PacketTypeProvider {
    HANDHELD_SAW_KEY(HandheldSawKeyPacket.class, HandheldSawKeyPacket.STREAM_CODEC),
    HANDHELD_DRILL_KEY(HandheldDrillKeyPacket.class, HandheldDrillKeyPacket.STREAM_CODEC),
    OBSERVER_MULTIMETER(GaugeObservedPacket.class, GaugeObservedPacket.STREAM_CODEC);

    private final CatnipPacketRegistry.PacketType<?> type;

    <T extends BasePacketPayload> DesiresPackets(Class<T> clazz, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        var name = name().toLowerCase(Locale.ROOT);
        type = new CatnipPacketRegistry.PacketType<>(new CustomPacketPayload.Type<>(DnDesires.loc(name)), clazz, codec);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> getType() {
        return (CustomPacketPayload.Type<T>) this.type.type();
    }

    public static void register() {
        var packetRegistry = new CatnipPacketRegistry(DnDesires.MOD_ID, 1);
        for (var packet : values()) packetRegistry.registerPacket(packet.type);
        packetRegistry.registerAllPackets();
    }
}
