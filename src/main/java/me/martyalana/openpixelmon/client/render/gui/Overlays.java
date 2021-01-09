package me.martyalana.openpixelmon.client.render.gui;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.player.PixelmonPlayer;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class Overlays extends DrawableHelper {

	private static final Identifier POKEBALL = OpenPixelmon.id("textures/gui/ingame/pokemon.png");
	
	public static void renderPartyOverlay(MatrixStack matrices, MinecraftClient client, int scaledHeight) {
		int pokeballSpacing = 27;
		client.getTextureManager().bindTexture(POKEBALL);
		for (int i = 0; i < 6; i++) {
			int offset = (65 - pokeballSpacing * i);
			drawTexture(matrices, 0, scaledHeight / 2 - (22/2) - offset, 0, 0, 22, 22, 22, 22);
		}
		for (int i = 0; i < 6; i++) {
			int offset = (65 - pokeballSpacing * i);
			if(((PixelmonPlayer) client.player).getParty().size() > i) {
				PixelmonEntity pixelmon = ((PixelmonPlayer) client.player).getParty().get(i);
				if(pixelmon != null) {
					client.textRenderer.draw(matrices, pixelmon.getNickname(), 24, scaledHeight / 2 - 12 - offset, pixelmon.getPokedexData().legendary ? 0xFF55FFFF : 0xFFFFFFFF);
					client.textRenderer.draw(matrices, "Lv. " + pixelmon.getLevel(), 24, scaledHeight / 2 - 4 - offset, 0xFFFFFFFF);
					client.textRenderer.draw(matrices, "HP: 69/420", 24, scaledHeight / 2 + 4 - offset, 0xFFFFFFFF);
				}
			}
		}
	}
}
