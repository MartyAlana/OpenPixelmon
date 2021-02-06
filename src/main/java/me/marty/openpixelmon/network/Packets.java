package me.marty.openpixelmon.network;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.api.component.PartyComponent;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.entity.pokeball.PokeballEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class Packets {
	public static final Identifier SEND_OUT = OpenPixelmon.id("send_out_pixelmon");

	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(SEND_OUT, (server, player, handler, buf, responseSender) -> {
			PartyComponent party = EntityComponents.PARTY_COMPONENT.get(player);
			PartyEntry importantPokemon = party.getParty().getEntries()[0];
			if(importantPokemon == null) {
				player.sendMessage(new TranslatableText("text.pixelmon.send.fail"), false);
				return;
			}
			if(importantPokemon.getHp() != 0){
				player.sendMessage(new TranslatableText("text.pixelmon.send.success", importantPokemon.getName()), false);
				player.getServerWorld().spawnEntity(new PokeballEntity(Entities.POKEBALL_ENTITY, player));
			}
		});
	}
}
