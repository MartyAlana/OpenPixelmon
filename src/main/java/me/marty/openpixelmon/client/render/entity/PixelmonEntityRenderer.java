package me.marty.openpixelmon.client.render.entity;

import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class PixelmonEntityRenderer extends EntityRenderer<PixelmonEntity> {

    public PixelmonEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(PixelmonEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        PokedexEntry pokedexEntry = entity.getPokedexEntry();
        if (pokedexEntry != null) {
            matrices.push();
            matrices.scale(pokedexEntry.renderScale[0], pokedexEntry.renderScale[1], pokedexEntry.renderScale[2]);
            super.render(entity, entityYaw, partialTicks, matrices, vertexConsumerProvider, light);
            matrices.pop();
            renderPixelmonInfo(entity, getTextRenderer(), dispatcher, matrices, light, vertexConsumerProvider);
        }
    }

    public static void renderPixelmonInfo(PixelmonEntity pixelmon, TextRenderer textRenderer, EntityRenderDispatcher dispatcher, MatrixStack matrices, int light, VertexConsumerProvider vertexConsumers) {
        Identifier type = pixelmon.getPokedexEntry().getIdentifier();

        matrices.push();
        drawLabel(matrices, textRenderer, dispatcher, pixelmon, light, vertexConsumers, OpenPixelmonTranslator.createTranslation(type), false);
        matrices.scale(0.8f, 0.8f, 0.8f);
        matrices.translate(0, 0.1, 0);
        drawLabel(matrices, textRenderer, dispatcher, pixelmon, light, vertexConsumers, new LiteralText("Lv. " + pixelmon.getLevel()), false);
        if (pixelmon.isBoss()) {
            matrices.translate(0, 0.5, 0);
            drawLabel(matrices, textRenderer, dispatcher, pixelmon, light, vertexConsumers, new LiteralText("Boss"), true);
        }
        if (!pixelmon.isWild() && pixelmon.getOwner() != null) {
            matrices.translate(0, 0.5, 0);
            drawLabel(matrices, textRenderer, dispatcher, pixelmon, light, vertexConsumers, pixelmon.getOwner().getDisplayName(), true);
        }
        matrices.pop();
    }

    public static void drawLabel(MatrixStack matrices, TextRenderer textRenderer, EntityRenderDispatcher dispatcher, PixelmonEntity entity, int light, VertexConsumerProvider vertexConsumers, Text text, boolean important) {
        double distance = dispatcher.getSquaredDistanceToCamera(entity);
        if (!(distance > 4096.0D)) {
            float f = entity.getHeight() + 0.5F;
            matrices.push();
            matrices.translate(0.0D, f, 0.0D);
            matrices.multiply(dispatcher.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float h = (float) (-textRenderer.getWidth(text) / 2);
            textRenderer.draw(text, h, 0, important ? 0xfffdff59 : 0xffffffff, false, matrix4f, vertexConsumers, false, 0, light);
            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(PixelmonEntity entity) {
        return null;
    }
}
