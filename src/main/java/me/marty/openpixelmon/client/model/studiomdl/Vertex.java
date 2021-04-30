package me.marty.openpixelmon.client.model.studiomdl;

import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;

public class Vertex {

    public float x;
    public float y;
    public float z;
    public float u;
    public float v;
    public float nx;
    public float ny;
    public float nz;

    public Vertex(TrianglesBlock.Vertex vertex) {
        this.x = vertex.posX;
        this.y = vertex.posY;
        this.z = vertex.posZ;
        this.u = vertex.u;
        this.v = vertex.v;
        this.nx = vertex.normX;
        this.ny = vertex.normY;
        this.nz = vertex.normZ;
    }
}