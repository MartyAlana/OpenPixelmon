package me.marty.openpixelmon.entity.pokeball;

import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class GreatballEntity extends AbstractPokeballEntity {

	public GreatballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public GreatballEntity(World world, PlayerEntity user) {
		super(Entities.GREATBALL_ENTITY, user, world);
	}

	public GreatballEntity(double x, double y, double z, ClientWorld world) {
		super(Entities.GREATBALL_ENTITY, x, y, z, world);
	}

	@Override
	public Item getItem() {
		return OpenPixelmonItems.GREAT_BALL;
	}
}
