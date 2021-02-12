package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.entity.pokeball.PokeballEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

	@Shadow
	private ClientWorld world;

	@Inject(method = "onEntitySpawn", at = @At("HEAD"))
	private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci) {
		double x = packet.getX();
		double y = packet.getY();
		double z = packet.getZ();
		EntityType<?> entityType = packet.getEntityTypeId();

		PokeballEntity pokeball = null;
		if (entityType == Entities.POKEBALL_ENTITY) {
			pokeball = new PokeballEntity(x, y, z, world);
		}

		if(pokeball != null) {
			int id = packet.getId();
			pokeball.updateTrackedPosition(x, y, z);
			pokeball.refreshPositionAfterTeleport(x, y, z);
			pokeball.pitch = (float) (packet.getPitch() * 360) / 256.0F;
			pokeball.yaw = (float) (packet.getYaw() * 360) / 256.0F;
			pokeball.setEntityId(id);
			pokeball.setUuid(packet.getUuid());
			this.world.addEntity(id, pokeball);
		}
	}
}
