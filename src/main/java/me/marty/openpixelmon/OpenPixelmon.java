package me.marty.openpixelmon;

import me.marty.openpixelmon.api.Registries;
import me.marty.openpixelmon.biome.Biomes;
import me.marty.openpixelmon.block.OpenPixelmonBlocks;
import me.marty.openpixelmon.command.Commands;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import me.marty.openpixelmon.network.Packets;
import me.marty.openpixelmon.sound.Sounds;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class OpenPixelmon implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("OpenPixelmon");
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("pixelmon");

	// Used for rendering to the splash info on what we are loading
	public static String loadingPixelmon;
	public static int maxPixelmon;
	public static int currentPixelmon;

	@Override
	public void onInitialize() {
		LOGGER.info("OpenPixelmon is Initializing");
		Sounds.initialize();
		Registries.register();
		Packets.initialize();
		GeckoLib.initialize();
		OpenPixelmonItems.initialize();
		OpenPixelmonBlocks.initialize();
		Entities.initialize();
		Biomes.initialize();
		Commands.initialize();
		RRPCallback.EVENT.register(resources -> resources.add(RESOURCE_PACK));

		LOGGER.info("OpenPixelmon Initialization successful!");
	}

	public static Identifier id(String path) {
		return new Identifier("pixelmon", path);
	}
}
