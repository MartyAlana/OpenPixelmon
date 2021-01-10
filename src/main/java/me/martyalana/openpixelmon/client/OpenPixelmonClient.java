package me.martyalana.openpixelmon.client;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.pixelmon.PokedexData;
import me.martyalana.openpixelmon.api.player.PixelmonPlayer;
import me.martyalana.openpixelmon.client.model.entity.GeckolibModel;
import me.martyalana.openpixelmon.client.render.entity.NonLivingGeckolibModelRenderer;
import me.martyalana.openpixelmon.client.render.entity.PixelmonEntityRenderer;
import me.martyalana.openpixelmon.entity.Entities;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.martyalana.openpixelmon.network.Packets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class OpenPixelmonClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerS2CPackets();
		registerKeybindings();
	}

	private void registerKeybindings() {
		KeyBinding keyBinding = new KeyBinding("keybind.open_pixelmon.throw_pokemon", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.open_pixelmon.pixelmon");
		KeyBindingHelper.registerKeyBinding(keyBinding);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				client.player.sendMessage(new LiteralText("This is meant to take out the first pokemon in the party :tiny_potato:"), false);
			}
		});
	}

	private void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(Packets.SYNC_PIXELMON, (client, handler, buf, responseSender) -> {
			Identifier pokemonIdentifier = buf.readIdentifier();
			PixelmonPlayer pixelmonPlayer = (PixelmonPlayer) MinecraftClient.getInstance().player;
			if (pixelmonPlayer == null) {
				OpenPixelmon.throwError("The Client Player is null when syncing pixelmon");
			}
			PixelmonEntity pixelmon = new PixelmonEntity(
					Entities.GENERATION_3.getPixelmonById(pokemonIdentifier).type,
					MinecraftClient.getInstance().world
			);

			pixelmon.setLevel(buf.readInt());
			pixelmonPlayer.givePixelmon(pixelmon);
		});
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Entities.GREATBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/greatball")));
		EntityRendererRegistry.INSTANCE.register(Entities.ULTRABALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/ultraball")));
		EntityRendererRegistry.INSTANCE.register(Entities.MASTERBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/masterball")));
		EntityRendererRegistry.INSTANCE.register(Entities.POKEBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/pokeball")));

		// Pixelmon
		for (PokedexData pokedexData : Entities.GENERATION_3.getPixelmon()) {
			EntityRendererRegistry.INSTANCE.register(pokedexData.type, ctx -> new PixelmonEntityRenderer(ctx, pokedexData.name));
		}
	}
}
