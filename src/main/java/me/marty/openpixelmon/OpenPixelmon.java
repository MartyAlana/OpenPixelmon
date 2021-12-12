package me.marty.openpixelmon;

import me.marty.openpixelmon.command.Commands;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import me.marty.openpixelmon.compatibility.PixelmonGenerationsCompatibility;
import me.marty.openpixelmon.entity.PixelmonDataTrackers;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenPixelmon implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("pixelmon");

    @Override
    public void onInitialize() {
        PixelmonAssetProvider.ASSET_PROVIDERS.add(new PixelmonGenerationsCompatibility());
        Commands.initialize();
        PixelmonDataTrackers.initialize();
        RRPCallback.AFTER_VANILLA.register(resources -> resources.add(RESOURCE_PACK));
    }

    public static Identifier id(String path) {
        return new Identifier("pixelmon", path);
    }
}
