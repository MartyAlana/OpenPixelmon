package me.marty.openpixelmon.client.render.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import me.marty.openpixelmon.client.model.smd.Tri;
import me.marty.openpixelmon.client.model.smd.Vertex;
import me.marty.openpixelmon.client.model.smd.animation.AnimationData;
import me.marty.openpixelmon.client.model.smd.animation.Keyframe;
import me.marty.openpixelmon.client.model.smd.loader.SmdModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.system.MemoryUtil;

import java.util.List;

/**
 * Represents a compiled model which can be rendered
 */
public class CompiledModel {

    private static final int BONE_STATE_SIZE = Float.BYTES * 6;
    private final StaticStorageBuffer keyframeBuffer;
    private final StaticStorageBuffer boneWeightsBuffer;
    private final StaticStorageBuffer boneRenderMapBuffer;
    private final VertexBuffer vertexBuffer;
    private final AnimationData animationData;
    public final SmdModel.Info smdInfo;

    public CompiledModel(VertexBuffer vertexBuffer, AnimationData animationData, SmdModel smdModel) {
        this.vertexBuffer = vertexBuffer;
        this.smdInfo = smdModel.smdInfo;
        this.animationData = animationData;

        int keyframeBufferSize = findAnimationSize(animationData);
        int keyframeBufferId = createStorageBuffer(keyframeBufferSize);
        long pointer = GL15C.nglMapBuffer(GL43C.GL_SHADER_STORAGE_BUFFER, GL15C.GL_WRITE_ONLY);
        uploadAnimationData(pointer, animationData);
        this.keyframeBuffer = new StaticStorageBuffer(keyframeBufferId, keyframeBufferSize, pointer);

        int boneWeightsSize = findBoneWeightSize(smdModel.tris);
        int boneWeightsBufferId = createStorageBuffer(boneWeightsSize);
        pointer = GL15C.nglMapBuffer(GL43C.GL_SHADER_STORAGE_BUFFER, GL15C.GL_WRITE_ONLY);
        uploadBoneWeights(pointer, smdModel);
        this.boneWeightsBuffer = new StaticStorageBuffer(boneWeightsBufferId, boneWeightsSize, pointer);

        int boneRenderMapSize = findBoneRenderMapSize(smdModel.tris);
        int boneRenderMapBufferId = createStorageBuffer(boneRenderMapSize);
        pointer = GL15C.nglMapBuffer(GL43C.GL_SHADER_STORAGE_BUFFER, GL15C.GL_WRITE_ONLY);
        uploadBoneRenderMaps(pointer, smdModel);
        this.boneRenderMapBuffer = new StaticStorageBuffer(boneRenderMapBufferId, boneRenderMapSize, pointer);
    }

    private int createStorageBuffer(int size) {
        int bufferId = GL15C.glGenBuffers();
        GL15C.glBindBuffer(GL43C.GL_SHADER_STORAGE_BUFFER, bufferId);
        GL15C.glBufferData(GL43C.GL_SHADER_STORAGE_BUFFER, size, GL15C.GL_STATIC_DRAW);
        return bufferId;
    }

    private int findAnimationSize(AnimationData animationData) {
        int totalSize = 0;
        for (Keyframe keyframe : animationData.keyframes) {
            totalSize += keyframe.states.size();
        }
        return totalSize * BONE_STATE_SIZE;
    }

    private int findBoneRenderMapSize(List<Tri> tris) {
        int totalSize = 0;
        for (Tri tri : tris) {
            totalSize += tri.v1.renderBoneToBoneMap.length;
            totalSize += tri.v2.renderBoneToBoneMap.length;
            totalSize += tri.v3.renderBoneToBoneMap.length;
        }

        return totalSize * Float.BYTES;
    }

    private int findBoneWeightSize(List<Tri> tris) {
        int totalSize = 0;
        for (Tri tri : tris) {
            totalSize += tri.v1.boneWeights.length;
            totalSize += tri.v2.boneWeights.length;
            totalSize += tri.v3.boneWeights.length;
        }

        return totalSize * Float.BYTES;
    }

    private void uploadBoneRenderMaps(long pointer, SmdModel smdModel) {
        for (Tri tri : smdModel.tris) {
            uploadBoneRenderMap(pointer, tri.v1);
        }
    }

