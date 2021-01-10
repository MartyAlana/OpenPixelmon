package me.marty.openpixelmon.entity;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.EggGroup;
import me.marty.openpixelmon.api.pixelmon.PokeType;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.entity.pixelmon.PokeGeneration;
import me.marty.openpixelmon.entity.pokeball.AbstractPokeballEntity;
import me.marty.openpixelmon.entity.pokeball.GreatballEntity;
import me.marty.openpixelmon.entity.pokeball.PokeballEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class Entities {

	/**
	 * Pixelmon Generations
	 */
	private static final PokeGeneration GENERATION_1 = new PokeGeneration.Builder(OpenPixelmon.id("generation_1"))
			.addPixelmon(new PokedexEntry(
					EntityDimensions.fixed(1, 1),
					"bulbasaur",
					88,
					new PokeType[]{PokeType.GRASS, PokeType.POISON},
					45,
					new EggGroup[]{EggGroup.MONSTER, EggGroup.GRASS},
					3855,
					0.7D,
					6.9,
					0xAAFFFFFF,
					70,
					16,
					false,
					false))
			.build();

	private static final PokeGeneration GENERATION_2 = new PokeGeneration.Builder(OpenPixelmon.id("generation_2"))
			.build();

	private static final PokeGeneration GENERATION_3 = new PokeGeneration.Builder(OpenPixelmon.id("generation_3"))
			.addPixelmon(new PokedexEntry(
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
					70,
					18,
					false,
					false))
			.build();

	/**
	 * Pokeball Entities
	 */
	public static final EntityType<AbstractPokeballEntity> POKEBALL_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("pokeball"),
			FabricEntityTypeBuilder.<AbstractPokeballEntity>create(SpawnGroup.MISC, PokeballEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	public static final EntityType<AbstractPokeballEntity> GREATBALL_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("greatball"),
			FabricEntityTypeBuilder.<AbstractPokeballEntity>create(SpawnGroup.MISC, GreatballEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	public static final EntityType<AbstractPokeballEntity> ULTRABALL_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("ultraball"),
			FabricEntityTypeBuilder.<AbstractPokeballEntity>create(SpawnGroup.MISC, PokeballEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	public static final EntityType<AbstractPokeballEntity> MASTERBALL_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("masterball"),
			FabricEntityTypeBuilder.<AbstractPokeballEntity>create(SpawnGroup.MISC, PokeballEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	public static void initialize() {
		for (PokedexEntry pokedexEntry : GENERATION_3.getAllPixelmon()) {
			FabricDefaultAttributeRegistry.register(pokedexEntry.type, PixelmonEntity.createPixelmonAttributes());
		}
	}
}
