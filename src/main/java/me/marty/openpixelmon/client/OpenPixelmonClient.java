package me.marty.openpixelmon.client;

import me.marty.openpixelmon.api.battle.client.ClientBattleManager;
import me.marty.openpixelmon.client.render.GameRendererAccessor;
import me.marty.openpixelmon.client.render.entity.GenerationsPixelmonRenderer;
import me.marty.openpixelmon.client.render.gui.Overlays;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.network.Packets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Shader;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.render.VertexFormats.POSITION_TEXTURE_COLOR_NORMAL;

@Environment(EnvType.CLIENT)
public class OpenPixelmonClient implements ClientModInitializer {

    public static final ClientBattleManager battleManager = new ClientBattleManager();
    public static Shader pixelmonSolidShader;

    public static void loadShaders(ResourceManager manager, GameRendererAccessor gameRenderer) throws IOException {
        pixelmonSolidShader = gameRenderer.loadPixelmonShader(manager, "pixelmon", POSITION_TEXTURE_COLOR_NORMAL);
    }

    @Override
    public void onInitializeClient() {
        registerEntityRenderers();
        registerS2CPackets();
        registerKeybindings();
        registerHudRenderers();
    }

    private void registerHudRenderers() {
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> Overlays.renderPartyOverlay(matrices, MinecraftClient.getInstance(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> Overlays.renderBattleOverlay(matrices, MinecraftClient.getInstance(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
    }

    private void registerKeybindings() {
        KeyBinding keyBinding = new KeyBinding("keybind.pixelmon.throw_pixelmon", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.pixelmon.pixelmon");
        KeyBindingHelper.registerKeyBinding(keyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                ClientPlayNetworking.send(Packets.SEND_OUT, PacketByteBufs.create());
            }
        });
    }

    private void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.BATTLE_START, (client, handler, buf, responseSender) -> {
            int participantCount = buf.readVarInt();
            List<PlayerEntity> participants = new ArrayList<>();
            for (int i = 0; i < participantCount; i++) {
                participants.add((PlayerEntity) client.world.getEntityById(buf.readVarInt()));
            }

            OpenPixelmonClient.battleManager.startBattle(participants, client);
        });

        ClientPlayNetworking.registerGlobalReceiver(Packets.BATTLE_END, (client, handler, buf, responseSender) -> {
            boolean forced = buf.readBoolean();
            if (forced) {
                client.execute(OpenPixelmonClient.battleManager::forceStopBattle);
            }
        });
    }

    private void registerEntityRenderers() {
        EntityRendererRegistry.register(Entities.PIXELMON, GenerationsPixelmonRenderer::new);
    }
}