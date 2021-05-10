package me.marty.openpixelmon.client;

import me.marty.openpixelmon.api.battle.client.ClientBattleManager;
import me.marty.openpixelmon.client.model.entity.GeckolibModel;
import me.marty.openpixelmon.client.render.GameRendererAccessor;
import me.marty.openpixelmon.client.render.entity.GenerationsPixelmonRenderer;
import me.marty.openpixelmon.client.render.entity.NonLivingGeckolibModelRenderer;
import me.marty.openpixelmon.client.render.entity.PixelmonEntityRenderer;
import me.marty.openpixelmon.client.render.gui.Overlays;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.network.Packets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.*;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OpenPixelmonClient implements ClientModInitializer {

    public static final ClientBattleManager battleManager = new ClientBattleManager();

    protected static Shader pixelmonSolidShader;
    protected static final RenderPhase.Shader PIXELMON_SOLID_SHADER = new RenderPhase.Shader(OpenPixelmonClient::getPixelmonShader);

    private static Shader getPixelmonShader() {
        return pixelmonSolidShader;
    }

    public static void loadShaders(ResourceManager manager, GameRendererAccessor gameRenderer) throws IOException {
        pixelmonSolidShader = gameRenderer.loadPixelmonShader(manager, "rendertype_pixelmon_solid", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
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
                OpenPixelmonClient.battleManager.forceStopBattle();
            }
        });
    }

    private void registerEntityRenderers() {
        if (useCompatModels()) {
            EntityRendererRegistry.INSTANCE.register(Entities.PIXELMON, GenerationsPixelmonRenderer::new);
        } else {
            EntityRendererRegistry.INSTANCE.register(Entities.PIXELMON, PixelmonEntityRenderer::new);
        }
        EntityRendererRegistry.INSTANCE.register(Entities.POKEBALL_ENTITY, ctx -> new NonLivingGeckolibModelRenderer<>(ctx, new GeckolibModel<>("pokeball", "pokeball/pokeball")));
    }

    private boolean useCompatModels() {
        return true; //TODO: currently forced due to us not having models for every pixelmon :pensive:
    }

    public static RenderLayer getPixelmonLayer(Identifier texture) {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                .shader(PIXELMON_SOLID_SHADER)
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.NO_TRANSPARENCY).lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(true);
        return RenderLayer.of("pixelmon", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false, multiPhaseParameters);
    }
}