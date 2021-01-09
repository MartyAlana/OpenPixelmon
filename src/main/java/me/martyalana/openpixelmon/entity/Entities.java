package me.martyalana.openpixelmon.entity;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.pixelmon.EggGroup;
import me.martyalana.openpixelmon.api.pixelmon.PokeType;
import me.martyalana.openpixelmon.api.pixelmon.PokedexData;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.martyalana.openpixelmon.entity.pixelmon.PokeGeneration;
import me.martyalana.openpixelmon.entity.pokeball.AbstractPokeballEntity;
import me.martyalana.openpixelmon.entity.pokeball.GreatballEntity;
import me.martyalana.openpixelmon.entity.pokeball.PokeballEntity;
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
		for (PokedexData pokedexData : GENERATION_3.getPixelmon()) {
			FabricDefaultAttributeRegistry.register(pokedexData.type, PixelmonEntity.createPixelmonAttributes());
		}
	}
}
