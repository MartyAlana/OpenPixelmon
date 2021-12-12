package me.marty.openpixelmon.compatibility;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Made to allow the use of assets from other pixelmon mods such as Pixelmon: Generations & Pixelmon: Reforged.
 *
 * @version 0.1.0
 */
public interface PixelmonAssetProvider extends ResourcePack {

    List<PixelmonAssetProvider> ASSET_PROVIDERS = new ArrayList<>();
    Path JAR_PATH = Paths.get("compatibility");

    /**
     * Gets the model of a pixelmon in Valve's proprietary model format (Studio Model or smd).
     *
     * @param name the name of the pixelmon to grab the model of.
     * @return a {@link InputStream} containing the bytes of a valid smd file.
     */
    InputStream getPixelmonModel(String name);

    /**
     * Gets the texture of a pixelmon.
     *
     * @param name the name of the pixelmon to grab the texture of.
     * @return a {@link InputStream} containing the bytes of a valid texture.
     */
    InputStream getPixelmonTexture(String name);

    /**
     * Used to check if the jar in the compatibility
     *
     * @param jarName the name of the jar we are currently checking
     * @return if we can provide compatibility
     */
    boolean isCompatibleJar(String jarName);

    @Nullable
    @Override
    default InputStream openRoot(String fileName) {
        return null;
    }

    @Nullable
    @Override
    default <T> T parseMetadata(ResourceMetadataReader<T> metaReader) {
        return null;
    }

    @Override
    default Set<String> getNamespaces(ResourceType type) {
        return Collections.singleton("pixelmon");
    }
}
