package dev.lopyluna.dndesires.content.blocks.kinetics.multimeter;

import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import dev.lopyluna.dndesires.register.DesiresPackets;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

public class GaugeObservedPacket extends BlockEntityConfigurationPacket<MultiMeterBE> {
    public static final StreamCodec<ByteBuf, GaugeObservedPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(
            GaugeObservedPacket::new, packet -> packet.pos
    );

    public GaugeObservedPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    protected void applySettings(ServerPlayer player, MultiMeterBE be) {
        be.onObserved();
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return DesiresPackets.OBSERVER_MULTIMETER;
    }
}