package me.marty.openpixelmon.client.model.studiomdl;

import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Vertex {

    public byte[] renderBoneToBoneMap;
    public float[] boneWeights;
    public float x;
    public float y;
    public float z;
    public float u;
    public float v;
    public float nx;
    public float ny;
    public float nz;

    public Vertex(TrianglesBlock.Vertex vertex) {
        calculateBoneWeights(vertex);
        this.x = vertex.posX;
        this.y = vertex.posY;
        this.z = vertex.posZ;
        this.u = vertex.u;
        this.v = vertex.v;
        this.nx = vertex.normX;
        this.ny = vertex.normY;
        this.nz = vertex.normZ;
    }

    private void calculateBoneWeights(TrianglesBlock.Vertex vertex) {
        List<TrianglesBlock.@NotNull Link> links = vertex.links;
        this.renderBoneToBoneMap = new byte[4];
        float[] boneWeights = new float[4]; // This is as low as we can go without causing issues
        float sum = 0.0F;
        float[] weights = new float[links.size()];

        for (int i = 0; i < links.size(); ++i) {
            // Get the weights and add it to the sum of all weights
            weights[i] = links.get(i).weight;
            sum += weights[i];

            // get the bone & weight from i & average
            TrianglesBlock.Link link = links.get(i);
            float weight = weights[i] / sum;

            // Store information about what this bone will be in the vertex shader
            renderBoneToBoneMap[i] = (byte) link.bone;
            boneWeights[i] = weight;
        }
        this.boneWeights = boneWeights;
    }
}