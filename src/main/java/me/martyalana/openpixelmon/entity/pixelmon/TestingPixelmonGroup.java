package me.martyalana.openpixelmon.entity.pixelmon;

import me.martyalana.openpixelmon.OpenPixelmon;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class TestingPixelmonGroup {

	public final EntityType<BasicPixelmonEntity> TESTING_A = Registry.register(
			Registry.ENTITY_TYPE,
			OpenPixelmon.id("testing_a"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, BasicPixelmonEntity::new)
					.dimensions(EntityDimensions.fixed(1, 1))
					.build());
}
