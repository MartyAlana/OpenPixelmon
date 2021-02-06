package me.marty.openpixelmon.client.model.studiomdl.animation;

import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;

public class Bone {

	public int id;
	public String name;
	public int parent;

	public Bone(NodesBlock.Bone bone) {
		this.id = bone.id;
		this.name = bone.name;
		this.parent = bone.parent;
	}
}
