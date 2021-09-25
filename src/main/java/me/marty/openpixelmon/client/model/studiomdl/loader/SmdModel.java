package me.marty.openpixelmon.client.model.studiomdl.loader;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.TrianglesBlock;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import me.marty.openpixelmon.client.model.studiomdl.Tri;
import me.marty.openpixelmon.client.model.studiomdl.Vertex;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import me.marty.openpixelmon.client.render.AnimatedVertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmdModel {

    public List<Tri> tris = new ArrayList<>();
    public String path;
    public final Info smdInfo;
    private AnimationData idleAnimation;
    private AnimatedVertexBuffer vertexBuffer;

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
        BufferBuilder builder = new BufferBuilder(tris.size() * OpenPixelmonClient.PIXELMON_VERTEX_FORMAT.getVertexSize());
        builder.begin(VertexFormat.DrawMode.TRIANGLES, OpenPixelmonClient.PIXELMON_VERTEX_FORMAT);
        for (Tri triangle : tris) {
            consumeVertex(builder, triangle.v1);
            consumeVertex(builder, triangle.v2);
            consumeVertex(builder, triangle.v3);
        }
        builder.end();
        AnimatedVertexBuffer vertexBuffer = new AnimatedVertexBuffer();
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
        RenderSystem.enableDepthTest();
        context.vertexBuffer.setShaderWithAnimationInfo(matrices.peek().getModel(), RenderSystem.getProjectionMatrix(), OpenPixelmonClient.pixelmonSolidShader, context.idleAnimation);
        RenderSystem.disableDepthTest();
        matrices.pop();
    }

    public static void consumeVertex(BufferBuilder consumer, Vertex vertex) {
        consumer.vertex(vertex.x, vertex.y, vertex.z)
                .texture(vertex.u, 1.0f - vertex.v)
                .color(255, 255, 255, 255)
                .normal(vertex.nx, vertex.ny, vertex.nz);

        // Spacing byte
        consumer.putByte(0, vertex.renderBoneToBoneMap[0]);
        consumer.nextElement();

        // Upload the bone map
        consumer.putByte(0, vertex.renderBoneToBoneMap[0]);
        consumer.nextElement();
        consumer.putByte(0, vertex.renderBoneToBoneMap[1]);
        consumer.nextElement();
        consumer.putByte(0, vertex.renderBoneToBoneMap[2]);
        consumer.nextElement();
        consumer.putByte(0, vertex.renderBoneToBoneMap[3]);
        consumer.nextElement();

        // Upload the bone weights
        consumer.putFloat(0, vertex.boneWeights[0]);
        consumer.nextElement();
        consumer.putFloat(0, vertex.boneWeights[1]);
        consumer.nextElement();
        consumer.putFloat(0, vertex.boneWeights[2]);
        consumer.nextElement();
        consumer.putFloat(0, vertex.boneWeights[3]);
        consumer.nextElement();

        consumer.next();
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
