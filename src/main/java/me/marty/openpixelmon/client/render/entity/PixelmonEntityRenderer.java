package me.marty.openpixelmon.client.render.entity;

import me.marty.openpixelmon.client.model.entity.GeckolibModel;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class PixelmonEntityRenderer extends GeoEntityRenderer<PixelmonEntity> {

	private final Identifier pixelmonName;

	public PixelmonEntityRenderer(EntityRendererFactory.Context context, Identifier pixelmonName) {
		super(context, GeckolibModel.of(pixelmonName.getPath()));
		this.pixelmonName = pixelmonName;
	}

	@Override
	public void render(PixelmonEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
		matrices.push();
		float levelScale = (float) (0.01 * entity.getLevel());
		matrices.scale(1 + levelScale, 1 + levelScale, 1 + levelScale);
		super.render(entity, entityYaw, partialTicks, matrices, vertexConsumerProvider, light);
		matrices.pop();
		renderPixelmonInfo(entity, matrices, light, vertexConsumerProvider);
	}

	private void renderPixelmonInfo(PixelmonEntity pixelmon, MatrixStack matrices, int light, VertexConsumerProvider vertexConsumers) {
		matrices.push();
		drawLabel(matrices, pixelmon, light, vertexConsumers, OpenPixelmonTranslator.createTranslation(pixelmonName), false);
		matrices.scale(0.8f, 0.8f, 0.8f);
		matrices.translate(0, 0.1, 0);
		drawLabel(matrices, pixelmon, light, vertexConsumers, new LiteralText("Lv. " + pixelmon.getLevel()), false);
		if(pixelmon.isBoss()) {
			matrices.translate(0, 0.5, 0);
			drawLabel(matrices, pixelmon, light, vertexConsumers, new LiteralText("Boss"), true);
		}
		matrices.pop();
	}

	private void drawLabel(MatrixStack matrices, PixelmonEntity entity, int light, VertexConsumerProvider vertexConsumers, Text text, boolean important) {
		double distance = this.dispatcher.getSquaredDistanceToCamera(entity);
		if (!(distance > 4096.0D)) {
			float f = entity.getHeight() + 0.5F;
			matrices.push();
			matrices.translate(0.0D, f, 0.0D);
			matrices.multiply(this.dispatcher.getRotation());
			matrices.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrices.peek().getModel();
			TextRenderer textRenderer = this.getFontRenderer();
			float h = (float)(-textRenderer.getWidth(text) / 2);
			textRenderer.draw(text, h, 0, important ? 0xfffdff59 : 0xffffffff, false, matrix4f, vertexConsumers, false, 0, light);
			matrices.pop();
		}
	}
}
