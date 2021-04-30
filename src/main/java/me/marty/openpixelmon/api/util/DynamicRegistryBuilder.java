package me.marty.openpixelmon.api.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class DynamicRegistryBuilder {

    private final ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder;

    public DynamicRegistryBuilder(ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder) {
        this.builder = builder;
    }

    public <E> void register(RegistryKey<? extends Registry<E>> registryRef, Codec<E> entryCodec) {
        register(registryRef, entryCodec, null);
    }

    public <E> void register(RegistryKey<? extends Registry<E>> registryRef, Codec<E> entryCodec, Codec<E> networkEntryCodec) {
        DynamicRegistryManager.register(builder, registryRef, entryCodec, networkEntryCodec);
    }
}
