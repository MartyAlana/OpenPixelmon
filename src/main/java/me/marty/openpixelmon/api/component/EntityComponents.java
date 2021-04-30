package me.marty.openpixelmon.api.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import me.marty.openpixelmon.OpenPixelmon;

public class EntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<PartyComponent> PARTY_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(OpenPixelmon.id("pixelmon_party"), PartyComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PARTY_COMPONENT, player -> new PartyComponent(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}