package me.marty.openpixelmon.client.model.smd.animation;

import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;
import me.marty.openpixelmon.client.model.smd.Vertex;

import java.util.HashMap;

public class Bone {

    public int id;
    public String name;
    public int parent;

    public float x, y, z;
    public float rotX, rotY, rotZ;

    public HashMap<Vertex, Float> vertexWeights = new HashMap<>();

    public Bone(NodesBlock.Bone bone) {
        this.id = bone.id;
        this.name = bone.name;
        this.parent = bone.parent;
    }
}
