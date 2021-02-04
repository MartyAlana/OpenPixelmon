package me.marty.openpixelmon.client.model.studiomdl;

import dev.thecodewarrior.binarysmd.studiomdl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMDContext {
	private List<Tri> tris;
	private String path;
	private Info smdInfo;
	private List<TrianglesBlock.Triangle> rawTris;

	protected SMDContext() {
	}

	public SMDContext(String path, Info smdInfo) {
		this.path = path;
		this.smdInfo = smdInfo;
		cacheVertices();
	}

	private void cacheVertices() {
		if (smdInfo == null) {
			return;
		}
		for (SMDFileBlock block : smdInfo.body.blocks) {
			if (block instanceof TrianglesBlock) {
				TrianglesBlock vertBlock = (TrianglesBlock) block;
				rawTris = vertBlock.triangles;
				tris = parseTris(vertBlock.triangles);
			}
		}
	}

	private List<Tri> parseTris(List<TrianglesBlock.Triangle> triangles) {
		List<Tri> tris = new ArrayList<>();
		for (TrianglesBlock.Triangle triangle : triangles) {
			Vertex v1 = parseVertex(triangle.v1);
			Vertex v2 = parseVertex(triangle.v1);
			Vertex v3 = parseVertex(triangle.v1);
			tris.add(new Tri(v1, v2, v3));
		}
		return tris;
	}

	private Vertex parseVertex(TrianglesBlock.Vertex v) {
		return new Vertex(v.posX, v.posY, v.posZ, v.u, v.v);
	}

//	public Info getInfo() {
//		return smdInfo;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public List<Tri> getTris() {
//		return this.tris;
//	}

	public List<TrianglesBlock.Triangle> getRawTris() {
		return this.rawTris;
	}

	public static class Tri {
		public final Vertex v1;
		public final Vertex v2;
		public final Vertex v3;

		public Tri(Vertex v1, Vertex v2, Vertex v3) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
		}
	}

	public static class Vertex {
		public final float x;
		public final float y;
		public final float z;
		public final float u;
		public final float v;

		public Vertex(float x, float y, float z, float u, float v) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.u = u;
			this.v = v;
		}
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

	public static class AnimationData {
		public List<NodesBlock.Bone> bones;
		public List<SkeletonBlock.Keyframe> keyframes;
	}
}
