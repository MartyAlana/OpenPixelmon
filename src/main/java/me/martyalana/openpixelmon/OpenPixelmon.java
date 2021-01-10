package me.martyalana.openpixelmon;

import me.martyalana.openpixelmon.biome.Biomes;
import me.martyalana.openpixelmon.block.OpenPixelmonBlocks;
import me.martyalana.openpixelmon.command.Commands;
import me.martyalana.openpixelmon.entity.Entities;
import me.martyalana.openpixelmon.item.OpenPixelmonItems;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class OpenPixelmon implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Open Pixelmon");
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("open_pixelmon");

	@Override
	public void onInitialize() {
		LOGGER.info("Open Pixelmon is Initializing");
		GeckoLib.initialize();
		OpenPixelmonItems.initialize();
		OpenPixelmonBlocks.initialize();
		Entities.initialize();
		Biomes.initialize();
		Commands.initialize();
		RRPCallback.EVENT.register(resources -> resources.add(RESOURCE_PACK));
		LOGGER.info("Open Pixelmon Initialization successful!");
	}

	public static void throwError(String errorInfo) {
		throw new RuntimeException(errorInfo);
	}

	public static Identifier id(String path) {
		return new Identifier("open_pixelmon", path);
	}
}
