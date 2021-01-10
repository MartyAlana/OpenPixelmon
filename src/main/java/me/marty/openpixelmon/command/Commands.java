package me.marty.openpixelmon.command;

import com.mojang.brigadier.Command;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.player.PixelmonPlayer;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PokeGeneration;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.literal;

public class Commands {
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
				dispatcher.register(
						literal("pokedebug")
								.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
								.executes(context -> {
									EntityComponents.PARTY_COMPONENT.get(context.getSource().getPlayer()).getParty().add(new PartyEntry(PokeGeneration.getPixelmonById(OpenPixelmon.id("seedot")).type.create(context.getSource().getWorld()), (PokeballItem) OpenPixelmonItems.POKE_BALL));
									return Command.SINGLE_SUCCESS;
								})));
	}
}