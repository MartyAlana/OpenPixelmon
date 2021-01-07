package me.martyalana.openpixelmon.entity;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.entity.pixelmon.TestingPixelmonGroup;
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
	public static final TestingPixelmonGroup TESTING = new TestingPixelmonGroup();

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
	}
}
