package uwu.lopyluna.create_flavored.Event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uwu.lopyluna.create_flavored.Flavoredcreate;
import uwu.lopyluna.create_flavored.entity.Bobert.SmartBuilderEntity;
import uwu.lopyluna.create_flavored.entity.bobert;

@Mod.EventBusSubscriber(modid = Flavoredcreate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FlavoredEventBusEvent {


    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(bobert.SMART_BUILDER.get(), SmartBuilderEntity.setAttributes());
    }
}
