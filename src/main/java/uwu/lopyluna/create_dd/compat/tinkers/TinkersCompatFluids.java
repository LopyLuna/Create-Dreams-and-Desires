package uwu.lopyluna.create_dd.compat.tinkers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import uwu.lopyluna.create_dd.DDCreate;

public class TinkersCompatFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(DDCreate.MOD_ID);

    public static FluidObject<ForgeFlowingFluid> mithril = register("molten_mithril", 1200);
    private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
        String still = String.format(DDCreate.MOD_ID + ":fluid/tinkers/%s/still", name);
        String flow = String.format(DDCreate.MOD_ID + ":fluid/tinkers/%s/flowing", name);
        return FLUIDS.register(name, FluidAttributes.builder(new ResourceLocation(still), new ResourceLocation(flow)).density(2000).viscosity(10000).temperature(temp).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), Material.LAVA, 15);
    }
}