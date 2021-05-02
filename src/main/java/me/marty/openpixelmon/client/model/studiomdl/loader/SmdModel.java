package me.marty.openpixelmon.client.model.studiomdl.loader;

import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import me.marty.openpixelmon.client.model.studiomdl.Tri;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmdModel {

    public List<Tri> faces = new ArrayList<>();
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
            if (block instanceof TrianglesBlock) {
                TrianglesBlock vertBlock = (TrianglesBlock) block;
                parseTris(vertBlock.triangles);
            }
        }
    }

    public static void render(MatrixStack matrices, SmdModel context, Identifier modelTexture, VertexConsumerProvider consumers, int light) {
        matrices.push();
        matrices.scale(context.smdInfo.scale, context.smdInfo.scale, context.smdInfo.scale);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getEntitySolid(modelTexture));
        List<Tri> tris = context.faces;
        if (tris == null) {
            throw new RuntimeException("Broken pixelmon model: missing triangles!");
        }
        for (Tri triangle : tris) {
            consumeVertex(matrices, consumer, triangle.v1, light);
            consumeVertex(matrices, consumer, triangle.v2, light);
            consumeVertex(matrices, consumer, triangle.v3, light);
            consumeVertex(matrices, consumer, triangle.v3, light);
        }
        matrices.pop();
    }

    public static void consumeVertex(MatrixStack matrices, VertexConsumer consumer, Vertex vertex, int light) {
        consumer.vertex(matrices.peek().getModel(), vertex.x, vertex.y, vertex.z)
                .color(255, 255, 255, 255)
                .texture(vertex.u, 1.0f - vertex.v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(0xAA, 0xAA)
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
            faces.add(new Tri(v1, v2, v3));
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
        for (Tri face : faces) {
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
