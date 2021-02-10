package me.marty.openpixelmon.client.model.studiomdl.animation;

import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class Bone {

	public int id;
	public String name;
	public int parent;

	public float x, y, z;
	public float rotX, rotY, rotZ;

	public HashMap<Vertex, Float> vertices = new HashMap<>();

	public Bone(NodesBlock.Bone bone) {
		this.id = bone.id;
		this.name = bone.name;
		this.parent = bone.parent;
	}
}
