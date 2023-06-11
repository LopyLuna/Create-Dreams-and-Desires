package uwu.lopyluna.create_flavored.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uwu.lopyluna.create_flavored.entity.Bobert.SmartBuilderModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.minecraftforge.client.ForgeHooksClient.loadLayerDefinitions;

@OnlyIn(Dist.CLIENT)
public class LayerDef {

      public static Map<ModelLayerLocation, LayerDefs> createRoots() {
            ImmutableMap.Builder<ModelLayerLocation, LayerDefs> builder = ImmutableMap.builder();

            builder.put(bobertModelLayer.SMART_BUILDER, SmartBuilderModel.createBodyLayer());


            loadLayerDefinitions(builder);
            ImmutableMap<ModelLayerLocation, LayerDefs> immutablemap = builder.build();
            List<ModelLayerLocation> list = ModelLayers.getKnownLocations().filter((p_171117_) -> {
                  return !immutablemap.containsKey(p_171117_);
            }).collect(Collectors.toList());
            if (!list.isEmpty()) {
                  throw new IllegalStateException("Missing layer definitions: " + list);
            } else {
                  return immutablemap;
            }
      }

      private static void loadLayerDefinitions(ImmutableMap.Builder<ModelLayerLocation, LayerDefs> builder) {
      }
}
