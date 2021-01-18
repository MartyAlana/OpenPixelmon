package me.marty.openpixelmon.network;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.api.component.PartyComponent;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
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
				player.sendMessage(new TranslatableText("text.pixelmon.cant_send_out"), false);
				return;
			}
			if(importantPokemon.getHp() != 0){
				player.sendMessage(new LiteralText("Go " + importantPokemon.getName() + "!"), false);
				//TODO: get where the player is facing then summon a fancy pokeball that spawns an entity version of the first party member.
			}
		});
	}
}
