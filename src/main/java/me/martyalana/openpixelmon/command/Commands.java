package me.martyalana.openpixelmon.command;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.player.PixelmonPlayer;
import me.martyalana.openpixelmon.entity.Entities;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.CommandException;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;

public class Commands {
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
				dispatcher.register(
						literal("pokedebug")
								.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
								.executes(context -> {
									if (context.getSource().getPlayer() != null) {
										ServerPlayerEntity player = context.getSource().getPlayer();
										PixelmonPlayer pixelmonPlayer = (PixelmonPlayer) player;
										pixelmonPlayer.givePixelmon(new PixelmonEntity(
												Entities.GENERATION_3.getPixelmonById(OpenPixelmon.id("seedot")).type,
												context.getSource().getWorld()));
									} else {
										throw new CommandException(new LiteralText("You must be a player to run this command!").formatted(Formatting.RED));
									}
									return 0;
								})));
	}
}
