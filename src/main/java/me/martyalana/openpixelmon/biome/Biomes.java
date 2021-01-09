package me.martyalana.openpixelmon.biome;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.entity.Entities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;

public class Biomes {

	public static void initialize() {
		// For testing spawns currently. will later on be used for spawning different types in different spots
		BiomeModifications.addSpawn(biomeSelectionContext -> true, SpawnGroup.AMBIENT, Entities.GENERATION_3.getPixelmonById(OpenPixelmon.id("seedot")).type, 1, 1, 1);
	}
}
