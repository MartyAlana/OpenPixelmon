package me.marty.openpixelmon.api;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.event.DynamicRegistryBuilderCallback;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.api.pixelmon.move.Move;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class Registries {

    public static final RegistryKey<Registry<PokedexEntry>> PIXELMON_REGISTRY = RegistryKey.ofRegistry(OpenPixelmon.id("pixelmon"));
    public static final RegistryKey<Registry<Move>> MOVE_REGISTRY = RegistryKey.ofRegistry(OpenPixelmon.id("move"));

    public static void register() {
        BuiltinRegistries.addRegistry(PIXELMON_REGISTRY, () -> null);
        BuiltinRegistries.addRegistry(MOVE_REGISTRY, () -> null);

        DynamicRegistryBuilderCallback.EVENT.register(builder -> {
            builder.register(PIXELMON_REGISTRY, PokedexEntry.CODEC);
            builder.register(MOVE_REGISTRY, Move.CODEC);
        });

        DynamicRegistrySetupCallback.EVENT.register(registryManager -> {
            Registry<PokedexEntry> pokedexRegistry = registryManager.get(PIXELMON_REGISTRY);
            RegistryEntryAddedCallback.event(pokedexRegistry).register((rawId, id, object) -> {
                System.out.println("Do i sync here?");
            });
        });
    }
}
