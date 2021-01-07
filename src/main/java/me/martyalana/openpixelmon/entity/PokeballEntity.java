package me.martyalana.openpixelmon.entity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class PokeballEntity extends ThrownEntity {
	public PokeballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public PokeballEntity(World world, PlayerEntity user) {
		super(Entities.POKEBALL_ENTITY, user, world);
	}

	public PokeballEntity(double x, double y, double z, ClientWorld world) {
		super(Entities.POKEBALL_ENTITY, x, y, z, world);
	}

	@Override
	protected void initDataTracker() {
	}
}
