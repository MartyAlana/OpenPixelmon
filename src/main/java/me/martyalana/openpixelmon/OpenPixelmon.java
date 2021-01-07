package me.martyalana.openpixelmon;

import me.martyalana.openpixelmon.entity.Entities;
import me.martyalana.openpixelmon.item.OpenPixelmonItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenPixelmon implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Open Pixelmon");

	@Override
	public void onInitialize() {
		OpenPixelmonItems.initialize();
		Entities.initialize();
		LOGGER.info("Open Pixelmon Initialization successful!");
	}

	public static Identifier id(String path) {
		return new Identifier("open_pixelmon", path);
	}
}
