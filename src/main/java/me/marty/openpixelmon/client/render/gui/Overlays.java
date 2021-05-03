package me.marty.openpixelmon.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.data.DataLoaders;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.entity.data.Party;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.Map;

public class Overlays extends DrawableHelper {

    public static final Map<Identifier, PixelmonEntity> PIXELMON_ENTITY_CACHE = new Object2ObjectArrayMap<>();
    private static final Identifier POKEBALL = OpenPixelmon.id("textures/gui/ingame/pixelmon.png");
    private static final Identifier DOCK_BG = OpenPixelmon.id("textures/gui/ingame/dock.png");

    public static void renderPartyOverlay(MatrixStack matrices, MinecraftClient client, int scaledHeight) {
        matrices.push();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, DOCK_BG);
//        GL20.glEnable(GL20.GL_ALPHA_TEST);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.45F);
        drawTexture(matrices, -8, scaledHeight / 2 - (165 / 2), 0, 0, 32, 176, 32, 176);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        GL20.glDisable(GL20.GL_ALPHA_TEST);
        int pokeballSpacing = 27;
        RenderSystem.setShaderTexture(0, POKEBALL);
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
                    PokedexEntry pokedexEntry = DataLoaders.PIXELMON_MANAGER.getPixelmon().get(partyEntry.getIdentifier());
                    drawPixelmon(11, scaledHeight / 2 - (15 / 2) - offset + 14, 24, getEntity(partyEntry.getIdentifier()), pokedexEntry);
                    client.textRenderer.drawWithShadow(matrices, OpenPixelmonTranslator.createTranslation(partyEntry.getIdentifier()).getString(), 24, scaledHeight / 2 - 12 - offset, pokedexEntry.legendary ? 0xFF55FFFF : 0xFFFFFFFF);
                    client.textRenderer.drawWithShadow(matrices, "Lv. " + partyEntry.getLevel(), 24, scaledHeight / 2 - 4 - offset, 0xFFFFFFFF);
                    client.textRenderer.drawWithShadow(matrices, "HP: " + partyEntry.getHp() + "/" + partyEntry.getMaxHp(), 24, scaledHeight / 2 + 4 - offset, 0xFFFFFFFF);
                }
            }
        }
        matrices.pop();
    }

    private static PixelmonEntity getEntity(Identifier identifier) {
        if (PIXELMON_ENTITY_CACHE.containsKey(identifier)) {
            return PIXELMON_ENTITY_CACHE.get(identifier);
        } else {
            PixelmonEntity entity = new PixelmonEntity(Entities.PIXELMON, MinecraftClient.getInstance().world);
            entity.setup(identifier);
            PIXELMON_ENTITY_CACHE.put(identifier, entity);
            return entity;
        }
    }

    public static void renderLegalOverlay(MatrixStack matrices, MinecraftClient client, int width, int height) {
    }

    public static void drawPixelmon(int x, int y, int size, PixelmonEntity entity, PokedexEntry entry) {
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x, y, 1050.0D);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale((float) size, (float) size, (float) size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(40 / 20f);
        quaternion.hamiltonProduct(quaternion2);
        quaternion.hamiltonProduct(Vec3f.POSITIVE_Y.getDegreesQuaternion(-175));
        matrixStack2.multiply(quaternion);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack2, immediate, 0xf0f0f0));
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
