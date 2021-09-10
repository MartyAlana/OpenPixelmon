package me.marty.openpixelmon.mixin.client.shader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.marty.openpixelmon.client.render.shader.ExtendedGlUniform;
import me.marty.openpixelmon.client.render.shader.PixelmonShaderExtensions;
import me.marty.openpixelmon.client.render.shader.ShaderExtensionInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderParseException;
import net.minecraft.client.render.Shader;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL20C;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(Shader.class)
public abstract class ShaderMixin {

    @Shadow
    protected abstract void addUniform(JsonElement json) throws ShaderParseException;

    @Shadow
    @Final
    private List<GlUniform> uniforms;
    @Shadow
    @Final
    static Logger LOGGER;
    @Shadow
    @Final
    private List<Integer> loadedUniformIds;
    @Shadow
    @Final
    private Map<String, GlUniform> loadedUniforms;
    @Shadow
    @Final
    private String name;
    @Shadow
    @Final
    private int programId;
    @Shadow
    @Final
    private List<String> samplerNames;
    @Shadow
    @Final
    private List<Integer> loadedSamplerIds;
    @Shadow
    @Final
    private Map<String, Object> samplers;
    private static final Map<String, ShaderExtensionInfo> EXTENSIONS =
            new ImmutableMap.Builder<String, ShaderExtensionInfo>()
                    .put("pixelmon_arrays", PixelmonShaderExtensions::doArrayExtension)
                    .build();

    @Inject(method = "addUniform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/GlUniform;<init>(Ljava/lang/String;IILnet/minecraft/client/gl/GlShader;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void handleExtensions(JsonElement json, CallbackInfo ci, JsonObject uniform, String name, int type, int rawCount, float[] count, int unknown) {
        try {
            JsonArray extensions = JsonHelper.getArray(uniform, "extensions");

            for (JsonElement jsonElement : extensions) {
                String extensionName = jsonElement.getAsString();
                ShaderExtensionInfo extension = EXTENSIONS.get(extensionName);
                uniforms.add(extension.createUniform(uniform, name, type, rawCount, count, unknown, (Shader) (Object) this));
            }
            ci.cancel();
        } catch (JsonParseException ignored) {
        }
    }

    /**
     * @author
     */
    @Overwrite
    private void loadReferences() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        IntList intList = new IntArrayList();

        int k;
        for (k = 0; k < this.samplerNames.size(); ++k) {
            String string = this.samplerNames.get(k);
            int j = GlUniform.getUniformLocation(this.programId, string);
            if (j == -1) {
                LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, string);
                this.samplers.remove(string);
                intList.add(k);
            } else {
                this.loadedSamplerIds.add(j);
            }
        }

        for (k = intList.size() - 1; k >= 0; --k) {
            int l = intList.getInt(k);
            this.samplerNames.remove(l);
        }

        for (GlUniform glUniform : this.uniforms) {
            String uniformName = glUniform.getName();
            int location = GlUniform.getUniformLocation(this.programId, uniformName);
            if (glUniform instanceof ExtendedGlUniform extendedGlUniform) {
                extendedGlUniform.loadReference(this.programId, uniformName, loadedUniformIds);
            } else {
                if (location == 0) {
                    LOGGER.debug("Uniform {} had a location of 0. This is most likely an array. If this isn't minecraft's, Please use the extension pixelmon_arrays in your shader.", uniformName);
                }
                if (location == -1) {
                    LOGGER.warn("Shader {} could not find uniform named {} in the specified shader program.", this.name, uniformName);
                } else {
                    this.loadedUniformIds.add(location);
                    glUniform.setLoc(location);
                }
            }
            this.loadedUniforms.put(uniformName, glUniform);
        }
    }
}
