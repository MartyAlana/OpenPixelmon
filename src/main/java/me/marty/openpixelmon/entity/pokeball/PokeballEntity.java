package me.marty.openpixelmon.entity.pokeball;

import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class PokeballEntity extends AbstractPokeballEntity {

	public PokeballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public PokeballEntity(World world, PlayerEntity user) {
		super(Entities.POKEBALL_ENTITY, user, world);
	}
	public PokeballEntity(double x, double y, double z, ClientWorld world) {
		super(Entities.POKEBALL_ENTITY, x, y, z, world);
	}

	public PokeballEntity(EntityType<AbstractPokeballEntity> type, ServerPlayerEntity player) {
		super(type, player.getServerWorld());
		this.setPos(player.getX(), player.getY(), player.getZ());
		setOwner(player);
		this.sendingOut = true;
	}

	@Override
	public Item getItem() {
		return OpenPixelmonItems.POKE_BALL;
	}
}
