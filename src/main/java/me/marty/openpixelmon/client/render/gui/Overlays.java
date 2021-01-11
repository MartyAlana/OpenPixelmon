package me.marty.openpixelmon.client.render.gui;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.entity.data.Party;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PokeGeneration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Overlays extends DrawableHelper {

	private static final Identifier POKEBALL = OpenPixelmon.id("textures/gui/ingame/pixelmon.png");
	private static final Identifier DEMO_BG = new Identifier("textures/gui/demo_background.png");

	public static void renderPartyOverlay(MatrixStack matrices, MinecraftClient client, int scaledHeight) {
		matrices.push();
		int pokeballSpacing = 27;
		client.getTextureManager().bindTexture(POKEBALL);
		for (int i = 0; i < 6; i++) {
			int offset = (65 - pokeballSpacing * i);
			drawTexture(matrices, 0, scaledHeight / 2 - (22 / 2) - offset, 0, 0, 22, 22, 22, 22);
		}

		for (int i = 0; i < 6; i++) {
			int offset = (65 - pokeballSpacing * i);
			Party party = EntityComponents.PARTY_COMPONENT.get(client.player).getParty();
			if (party.partySize() > i) {
				PartyEntry partyEntry = party.getEntries()[i];
				if (partyEntry != null) {
					PokedexEntry pokedexEntry = PokeGeneration.getPixelmonByType(partyEntry.getEntityType());
					client.textRenderer.draw(matrices, OpenPixelmonTranslator.createTranslation(pokedexEntry.name).getString(), 24, scaledHeight / 2 - 12 - offset, pokedexEntry.legendary ? 0xFF55FFFF : 0xFFFFFFFF);
					client.textRenderer.draw(matrices, "Lv. " + partyEntry.getLevel(), 24, scaledHeight / 2 - 4 - offset, 0xFFFFFFFF);
					client.textRenderer.draw(matrices, "HP: " + partyEntry.getHp() + "/??", 24, scaledHeight / 2 + 4 - offset, 0xFFFFFFFF);
				}
			}
		}
		matrices.pop();
	}

	public static void renderLegalOverlay(MatrixStack matrices, MinecraftClient client, int height, int width) {
//		client.getTextureManager().bindTexture(DEMO_BG);
//		drawTexture(matrices, (width - 248) / 2, (height - 166) / 2, 0, 0, 248, 166, 256, 256);
//		Window window = client.getWindow();
//		TextRenderer textRenderer = client.textRenderer;
//
//		int textWidth = textRenderer.getWidth("Legal Disclaimer");
//		textRenderer.drawWithShadow(matrices, new LiteralText("Legal Disclaimer").formatted(Formatting.YELLOW), (window.getScaledWidth() - textWidth) / 2F, 85, 0xFFFFFFFF);
	}
}
