package me.martyalana.openpixelmon.entity.pixelmon;

import me.martyalana.openpixelmon.entity.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("EntityConstructor")
public class BasicPixelmonEntity extends AnimalEntity {

	public BasicPixelmonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new BasicPixelmonEntity(Entities.TESTING.TESTING_A, world);
	}
}
