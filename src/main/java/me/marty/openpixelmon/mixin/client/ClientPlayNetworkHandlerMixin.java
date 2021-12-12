package me.marty.openpixelmon.mixin.client;

import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
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

        ItemEntity pokeball = null;
        if (entityType == Entities.POKEBALL_ENTITY) {
            // TODO: fix pokeballs. For now, lets use an ItemEntity
            // pokeball = new PokeballEntity(x, y, z, world);
            pokeball = new ItemEntity(world, x, y, z, new ItemStack(OpenPixelmonItems.POKE_BALL));
        }

        if(pokeball != null) {
            int id = packet.getId();
            pokeball.updateTrackedPosition(x, y, z);
            pokeball.refreshPositionAfterTeleport(x, y, z);
            pokeball.setPitch((float) (packet.getPitch() * 360) / 256.0F);
            pokeball.setYaw((float) (packet.getYaw() * 360) / 256.0F);
            pokeball.setId(id);
            pokeball.setUuid(packet.getUuid());
            this.world.addEntity(id, pokeball);
        }
    }
}
