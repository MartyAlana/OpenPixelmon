package me.marty.openpixelmon.client.render.entity;

import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.model.studiomdl.LazySMDContext;
import me.marty.openpixelmon.client.model.studiomdl.SMDContext;
import me.marty.openpixelmon.client.model.studiomdl.SMDReader;
import me.marty.openpixelmon.compatibility.ModelCompatibility;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationsPixelmonRenderer extends EntityRenderer<PixelmonEntity> {

	public Map<String, Pair<Identifier, LazySMDContext>> rendererInfoMap = new Object2ObjectOpenHashMap<>();

	public GenerationsPixelmonRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		OpenPixelmon.LOGGER.info("Loading Pixelmon Generations Models");
		for (String pokemon : loopAssetDir("assets/pixelmon/models/pokemon")) {
			if(ModelCompatibility.INSTANCE.getPixelmonModel("models/pokemon/" + pokemon + "/" + pokemon + ".pqc") == null){
				OpenPixelmon.LOGGER.warn(pokemon + " could not be loaded!");
			} else {
				rendererInfoMap.put(pokemon, new Pair<>(new Identifier("pixelmon", "textures/pokemon/" + pokemon + ".png"), SMDReader.createLazyModel("pokemon/" + pokemon)));
			}
		}
	}

	@Override
	public void render(PixelmonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
		matrices.scale(0.02f, 0.02f, 0.02f);
		Pair<Identifier, LazySMDContext> pair = rendererInfoMap.get(entity.getPixelmonId().getPath());
		LazySMDContext modelFile = pair.getRight();
		Identifier modelTexture = pair.getLeft();
		renderInstance(matrices, modelFile.getContext(), modelTexture, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(modelTexture)), light);
		matrices.pop();
	}

	private void renderInstance(MatrixStack matrices, SMDContext context, Identifier modelTexture, VertexConsumer consumer, int light) {
		TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
		textureManager.bindTexture(modelTexture);
		List<TrianglesBlock.Triangle> tris = context.getRawTris();
		if(tris == null){
			throw new RuntimeException("BROKEN POKEMON MODEL: missing triangles");
		}
		for (TrianglesBlock.Triangle triangle : tris) {
			consumeVertex(matrices, consumer, triangle.v1, light);
			consumeVertex(matrices, consumer, triangle.v2, light);
			consumeVertex(matrices, consumer, triangle.v3, light);
			consumeVertex(matrices, consumer, triangle.v3, light);
		}
	}

	private void consumeVertex(MatrixStack matrices, VertexConsumer consumer, TrianglesBlock.Vertex vertex, int light) {
		consumer.vertex(matrices.peek().getModel(), vertex.posX, vertex.posY, vertex.posZ);
		consumer.color(160, 255, 255, 255);
		consumer.texture(vertex.u, 1.0f - vertex.v);
		consumer.overlay(0, 0);
		consumer.light(light / 100000, light / 100000);
		consumer.normal(vertex.normX, -vertex.normY, -vertex.normZ);
		consumer.next();
	}

	@Override
	public Identifier getTexture(PixelmonEntity entity) {
		return rendererInfoMap.get(entity.getPixelmonId().getPath()).getLeft();
	}

	public static List<String> loopAssetDir(String rootDir) {
		Path root = ModelCompatibility.INSTANCE.root.getPath("/" + rootDir);
		try {
			return Files.list(root).map(path -> root.relativize(path).toString()).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
