package me.marty.openpixelmon.biome;

import me.marty.openpixelmon.entity.Entities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;

public class Biomes implements ModInitializer {

    @Override
    public void onInitialize() {
        BiomeModifications.addSpawn(biomeSelectionContext -> true, SpawnGroup.AMBIENT, Entities.PIXELMON, 1, 1, 2);
    }
}
