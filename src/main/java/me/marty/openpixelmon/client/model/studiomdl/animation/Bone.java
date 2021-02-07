package me.marty.openpixelmon.client.model.studiomdl.animation;

import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
