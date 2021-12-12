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

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OpenPixelmon implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("pixelmon");

    @Override
    public void onInitialize() {
        PixelmonAssetProvider.ASSET_PROVIDERS.add(new PixelmonGenerationsCompatibility()); // TODO: add a way to add more of these.
        Commands.initialize();
        PixelmonDataTrackers.initialize();
        RRPCallback.AFTER_VANILLA.register(resources -> resources.add(RESOURCE_PACK));
    }

    /**
     * Checks if at least one asset provider is present and usable. Try not to call this method too much as it has to do a few I/O operations.
     */
    public static boolean isAssetProviderPresent() {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        try {
            Files.walk(PixelmonAssetProvider.JAR_PATH, 1).forEach(path -> {
                for (PixelmonAssetProvider assetProvider : PixelmonAssetProvider.ASSET_PROVIDERS) {
                    if (assetProvider.isCompatibleJar(path.getFileName().toString())) {
                        isPresent.set(true);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("An IOException occurred while looking for Asset Providers.", e);
        }
        return isPresent.get();
    }

    public static Identifier id(String path) {
        return new Identifier("pixelmon", path);
    }
}
