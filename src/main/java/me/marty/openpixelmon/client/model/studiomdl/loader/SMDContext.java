package me.marty.openpixelmon.client.model.studiomdl.loader;

import dev.thecodewarrior.binarysmd.studiomdl.*;
import me.marty.openpixelmon.client.model.studiomdl.Bone;
import me.marty.openpixelmon.client.model.studiomdl.Tri;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMDContext {
	public List<Tri> triangles;
	public String path;
	public final Info smdInfo;

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
				triangles = parseTris(vertBlock.triangles);
			}
			if(block instanceof NodesBlock) {
				NodesBlock nodesBlock = (NodesBlock) block;
			}
		}
	}

	private List<Bone> parseBones(NodesBlock nodesBlock) {
		List<Bone> bones = new ArrayList<>();
//		for (NodesBlock.Bone bone : nodesBlock.bones) {
//			boneMap.put(bone.name, bone.id);
//			bones.set(bone.id, new Bone(bone));
//		}
		return bones;
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

	public static class AnimationData {
		public List<NodesBlock.Bone> bones;
		public List<SkeletonBlock.Keyframe> keyframes;
	}
}
