package me.marty.openpixelmon.compatibility;

import java.io.InputStream;

/**
 * Made so people have the ability to use pixelmon assets from other mods. useful for development and people who want the smooth pixelmon look and feel.
 */
public interface OtherModCompat {

    PixelmonGenerationsCompatibility INSTANCE = new PixelmonGenerationsCompatibility();

    InputStream getPixelmonModel(String name);
    InputStream getPixelmonTexture(String name);

    /**
     * Used to check if the jar in the compatibility
     * @param modName the name of the jar we are currently checking
     * @return if we can provide compatibility
     */
    boolean isCompatibleMod(String modName);
}
