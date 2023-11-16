package uwu.lopyluna.create_dd.configs.server;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.BlockStressValues;
import uwu.lopyluna.create_dd.configs.DDConfigBase;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import uwu.lopyluna.create_dd.DDCreate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DDStress extends DDConfigBase implements BlockStressValues.IStressValueProvider {

    private final Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> capacities = new HashMap<>();
    private final Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> impacts = new HashMap<>();

    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
        builder.comment(".", DDStress.Comments.su, DDStress.Comments.impact)
                .push("impact");
        BlockStressDefaults.DEFAULT_IMPACTS.forEach((r, i) -> {
            if (r.getNamespace()
                    .equals(DDCreate.MOD_ID))
                getImpacts().put(r, builder.define(r.getPath(), i));
        });
        builder.pop();

        builder.comment(".", DDStress.Comments.su, DDStress.Comments.capacity)
                .push("capacity");
        BlockStressDefaults.DEFAULT_CAPACITIES.forEach((r, i) -> {
            if (r.getNamespace()
                    .equals(DDCreate.MOD_ID))
                getCapacities().put(r, builder.define(r.getPath(), i));
        });
        builder.pop();
    }

    @Override
    public double getImpact(Block block) {
        block = redirectValues(block);
        ResourceLocation key = RegisteredObjects.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = getImpacts().get(key);
        if (value != null)
            return value.get();
        return 0;
    }

    @Override
    public double getCapacity(Block block) {
        block = redirectValues(block);
        ResourceLocation key = RegisteredObjects.getKeyOrThrow(block);
        ForgeConfigSpec.ConfigValue<Double> value = getCapacities().get(key);
        if (value != null)
            return value.get();
        return 0;
    }

    @Override
    public Couple<Integer> getGeneratedRPM(Block block) {
        block = redirectValues(block);
        ResourceLocation key = RegisteredObjects.getKeyOrThrow(block);
        Supplier<Couple<Integer>> supplier = BlockStressDefaults.GENERATOR_SPEEDS.get(key);
        if (supplier == null)
            return null;
        return supplier.get();
    }

    @Override
    public boolean hasImpact(Block block) {
        block = redirectValues(block);
        ResourceLocation key = RegisteredObjects.getKeyOrThrow(block);
        return getImpacts().containsKey(key);
    }

    @Override
    public boolean hasCapacity(Block block) {
        block = redirectValues(block);
        ResourceLocation key = RegisteredObjects.getKeyOrThrow(block);
        return getCapacities().containsKey(key);
    }

    protected Block redirectValues(Block block) {
        return block;
    }

    @Override
    public String getName() {
        return "stressValues.v" + BlockStressDefaults.FORCED_UPDATE_VERSION;
    }

    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getImpacts() {
        return impacts;
    }

    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getCapacities() {
        return capacities;
    }

    private static class Comments {
        static String su = "[in Stress Units]";
        static String impact =
                "Configure the individual stress impact of mechanical blocks. Note that this cost is doubled for every speed increase it receives.";
        static String capacity = "Configure how much stress a source can accommodate for.";
    }
}
