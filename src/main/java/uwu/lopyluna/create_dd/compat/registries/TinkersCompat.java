package uwu.lopyluna.create_dd.compat.registries;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uwu.lopyluna.create_dd.compat.CompatibleMod;
import uwu.lopyluna.create_dd.compat.tinkers.TinkersCompatFluids;

public class TinkersCompat extends CompatibleMod {
    public static final String MODID = "tconstruct";

    @Override
    public String getModID() {
        return MODID;
    }

    @Override
    protected void onLoad() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TinkersCompatFluids.FLUIDS.register(bus);
    }
}