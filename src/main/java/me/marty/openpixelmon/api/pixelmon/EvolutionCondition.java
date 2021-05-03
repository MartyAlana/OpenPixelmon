package me.marty.openpixelmon.api.pixelmon;

import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;

/**
 * Used as a way to make sure something is true before a pixelmon evolves
 */
public interface EvolutionCondition {
    boolean canEvolve(PixelmonEntity pixelmon);
}
