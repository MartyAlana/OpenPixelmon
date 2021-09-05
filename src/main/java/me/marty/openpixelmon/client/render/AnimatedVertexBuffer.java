package me.marty.openpixelmon.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import me.marty.openpixelmon.client.model.studiomdl.animation.Keyframe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.Objects;

/**
 * Modified version of VertexBuffer, allowing us to specify custom data into uniforms such as bones, weights, etc.
 */
public class AnimatedVertexBuffer extends VertexBuffer {

    private static final Matrix4f EMPTY = new Matrix4f();
    private static final int MAX_SAMPLER_COUNT = 12;

    public void setShaderWithAnimationInfo(Matrix4f viewMatrix, Matrix4f projectionMatrix, Shader shader, AnimationData animationData) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.internalRender(viewMatrix.copy(), projectionMatrix.copy(), shader, animationData));
        } else {
            this.internalRender(viewMatrix, projectionMatrix, shader, animationData);
        }
    }

    public void internalRender(Matrix4f modelViewMatrix, Matrix4f projectionMatrix, Shader shader, AnimationData animationData) {
        if (this.vertexCount != 0) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);

            Matrix4f[] boneTransformations = new Matrix4f[64];
            for (Keyframe.BoneState state : animationData.keyframes.get(0).states) {
                Matrix4f transformation = new Matrix4f();
                transformation.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(state.rotX));
                transformation.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(state.rotY));
                transformation.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(state.rotZ));
                transformation.multiply(Matrix4f.translate(state.posX, state.posY, state.posZ));
                boneTransformations[state.bone] = transformation;
            }

            BufferRenderer.unbindAll();
            for (int i = 0; i < MAX_SAMPLER_COUNT; ++i) {
                int texture = RenderSystem.getShaderTexture(i);
                shader.addSampler("Sampler" + i, texture);
            }

            if (shader.projectionMat != null) {
                shader.projectionMat.set(projectionMatrix);
            }

            if (shader.modelViewMat != null) {
                shader.modelViewMat.set(modelViewMatrix);
            }

            GlUniform boneTransformationsUniform = shader.getUniform("BoneTransformations");
            if (boneTransformationsUniform != null) {
                for (Matrix4f boneTransformation : boneTransformations) {
                    boneTransformationsUniform.set(Objects.requireNonNullElse(boneTransformation, EMPTY));
                }
            } else {
                throw new RuntimeException("Couldn't find BoneTransformations Uniform!");
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
                Window window = MinecraftClient.getInstance().getWindow();
                shader.screenSize.set((float) window.getFramebufferWidth(), (float) window.getFramebufferHeight());
            }

            if (shader.lineWidth != null && (this.drawMode == VertexFormat.DrawMode.LINES || this.drawMode == VertexFormat.DrawMode.LINE_STRIP)) {
                shader.lineWidth.set(RenderSystem.getShaderLineWidth());
            }

            RenderSystem.setupShaderLights(shader);
            this.bindVertexArray();
            this.bind();
            this.getElementFormat().startDrawing();
            shader.bind();
            RenderSystem.drawElements(this.drawMode.mode, this.vertexCount, this.vertexFormat.count);
            shader.unbind();
            this.getElementFormat().endDrawing();
            unbind();
            unbindVertexArray();
        }
    }
}
