package me.martyalana.openpixelmon.entity;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.pixelmon.EggGroup;
import me.martyalana.openpixelmon.api.pixelmon.PokeType;
import me.martyalana.openpixelmon.api.pixelmon.PokedexData;
import me.martyalana.openpixelmon.entity.pixelmon.BasicPixelmonEntity;
import me.martyalana.openpixelmon.entity.pixelmon.PokeGeneration;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.registry.Registry;

public class Entities {

	/**
	 * Pixelmon Generations
	 */
	public static final PokeGeneration GENERATION_3 = new PokeGeneration.Builder(OpenPixelmon.id("generation_3"))
			.addPixelmon(new PokedexData(
					EntityDimensions.fixed(1, 1),
					"seedot",
					50,
					new PokeType[]{PokeType.GRASS},
					42,
					new EggGroup[]{EggGroup.FIELD, EggGroup.GRASS},
					3855,
					0.5D,
					4,
					0xAAFFFFFF,
					70))
			.addPixelmon(new PokedexData(
					EntityDimensions.fixed(1, 2),
					"seedot2",
					100,
					new PokeType[]{PokeType.GRASS, PokeType.DARK},
					42,
					new EggGroup[]{EggGroup.FIELD, EggGroup.GRASS},
					3855,
					0.5D,
					4,
					0xAAFFFFFF,
					70))
			.build();

	/**
	 * Misc Entities
	 */
	public static final EntityType<? extends ThrownEntity> POKEBALL_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("pokeball"),
			FabricEntityTypeBuilder.<PokeballEntity>create(SpawnGroup.MISC, PokeballEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 0.75f))
					.build());

	public static void initialize() {
		for (PokedexData pokedexData : GENERATION_3.getPokemon()) {
			FabricDefaultAttributeRegistry.register(pokedexData.type, BasicPixelmonEntity.createPixelmonAttributes());
		}
	}
}
