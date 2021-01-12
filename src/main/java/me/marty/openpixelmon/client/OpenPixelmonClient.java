package me.marty.openpixelmon.client;

import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.client.model.entity.GeckolibModel;
import me.marty.openpixelmon.client.render.entity.NonLivingGeckolibModelRenderer;
import me.marty.openpixelmon.client.render.entity.PixelmonEntityRenderer;
import me.marty.openpixelmon.client.render.gui.Overlays;
import me.marty.openpixelmon.entity.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class OpenPixelmonClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerS2CPackets();
		registerKeybindings();
		registerHudRenderers();
	}

	private void registerHudRenderers() {
		HudRenderCallback.EVENT.register((matrices, tickDelta) -> Overlays.renderPartyOverlay(matrices, MinecraftClient.getInstance(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
	}

	private void registerKeybindings() {
		KeyBinding keyBinding = new KeyBinding("keybind.pixelmon.throw_pixelmon", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.pixelmon.pixelmon");
		KeyBindingHelper.registerKeyBinding(keyBinding);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				client.player.sendMessage(new LiteralText("This is meant to take out the first pixelmon in the party :tiny_potato:"), false);
			}
		});
	}

	private void registerS2CPackets() {
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Entities.GREATBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/greatball")));
		EntityRendererRegistry.INSTANCE.register(Entities.ULTRABALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/ultraball")));
		EntityRendererRegistry.INSTANCE.register(Entities.MASTERBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/masterball")));
		EntityRendererRegistry.INSTANCE.register(Entities.POKEBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/pokeball")));
		EntityRendererRegistry.INSTANCE.register(Entities.PIXELMON, PixelmonEntityRenderer::new);
	}
}
