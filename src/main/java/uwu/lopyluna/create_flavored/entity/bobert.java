package uwu.lopyluna.create_flavored.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import uwu.lopyluna.create_flavored.Flavoredcreate;
import uwu.lopyluna.create_flavored.entity.Bobert.SmartBuilderEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class bobert {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Flavoredcreate.MOD_ID);

    public static final RegistryObject<EntityType<SmartBuilderEntity>> SMART_BUILDER =
            ENTITY_TYPE.register("smart_builder",
                    () -> EntityType.Builder.of(SmartBuilderEntity::new, MobCategory.MISC)
                            .clientTrackingRange(10)
                            .setShouldReceiveVelocityUpdates(true)
                            .sized(1f, 1.4f)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .fireImmune()
                            .build(new ResourceLocation(Flavoredcreate.MOD_ID, "smart_builder").toString()));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
