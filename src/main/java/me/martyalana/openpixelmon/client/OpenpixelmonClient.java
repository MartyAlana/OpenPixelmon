package me.martyalana.openpixelmon.client;

import me.martyalana.openpixelmon.api.pixelmon.PokedexData;
import me.martyalana.openpixelmon.client.render.entity.EmptyEntityRenderer;
import me.martyalana.openpixelmon.entity.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class OpenpixelmonClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
	}

	private void registerEntityRenderers() {
		// Misc
		EntityRendererRegistry.INSTANCE.register(Entities.POKEBALL_ENTITY, EmptyEntityRenderer::new);

		// Pixelmon
		for (PokedexData pokedexData : Entities.GENERATION_3.getPokemon()) {
			EntityRendererRegistry.INSTANCE.register(pokedexData.type, EmptyEntityRenderer::new);
		}
	}
}
