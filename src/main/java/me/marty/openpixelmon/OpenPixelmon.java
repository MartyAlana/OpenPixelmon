package me.marty.openpixelmon;

import me.marty.openpixelmon.api.Registries;
import me.marty.openpixelmon.biome.Biomes;
import me.marty.openpixelmon.block.OpenPixelmonBlocks;
import me.marty.openpixelmon.command.Commands;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.IOException;

public class OpenPixelmon implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("OpenPixelmon");
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("pixelmon");

	@Override
	public void onInitialize() {
		LOGGER.info("OpenPixelmon is Initializing");
		Registries.register();
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

	public static Resource getResource(ResourceManager resourceManager, Identifier resourcePath) {
		try {
			resourceManager.getResource(resourcePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to retrieve resource " + resourcePath);
	}
}
