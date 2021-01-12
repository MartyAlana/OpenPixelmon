package me.marty.openpixelmon.api;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.event.DynamicRegistryBuilderCallback;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.api.pixelmon.move.Move;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class PixelmonRegistries {

	public static final RegistryKey<Registry<PokedexEntry>> PIXELMON_REGISTRY = RegistryKey.ofRegistry(OpenPixelmon.id("pixelmon"));
	public static final RegistryKey<Registry<Move>> MOVE_REGISTRY = RegistryKey.ofRegistry(OpenPixelmon.id("move"));

	public static void register() {
		DynamicRegistryBuilderCallback.EVENT.register(builder -> {
			builder.register(PIXELMON_REGISTRY, PokedexEntry.CODEC);
			builder.register(MOVE_REGISTRY, Move.CODEC);
		});

		DynamicRegistrySetupCallback.EVENT.register(registryManager -> {
		});
	}
}
