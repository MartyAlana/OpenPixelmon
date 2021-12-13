package me.marty.openpixelmon.client.model.smd.loader;

import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import me.marty.openpixelmon.client.model.smd.Tri;
import me.marty.openpixelmon.client.model.smd.Vertex;
import me.marty.openpixelmon.client.model.smd.animation.AnimationData;
import me.marty.openpixelmon.client.render.renderer.CompiledModel;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.render.VertexFormats.POSITION_TEXTURE_COLOR_NORMAL;

public class SmdModel {

    public List<Tri> tris = new ArrayList<>();
    public String path;
    public final Info smdInfo;
    private AnimationData idleAnimation;

    public SmdModel(String path, Info smdInfo) {
        this.path = path;
        this.smdInfo = smdInfo;
        if (smdInfo == null) {
            return;
        }
        this.idleAnimation = smdInfo.animationMap.get("idle.smdx");
        for (SMDFileBlock block : smdInfo.body.blocks) {
            if (block instanceof TrianglesBlock vertBlock) {
                parseTris(vertBlock.triangles);
            }
        }
    }

    public CompiledModel compile() {
        BufferBuilder builder = new BufferBuilder(tris.size() * POSITION_TEXTURE_COLOR_NORMAL.getVertexSize());
        builder.begin(VertexFormat.DrawMode.TRIANGLES, POSITION_TEXTURE_COLOR_NORMAL);
        for (Tri triangle : tris) {
            consumeVertex(builder, triangle.v1);
            consumeVertex(builder, triangle.v2);
            consumeVertex(builder, triangle.v3);
        }
        builder.end();
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.upload(builder);
        return new CompiledModel(vertexBuffer, idleAnimation, this);
    }

    public static void consumeVertex(BufferBuilder consumer, Vertex vertex) {
        consumer.vertex(vertex.x, vertex.y, vertex.z)
                .texture(vertex.u, 1.0f - vertex.v)
                .color(255, 255, 255, 255)
                .normal(vertex.nx, vertex.ny, vertex.nz)
                .next();
    }

    private void parseTris(List<TrianglesBlock.Triangle> triangles) {
        for (TrianglesBlock.Triangle triangle : triangles) {
            Vertex v1 = getOrDefaultVertex(new Vertex(triangle.v1));
            Vertex v2 = getOrDefaultVertex(new Vertex(triangle.v2));
            Vertex v3 = getOrDefaultVertex(new Vertex(triangle.v3));
            tris.add(new Tri(v1, v2, v3));
        }
    }

    /**
     * Used to lower rendering costs of rendering pixelmon
     *
     * @param vertex the vertex to default to and to compare
     * @return vertex
     */
    private Vertex getOrDefaultVertex(Vertex vertex) {
        for (Tri face : tris) {
            if (face.v1.equals(vertex)) {
                return face.v1;
            }
            if (face.v2.equals(vertex)) {
                return face.v2;
            }
            if (face.v3.equals(vertex)) {
                return face.v3;
            }
        }
        return vertex;
    }

    public record Info(SMDFile body, float scale,
                       Map<String, AnimationData> animationMap) {
    }
}
