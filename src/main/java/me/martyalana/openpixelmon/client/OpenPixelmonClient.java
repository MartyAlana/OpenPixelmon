package me.martyalana.openpixelmon.client;

import me.martyalana.openpixelmon.api.pixelmon.PokedexData;
import me.martyalana.openpixelmon.client.model.entity.GeckolibModel;
import me.martyalana.openpixelmon.client.render.entity.NonLivingGeckolibModelRenderer;
import me.martyalana.openpixelmon.client.render.entity.PixelmonEntityRenderer;
import me.martyalana.openpixelmon.entity.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class OpenPixelmonClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Entities.GREATBALL_ENTITY, (manager, context) -> new NonLivingGeckolibModelRenderer<>(manager, new GeckolibModel<>("pokeball", "pokeball/greatball")));
		EntityRendererRegistry.INSTANCE.register(Entities.ULTRABALL_ENTITY, (manager, context) -> new NonLivingGeckolibModelRenderer<>(manager, new GeckolibModel<>("pokeball", "pokeball/ultraball")));
		EntityRendererRegistry.INSTANCE.register(Entities.MASTERBALL_ENTITY, (manager, context) -> new NonLivingGeckolibModelRenderer<>(manager, new GeckolibModel<>("pokeball", "pokeball/masterball")));
		EntityRendererRegistry.INSTANCE.register(Entities.POKEBALL_ENTITY, (manager, context) -> new NonLivingGeckolibModelRenderer<>(manager, new GeckolibModel<>("pokeball", "pokeball/pokeball")));

		// Pixelmon
		for (PokedexData pokedexData : Entities.GENERATION_3.getPixelmon()) {
			EntityRendererRegistry.INSTANCE.register(pokedexData.type, (manager, context) -> new PixelmonEntityRenderer(manager, pokedexData.name));
		}
	}
}
