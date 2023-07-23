package uwu.lopyluna.create_dd.content.block.bronze_encased_fan;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import uwu.lopyluna.create_dd.init.DDParticleTypes;

import java.util.Locale;

public class DDAirParticleData implements ParticleOptions, ICustomParticleDataWithSprite<DDAirParticleData> {

	public static final Codec<DDAirParticleData> CODEC = RecordCodecBuilder.create(i ->
			i.group(
							Codec.FLOAT.fieldOf("drag").forGetter(p -> p.drag),
							Codec.FLOAT.fieldOf("speed").forGetter(p -> p.speed))
					.apply(i, DDAirParticleData::new));
	@Deprecated
	public static final ParticleOptions.Deserializer<DDAirParticleData> DESERIALIZER =
			new ParticleOptions.Deserializer<DDAirParticleData>() {
				public DDAirParticleData fromCommand(ParticleType<DDAirParticleData> particleTypeIn, StringReader reader)
						throws CommandSyntaxException {
					reader.expect(' ');
					float drag = reader.readFloat();
					reader.expect(' ');
					float speed = reader.readFloat();
					return new DDAirParticleData(drag, speed);
				}

				public DDAirParticleData fromNetwork(ParticleType<DDAirParticleData> particleTypeIn, FriendlyByteBuf buffer) {
					return new DDAirParticleData(buffer.readFloat(), buffer.readFloat());
				}
			};

	float drag;
	float speed;

	public DDAirParticleData(float drag, float speed) {
		this.drag = drag;
		this.speed = speed;
	}

	public DDAirParticleData() {
		this(0, 0);
	}

	@Override
	public ParticleType<?> getType() {
		return DDParticleTypes.AIR.get();
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeFloat(drag);
		buffer.writeFloat(speed);
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %f %f", DDParticleTypes.AIR.parameter(), drag, speed);
	}
	@Deprecated
	@Override
	public Deserializer<DDAirParticleData> getDeserializer() {
		return DESERIALIZER;
	}

	@Override
	public Codec<DDAirParticleData> getCodec(ParticleType<DDAirParticleData> type) {
		return CODEC;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ParticleEngine.SpriteParticleRegistration<DDAirParticleData> getMetaFactory() {
		return BronzeAirParticle.Factory::new;
	}

}
