package me.marty.openpixelmon.client.model.studiomdl.loader;

import dev.thecodewarrior.binarysmd.studiomdl.*;
import me.marty.openpixelmon.client.model.studiomdl.Tri;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmdModel {
	public List<Tri> triangles;
	public String path;
	public final Info smdInfo;

	public SmdModel(String path, Info smdInfo) {
		this.path = path;
		this.smdInfo = smdInfo;
		if (smdInfo == null) {
			return;
		}
		for (SMDFileBlock block : smdInfo.body.blocks) {
			if (block instanceof TrianglesBlock) {
				TrianglesBlock vertBlock = (TrianglesBlock) block;
				triangles = parseTris(vertBlock.triangles);
			}
		}
	}

	public static void render(MatrixStack matrices, SmdModel context, Identifier modelTexture, VertexConsumer consumer) {
		TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
		textureManager.bindTexture(modelTexture);
		List<Tri> tris = context.triangles;
		if (tris == null) {
			throw new RuntimeException("Broken pixelmon model: missing triangles!");
		}
		for (Tri triangle : tris) {
			consumeVertex(matrices, consumer, triangle.v1);
			consumeVertex(matrices, consumer, triangle.v2);
			consumeVertex(matrices, consumer, triangle.v3);
			consumeVertex(matrices, consumer, triangle.v3);
		}
	}

	public static void consumeVertex(MatrixStack matrices, VertexConsumer consumer, Vertex vertex) {
		consumer.vertex(matrices.peek().getModel(), vertex.x, vertex.y, vertex.z)
				.color(255, 255, 255, 255)
				.texture(vertex.u, 1.0f - vertex.v)
				.overlay(0, 0)
				.light(0xAA, 0xAA)
				.normal(-vertex.nx, vertex.ny, vertex.nz)
				.next();
	}

	private List<Tri> parseTris(List<TrianglesBlock.Triangle> triangles) {
		List<Tri> tris = new ArrayList<>();
		for (TrianglesBlock.Triangle triangle : triangles) {
			Vertex v1 = new Vertex(triangle.v1);
			Vertex v2 = new Vertex(triangle.v2);
			Vertex v3 = new Vertex(triangle.v3);
			tris.add(new Tri(v1, v2, v3));
		}
		return tris;
	}

	public static class Info {
		public final SMDFile body;
		public final float scale;
		public final Map<String, AnimationData> animationMap;

		public Info(SMDFile body, float scale, Map<String, AnimationData> animationMap) {
			this.body = body;
			this.scale = scale;
			this.animationMap = animationMap;
		}
	}
}
