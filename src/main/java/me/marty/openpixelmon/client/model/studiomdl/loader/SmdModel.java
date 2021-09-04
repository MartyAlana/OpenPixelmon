package me.marty.openpixelmon.client.model.studiomdl.loader;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import me.marty.openpixelmon.client.model.studiomdl.Tri;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmdModel {

    public List<Tri> tris = new ArrayList<>();
    public String path;
    public final Info smdInfo;
    private AnimationData idleAnimation;
    private VertexBuffer vertexBuffer;

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

    public void cacheVertexConsumer() {
        BufferBuilder builder = new BufferBuilder(tris.size() * VertexFormats.POSITION_TEXTURE_COLOR_NORMAL.getVertexSize());
        builder.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
        for (Tri triangle : tris) {
            consumeVertex(builder, triangle.v1);
            consumeVertex(builder, triangle.v2);
            consumeVertex(builder, triangle.v3);
        }
        builder.end();
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.upload(builder);
        this.vertexBuffer = vertexBuffer;
    }

    public static void render(MatrixStack matrices, SmdModel context, Identifier modelTexture, VertexConsumerProvider consumers, int light) {
        if (context.vertexBuffer == null) {
            context.cacheVertexConsumer();
        }
        matrices.push();
        matrices.scale(context.smdInfo.scale, context.smdInfo.scale, context.smdInfo.scale);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
        RenderSystem.setShaderTexture(0, modelTexture);
        context.vertexBuffer.setShader(matrices.peek().getModel(), RenderSystem.getProjectionMatrix(), OpenPixelmonClient.pixelmonSolidShader);
        matrices.pop();
    }

    public static void consumeVertex(VertexConsumer consumer, Vertex vertex) {
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
            calculateBoneWeights(triangle.v1.links, v1);
            calculateBoneWeights(triangle.v2.links, v2);
            calculateBoneWeights(triangle.v3.links, v3);
            tris.add(new Tri(v1, v2, v3));
        }
    }

    private void calculateBoneWeights(List<TrianglesBlock.Link> values, Vertex vertex) {
        int links = values.size();
        float[] weights = new float[links];
        float sum = 0.0F;
        for (int i = 0; i < links; ++i) {
            weights[i] = values.get(i).weight;
            sum += weights[i];

            TrianglesBlock.Link link = values.get(i);
            float weight = weights[i] / sum;
            this.idleAnimation.bones.get(link.bone).vertices.put(vertex, weight);
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
