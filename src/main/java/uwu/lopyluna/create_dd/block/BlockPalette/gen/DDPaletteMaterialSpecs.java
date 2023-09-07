package uwu.lopyluna.create_dd.block.BlockPalette.gen;

import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_dd.DDCreate;

@OnlyIn(Dist.CLIENT)
public class DDPaletteMaterialSpecs extends AllMaterialSpecs {

    public static class Locations {
        public static final ResourceLocation ACTORS = DDCreate.asResource("actors");
    }
}