    private void uploadBoneRenderMap(long pointer, Vertex vertex) {
        for (int boneMap : vertex.renderBoneToBoneMap) {
            MemoryUtil.memPutInt(pointer, boneMap);
        }
    }

    private void uploadBoneWeights(long pointer, SmdModel smdModel) {
        for (Tri tri : smdModel.tris) {
            uploadBoneWeight(pointer, tri.v1);
        }
    }

    private void uploadBoneWeight(long pointer, Vertex vertex) {
        for (float boneWeight : vertex.boneWeights) {
            MemoryUtil.memPutFloat(pointer, boneWeight);
        }
    }

    private void uploadAnimationData(long pointer, AnimationData animationData) {
        for (Keyframe keyframe : animationData.keyframes) {
            for (Keyframe.BoneState state : keyframe.states) {
                MemoryUtil.memPutFloat(pointer, state.posX);
                MemoryUtil.memPutFloat(pointer, state.posY);
                MemoryUtil.memPutFloat(pointer, state.posZ);
                MemoryUtil.memPutFloat(pointer, state.rotX);
                MemoryUtil.memPutFloat(pointer, state.rotX);
                MemoryUtil.memPutFloat(pointer, state.rotX);
            }
        }
    }

    public void render(MatrixStack matrices) {
        MinecraftClient.getInstance().getProfiler().push("render_pixelmon");
        Shader shader = OpenPixelmonClient.pixelmonSolidShader;
        assert shader != null;

        for (int id = 0; id < 12; ++id) {
            int textureId = RenderSystem.getShaderTexture(id);
            shader.addSampler("Sampler" + id, textureId);
        }

        if (shader.modelViewMat != null) {
            shader.modelViewMat.set(matrices.peek().getPositionMatrix());
        }

        if (shader.projectionMat != null) {
            shader.projectionMat.set(RenderSystem.getProjectionMatrix());
        }

        if (shader.field_36323 != null) { //IViewRotationMatrix
            shader.field_36323.method_39978(RenderSystem.getInverseViewRotationMatrix());
        }

        if (shader.colorModulator != null) {
            shader.colorModulator.set(RenderSystem.getShaderColor());
        }

        if (shader.fogStart != null) {
            shader.fogStart.set(RenderSystem.getShaderFogStart());
        }

        if (shader.fogEnd != null) {
            shader.fogEnd.set(RenderSystem.getShaderFogEnd());
        }

        if (shader.fogColor != null) {
            shader.fogColor.set(RenderSystem.getShaderFogColor());
        }

        if (shader.textureMat != null) {
            shader.textureMat.set(RenderSystem.getTextureMatrix());
        }

        if (shader.gameTime != null) {
            shader.gameTime.set(RenderSystem.getShaderGameTime());
        }

        if (shader.screenSize != null) {
            Window i = MinecraftClient.getInstance().getWindow();
            shader.screenSize.set((float) i.getFramebufferWidth(), (float) i.getFramebufferHeight());
        }

        if (shader.lineWidth != null && (vertexBuffer.drawMode == VertexFormat.DrawMode.LINES || vertexBuffer.drawMode == VertexFormat.DrawMode.LINE_STRIP)) {
            shader.lineWidth.set(RenderSystem.getShaderLineWidth());
        }

        GlUniform boneCount = shader.getUniform("boneCount");
        if (boneCount != null) {
            boneCount.set(animationData.bones.size());
        }

        RenderSystem.setupShaderLights(shader);
        vertexBuffer.bindVertexArray();
        vertexBuffer.bind();
        vertexBuffer.getElementFormat().startDrawing();
        GL30C.glBindBufferBase(GL43C.GL_SHADER_STORAGE_BUFFER, 1, keyframeBuffer.id());
        GL30C.glBindBufferBase(GL43C.GL_SHADER_STORAGE_BUFFER, 2, boneWeightsBuffer.id());
        GL30C.glBindBufferBase(GL43C.GL_SHADER_STORAGE_BUFFER, 3, boneRenderMapBuffer.id());
        shader.bind();
        GL11.glDrawElements(vertexBuffer.drawMode.mode, vertexBuffer.vertexCount, vertexBuffer.vertexFormat.count, MemoryUtil.NULL);
        shader.unbind();
        vertexBuffer.getElementFormat().endDrawing();
        MinecraftClient.getInstance().getProfiler().pop();
    }
}
