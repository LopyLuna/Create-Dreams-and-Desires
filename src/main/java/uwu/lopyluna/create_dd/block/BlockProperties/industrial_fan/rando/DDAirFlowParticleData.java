package uwu.lopyluna.create_dd.block.BlockProperties.industrial_fan.rando;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;

import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DDAirFlowParticleData implements ParticleOptions, ICustomParticleDataWithSprite<DDAirFlowParticleData> {

    public static final Codec<DDAirFlowParticleData> CODEC = RecordCodecBuilder.create(i ->
            i.group(
                            Codec.INT.fieldOf("x").forGetter(p -> p.posX),
                            Codec.INT.fieldOf("y").forGetter(p -> p.posY),
                            Codec.INT.fieldOf("z").forGetter(p -> p.posZ))
                    .apply(i, DDAirFlowParticleData::new));
    @Deprecated
    public static final ParticleOptions.Deserializer<DDAirFlowParticleData> DESERIALIZER = new ParticleOptions.Deserializer<DDAirFlowParticleData>() {
        public DDAirFlowParticleData fromCommand(ParticleType<DDAirFlowParticleData> particleTypeIn, StringReader reader)
                throws CommandSyntaxException {
            reader.expect(' ');
            int x = reader.readInt();
            reader.expect(' ');
            int y = reader.readInt();
            reader.expect(' ');
            int z = reader.readInt();
            return new DDAirFlowParticleData(x, y, z);
        }

        public DDAirFlowParticleData fromNetwork(ParticleType<DDAirFlowParticleData> particleTypeIn, FriendlyByteBuf buffer) {
            return new DDAirFlowParticleData(buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
    };

    final int posX;
    final int posY;
    final int posZ;

    public DDAirFlowParticleData(Vec3i pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public DDAirFlowParticleData(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public DDAirFlowParticleData() {
        this(0, 0, 0);
    }

    @Override
    public ParticleType<?> getType() {
        return DDParticleTypes.AIR_FLOW.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %d %d %d", DDParticleTypes.AIR_FLOW.parameter(), posX, posY, posZ);
    }
    @Deprecated
    @Override
    public Deserializer<DDAirFlowParticleData> getDeserializer() {
        return DESERIALIZER;
    }

    @Override
    public Codec<DDAirFlowParticleData> getCodec(ParticleType<DDAirFlowParticleData> type) {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public SpriteParticleRegistration<DDAirFlowParticleData> getMetaFactory() {
        return IndustrialAirFlowParticle.Factory::new;
    }

}