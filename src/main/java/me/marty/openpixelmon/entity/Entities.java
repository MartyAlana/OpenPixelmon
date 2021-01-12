package me.marty.openpixelmon.entity;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
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
	 * Misc Entities
	 */
	public static final EntityType<PixelmonEntity> PIXELMON = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("pixelmon"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PixelmonEntity::new)
			.dimensions(EntityDimensions.changing(1, 1))
			.build());

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
		FabricDefaultAttributeRegistry.register(PIXELMON, PixelmonEntity.createPixelmonAttributes());
	}
}
